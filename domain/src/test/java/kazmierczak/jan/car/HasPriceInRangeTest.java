package kazmierczak.jan.car;

import kazmierczak.jan.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HasPriceInRangeTest {
    @Test
    @DisplayName("return true if car's price is in range")
    public void test1() {
        var givenCar = Car.builder()
                .price(new BigDecimal(100)).build();
        var fromPrice1 = new BigDecimal(99);
        var toPrice1 = new BigDecimal(101);

        var expectedResult = givenCar.hasPriceInRange(fromPrice1, toPrice1);
        assertThat(expectedResult).isTrue();

        var fromPrice2 = new BigDecimal(100);
        var toPrice2 = new BigDecimal(100);

        var expectedResult2 = givenCar.hasPriceInRange(fromPrice2, toPrice2);
        assertThat(expectedResult2).isTrue();
    }
}
