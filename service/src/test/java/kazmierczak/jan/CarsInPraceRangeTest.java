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

    @Test
    @DisplayName("when minimum value - fromPrice - is null")
    public void test1() {
        assertThatThrownBy(() -> carsService.inPriceRange(null, new BigDecimal(1200)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("From price is null");
    }

    @Test
    @DisplayName("when maximum value - toPrice - is null")
    public void test2() {
        assertThatThrownBy(() -> carsService.inPriceRange(new BigDecimal(1200), null))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("To price is null");
    }

    @Test
    @DisplayName("when minimum value is greater than maximum value")
    public void test3() {
        assertThatThrownBy(() -> carsService.inPriceRange(new BigDecimal(1200), new BigDecimal(1)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Incorrect price range");
    }

    @Test
    @DisplayName("when values are not null")
    public void test4() {
        var expectedResult = List.of(
                "AUDI", "FORD"
        );

        var result = carsService.inPriceRange(new BigDecimal(160000), new BigDecimal(200000))
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(expectedResult).containsExactlyElementsOf(result);
    }
}
