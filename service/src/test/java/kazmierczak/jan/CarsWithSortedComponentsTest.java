package kazmierczak.jan;

import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CarsWithSortedComponentsTest {
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
    @DisplayName("list of cars with sorted components")
    public void test1() {
        var expectedResult = List.of(
                List.of("AIR CONDITIONING", "VOICE SERVICE"),
                List.of("AIR CONDITIONING", "VOICE SERVICE"),
                List.of("AUTOMATIC GEARBOX", "NAVIGATION")
        );

        var result = carsService.getCarsWithSortedComponents()
                .stream()
                .map(CarUtils.toComponents)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyElementsOf(expectedResult);
    }
}