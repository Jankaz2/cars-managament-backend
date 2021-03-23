package kazmierczak.jan.config.converter;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.extension.CarJsonConverterExtension;
import kazmierczak.jan.types.Color;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(CarJsonConverterExtension.class)
@RequiredArgsConstructor
public class CarJsonConverterTest {
    private final CarJsonConverter carJsonConverter;

    @Test
    @DisplayName("when json converter works correctly")
    public void test1() {
        var expectedCarResult = List.of(
                Car.builder()
                        .model("FORD")
                        .price(new BigDecimal(120000))
                        .color(Color.RED)
                        .mileage(150000)
                        .components(List.of(
                                "AIR CONDITIONING",
                                "VOICE SERVICE"
                        ))
                        .build()
        );

        var carFromJson = carJsonConverter.fromJson().orElseThrow();

        assertDoesNotThrow(() -> assertThat(carFromJson)
                .hasSize(1)
                .containsExactlyElementsOf(expectedCarResult));
    }
}
