package com.example.customgatherer;

import com.funnelback.plugin.gatherer.mock.MockPluginGatherContext;
import com.funnelback.plugin.gatherer.mock.MockPluginStore;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CustomGathererPluginGathererTest {

    @Test
    public void testCustomGatherPlugin() throws Exception {

        MockPluginGatherContext mockContext = new MockPluginGatherContext();
        MockPluginStore mockStore = new MockPluginStore();

        CustomGathererPluginGatherer underTest = new CustomGathererPluginGatherer();

        // Set the collection.cfg settings
        mockContext.setConfigSetting("plugin.custom-gatherer.number-of-documents-to-make", "2");
        mockContext.setConfigSetting("plugin.custom-gatherer.document-url", "http://www.example.com/");

        // As the plugin gatherer is likely to interact with an external system you may need
        // to mock those interactions out. Until that is done you can still use this test to
        // try out your gatherer locally.
        underTest.gather(mockContext, mockStore);

        // This list holds the result of what the plugin class.
        List<MockPluginStore.MockPluginStoreResult> resultList = mockStore.getStored();

        // Now check each of the results ensuring that the correct values were set.
        Assert.assertTrue("Check how many documents were stored.", mockStore.getStored().size() >= 0);
        Assert.assertEquals("Document URL should be taken from the collection.cfg","http://www.example.com/" + 1, resultList.get(1).getUri().toString());
        Assert.assertEquals("Document should contain some content","Hello world!", new String(resultList.get(1).getContent()));
        Assert.assertEquals("Content-Type should be set properly","text/html; charset=UTF-8", resultList.get(1).getMetadata().get("Content-Type").get(0));
        Assert.assertEquals("total-docs metadata should be set to 2","2", resultList.get(1).getMetadata().get("total-docs").get(0));
        Assert.assertEquals("this-doc-number metadata should be set to 1",String.valueOf(1), resultList.get(1).getMetadata().get("this-doc-number").get(0));
    }
}
