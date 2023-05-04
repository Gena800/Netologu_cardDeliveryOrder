import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {
    @Test
    void positiveValue() {
//        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Москва");
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(10)         //Текущая дата плюс 3 дня
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));     //Формат даты день.месяц.год
        $("[data-test-id=date] input").setValue(verificationDate);
        $("[data-test-id=\"name\"] input").setValue("Василий Пупкин");
        $("[data-test-id=\"phone\"] input").setValue("+79876543210");
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").shouldBe(visible, Duration.ofSeconds(15));


    }

}
