package kazmierczak.jan.service;


import kazmierczak.jan.CarsService;
import kazmierczak.jan.domain.config.AppSpringConfig;
import kazmierczak.jan.types.CarStatistics;
import kazmierczak.jan.types.Statistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StatsServiceTest {
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
    @DisplayName("statistics for price and mileage of cars")
    public void test1() {
        var prices = List.of(
                new BigDecimal(120000),
                new BigDecimal(160000),
                new BigDecimal(200000)
        );

        var mileages = List.of(
                150000, 160000, 195000
        );

        var expectedPriceStats = prices.stream().collect(Collectors2.summarizingBigDecimal(x -> x));
        var expectedMileageStats = mileages.stream().collect(Collectors.summarizingInt(x -> x));
        var expectedResult = CarStatistics.builder()
                .priceStatistics(Statistics.fromBigDecimalSummaryStatistics(expectedPriceStats))
                .mileageStatistics(Statistics.fromIntSummaryStatistics(expectedMileageStats))
                .build();

        var result = carsService.getStats();

        assertThat(expectedResult).isEqualTo(result);
    }
}
