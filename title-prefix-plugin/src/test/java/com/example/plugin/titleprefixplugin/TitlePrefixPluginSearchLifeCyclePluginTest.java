package com.example.plugin.titleprefixplugin;

import com.funnelback.publicui.search.model.padre.Result;
import com.funnelback.publicui.search.model.transaction.testutils.TestableSearchTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TitlePrefixPluginSearchLifeCyclePluginTest {

    TitlePrefixPluginSearchLifeCyclePlugin pluginUnderTest = new TitlePrefixPluginSearchLifeCyclePlugin(); // 1
    TestableSearchTransaction testInput; // 2

    @Before // 3
    public void setup() {
        // Create some test data.
        testInput = new TestableSearchTransaction() // 4
            .withResult(Result.builder().title("ExampleCorp - Fake search data to take home").build()) // 5
            .withResult(Result.builder().title("ExampleCorp - Another result with the same prefix").build())
            .withResult(Result.builder().title("This result does not match the pattern").build());
    }

    @Test // 6
    public void testPostProcess_with_replacement_pattern() { // 7
        testInput
            .withProfileSetting("plugin.title-prefix-plugin.pattern", "ExampleCorp -") // 8
            .withProfileSetting("plugin.title-prefix-plugin.replaceWith", "EC |");
        pluginUnderTest.postProcess(testInput); // 9
        Set<String> expectedResultTitles = Set.of(
            "EC | Fake search data to take home",
            "EC | Another result with the same prefix",
            "This result does not match the pattern"); // 10
        List<Result> mockResults = testInput.getResponse().getResultPacket().getResults(); // 11
        Set<String> actualResultTitles = mockResults.stream().map(finalResult -> finalResult.getTitle()).collect(Collectors.toSet()); // 12
        Assert.assertEquals("the pattern should have been replaced with the new one", expectedResultTitles,actualResultTitles); // 13
    }

    @Test
    public void testPostProcess_without_replacement_pattern(){ // 14
        testInput.withProfileSetting("plugin.title-prefix-plugin.pattern", "ExampleCorp - "); // 15
        pluginUnderTest.postProcess(testInput);
        Set<String> expectedResultTitles = Set.of(
            "Fake search data to take home",
            "Another result with the same prefix",
            "This result does not match the pattern");
        List<Result> mockResults = testInput.getResponse().getResultPacket().getResults();
        Set<String> actualResultTitles = mockResults.stream().map(finalResult -> finalResult.getTitle()).collect(Collectors.toSet());
        Assert.assertEquals("the pattern should have been removed", expectedResultTitles, actualResultTitles); // 16
    }
}