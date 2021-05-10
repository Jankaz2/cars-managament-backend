package kazmierczak.jan.domain.config.validator;


import kazmierczak.jan.domain.config.validator.exception.ValidatorException;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator<T> {
    Map<String, String> validate(T item);

    static <T> T validate(Validator<T> validator, T item) {
        var errors = validator.validate(item);
        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException("[Validation errors] -> " + message);
        }
        return item;
    }
}
