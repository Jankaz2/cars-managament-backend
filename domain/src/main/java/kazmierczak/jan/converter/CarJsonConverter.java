package kazmierczak.jan.converter;

import kazmierczak.jan.Car;
import kazmierczak.jan.converter.generic.JsonConverter;

import java.util.List;

public class CarJsonConverter extends JsonConverter<List<Car>> {
    public CarJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
