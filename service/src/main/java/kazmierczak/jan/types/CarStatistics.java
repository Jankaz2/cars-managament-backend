package kazmierczak.jan.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarStatistics {
    private Statistics priceStatistics;
    private Statistics mileageStatistics;
}
