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
            Assert.assertEquals(resultList.get(i).getUri().toString(), "http://www.example.com/" + i);
            Assert.assertEquals(new String(resultList.get(i).getContent()), "Hello world!");
            Assert.assertEquals(resultList.get(i).getMetadata().get("Content-Type").get(0), "text/html; charset=UTF-8");
            Assert.assertEquals(resultList.get(i).getMetadata().get("total-docs").get(0), "10");
            Assert.assertEquals(resultList.get(i).getMetadata().get("this-doc-number").get(0), String.valueOf(i));
        }
    }
}
