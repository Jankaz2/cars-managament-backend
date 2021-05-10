package kazmierczak.jan.domain.config.converter;

import kazmierczak.jan.domain.car.Car;
import kazmierczak.jan.domain.config.converter.generic.JsonConverter;

import java.util.List;

public class CarJsonConverter extends JsonConverter<List<Car>> {
    public CarJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
