package kazmierczak.jan.converters;

import kazmierczak.jan.Car;
import kazmierczak.jan.converters.generic.JsonConverter;

import java.util.List;

public class CarJsonConverter extends JsonConverter<List<Car>> {
    public CarJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
