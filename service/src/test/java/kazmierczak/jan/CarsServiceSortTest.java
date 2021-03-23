package kazmierczak.jan;

import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.config.converter.exception.CarsServiceException;
import kazmierczak.jan.types.SortItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarsServiceSortTest {
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
    @DisplayName("when sort item instance is null")
    public void test1() {
        assertThatThrownBy(() -> carsService.sort(null, false))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Sort item is not correct");
    }

    @ParameterizedTest
    @DisplayName("when sort item is not null and sorting is ascending")
    @EnumSource(SortItem.class)
    public void test2(SortItem sortItem) {
        var expectedSotringResult = Map.ofEntries(
                Map.entry(SortItem.COLOR, List.of("AUDI", "FORD", "FORD")),
                Map.entry(SortItem.MODEL, List.of("AUDI", "FORD", "FORD")),
                Map.entry(SortItem.MILEAGE, List.of("FORD", "FORD", "AUDI")),
                Map.entry(SortItem.PRICE, List.of("FORD", "FORD", "AUDI"))
        );

        var sortedCarsBySortItem = carsService
                .sort(sortItem, false)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(sortedCarsBySortItem).isEqualTo(expectedSotringResult.get(sortItem));
    }

    @ParameterizedTest
    @DisplayName("when sort item is not null and sorting is descending")
    @EnumSource(SortItem.class)
    public void test3(SortItem sortItem) {
        var expectedSotringResult = Map.ofEntries(
                Map.entry(SortItem.COLOR, List.of("FORD", "FORD", "AUDI")),
                Map.entry(SortItem.MODEL, List.of("FORD", "FORD", "AUDI")),
                Map.entry(SortItem.MILEAGE, List.of("AUDI", "FORD", "FORD")),
                Map.entry(SortItem.PRICE, List.of("AUDI", "FORD", "FORD"))
        );

        var sortedCarsBySortItem = carsService
                .sort(sortItem, true)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(sortedCarsBySortItem).isEqualTo(expectedSotringResult.get(sortItem));
    }
}


