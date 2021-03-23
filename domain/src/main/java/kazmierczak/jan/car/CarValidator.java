package kazmierczak.jan.car;

import kazmierczak.jan.types.Color;
import kazmierczak.jan.config.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarValidator implements Validator<Car> {
    @Override
    public Map<String, String> validate(Car car) {

        var errors = new HashMap<String, String>();
        if (car == null) {
            errors.put("car", "object is null");
            return errors;
        }

        var model = car.model;
        if (hasIncorrectModel(model)) {
            errors.put("model", "should contain only upper case letters or white spaces");
        }

        var mileage = car.mileage;
        if (hasIncorrectMileage(mileage)) {
            errors.put("mileage", "must have non negative value");
        }

        var price = car.price;
        if (hasIncorrectPrice(price)) {
            errors.put("price", "must have non negative value");
        }

        var color = car.color;
        if(hasIncorrectColor(color)) {
            errors.put("color", "color value must not be null");
        }

        var components = car.components;
        if (hasIncorrectCompoentsCollection(components)) {
            errors.put("components", "must have all elements with upper case letters or white spaces");
        }
        return errors;
    }

    /**
     * @param model - model of car
     * @return false if model we are checking does not match syntax
     */
    private static boolean hasIncorrectModel(String model) {
        return isIncorrectExpression(model);
    }

    /**
     * @param color - color of car
     * @return true if color is null, otherwise return false
     */
    private static boolean hasIncorrectColor(Color color) {
        return color == null;
    }

    /**
     * @param mileage - mileage of car
     * @return true if mileage is less than 0, otherwise return false
     */
    private static boolean hasIncorrectMileage(int mileage) {
        return mileage < 0;
    }

    /**
     * @param price - price of car
     * @return true if price is null or less than 0, otherwise return false
     */
    private static boolean hasIncorrectPrice(BigDecimal price) {
        return price == null || price.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * @param components - components of car
     * @return true if components list is null or any of component from the list
     * has incorrect syntax
     */
    private static boolean hasIncorrectCompoentsCollection(List<String> components) {
        return components == null || components.stream().anyMatch(CarValidator::isIncorrectExpression);
    }

    /**
     * @param expression - expression we want to compare
     * @return true if expression is null or expression does not matches
     * common syntax, otherwise return false
     */
    private static boolean isIncorrectExpression(String expression) {
        return expression == null || !expression.matches("[A-Z\\s]+");
    }

}
