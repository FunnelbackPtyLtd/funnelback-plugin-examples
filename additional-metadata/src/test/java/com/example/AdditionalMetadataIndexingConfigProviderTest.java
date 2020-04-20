package com.example;

import com.funnelback.plugin.index.consumers.mock.MockMetadataMappingConsumer;
import com.funnelback.plugin.index.mock.MockIndexConfigProviderContext;
import com.funnelback.plugin.index.model.metadatamapping.MetadataSourceType;
import com.funnelback.plugin.index.model.metadatamapping.MetadataType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AdditionalMetadataIndexingConfigProviderTest {

    @Test
    public void testMetadataMappings(){
        MockIndexConfigProviderContext mockContext = new MockIndexConfigProviderContext();
        MockMetadataMappingConsumer mockConsumer = new MockMetadataMappingConsumer();
        AdditionalMetadataIndexingConfigProvider underTest = new AdditionalMetadataIndexingConfigProvider();
        
        // Set collection.cfg config options.
        mockContext.setConfigSetting("plugin.additional-metadata.metadata", "publishers");

        // Now run the method on our class we implemented.
        underTest.metadataMappings(mockContext, mockConsumer);

        // This list holds the result of what the plugin class.
        List<MockMetadataMappingConsumer.MockMetadataMappingInvocation> invocations = mockConsumer.getInvocations();

        Assert.assertEquals("Expected to see the plugin provide two metadata mappings.", 2, invocations.size());
        
        // Now check each of the invocations ensuring that the correct values were set.
        Assert.assertEquals("authors", invocations.get(0).getMetadataClass());
        Assert.assertEquals("author", invocations.get(0).getLocator());
        Assert.assertEquals(MetadataSourceType.HTML_OR_HTTP_HEADERS, invocations.get(0).getSourceType());
        Assert.assertEquals(MetadataType.TEXT_NOT_INDEXED_AS_DOCUMENT_CONTENT, invocations.get(0).getType());

        Assert.assertEquals("authors", invocations.get(1).getMetadataClass());
        Assert.assertEquals("In this case we should have read 'publishers' from config settings.", "publishers", invocations.get(1).getLocator());
        Assert.assertEquals(MetadataSourceType.HTML_OR_HTTP_HEADERS, invocations.get(1).getSourceType());
        Assert.assertEquals(MetadataType.TEXT_INDEXED_AS_DOCUMENT_CONTENT, invocations.get(1).getType());
    }
}
