package kazmierczak.jan;

import kazmierczak.jan.converters.CarJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
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
}
