package com.funnelback.xforwardedforedit;

import com.funnelback.plugin.servlet.filter.SearchServletFilterHook;
import com.funnelback.plugin.servlet.filter.SearchServletFilterHookContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.funnelback.xforwardedforedit.HttpServletRequestXForwardedForWrapper.getMode;

public class XForwardedForEditSearchServletFilterPlugin implements SearchServletFilterHook {

    private static final Logger log = LogManager.getLogger(XForwardedForEditSearchServletFilterPlugin.class);

    private static final String modeKey = PluginUtils.KEY_PREFIX + "mode";

    @Override
    public ServletRequest preFilterRequest(SearchServletFilterHookContext context, ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String modeConfig = context.getCurrentProfileConfig().get(modeKey);
            Optional<HttpServletRequestXForwardedForWrapper.Mode> mode = getMode(modeConfig);
            if (mode.isPresent()) {
                log.debug("wrapping HttpServletRequest");
                return new HttpServletRequestXForwardedForWrapper(
                        mode.get(), (HttpServletRequest) request);
            }
            log.warn("Could not parse mode from config key '{}', expected one of '{}' but got '{}'",
                    modeKey,
                    Arrays.stream(HttpServletRequestXForwardedForWrapper.Mode.values())
                            .map(HttpServletRequestXForwardedForWrapper.Mode::name)
                            .collect(Collectors.joining(",")),
                    modeConfig);
        }

        return request;
    }
}
