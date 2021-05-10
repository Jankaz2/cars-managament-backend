package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.types.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CountByColorsTest {
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
    @DisplayName("comparison of color and number of cars that got this color")
    public void test1() {
        var expectedResult = Map.ofEntries(
                Map.entry(Color.RED, 1L),
                Map.entry(Color.WHITE, 1L),
                Map.entry(Color.BLACK, 1L)
        );

        var result = carsService.countCarsByColor();

        assertThat(result).isEqualTo(expectedResult);
    }
}
