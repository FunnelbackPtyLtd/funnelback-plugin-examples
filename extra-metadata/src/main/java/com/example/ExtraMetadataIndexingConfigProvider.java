package com.example;

import com.funnelback.plugin.index.IndexConfigProviderContext;
import com.funnelback.plugin.index.IndexingConfigProvider;
import com.funnelback.plugin.index.consumers.MetadataMappingConsumer;
import com.funnelback.plugin.index.model.metadatamapping.MetadataSourceType;
import com.funnelback.plugin.index.model.metadatamapping.MetadataType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtraMetadataIndexingConfigProvider implements IndexingConfigProvider {

    private static Logger log = LogManager.getLogger(ExtraMetadataIndexingConfigProvider.class);

    @Override
    public void metadataMappings(IndexConfigProviderContext context, MetadataMappingConsumer consumer) {
        log.warn("Executing ExtraMetaData for " + context.getCollectionName());
        String valueToSet = context.getConfigSetting("plugin.example.extra-metadata");
        consumer.map("author", MetadataType.TEXT_NOT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, "author");
        consumer.map(valueToSet, MetadataType.TEXT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, valueToSet);
    }
}
