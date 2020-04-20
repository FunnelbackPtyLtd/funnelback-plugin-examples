package com.example;

import com.funnelback.plugin.gatherer.mock.MockPluginGatherContext;
import com.funnelback.plugin.gatherer.mock.MockPluginStore;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CustomGathererPluginGathererTest {

    @Test
    public void testCustomGatherPlugin() throws Exception {

        MockPluginGatherContext mockPluginGatherContext = new MockPluginGatherContext();
        MockPluginStore mockPluginStore = new MockPluginStore();
        mockPluginGatherContext.setConfigSetting("plugin.custom-gatherer.number-of-documents-to-make", "2");
        mockPluginGatherContext.setConfigSetting("plugin.custom-gatherer.document-url", "http://www.example.com/");

        CustomGathererPluginGatherer customGathererPluginGatherer = new CustomGathererPluginGatherer();
        customGathererPluginGatherer.gather(mockPluginGatherContext, mockPluginStore);

        List<MockPluginStore.MockPluginStoreResult> resultList = mockPluginStore.getStored();

        Assert.assertEquals("There should be only 2 documents",2, mockPluginStore.getStored().size());
        Assert.assertEquals("Document URL should be taken from the collection.cfg","http://www.example.com/" + 1, resultList.get(1).getUri().toString());
        Assert.assertEquals("Document should contain some content","Hello world!", new String(resultList.get(1).getContent()));
        Assert.assertEquals("Content-Type should be set properly","text/html; charset=UTF-8", resultList.get(1).getMetadata().get("Content-Type").get(0));
        Assert.assertEquals("total-docs metadata should be set to 2","2", resultList.get(1).getMetadata().get("total-docs").get(0));
        Assert.assertEquals("this-doc-number metadata should be set to 1",String.valueOf(1), resultList.get(1).getMetadata().get("this-doc-number").get(0));

    }
}
