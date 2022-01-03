package com.npo.app;

import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class GoogleTest {
    @Test
    public void search_selenide_in_google() {
        open("https://duckduckgo.com");
        $(By.name("q")).val("selenide java").pressEnter();
        $$(".results .result").shouldHave(sizeGreaterThan(1));
        $(".results .result").shouldBe(visible).shouldHave(
                text("Selenide: concise UI tests in Java"),
                text("selenide.org"));
    }
}
