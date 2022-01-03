package com.npo.app;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class FlowerShopTests {
    @Before
    public void beforeTest() {
        open("https://koshikshop.by/");
    }

    private void likeItem(int itemIndex) {
        SelenideElement firstItem = $$(".product_thumbnail").get(itemIndex);
        firstItem.hover();
        firstItem.$(".add_to_wishlist").click();
    }

    private void addItem(int itemIndex) {
        SelenideElement firstItem = $$(".product_thumbnail").get(itemIndex);
        firstItem.hover();
        firstItem.$(".add_to_cart").click();
    }

    @Test
    //Проверить что пользователь может залогинется
    public void loginTest() {
        //Нажать кнопку войти
        $(".et-menu-account-btn").click();
        //Ввести имя пользователя и пароль
        $("#username").sendKeys("olga.ivzhich@gmail.com");
        $("#password").sendKeys("olgaTest123!");
        //Нажать Ввойти
        $(By.name("login")).click();
        //Проверить что пользователь залонелся
        $(".page-title").shouldHave(visible);
        $(".page-title").shouldHave(text("Мой профиль"));
    }

    @Test
    //Проверить что пользователь может добавить товар в избранное
    public void likeTest() {
        //Открыть Магазин -> Новогодний и Рождественский декор
        $("#menu-main #menu-item-4897").hover();
        $("#menu-item-8187").click();
        $(".hero-header-container h1").shouldHave(text("Новогодний и Рождественский декор"));
        //Нажать на кнопку "Добавить в избранное"
        likeItem(0);
        //Проверить что счетчик избранного изменился
        $(".et-wishlist-counter").shouldHave(text("1"));
        //Проверить товар в избранном
        $(".quick_wishlist").click();
        $(".page-header h1").shouldHave(text("Список желаемого"));
        $$(".wishlist-items-wrapper tr").shouldHave(CollectionCondition.size(1));
    }

    @Test
    //Проверить что пользователь может добавить товар в корзину
    public void addItemToBasketTest() {
        //Открыть Магазин -> Букеты -> Букеты авторские
        $("#menu-main #menu-item-4897").hover();
        $("#menu-item-4926").hover();
        $("#menu-item-5035").click();
        $(".hero-header-container h1").shouldHave(text("Букеты Авторские"));
        //Нажать на кнопку "В корзину"
        addItem(0);
        //Проверить что счетчик корзины изменился
        $(".minicart-counter").shouldHave(text("1"));
        //Проверить товар в корзине
        $(".quick_cart").click();
        $("#side-cart").shouldBe(visible);
        $$(".woocommerce-mini-cart-item").shouldHave(CollectionCondition.size(1));
        $(".woocommerce-mini-cart__buttons").shouldBe(visible);
    }

    @Test
    //Проверить что пользователь может удалить товар из избранного
    public void deleteLikeTest() {
        //Открыть Магазин -> Новогодний и Рождественский декор
        $("#menu-main #menu-item-4897").hover();
        $("#menu-item-8187").click();
        $(".hero-header-container h1").shouldHave(text("Новогодний и Рождественский декор"));
        //Нажать на кнопку "Добавить в избранное"
        likeItem(0);
        //Проверить что счетчик избранного изменился
        $(".et-wishlist-counter").shouldHave(text("1"));
        //Открыть список избранного
        $(".quick_wishlist").click();
        $$(".wishlist-items-wrapper tr .remove_from_wishlist").get(0).click();
        $(".woocommerce-message").shouldHave(text("Продукт успешно удален из избранного"));
    }

    @Test
    public void deleteItemFromBasketTest() {
        //Открыть Магазин -> Букеты -> Букеты авторские
        $("#menu-main #menu-item-4897").hover();
        $("#menu-item-4926").hover();
        $("#menu-item-5035").click();
        $(".hero-header-container h1").shouldHave(text("Букеты Авторские"));
        //Нажать на кнопку "В корзину"
        addItem(0);
        //Проверить что счетчик корзины изменился
        $(".minicart-counter").shouldHave(text("1"));
        //Проверить товар в корзине
        $(".quick_cart").click();
        $$(".woocommerce-mini-cart-item .remove_from_cart_button").get(0).click();
        $$(".woocommerce-mini-cart-item").shouldHave(CollectionCondition.size(0));
        $(".woocommerce-mini-cart__empty-message").shouldHave(text("Нет товаров в корзине."));
    }
}
