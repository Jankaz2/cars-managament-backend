
package kazmierczak.jan.domain.car.validator;

import kazmierczak.jan.domain.car.Car;
import kazmierczak.jan.domain.car.CarValidator;
import kazmierczak.jan.domain.config.validator.Validator;
import kazmierczak.jan.domain.config.validator.exception.ValidatorException;
import kazmierczak.jan.types.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CarValidatorTest {
    @Test
    @DisplayName("when create car is null")
    public void test1() {
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("car: object is null");
    }

    @Test
    @DisplayName("when validation has model errors")
    public void test2() {
        var car = Car
                .builder()
                .model("ForD")
                .price(new BigDecimal(120000))
                .color(Color.RED)
                .mileage(150000)
                .components(List.of(
                        "AIR CONDITIONING",
                        "VOICE SERVICE"
                ))
                .build();
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("model: should contain only upper case letters or white spaces");
    }

    @Test
    @DisplayName("when validation has mileage errors")
    public void test3() {
        var car = Car
                .builder()
                .model("FORD")
                .price(new BigDecimal(120000))
                .color(Color.RED)
                .mileage(-1)
                .components(List.of(
                        "AIR CONDITIONING",
                        "VOICE SERVICE"
                ))
                .build();
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("mileage: must have non negative value");
    }

    @Test
    @DisplayName("when validation has price errors")
    public void test4() {
        var car = Car
                .builder()
                .model("FORD")
                .price(new BigDecimal(-2))
                .color(Color.RED)
                .mileage(120)
                .components(List.of(
                        "AIR CONDITIONING",
                        "VOICE SERVICE"
                ))
                .build();
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("price: must have non negative value");
    }

    @Test
    @DisplayName("when validation has components errors")
    public void test5() {
        var car = Car
                .builder()
                .model("FORD")
                .price(new BigDecimal(120))
                .color(Color.RED)
                .mileage(120)
                .components(List.of(
                        "air CONDITIONING",
                        "VOICE SERVICE"
                ))
                .build();
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("components: must have all elements with upper case letters or white spaces");
    }

    @Test
    @DisplayName("when validation has color errors")
    public void test6() {
        var car = Car
                .builder()
                .model("FORD")
                .price(new BigDecimal(-2))
                .color(null)
                .mileage(120)
                .components(List.of(
                        "AIR CONDITIONING",
                        "VOICE SERVICE"
                ))
                .build();
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[Validation errors] -> ")
                .hasMessageContaining("color: must not be null");
    }
}