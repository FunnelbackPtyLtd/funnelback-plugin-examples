package com.example.additionalmetadata;

import com.funnelback.plugin.index.IndexConfigProviderContext;
import com.funnelback.plugin.index.IndexingConfigProvider;
import com.funnelback.plugin.index.consumers.MetadataMappingConsumer;
import com.funnelback.plugin.index.model.metadatamapping.MetadataSourceType;
import com.funnelback.plugin.index.model.metadatamapping.MetadataType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demonstrates supplying additional metadata mappings.
 * 
 * Also shows reading from collection.cfg
 */
public class AdditionalMetadataIndexingConfigProvider implements IndexingConfigProvider {

    private static Logger log = LogManager.getLogger(AdditionalMetadataIndexingConfigProvider.class);

    /**
     * Map values in {@code <meta name="author" >} to metadata class authors.
     * 
     * Also maps values in a HTML metadata class specified in collection.cfg to
     * the metadata class authors.
     */
    @Override
    public void metadataMappings(IndexConfigProviderContext context, MetadataMappingConsumer consumer) {
        log.debug("Adding additional metadata mappings for '{}'.", context.getCollectionName());
        
        // Always map the value of HTML <meta name="author" > as well as HTTP header "author" to metadata class authors.
        consumer.map("authors", MetadataType.TEXT_NOT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, "author");
        
        // Also map 
        String htmlMetadataName = context.getConfigSetting("plugin.additional-metadata.metadata");
        consumer.map("authors", MetadataType.TEXT_INDEXED_AS_DOCUMENT_CONTENT, MetadataSourceType.HTML_OR_HTTP_HEADERS, htmlMetadataName);
    }
}
