package kazmierczak.jan;

import kazmierczak.jan.types.Color;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface CarUtils {
    Comparator<Car> compareByModel = Comparator.comparing(car -> car.model);

    Comparator<Car> compareByMileage = Comparator.comparing(car -> car.mileage);

    Comparator<Car> compareByPrice = Comparator.comparing(car -> car.price);

    Comparator<Car> compareByColor = Comparator.comparing(car -> car.color);

    Function<Car, Color> toColor = car -> car.color;

    Function<Car, String> toModel = car -> car.model;

    Function<Car, BigDecimal> toPrice = car -> car.price;

    ToIntFunction<Car> toMileage = car -> car.mileage;

    org.eclipse.collections.api.block.function.Function<Car, BigDecimal> toPriceStats = car -> car.price;
}
