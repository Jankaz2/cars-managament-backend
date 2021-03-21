package kazmierczak.jan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WithSortedComponentsTest {
    @Test
    @DisplayName("returns car with sorted components list")
    public void test1() {
        var givenCar = Car.builder()
                .components(List.of("BBB", "CCC", "AAA")).build();
        var expectedCar = Car.builder()
                .components(List.of("AAA", "BBB", "CCC")).build();
        var result = givenCar.withSortedComponents();

        assertThat(result).isEqualTo(expectedCar);
    }
}
