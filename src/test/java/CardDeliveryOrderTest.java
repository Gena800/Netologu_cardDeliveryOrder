import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999/");
    }

    @Test
    void positiveValue() {
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=\"city\"] input").setValue("Москва");
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=\"name\"] input").setValue("Василий Пупкин");
        $("[data-test-id=\"phone\"] input").setValue("+79876543210");
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void positiveValueWithComplexElements() {
        $("[data-test-id=\"city\"] input").setValue("Мо");
        $(withText("Москва")).click();
        $(".input__icon").click();

        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {     //если месяц минВозможной даты не равен месяцу нашей даты
            $("[data-step='1']").click();
        }

        $$("td.calendar__day").find(exactText(generateDate(7, "d"))).click();
        $("[data-test-id=\"name\"] input").setValue("Василий Пупкин");
        $("[data-test-id=\"phone\"] input").setValue("+79876543210");
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
