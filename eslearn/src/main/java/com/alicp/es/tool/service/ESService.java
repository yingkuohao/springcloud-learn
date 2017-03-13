package com.alicp.es.tool.service;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/15
 * Time: 下午2:59
 * CopyRight: taobao
 * Descrption:    http://blog.csdn.net/ty4315/article/details/52434296
 */
@Component
public class ESService {
    private static Logger log = LoggerFactory.getLogger(ESService.class);

    @Autowired
    ESConfig esConfig;

    private TransportClient client;
    String host = "127.0.0.1";
    int port = 9300;

    @PostConstruct
    private TransportClient getTransportClient() throws UnknownHostException {
        // establish the client
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", esConfig.getClusterName()).put("client.transport.sniff", true).build();
        //创建client

        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfig.getHost()), esConfig.getPort()));
        return client;
    }

    public Map<String, Object> testSearch(BaseQO baseQO) {
//        curl -XGET localhost:9200/logstash-zipkin-2017.02.15/bizlog/AVpAHwLn1JRGJPk92IPq
        try {
            GetResponse response = client.prepareGet(baseQO.getIndex(), baseQO.getType(), baseQO.getId()).execute().actionGet();
            //输出结果
            if (response.isExists()) {
                return response.getSourceAsMap();
            }
        } catch (Exception e) {
            log.error("getSearch error", e);
        }
        return null;
    }


    public void alertBoolQuery(BaseQO baseQO, Map<String, Object> queryRules, AlertHandler alertHandler) {
        SearchHits hits = null;
        SearchRequestBuilder responsebuilder = client.prepareSearch(baseQO.getIndex()).setTypes(baseQO.getType()).setSearchType(SearchType.QUERY_AND_FETCH);
        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (!org.springframework.util.CollectionUtils.isEmpty(queryRules)) {
                for (Map.Entry<String, Object> entry : queryRules.entrySet()) {
//                    boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()));
                    boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
//                    TermQueryBuilder termQueryBuilder = new TermQueryBuilder(entry.getKey(), entry.getValue());
//                    MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(entry.getKey(), entry.getValue()).operator(Operator.AND);
//                    responsebuilder.setQuery(matchQueryBuilder);
//                    QueryBuilders.termQuery(entry.getKey(), entry.getValue());
                }
            }
            //日期,过滤5分钟之前到现在的时间区间
            DateTime now = DateTime.now();
            DateTime minusBefore = now.minusMinutes(3);
            RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("@timestamp").from(minusBefore).to(now);
            long begin = Instant.now().getEpochSecond();
            boolQueryBuilder.must(rangeQueryBuilder);
            //采用scroll模式来提高效率,setScroll 1分钟来保留上下文
            SearchResponse myresponse = responsebuilder.setQuery(boolQueryBuilder).setSize(baseQO.getSize()).setScroll(TimeValue.timeValueMinutes(2)).execute().actionGet();

            for (SearchHit searchHitFields : myresponse.getHits().getHits()) {
                alertHandler.execute(searchHitFields);
            }

            if (myresponse != null) {

                long count = myresponse.getHits().getTotalHits();
                log.info("----startTime:{},endTime:{},hits-count:{},cur-count:{}", minusBefore, now, count, myresponse.getHits().getHits().length);
                System.out.println("scroll 模式启动！");
                //通过scrollId分页遍历
                for (int i = 0, sum = 0; sum < count; i++) {
                    myresponse = client.prepareSearchScroll(myresponse.getScrollId())
                            .setScroll(TimeValue.timeValueMinutes(2))
                            .execute().actionGet();
                    SearchHit[] searchHits = myresponse.getHits().getHits();
                    long curCount = searchHits.length;
                    if (curCount == 0) {
                        break;
                    }
                    log.info("--curCount-{}", curCount);
                    sum += myresponse.getHits().hits().length;
                    System.out.println("总量" + count + " 已经查到" + sum);
                    for (SearchHit searchHitFields : searchHits) {
                        alertHandler.execute(searchHitFields);
                    }
                }
                System.out.println("耗时: " + (Instant.now().getEpochSecond() - begin));
            }
        } catch (Exception e) {
            log.error("querey es error,baseQo={},error={}", baseQO.toString(), e);
        }
    }

    public SearchHits alertBoolQuery(BaseQO baseQO) {
        SearchHits hits = null;
        try {
            SearchRequestBuilder responsebuilder = client.prepareSearch(baseQO.getIndex()).setTypes(baseQO.getType());
            responsebuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("_index", baseQO.getIndex())).
                    must(QueryBuilders.matchPhraseQuery("metricset.module", "system")));//yingkhtodo:desc: test

            SearchResponse myresponse = responsebuilder.execute().actionGet();
            hits = myresponse.getHits();
        } catch (Exception e) {
            log.error("querey es error,baseQo={}", baseQO.toString());
        }
        return hits;
    }

    public void alert(BaseQO baseQO) {
        //查询1分钟内的数据
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("_index", "metricbeat-2017.02.20");
        QueryBuilder queryBuilder2 = QueryBuilders.termQuery("_type", "metricsets");
        QueryBuilder queryBuilder3 = QueryBuilders.termQuery("metricset.module", "system");


        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("_index", "metricbeat-2017.02.20")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("metricset.module", "system")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            response.getHits().getHits()[0].getSourceAsString();
            System.out.println("response=" + response.toString());
        }
    }


    //关闭client
    public void close() {
        client.close();
    }

    private void reIndex() throws UnknownHostException {

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
                QueryBuilders.matchAllQuery());

        SearchResponse searchResponse = client.prepareSearch("liu")
                .setTypes("seqs").setSearchType(SearchType.QUERY_AND_FETCH)
                .setScroll(new TimeValue(60000)).setQuery(queryBuilder)
                .setSize(100).execute().actionGet();

        //scroll to get the data
        while (true) {

            searchResponse = client
                    .prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(600000)).execute().actionGet();


            for (SearchHit hit : searchResponse.getHits().getHits()) {
                // copy the data to the new index

                client.prepareIndex("my_index_v1", "seqs", hit.getId())
                        .setSource(hit.getSourceAsString()).execute().actionGet();
            }

            // when there is no data,break the loop
            if (searchResponse.getHits().getHits().length == 0) {
                break;
            }

        }
    }


}
