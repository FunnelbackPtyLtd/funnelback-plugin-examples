package com.example;

import org.junit.Assert;
import org.junit.Test;
import com.funnelback.publicui.search.model.padre.Result;

public class CleanTitleSearchLifeCyclePluginTest {

    @Test
    public void postDatafetchTest(){
        CleanTitleSearchLifeCyclePlugin underTest = new CleanTitleSearchLifeCyclePlugin();
        String regex = "(- Funnelback Documentation - Version )?\\d+\\.\\d+\\.\\d+(-SNAPSHOT)?";
        Result result = new Result();
        result.setTitle("Funnelback documentation - Funnelback Documentation - Version 15.25.2007");
        Result cleanResult = underTest.cleanTitle(result, regex);

        Assert.assertEquals("Title of the document should be \"Funnelback documentation\"","Funnelback documentation ", cleanResult.getTitle());
    }
}
