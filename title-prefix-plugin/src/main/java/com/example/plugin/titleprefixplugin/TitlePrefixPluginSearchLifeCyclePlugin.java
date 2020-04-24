package com.example.plugin.titleprefixplugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.funnelback.plugin.SearchLifeCyclePlugin;
import com.funnelback.publicui.search.model.transaction.SearchTransaction;

public class TitlePrefixPluginSearchLifeCyclePlugin implements SearchLifeCyclePlugin {

    private static final Logger log = LogManager.getLogger(TitlePrefixPluginSearchLifeCyclePlugin.class); // A

    @Override public void postProcess(SearchTransaction transaction) {
        String prefixToMatch = transaction.getQuestion().getCurrentProfileConfig().get(PluginUtils.KEY_PREFIX + "pattern"); // B
        String givenReplaceWith = transaction.getQuestion().getCurrentProfileConfig().get(PluginUtils.KEY_PREFIX + "replaceWith"); // C

        if (prefixToMatch == null || prefixToMatch.isBlank()) {  // D
            // There is no prefix configured, we can't do anything. Lets log a warning and abort.
            log.warn("Could not find a pattern specified at " + PluginUtils.KEY_PREFIX +  "pattern. The TitlePrefixPlugin will do nothing."); // E
            return;
        }
        // We'll mark replaceWith as an optional key, if someone doesnt supply it, then we'll just remove the pattern configured.
        String replaceWithToUse = givenReplaceWith != null ? givenReplaceWith: ""; // F

        transaction.getResponse().getResultPacket().getResults().stream().filter(result -> { // G
            return result.getTitle().startsWith(prefixToMatch); // H
        }).forEach(matchingResult -> { // I
            String modifiedTitle = matchingResult.getTitle().replace(prefixToMatch, replaceWithToUse); // J
            matchingResult.setTitle(modifiedTitle); // K
        });
    }
}
