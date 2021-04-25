package kazmierczak.jan;

import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.config.converter.exception.CarsServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarsInPraceRangeTest {
    private CarsService carsService;

    @BeforeEach
    public void setup() {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("test");
        context.register(AppSpringConfig.class);
        context.refresh();
        carsService = context.getBean("carsService", CarsService.class);
    }

    @DisplayName("when minimum value is greater than maximum value")
    public void test1() {
        assertThatThrownBy(() -> carsService.filterByPriceRange(new BigDecimal(1200), new BigDecimal(1)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Minimum price cannot be greater than maximum price");
    }

    @Test
    @DisplayName("when values are not null")
    public void test2() {
        var expectedResult = List.of(
                "FORD", "AUDI"
        );

        var result = carsService.filterByPriceRange(new BigDecimal(160000), new BigDecimal(200000))
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(expectedResult).containsExactlyElementsOf(result);
    }
}
