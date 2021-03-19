package kazmierczak.jan;

import kazmierczak.jan.types.Color;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface CarUtils {
    /**
     * method to compare cars by model
     */
    Comparator<Car> compareByModel = Comparator.comparing(car -> car.model);
    /**
     * method to compare cars by mileage
     */
    Comparator<Car> compareByMileage = Comparator.comparing(car -> car.mileage);
    /**
     * method to compare cars by price
     */
    Comparator<Car> compareByPrice = Comparator.comparing(car -> car.price);
    /**
     * method to compare cars by color
     */
    Comparator<Car> compareByColor = Comparator.comparing(car -> car.color);
    /**
     * method to map car object to color of this car
     */
    Function<Car, Color> toColor = car -> car.color;
    /**
     * method to map car object to model of this car
     */
    Function<Car, String> toModel = car -> car.model;
    /**
     * method to map car object to price of this car
     */
    Function<Car, BigDecimal> toPrice = car -> car.price;
    /**
     * method to map car object to components list of this car
     */
    Function<Car, List<String>> toComponents = car -> car.components;
    /**
     * method to map car object to mileage of this car
     */
    ToIntFunction<Car> toMileage = car -> car.mileage;
    /**
     * method to map car object to price of this car but to get statisitcs
     */
    org.eclipse.collections.api.block.function.Function<Car, BigDecimal> toPriceStats = car -> car.price;
}
