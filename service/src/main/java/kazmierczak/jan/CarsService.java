package kazmierczak.jan;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.CarValidator;
import kazmierczak.jan.config.converter.CarJsonConverter;
import kazmierczak.jan.config.converter.exception.CarsServiceException;
import kazmierczak.jan.types.CarStatistics;
import kazmierczak.jan.types.Color;
import kazmierczak.jan.types.SortItem;
import kazmierczak.jan.types.Statistics;
import kazmierczak.jan.config.validator.Validator;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static kazmierczak.jan.car.CarUtils.*;

@Service
public class CarsService {
    @Value("${cars.filename}")
    private String filename;
    private List<Car> cars;

    /**
     * method to init cars list with data from file
     */
    @PostConstruct
    private void init() {
        cars = new CarJsonConverter(jarPath() + "/resources/" + filename)
                .fromJson()
                .orElseThrow(() -> new CarsServiceException("Cannot read data from file " + filename))
                .stream()
                .peek(car -> {
                    Validator.validate(new CarValidator(), car);
                }).collect(toList());
    }

    /**
     *
     * @return jar path
     */
    private String jarPath() {
        try {
            var path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            var pathElements = path.split("/");
            var size = pathElements.length;
            return Arrays
                    .stream(pathElements)
                    .limit(size - 1)
                    .skip(1)
                    .collect(joining("/"));
        } catch (Exception e) {
            throw new CarsServiceException(e.getMessage());
        }
    }

    /**
     * @return list of all cars
     */
    public List<Car> getAllCars() {
        return cars;
    }

    /**
     * @param sortItem   describes criteria of sort
     * @param descending if true sorting in ascending order otherwise descending order
     * @return sorted collection of cars
     */
    public List<Car> sort(SortItem sortItem, boolean descending) {
        if (sortItem == null) {
            throw new CarsServiceException("Sort item is not correct");
        }
        var sortedCars = switch (sortItem) {
            case COLOR -> cars.stream().sorted(compareByColor).collect(toList());
            case MODEL -> cars.stream().sorted(compareByModel).collect(toList());
            case MILEAGE -> cars.stream().sorted(compareByMileage).collect(toList());
            default -> cars.stream().sorted(compareByPrice).collect(toList());
        };

        if (descending) {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }

    /**
     * @param mileage the mileage value we are comparing to
     * @return cars which mileage is greater than mileage from param
     */
    public List<Car> withMileageGreaterThan(int mileage) {
        if (mileage <= 0) {
            throw new CarsServiceException("Mileage value should be greater than 0");
        }
        return cars
                .stream()
                .filter(car -> car.hasMileageGreaterThan(mileage))
                .collect(toList());
    }

    /**
     * @return map where the key is color and value is number of cars
     * which got this color
     */
    public Map<Color, Long> countCarsByColor() {
        return cars
                .stream()
                .collect(groupingBy(toColor, counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Long::max, LinkedHashMap::new));
    }

    /**
     * @return map where the key is model name and value is
     * the most expensive car of this model
     */
    public Map<String, List<Car>> getModelsWithMostExpensiveCars() {
        return cars
                .stream()
                .collect(groupingBy(
                        toModel,
                        collectingAndThen(
                                groupingBy(toPrice),
                                result -> result
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByKey())
                                        .orElseThrow(() -> new CarsServiceException("Canot find the most expensive car"))
                                        .getValue()
                        )
                ));
    }

    /**
     * @return statistics with average value, minimum value and maximum value
     * for price and mileage
     */
    public CarStatistics getStats() {
        var priceStats = cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(toPriceStats));

        var milleageStats = cars
                .stream()
                .collect(summarizingInt(toMileage));

        return CarStatistics
                .builder()
                .priceStatistics(Statistics.fromBigDecimalSummaryStatistics(priceStats))
                .mileageStatistics(Statistics.fromIntSummaryStatistics(milleageStats))
                .build();
    }

    /**
     * @return the most expensive car or list of the most
     * expensive cars if there are more than one
     * most expensive cars
     */
    public List<Car> theMostExpensiveCar() {
        var maxPrice = cars
                .stream()
                .map(toPrice)
                .max(Comparator.naturalOrder())
                .orElseThrow();
        return cars
                .stream()
                .filter(car -> car.hasPriceGreaterOrEqualTo(maxPrice))
                .collect(toList());
    }

    /**
     * @return list of cars with sorted components
     */
    public List<Car> getCarsWithSortedComponents() {
        return cars
                .stream()
                .map(Car::withSortedComponents)
                .collect(toList());
    }

    /**
     * @return map where the key is name of component
     * and value is list of car which got this component.
     * Pairs are descending sorted by length of value
     */
    public Map<String, List<Car>> componentWithCarsList() {
        return cars
                .stream()
                .flatMap(car -> toComponents.apply(car).stream())
                .distinct()
                .collect(toMap(
                        Function.identity(),
                        component -> cars
                                .stream()
                                .filter(car -> car
                                        .hasComponent(component))
                                .collect(toList())
                ));
    }

    /**
     * @param fromPrice represents the minimum value from range we are looking for cars
     * @param toPrice   represents the maximum value from range we are looking for cars
     * @return list of cars which price is in range between fromPrice and toPrice
     */
    public List<Car> filterByPriceRange(BigDecimal fromPrice, BigDecimal toPrice) {
        if (fromPrice.compareTo(toPrice) > 0) {
            throw new CarsServiceException("Minimum price cannot be greater than maximum price");
        }

        if (fromPrice != null && toPrice != null) {
            return cars
                    .stream()
                    .filter(car -> car.hasPriceInRange(fromPrice, toPrice))
                    .collect(toList());
        }
        return cars;
    }

    private List<Car> filterByModel(String model) {
        if (model != null && !model.equals("default-model")) {
            return cars
                    .stream()
                    .filter(car -> car.equalsModel(model))
                    .collect(toList());
        }
        return cars;
    }

    private List<Car> filterByColor(String color) {
        if (color != null && !(color).equals("default-color")) {
            return cars
                    .stream()
                    .filter(car -> car.equalsColor(Color.valueOf(color)))
                    .collect(toList());
        }
        return cars;
    }

    private List<Car> inMileageRange(int minMileage, int maxMileage) {
        if (minMileage < maxMileage) {
            return cars
                    .stream()
                    .filter(car -> car.inMileageRange(minMileage, maxMileage))
                    .collect(toList());
        }
        return cars;
    }

    private List<Car> filterByComponents(List<String> components) {
        if (components != null && !components.isEmpty()) {
            return cars
                    .stream()
                    .filter(car -> car.containsComponents(components))
                    .collect(toList());
        }
        return cars;
    }

    public List<Car> filterCarsByManyParameters(String model, BigDecimal minPrice, BigDecimal maxPrice, String color,
                                                int minMileage, int maxMileage, List<String> components) {
        return cars
                .stream()
                .filter(filterByModel(model)::contains)
                .filter(filterByPriceRange(minPrice, maxPrice)::contains)
                .filter(filterByColor(color)::contains)
                .filter(inMileageRange(minMileage, maxMileage)::contains)
                .filter(filterByComponents(components)::contains)
                .collect(toList());
    }
}
