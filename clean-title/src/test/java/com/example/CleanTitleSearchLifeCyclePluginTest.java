package com.example;

import org.junit.Assert;
import org.junit.Test;
import com.funnelback.publicui.search.model.padre.Result;

public class CleanTitleSearchLifeCyclePluginTest {

    @Test
    public void testSearchLifeCyclePlugin(){
        CleanTitleSearchLifeCyclePlugin underTest = new CleanTitleSearchLifeCyclePlugin();

        Result result = new Result();
        result.setTitle("Funnelback documentation - Funnelback Documentation - Version 15.25.2007");
        Result cleanResult = underTest.cleanTitle(result);

        Assert.assertEquals("Funnelback documentation", cleanResult.getTitle());
    }
}
