package com.example.jsoupfiltering;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import org.hamcrest.core.IsCollectionContaining;

import com.funnelback.common.filter.jsoup.MockJsoupFilterContext;

public class JsoupFilteringJsoupFilterTest {

    @Test
    public void countingClassesTest() {

        // Make a mock context with the HTML document we want to test our filter on.
        MockJsoupFilterContext filterContext = new MockJsoupFilterContext(
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<div class=\"cities\">\n" +
            "    <h2>London</h2>\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"cities historic\">\n" +
            "  <h2>Paris</h2>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>");

        JsoupFilteringJsoupFilter underTest = new JsoupFilteringJsoupFilter();

        // Simulate configuring the plugin, these options would go in the collection configuration (collection.cfg)
        filterContext.getSetup().setConfigSetting(PluginUtils.KEY_PREFIX + "class-to-count", "cities");
        
        // Run our filter.
        underTest.processDocument(filterContext);
        
        // Check the results.
        Collection<String> counts = filterContext.getAdditionalMetadata().get("my-class-count");
        
        Assert.assertThat("Check we correctly counted the number of 'cities' classes", counts, IsCollectionContaining.hasItem("2"));
        Assert.assertEquals("Check we only stored one value for this metadata.", 1, counts.size());
    }
}
