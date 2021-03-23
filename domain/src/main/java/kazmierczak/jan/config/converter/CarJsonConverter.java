package kazmierczak.jan.config.converter;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.config.converter.generic.JsonConverter;

import java.util.List;

public class CarJsonConverter extends JsonConverter<List<Car>> {
    public CarJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
