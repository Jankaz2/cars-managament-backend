package kazmierczak.jan;

import kazmierczak.jan.types.Color;

import java.util.Comparator;
import java.util.function.Function;

public interface CarUtils {
    Comparator<Car> compareByModel = Comparator.comparing(car -> car.model);

    Comparator<Car> compareByMileage = Comparator.comparing(car -> car.mileage);

    Comparator<Car> compareByPrice = Comparator.comparing(car -> car.price);

    Comparator<Car> compareByColor = Comparator.comparing(car -> car.color);

    Function<Car, Color> toColor = car -> car.color;
}
