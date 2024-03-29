package com.example.mockgathering;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import java.net.URI;

import com.funnelback.plugin.gatherer.FileScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.mockgathering.remotefetch.RealRemoteFetcher;
import com.example.mockgathering.remotefetch.RemoteFetcher;
import com.funnelback.plugin.gatherer.PluginGatherContext;
import com.funnelback.plugin.gatherer.PluginGatherer;
import com.funnelback.plugin.gatherer.PluginStore;

/**
 * The gather here will use the object in field "remoteFetcher" for actually
 * fetching documents. The test for this class will show how it is possible to
 * change the implementation of "remoteFetcher" such that it uses our own dummy
 * mock class which does not make real HTTP requests.
 *
 */
public class MockGatheringPluginGatherer implements PluginGatherer {

    private static final Logger log = LogManager.getLogger(MockGatheringPluginGatherer.class);
    
    // By default we use the real fetcher, under test will we change this.
    public RemoteFetcher remoteFetcher = new RealRemoteFetcher();
    
    @Override
    public void gather(PluginGatherContext pluginGatherContext, PluginStore store) throws Exception {
        // Files gathered via URL and therefore need scanning and therefore this method is not implemented
    }

    @Override
    public void gather(PluginGatherContext pluginGatherContext, PluginStore store, FileScanner scanner) throws Exception {
        
        // Get the security-token from config, this will be supplied in each request.
        Map<String, String> headers = new HashMap<>();
        headers.put("security-token", pluginGatherContext.getConfigSetting(PluginUtils.KEY_PREFIX + "security-token"));
        
        Stack<URI> urlsToFetch = new Stack<>();
        
        // Get the start URLs which is a space separated list.
        Arrays.asList(pluginGatherContext.getConfigSetting(PluginUtils.KEY_PREFIX+"start-urls").split(" "))
            .stream()
            .filter(s -> !s.isBlank()) // remove blank/empty strings.
            .map(URI::create) // convert all of the strings to URLs
            .forEach(urlsToFetch::add); // add all the urls to the urlsToFetch array list.
        
        while(!urlsToFetch.isEmpty()) {
            URI url = urlsToFetch.pop();
            byte[] fetchedContent = remoteFetcher.get(url, headers);
            // Scan document for viruses
            if (scanner.checkbytes(fetchedContent)) {
                // Store current URI and check for more
                List<URI> moreUrlsToFetch = new FetchedResultParser().parseFetchedContent(url, new String(fetchedContent, StandardCharsets.UTF_8), store);
                urlsToFetch.addAll(moreUrlsToFetch);
            } else {
                throw new RuntimeException("File " + url + " has failed virus scanning");
            }

        }
        
        
        log.debug("Gathering documents");
    }
    
    
}
