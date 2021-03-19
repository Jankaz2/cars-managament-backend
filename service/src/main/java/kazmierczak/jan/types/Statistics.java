package kazmierczak.jan.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.IntSummaryStatistics;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {
    private BigDecimal min;
    private BigDecimal avg;
    private BigDecimal max;

    public static Statistics fromBigDecimalSummaryStatistics(BigDecimalSummaryStatistics stats) {
        return Statistics
                .builder()
                .min(stats.getMin())
                .avg(stats.getAverage().setScale(2, RoundingMode.UP))
                .max(stats.getMax())
                .build();
    }

    public static Statistics fromIntSummaryStatistics(IntSummaryStatistics stats) {
        return Statistics
                .builder()
                .min(BigDecimal.valueOf(stats.getMin()))
                .avg(BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.UP)) // zamienic na napis i sofrmatuje np do dwoch miejsc po przecinku
                .max(BigDecimal.valueOf(stats.getMax()))
                .build();
    }
}