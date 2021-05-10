package kazmierczak.jan.domain.extension;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.domain.config.converter.CarJsonConverter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CarJsonConverterExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarJsonConverter.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var carsService = new CarsService();
        var filename = carsService.jarPath() + "/test/cars.json";
        return new CarJsonConverter(filename);
    }
}