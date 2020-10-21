package com.funnelback.xforwardedforedit;

public class PluginUtils {

    /**
     * The name of the plugin.
     * 
     * Should match the artifactId in the pom.xml file.
     *
     */
    public static final String PLUGIN_NAME = "x-forwarded-for-edit";
    
    /**
     * A prefix that should be prefixed to configuration keys used by this plugin.
     * 
     * For example if this plugin configured the number of foos to use, the key 
     * might look like:
     * <code>
     * String foosKey = XForwardedForEditPluginUtils.KEY_PREFIX + "foos-count";
     * </code>
     * The resulting key would be:
     * <code>
     * plugin.x-forwarded-for-edit.config.foos-count
     * </code>
     */
    public static final String KEY_PREFIX = "plugin." + PLUGIN_NAME + ".config." ;
}
