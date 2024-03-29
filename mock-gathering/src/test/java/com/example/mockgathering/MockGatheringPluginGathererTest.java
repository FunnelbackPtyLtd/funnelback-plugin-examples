package com.example.mockgathering;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.funnelback.plugin.gatherer.FileScanner;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

import com.example.mockgathering.remotefetch.RemoteFetcher;
import com.funnelback.plugin.gatherer.mock.MockPluginGatherContext;
import com.funnelback.plugin.gatherer.mock.MockPluginStore;

public class MockGatheringPluginGathererTest {

        
    /**
     * This is our mock remote fetcher, this is what we will use to stop making
     * HTTP requests in our test.
     * 
     * This could have been in its own file like a normal class.
     *
     */
    public static class MockRemoteFetcher implements RemoteFetcher {
        
        /**
         * When a URI is requested we will look for it in this map.
         */
        public Map<URI, byte[]> uriToResult = new HashMap<>();
        

        @Override
        public byte[] get(URI url, Map<String, String> headers) {
            // In our mock fetcher we ensure that the security-token is set.
            Assert.assertEquals("The security token was not set.", "password2", headers.get("security-token"));
            
            if(uriToResult.containsKey(url)) {
                return uriToResult.get(url);
            }
            
            throw new RuntimeException("404 could not find: " + url);
        }
        
    }

    /**
     * This is our mock scanner, this is what we will use to scan files in our test.
     *
     * This could have been in its own file like a normal class.
     *
     */
    public static class MockFileScanner implements FileScanner {

        /**
         * When a URI is requested we will look for it in this map.
         */
        public Map<byte[], Boolean> uriToScanResult = new HashMap<>();

        @Override
        public boolean checkbytes(byte[] bytes) {
            if(uriToScanResult.containsKey(bytes)) {
                return uriToScanResult.get(bytes);
            }
            return true;
        }
    }
    
    /**
     * This is going to show how we mock out making real web requests.
     * 
     */
    @Test 
    public void testCustomGatherPlugin() throws Exception {
        MockPluginGatherContext mockContext = new MockPluginGatherContext();
        MockPluginStore mockStore = new MockPluginStore();
        FileScanner mockFileScanner = new MockFileScanner();
        
        // Configure the required config settings for the test:
        mockContext.setConfigSetting("plugin.mock-gathering.security-token", "password2");
        mockContext.setConfigSetting("plugin.mock-gathering.start-urls", "http://example.com/1");
        
        // Here we also create our mock RemoteFetcher, this way we don't make HTTP requests in our test.
        MockRemoteFetcher mockRemoteFetcher = new MockRemoteFetcher();
        
        // Lets add some data to our remote fetcher:
        mockRemoteFetcher.uriToResult.put(URI.create("http://example.com/1"), ("{\n" +
            "   \"content\":\"hello1\",\n" + 
            "   \"next\":\"http://example.com/2\"\n" + 
            "}").getBytes(StandardCharsets.UTF_8));
        
        mockRemoteFetcher.uriToResult.put(URI.create("http://example.com/2"), ("{\n" +
            "   \"content\":\"hello2\",\n" + 
            "   \"next\": null\n" + 
            "}").getBytes(StandardCharsets.UTF_8));
        
        MockGatheringPluginGatherer underTest = new MockGatheringPluginGatherer();
        
        // It is important to actually tell our gather which RemoteFetcher we want it to use.
        // use our mock RemoteFetcher.
        underTest.remoteFetcher = mockRemoteFetcher;
        
        // Now run it, it will use our movk remote fetcher so we can test it without needing a real server.
        underTest.gather(mockContext, mockStore, mockFileScanner);
        
        Assert.assertEquals("Check how many documents were stored.", 2, mockStore.getStored().size());
    }

    
}
