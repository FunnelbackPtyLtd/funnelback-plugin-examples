package com.example;

import com.funnelback.plugin.gatherer.PluginGatherContext;
import com.funnelback.plugin.gatherer.PluginGatherer;
import com.funnelback.plugin.gatherer.PluginStore;
import com.google.common.collect.ArrayListMultimap;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class CustomGathererPluginGatherer implements PluginGatherer {

    @Override
    public void gather(PluginGatherContext pluginGatherContext, PluginStore store) throws Exception {
        int docsToMake = Integer.parseInt(pluginGatherContext.getConfigSetting("plugin.number-of-documents-to-make"));
        String documentUrl = pluginGatherContext.getConfigSetting("plugin.document-url");

        for(int i = 0; i< docsToMake; i++) {
            ArrayListMultimap<String, String> metadata = ArrayListMultimap.create();
            metadata.put("Content-Type", "text/html; charset=UTF-8");
            metadata.put("total-docs", String.valueOf(docsToMake));
            metadata.put("this-doc-number", String.valueOf(i));
            store.store(
                new URI(documentUrl + i),
                "Hello world!".getBytes(StandardCharsets.UTF_8),
                metadata
            );
        }
    }
}
