package com.example;

import com.funnelback.plugin.SearchLifeCyclePlugin;
import com.funnelback.publicui.search.model.transaction.SearchTransaction;
import com.funnelback.publicui.search.model.padre.Result;

public class CleanTitleSearchLifeCyclePlugin implements SearchLifeCyclePlugin {

    @Override
    public void postDatafetch(SearchTransaction transaction) {
        if (transaction.hasResponse()) {
            if (transaction.getResponse().hasResultPacket()) {
                transaction.getResponse().getResultPacket().getResults().forEach(this::cleanTitle);
            }
        }
    }

    private Result cleanTitle(Result result) {
        String title = result.getTitle();
        title = title.replaceAll(" - Funnelback Documentation", "");
        title = title.replaceAll(" (- Version )?\\d+\\.\\d+\\.\\d+(-SNAPSHOT)?", "");
        title = title.replaceAll(" \\(Modern UI for Funnelback.*$", "");
        result.setTitle(title);
        return result;
    }

}
