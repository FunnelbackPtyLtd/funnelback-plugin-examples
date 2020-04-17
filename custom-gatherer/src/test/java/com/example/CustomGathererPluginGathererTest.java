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
        mockPluginGatherContext.setConfigSetting("plugin.number-of-documents-to-make", "10");
        mockPluginGatherContext.setConfigSetting("plugin.document-url", "http://www.example.com/");

        CustomGathererPluginGatherer customGathererPluginGatherer = new CustomGathererPluginGatherer();
        customGathererPluginGatherer.gather(mockPluginGatherContext, mockPluginStore);

        List<MockPluginStore.MockPluginStoreResult> resultList = mockPluginStore.getStored();

        for (int i = 0; i < 10 ; i++) {
            Assert.assertEquals("http://www.example.com/" + i, resultList.get(i).getUri().toString());
            Assert.assertEquals("Hello world!", new String(resultList.get(i).getContent()));
            Assert.assertEquals("text/html; charset=UTF-8", resultList.get(i).getMetadata().get("Content-Type").get(0));
            Assert.assertEquals("10", resultList.get(i).getMetadata().get("total-docs").get(0));
            Assert.assertEquals( String.valueOf(i), resultList.get(i).getMetadata().get("this-doc-number").get(0));
        }
    }
}
