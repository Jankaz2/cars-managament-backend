package kazmierczak.jan;

import kazmierczak.jan.types.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Car {
    String model;
    BigDecimal price;
    Color color;
    int mileage;
    List<String> components;

    /**
     *
     * @param mileage - mileage we chec whether car got greater mileage than this
     * @return true if mileage from argument is less than
     *         mileage of car we are checking otherwise return
     *         false
     */
    public boolean hasMileageGreaterThan(int mileage) {
        return this.mileage > mileage;
    }

    @Override
    public String toString() {
        return "\nModel: " + model +
                " Price: " + price +
                " Color: " + color +
                " Milleage: " + mileage +
                " Components :" + components + "\n";
    }
}
