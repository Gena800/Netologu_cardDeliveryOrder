import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {
    Calendar cal = Calendar.getInstance();

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999/");
    }

    @Test
    void positiveValue() {
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

    @Test
    void positiveValueWithComplexElements() {
        $("[data-test-id=\"city\"] input").setValue("Мо");
        $(withText("Москва")).click();
        $(".input__icon").click();


        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfDelivery = LocalDate.now().plusDays(7);
        String date = dateOfDelivery.format(DateTimeFormatter.ofPattern("d"));


        if (dateOfDelivery.getMonthValue() - currentDate.getMonthValue() == 1) {                        // говнокод по борьбе с автоматическим перелистыванием календаря, если до конца месяца меньше 3х дней
            if ((cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH)) >= 3) {
                $("[data-step='1']").click();                                                   // Шуфутинский
            }
        }


        $$("td.calendar__day").find(exactText(date)).click();
        $("[data-test-id=\"name\"] input").setValue("Василий Пупкин");
        $("[data-test-id=\"phone\"] input").setValue("+79876543210");
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").shouldBe(visible, Duration.ofSeconds(15));
    }

}
