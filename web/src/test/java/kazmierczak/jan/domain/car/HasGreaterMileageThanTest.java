package kazmierczak.jan.domain.car;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HasGreaterMileageThanTest {
    @Test
    @DisplayName("when parameter mileage is greater than car's object mileage")
    public void test1() {
        var givenMileage = 100;
        var car = Car.builder().mileage(101).build();
        var expectedResult = car.hasMileageGreaterThan(givenMileage);

        assertThat(expectedResult).isTrue();
    }
}