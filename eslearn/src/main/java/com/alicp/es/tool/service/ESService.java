package com.alicp.es.tool.service;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/15
 * Time: 下午2:59
 * CopyRight: taobao
 * Descrption:
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


    public void alert() {


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
