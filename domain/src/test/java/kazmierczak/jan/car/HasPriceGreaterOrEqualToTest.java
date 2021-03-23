package kazmierczak.jan.car;

import kazmierczak.jan.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HasPriceGreaterOrEqualToTest {
    @Test
    @DisplayName("return true if car has greater or equal price to argument price")
    public void test1() {
        var givenCar = Car.builder()
                .price(new BigDecimal(120)).build();
        var price = new BigDecimal(120);
        var expectedResult1 = givenCar.hasPriceGreaterOrEqualTo(price);
        assertThat(expectedResult1).isTrue();

        var givenCar2 =  Car.builder()
                .price(new BigDecimal(121)).build();
        var expectedResult2 = givenCar2.hasPriceGreaterOrEqualTo(price);
        assertThat(expectedResult2).isTrue();

        var givenCar3 =  Car.builder()
                .price(new BigDecimal(119)).build();
        var expectedResult3 = givenCar3.hasPriceGreaterOrEqualTo(price);
        assertThat(expectedResult3).isFalse();
    }
}
