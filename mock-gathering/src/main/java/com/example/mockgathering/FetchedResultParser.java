package com.example.mockgathering;

import java.util.List;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funnelback.plugin.gatherer.PluginStore;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class FetchedResultParser {

    public List<URI> parseFetchedContent(URI uri, String fetchedContent, PluginStore store) {
        // Use jackson to parse the response.
        // we will pretend the response looks like this:
        // {
        // content: "the content to index",
        // next: "https://the.next/url/to/fetch"
        // }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;
        try {
            json = mapper.readTree(fetchedContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        // Get the content from the JSON response and store it.
        String content  = json.at("/content").asText();
        ListMultimap<String, String> map = ArrayListMultimap.create();
        map.put("Content-Type", "text/html; charset=utf-8");
        store.store(uri, content.getBytes(StandardCharsets.UTF_8), map);
        
        // Now get any further URLs if any.
        if(json.at("/next").isMissingNode() || json.get("next").isNull() || json.at("/next").asText().isBlank()) {
            return List.of();
        }
        
        return List.of(URI.create(json.at("/next").asText()));
    }
}
