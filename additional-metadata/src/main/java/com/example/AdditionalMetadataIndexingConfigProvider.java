package com.example;

import com.funnelback.plugin.index.IndexConfigProviderContext;
import com.funnelback.plugin.index.IndexingConfigProvider;
import com.funnelback.plugin.index.consumers.MetadataMappingConsumer;
import com.funnelback.plugin.index.model.metadatamapping.MetadataSourceType;
import com.funnelback.plugin.index.model.metadatamapping.MetadataType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This example explains how we can use the `Indexing` plugin to add some additional metadata
 */
public class AdditionalMetadataIndexingConfigProvider implements IndexingConfigProvider {

    private static Logger log = LogManager.getLogger(AdditionalMetadataIndexingConfigProvider.class);

    @Override
    public void metadataMappings(IndexConfigProviderContext context, MetadataMappingConsumer consumer) {
        log.debug("Executing ExtraMetaData for " + context.getCollectionName());
        String valueToSet = context.getConfigSetting("plugin.additional-metadata.metadata");
        consumer.map("author", MetadataType.TEXT_NOT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, "author");
        consumer.map(valueToSet, MetadataType.TEXT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, valueToSet);
    }
}
