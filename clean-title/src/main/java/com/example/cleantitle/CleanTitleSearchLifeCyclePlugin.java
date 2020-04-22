package com.example.cleantitle;

import com.funnelback.plugin.SearchLifeCyclePlugin;
import com.funnelback.publicui.search.model.transaction.SearchTransaction;
import com.funnelback.publicui.search.model.padre.Result;

/**
 * This example explains how we can use the `SearchLifeCycle` plugin to modify the title of a document
 * during the `postDataFetch` phase. The title of the documents will be modified after gathering finished.
 */
public class CleanTitleSearchLifeCyclePlugin implements SearchLifeCyclePlugin {

    @Override
    public void postDatafetch(SearchTransaction transaction) {
        String regex = transaction.getQuestion().getCurrentProfileConfig().get(PluginUtils.KEY_PREFIX + "regex-pattern");
        if (transaction.hasResponse()) {
            if (transaction.getResponse().hasResultPacket()) {
                transaction.getResponse().getResultPacket().getResults().forEach(r -> cleanTitle(r, regex));
            }
        }
    }

    protected Result cleanTitle(Result result, String regex) {
        String title = result.getTitle();
        title = title.replaceAll(regex, "");
        result.setTitle(title);
        return result;
    }

}
