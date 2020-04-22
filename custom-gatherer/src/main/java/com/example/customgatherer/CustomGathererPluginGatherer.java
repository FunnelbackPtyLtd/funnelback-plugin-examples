package com.example.customgatherer;

import com.funnelback.plugin.gatherer.PluginGatherContext;
import com.funnelback.plugin.gatherer.PluginGatherer;
import com.funnelback.plugin.gatherer.PluginStore;
import com.google.common.collect.ArrayListMultimap;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Demonstrates using a plugin to create documents in gathering phase.
 * This plugin reads from `collection.cfg`, creates documents and adds some metadata to those documents.
 */
public class CustomGathererPluginGatherer implements PluginGatherer {

    @Override
    public void gather(PluginGatherContext pluginGatherContext, PluginStore store) throws Exception {

        // Read from collection.cfg
        int docsToMake = Integer.parseInt(pluginGatherContext.getConfigSetting("plugin.custom-gatherer.number-of-documents-to-make"));
        String documentUrl = pluginGatherContext.getConfigSetting("plugin.custom-gatherer.document-url");

        for(int i = 0; i< docsToMake; i++) {
            ArrayListMultimap<String, String> metadata = ArrayListMultimap.create();
            // Add metadata
            metadata.put("Content-Type", "text/html; charset=UTF-8");
            metadata.put("total-docs", String.valueOf(docsToMake));
            metadata.put("this-doc-number", String.valueOf(i));
            // Store the documents
            store.store(
                new URI(documentUrl + i),
                "Hello world!".getBytes(StandardCharsets.UTF_8),
                metadata
            );
        }
    }
}
