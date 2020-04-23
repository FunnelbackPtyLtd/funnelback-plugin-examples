package com.example.mockgathering.remotefetch;

import java.util.Map;

import java.net.URI;

public interface RemoteFetcher {

    /**
     * Returns the content of the given url if successful, otherwise an exception is thrown. 
     * 
     * @param url the url to fetch
     * @param headers headers to also send in the request.
     * @return the body content of the request if all goes well.
     */
    public String get(URI url, Map<String, String> headers);
}
