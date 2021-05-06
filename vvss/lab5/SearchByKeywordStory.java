package org.example.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import org.example.steps.serenity.EndUserSteps;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    public static final String ORANGE_DEFINITION = "An evergreen tree of the genus Citrus such as Citrus sinensis.";
    public static final String MANGO_DEGINITION = "A tropical Asian fruit tree, Mangifera indica.";
    public static final String PEAR_DEFINITION = "An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem.";
    public static final String APPLE_DEFINITION = "A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.";
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps anna;

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_apple_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("apple");
        anna.should_see_definition(APPLE_DEFINITION);

    }

    @Test
    public void searching_by_keyword_pear_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("pear");
        anna.should_see_definition(PEAR_DEFINITION);
    }

    @Test
    public void searching_by_keyword_mango_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("mango");
        anna.should_see_definition(MANGO_DEGINITION);
    }

    @Test
    public void searching_by_keyword_orange_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("orange");
        anna.should_see_definition(ORANGE_DEFINITION);
    }

//    @Pending @Test @Ignore
//    public void searching_by_ambiguious_keyword_should_display_the_disambiguation_page() {
//    }
} 
