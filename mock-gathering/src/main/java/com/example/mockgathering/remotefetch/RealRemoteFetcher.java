package com.example.mockgathering.remotefetch;

import java.util.Map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * This is the real fetcher which is going to make real web requests.
 *
 */
public class RealRemoteFetcher implements RemoteFetcher {

    @Override
    public byte[] get(URI url, Map<String, String> headers) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url.toASCIIString()).openConnection();
            try {
                connection.setConnectTimeout(10_000);
                connection.setReadTimeout(10_1000);
                
                // Add all the headers
                headers.forEach(connection::setRequestProperty);
                
                connection.setRequestMethod("GET");
                
                connection.setUseCaches(false);
                
                int responseCode = connection.getResponseCode();
                
                try (InputStream is = connection.getInputStream()){            
                    return getBytesFromInputSteam(is);
        
                } catch (Exception e) {
                    InputStream is = connection.getErrorStream();
                    // Attempt to deserialise the stacktrace if there's one
                    String errorResponse = "";
                    if(is != null) {
                        
                        try(InputStream eis = connection.getErrorStream()){
                            errorResponse =  new String(getBytesFromInputSteam(eis), StandardCharsets.UTF_8);
                        }
                    }
                    throw new RuntimeException("Error for: '" + url.toASCIIString() + "' response code was: '" + responseCode
                        + "' " + "Error message was: '" + errorResponse + "'");
                }
            } finally {
                connection.disconnect();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error for: '" + url.toASCIIString() + "'", e);
        }
    }
        
    
    public static byte[] getBytesFromInputSteam(InputStream is) throws IOException{
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int i;
            while((i = is.read()) != -1 ){
                bos.write((byte) i); 
            }
            return bos.toByteArray();
        }
    }
        

}
