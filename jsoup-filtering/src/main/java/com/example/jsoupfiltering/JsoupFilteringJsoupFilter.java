package com.example.jsoupfiltering;

import com.funnelback.common.filter.jsoup.FilterContext;
import com.funnelback.common.filter.jsoup.IJSoupFilter;

/**
 * Demonstrates using a plugin to count number of elements which contain the specified class name.
 * Enabled this by adding it to the Jsoup filter chain as well as enabling the plugin.
 * Example shows reading from configuration and setting metadata.
 */
public class JsoupFilteringJsoupFilter implements IJSoupFilter {

    @Override
    public void processDocument(FilterContext filterContext) {
        // Get the configured class to count from collection.cfg
        String classToCount = filterContext.getSetup().getConfigSetting(PluginUtils.KEY_PREFIX + "class-to-count");
        
        // Find the number of elements with that class.
        int elementCount = filterContext.getDocument().getElementsByClass(classToCount).size();
        
        // Add the count to metadata which can be mapped to a metadata class to be used in searches. 
        filterContext.getAdditionalMetadata().put("my-class-count", Integer.toString(elementCount));
    }

}
