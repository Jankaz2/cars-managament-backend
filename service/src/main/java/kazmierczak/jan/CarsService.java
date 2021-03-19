package kazmierczak.jan;

import kazmierczak.jan.converters.CarJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.Color;
import kazmierczak.jan.types.SortItem;
import kazmierczak.jan.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        cars = new CarJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new CarsServiceException("Cannot read data from file " + filename))
                .stream()
                .peek(car -> {
                    Validator.validate(new CarValidator(), car);
                }).collect(Collectors.toList());
    }

    /**
     *
     * @return list of all cars
     */
    public List<Car> getAllCars(){
        return cars;
    }

    /**
     * @param sortItem   describes criteria of sort
     * @param descending if true sortng in ascending order otherwise descending order
     * @return sorted collection of cars
     */
    public List<Car> sort(SortItem sortItem, boolean descending) {
        if (sortItem == null) {
            throw new CarsServiceException("Sort item is not correct");
        }
        var sortedCars = switch (sortItem) {
            case COLOR -> cars.stream().sorted(CarUtils.compareByColor).collect(Collectors.toList());
            case MODEL -> cars.stream().sorted(CarUtils.compareByModel).collect(Collectors.toList());
            case MILEAGE -> cars.stream().sorted(CarUtils.compareByMileage).collect(Collectors.toList());
            default -> cars.stream().sorted(CarUtils.compareByPrice).collect(Collectors.toList());
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
                .collect(Collectors.toList());
    }

    /**
     * @return map where the key is color and value is number of cars
     * which got this color
     */
    public Map<Color, Long> countCarsByColor() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(CarUtils.toColor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::max, LinkedHashMap::new));
    }
}
