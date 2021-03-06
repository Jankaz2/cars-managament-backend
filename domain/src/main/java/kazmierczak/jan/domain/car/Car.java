package kazmierczak.jan.domain.car;

import kazmierczak.jan.types.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param mileage - mileage we chec whether car got greater mileage than this
     * @return true if mileage from argument is less than
     * mileage of car we are checking otherwise return
     * false
     */
    public boolean hasMileageGreaterThan(int mileage) {
        return this.mileage > mileage;
    }

    /**
     * @param price we will compare to
     * @return true if car we are checking has greater or equal price
     * to price from param, otherwise return false
     */
    public boolean hasPriceGreaterOrEqualTo(BigDecimal price) {
        return this.price.compareTo(price) >= 0;
    }

    /**
     * @return car object with sorted components list
     */
    public Car withSortedComponents() {
        return Car
                .builder()
                .model(model)
                .color(color)
                .price(price)
                .mileage(mileage)
                .components(components
                        .stream()
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * @param component - component we check which cars got this
     * @return return true if components list of car we are checking
     * contains component param, otherwise return false
     */
    public boolean hasComponent(String component) {
        return components.contains(component);
    }

    /**
     * @param fromPrice - minimum price from range
     * @param toPrice   - maximum price from range
     * @return true if price of car we are checking is between fromPrice param
     * and toPrice param
     */
    public boolean hasPriceInRange(BigDecimal fromPrice, BigDecimal toPrice) {
        return price.compareTo(fromPrice) >= 0 && price.compareTo(toPrice) <= 0;
    }

    /**
     *
     * @param modelToCompare model of car we want to compare
     * @return true if modelToComare equals model of car,
     * otherwise return false
     */
    public boolean equalsModel(String modelToCompare) {
        return model.equals(modelToCompare);
    }

    /**
     *
     * @param colorToCompare color of car we want to compare
     * @return true if colortoCompare equals color of car,
     * otherwise return false
     */
    public boolean equalsColor(Color colorToCompare) {
        return color.equals(colorToCompare);
    }

    /**
     *
     * @param min minimum value of mileage we want to check
     * @param max maximum value of mileage we want to check
     * @return true if the mileage of car we are checking
     * is between min and max values, otherwise return false
     */
    public boolean inMileageRange(int min, int max) {
        return mileage >= min && mileage <= max;
    }

    /**
     *
     * @param componentsToCompare components we want to compare
     * @return true if car we are checking contains componentsToCompare,
     * otherwise return false
     */
    public boolean containsComponents(List<String> componentsToCompare) {
        return components.containsAll(componentsToCompare);
    }

    @Override
    public String toString() {
        return "\nModel: " + model +
                " Price: " + price +
                " Color: " + color +
                " Milleage: " + mileage +
                " Components :" + components;
    }
}
