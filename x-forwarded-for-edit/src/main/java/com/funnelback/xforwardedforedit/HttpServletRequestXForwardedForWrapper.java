package com.funnelback.xforwardedforedit;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class to manipulate the X-Forwarded-For header of an HTTP request
 *
 * Allows removal of the first or last value of the X-Forwarded-For
 * or removal of all values but the first
 */
class HttpServletRequestXForwardedForWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LogManager.getLogger(HttpServletRequestXForwardedForWrapper.class);

    static final String XFF_HEADER = "X-Forwarded-For";

    public static Optional<Mode> getMode(String modeString) {
        for (Mode mode : Mode.values()) {
            if (mode.name().equalsIgnoreCase(modeString)) {
                return Optional.of(mode);
            }
        }
        return Optional.empty();
    }

    /**
     * Possible modes of operation
     */
    public enum Mode {
        /** Remove first value of the X-Forwarded-For */
        RemoveFirst,
        /** Remove last value of the X-Forwarded-For */
        RemoveLast,
        /** Keep only the first value of the X-Forwarded-For */
        KeepFirst,
    }


    /** Mode of operation */
    final Mode mode;

    HttpServletRequestXForwardedForWrapper(Mode mode, HttpServletRequest request) {
        super(request);
        this.mode = mode;
    }

    /**
     * Check if the requested header is X-Forwarded-For, and if it is
     * alter the value according to the mode
     *
     * @param name Name of the header to retrieve
     * @return Header value, possibly altered
     */
    @Override
    public String getHeader(String name) {

        if (name.toLowerCase().equals(XFF_HEADER.toLowerCase())) {
            String oldHeader = super.getHeader(name);
            String updatedHeader = "";

            if (oldHeader != null && oldHeader.contains(",")) {
                List<String> xffEntries = List.of(oldHeader.split(","));
                try {
                    switch (mode) {
                        case RemoveFirst:
                            updatedHeader = xffEntries.subList(1, xffEntries.size())
                                    .stream().collect(Collectors.joining(","));
                            break;
                        case RemoveLast:
                            updatedHeader = xffEntries.subList(0, xffEntries.size() - 1)
                                    .stream().collect(Collectors.joining(","));
                            break;
                        case KeepFirst:
                            updatedHeader = xffEntries.get(0);
                            break;
                    }
                    log.debug("Changed '{}' value from '{}' to '{}'", XFF_HEADER, oldHeader, updatedHeader);
                    return updatedHeader;
                } catch (Exception e) {
                    log.error("Error while altering ${XFF_HEADER} value '{}'", oldHeader, e);
                }
            }
        }

        return super.getHeader(name);
    }
}
