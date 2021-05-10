package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.domain.car.Car;
import kazmierczak.jan.domain.config.AppSpringConfig;
import kazmierczak.jan.types.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelMostExpensiveCarsTest {
    private CarsService carsService;

    @BeforeEach
    public void setup() {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("test");
        context.register(AppSpringConfig.class);
        context.refresh();
        carsService = context.getBean("carsService", CarsService.class);
    }

    @Test
    @DisplayName("comparison of model and most expensive cars for this model")
    public void test1() {
        var expectedResult = Map.ofEntries(
                Map.entry("FORD", List.of(
                        Car.builder().model("FORD").price(new BigDecimal(160000)).color(Color.WHITE).mileage(160000)
                                .components(List.of("AIR CONDITIONING", "VOICE SERVICE")).build()
                )),
                Map.entry("AUDI", List.of(
                        Car.builder().model("AUDI").price(new BigDecimal(200000)).color(Color.BLACK).mileage(195000)
                                .components(List.of("NAVIGATION", "AUTOMATIC GEARBOX")).build()
                ))
        );

        var result = carsService.getModelsWithMostExpensiveCars();

        assertThat(result).isEqualTo(expectedResult);
    }
}
