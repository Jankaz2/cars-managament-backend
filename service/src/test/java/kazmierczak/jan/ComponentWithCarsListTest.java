package kazmierczak.jan;

import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentWithCarsListTest {
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
    @DisplayName("cars that got list of components")
    public void test1() {
        var expectedResult = Map.ofEntries(
                Map.entry("AIR CONDITIONING", List.of("FORD", "FORD")),
                Map.entry("VOICE SERVICE", List.of("FORD", "FORD")),
                Map.entry("NAVIGATION", List.of("AUDI")),
                Map.entry("AUTOMATIC GEARBOX", List.of("AUDI"))
        );

        var result = carsService.componentWithCarsList()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        cars -> cars.getValue().stream().map(CarUtils.toModel).collect(Collectors.toList())
                ));

        assertThat(result).isEqualTo(expectedResult);
    }
}
