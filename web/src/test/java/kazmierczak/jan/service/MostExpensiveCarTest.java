package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.domain.car.CarUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MostExpensiveCarTest {
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
    @DisplayName("list of most expensive cars")
    public void test1() {
        var expectedResult = List.of("AUDI");

        var result = carsService.theMostExpensiveCar()
                .stream().map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).isEqualTo(expectedResult);
    }
}
