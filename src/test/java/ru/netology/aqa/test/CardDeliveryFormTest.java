package ru.netology.aqa.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.aqa.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryFormTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitSuccessfully() {
        var userInfo = DataGenerator.Registration.generateUser("ru");
        String date1 = DataGenerator.generateDate(10);
        String date2 = DataGenerator.generateDate(15);
        $("[data-test-id = city] input").setValue(userInfo.getCity());
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = date] input").setValue(date1);
        $("[data-test-id = name] input").setValue(userInfo.getName());
        $("[data-test-id = phone] input").setValue(userInfo.getPhone());
        $("[data-test-id = agreement]").click();
//        Thread.sleep(10000);
        $(byText("Запланировать")).click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + date1));

        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = date] input").setValue(date2);
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + date2));

    }
}
