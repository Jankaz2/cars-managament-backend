package kazmierczak.jan.car;

import kazmierczak.jan.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HasComponentTest {
    @Test
    @DisplayName("returns true if car got component from argument")
    public void test1() {
        var component = "WIFI";
        var car = Car.builder()
                .components(List.of("GPS", "WIFI")).build();
        var result = car.hasComponent(component);

        assertThat(result).isTrue();
    }
}
