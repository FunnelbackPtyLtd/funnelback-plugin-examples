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
        AdditionalMetadataIndexingConfigProvider underTest = new AdditionalMetadataIndexingConfigProvider();
        MockIndexConfigProviderContext mockContext = new MockIndexConfigProviderContext();
        MockMetadataMappingConsumer mockConsumer = new MockMetadataMappingConsumer();

        mockContext.setCollectionName("collection");
        mockContext.setConfigSetting("plugin.additional-metadata.metadata", "FUNkgLiveUrl");

        underTest.metadataMappings(mockContext, mockConsumer);

        List<MockMetadataMappingConsumer.MockMetadataMappingInvocation> invocations = mockConsumer.getInvocations();

        Assert.assertEquals(2, invocations.size());
        Assert.assertEquals("author", invocations.get(0).getMetadataClass());
        Assert.assertEquals("author", invocations.get(0).getLocator());
        Assert.assertEquals(MetadataSourceType.HTML_OR_HTTP_HEADERS, invocations.get(0).getSourceType());
        Assert.assertEquals(MetadataType.TEXT_NOT_INDEXED_AS_DOCUMENT_CONTENT, invocations.get(0).getType());

        Assert.assertEquals("FUNkgLiveUrl", invocations.get(1).getMetadataClass());
        Assert.assertEquals("FUNkgLiveUrl", invocations.get(1).getLocator());
        Assert.assertEquals(MetadataSourceType.HTML_OR_HTTP_HEADERS, invocations.get(1).getSourceType());
        Assert.assertEquals(MetadataType.TEXT_INDEXED_AS_DOCUMENT_CONTENT, invocations.get(1).getType());
    }
}
