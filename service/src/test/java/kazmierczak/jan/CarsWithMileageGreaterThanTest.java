package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CarsWithMileageGreaterThanTest {
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
    @DisplayName("cars that got mileage greater than parameter")
    public void test1() {
        //greater than 100 000
        var expectedResultWith100000 = List.of(
                "FORD", "FORD", "AUDI"
        );

        var result = carsService
                .withMileageGreaterThan(100000)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());
        assertThat(result).isEqualTo(expectedResultWith100000);

        //greater than 155 000
        var expectedResultWith155000 = List.of(
                "FORD", "AUDI"
        );

        var result2 = carsService
                .withMileageGreaterThan(155000)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result2).isEqualTo(expectedResultWith155000);
    }
}
