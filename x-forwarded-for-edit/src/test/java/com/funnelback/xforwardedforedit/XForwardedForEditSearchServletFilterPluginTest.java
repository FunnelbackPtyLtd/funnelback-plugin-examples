package com.funnelback.xforwardedforedit;

import com.funnelback.plugin.servlet.filter.SearchServletFilterHookContext;
import com.funnelback.publicui.search.model.collection.ServiceConfig;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import static com.funnelback.xforwardedforedit.HttpServletRequestXForwardedForWrapper.XFF_HEADER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XForwardedForEditSearchServletFilterPluginTest {

    @Test
    public void testKeepFirstHeader() {
        assertEquals("1.1.1.1", makeTestRequest("1.1.1.1,2.2.2.2", "KeepFirst").getHeader(XFF_HEADER));
        assertEquals("1.1.1.1", makeTestRequest("1.1.1.1", "KeepFirst").getHeader(XFF_HEADER));
        assertEquals("", makeTestRequest("", "KeepFirst").getHeader(XFF_HEADER));
        assertEquals(null, makeTestRequest(null, "KeepFirst").getHeader(XFF_HEADER));
    }

    @Test
    public void testRemoveFirstHeader() {
        assertEquals("2.2.2.2,3.3.3.3", makeTestRequest("1.1.1.1,2.2.2.2,3.3.3.3", "RemoveFirst").getHeader(XFF_HEADER));
        assertEquals("2.2.2.2", makeTestRequest("1.1.1.1,2.2.2.2", "RemoveFirst").getHeader(XFF_HEADER));
        assertEquals(
                "Does not remove x-forwarded-for header if there is only one value",
                "1.1.1.1", makeTestRequest("1.1.1.1", "RemoveFirst").getHeader(XFF_HEADER));
        assertEquals("", makeTestRequest("", "RemoveFirst").getHeader(XFF_HEADER));
        assertEquals(null, makeTestRequest(null, "RemoveFirst").getHeader(XFF_HEADER));
    }

    @Test
    public void testRemoveLastHeader() {
        assertEquals("1.1.1.1,2.2.2.2", makeTestRequest("1.1.1.1,2.2.2.2,3.3.3.3", "RemoveLast").getHeader(XFF_HEADER));
        assertEquals("1.1.1.1", makeTestRequest("1.1.1.1,2.2.2.2", "RemoveLast").getHeader(XFF_HEADER));
        assertEquals(
                "Does not remove x-forwarded-for header if there is only one value",
                "1.1.1.1", makeTestRequest("1.1.1.1", "RemoveLast").getHeader(XFF_HEADER));
        assertEquals("", makeTestRequest("", "RemoveLast").getHeader(XFF_HEADER));
        assertEquals(null, makeTestRequest(null, "RemoveLast").getHeader(XFF_HEADER));
    }

    @Test
    public void testCaseInsensitveOnHeader() {
        assertEquals(
                "1.1.1.1,2.2.2.2",
                makeTestRequest("1.1.1.1,2.2.2.2,3.3.3.3", "RemoveLast").getHeader(XFF_HEADER.toLowerCase()));
    }

    @Test
    public void testCaseInsensitveOnSetting() {
        assertEquals(
                "1.1.1.1,2.2.2.2",
                makeTestRequest("1.1.1.1,2.2.2.2,3.3.3.3", "RemoveLast".toUpperCase()) .getHeader(XFF_HEADER));
    }

    @Test
    public void testNoMode_NoEffect() {
        checkNoEffectfor("RemoveFirstHeader");
        checkNoEffectfor(null);
        checkNoEffectfor("not a setting");
    }

    public void checkNoEffectfor(String mode) {
        assertEquals("1.1.1.1,2.2.2.2,3.3.3.3",
                makeTestRequest("1.1.1.1,2.2.2.2,3.3.3.3", mode).getHeader(XFF_HEADER));
    }

    HttpServletRequest makeTestRequest(String headerKey, String headerValue, String mode) {
        HttpServletRequest mockServerHttpRequest = mock(HttpServletRequest.class);
        when(mockServerHttpRequest.getHeader(matches("(?i)" + headerKey))).thenReturn(headerValue);
        // Update this to call the method(s) that should be tested.
        return (HttpServletRequest) new XForwardedForEditSearchServletFilterPlugin()
                        .preFilterRequest(
                                new SearchServletFilterHookContext() {
                                    @Override
                                    public ServiceConfig getCurrentProfileConfig() {
                                        return new InMemoryConfig(
                                                mode == null ? null
                                                        : Map.of(PluginUtils.KEY_PREFIX + "mode", mode));
                                    }
                                },
                                mockServerHttpRequest);
    }

    HttpServletRequest makeTestRequest(String xForwardedForHeader, String mode) {
        return makeTestRequest(XFF_HEADER, xForwardedForHeader, mode);
    }


    private static final class InMemoryConfig implements ServiceConfig {

        Map<String, String> config;

        InMemoryConfig(Map<String, String> config) {
            this.config = config;
        }

        @Override
        public String get(String key) {
            return config == null ? null : config.get(key);
        }

        @Override
        public Set<String> getRawKeys() {
            return config == null ? null : config.keySet();
        }
    }

}
