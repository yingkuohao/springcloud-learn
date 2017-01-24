package com.alicp.logapp.log.endpoint.metric;

import org.springframework.boot.actuate.metrics.export.Exporter;
import org.springframework.boot.actuate.metrics.export.MetricCopyExporter;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.export.TriggerProperties;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:58
 * CopyRight: taobao
 * Descrption:
 */

public class MetricExporters implements SchedulingConfigurer, Closeable {

    private MetricReader reader;

    private Map<String, GaugeWriter> writers = new HashMap<String, GaugeWriter>();

    private final MetricExportProperties properties;

    private final Map<String, Exporter> exporters = new HashMap<String, Exporter>();

    private final Set<String> closeables = new HashSet<String>();

    public MetricExporters(MetricExportProperties properties) {
        this.properties = properties;
    }

    public void setReader(MetricReader reader) {
        this.reader = reader;
    }

    public void setWriters(Map<String, GaugeWriter> writers) {
        this.writers.putAll(writers);
    }

    public void setExporters(Map<String, Exporter> exporters) {
        this.exporters.putAll(exporters);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (Map.Entry<String, Exporter> entry : this.exporters.entrySet()) {
            String name = entry.getKey();
            Exporter exporter = entry.getValue();
            TriggerProperties trigger = this.properties.findTrigger(name);
            if (trigger != null) {
                ExportRunner runner = new ExportRunner(exporter);
                IntervalTask task = new IntervalTask(runner, trigger.getDelayMillis(),
                        trigger.getDelayMillis());
                taskRegistrar.addFixedDelayTask(task);
            }
        }
        for (Map.Entry<String, GaugeWriter> entry : this.writers.entrySet()) {
            String name = entry.getKey();
            GaugeWriter writer = entry.getValue();
            TriggerProperties trigger = this.properties.findTrigger(name);
            if (trigger != null) {
                MetricCopyExporter exporter = getExporter(writer, trigger);
                this.exporters.put(name, exporter);
                this.closeables.add(name);
                ExportRunner runner = new ExportRunner(exporter);
                IntervalTask task = new IntervalTask(runner, trigger.getDelayMillis(),
                        trigger.getDelayMillis());
                taskRegistrar.addFixedDelayTask(task);
            }
        }
    }

    private MetricCopyExporter getExporter(GaugeWriter writer,
            TriggerProperties trigger) {
        MetricCopyExporter exporter = new MetricCopyExporter(this.reader, writer);
        exporter.setIncludes(trigger.getIncludes());
        exporter.setExcludes(trigger.getExcludes());
        exporter.setSendLatest(trigger.isSendLatest());
        return exporter;
    }

    public Map<String, Exporter> getExporters() {
        return this.exporters;
    }

    @Override
    public void close() throws IOException {
        for (String name : this.closeables) {
            Exporter exporter = this.exporters.get(name);
            if (exporter instanceof Closeable) {
                ((Closeable) exporter).close();
            }
        }
    }

    private static class ExportRunner implements Runnable {

        private final Exporter exporter;

        ExportRunner(Exporter exporter) {
            this.exporter = exporter;
        }

        @Override
        public void run() {
            this.exporter.export();
        }

    }

}
