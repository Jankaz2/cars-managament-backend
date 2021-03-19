package kazmierczak.jan;

import java.util.Comparator;

public interface CarUtils {
    Comparator<Car> compareByModel = Comparator.comparing(car -> car.model);

    Comparator<Car> compareByMileage = Comparator.comparing(car -> car.mileage);

    Comparator<Car> compareByPrice = Comparator.comparing(car -> car.price);

    Comparator<Car> compareByColor = Comparator.comparing(car -> car.color);
}
