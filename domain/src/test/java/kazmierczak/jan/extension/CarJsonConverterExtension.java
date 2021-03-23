package kazmierczak.jan.extension;

import kazmierczak.jan.config.converter.CarJsonConverter;
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
        var filename = "C:\\Users\\Hp\\OneDrive\\kmprograms\\Java\\projects\\cars-menagament\\domain\\src\\main\\resources\\files\\test\\car-test.json";
        return new CarJsonConverter(filename);
    }
}
