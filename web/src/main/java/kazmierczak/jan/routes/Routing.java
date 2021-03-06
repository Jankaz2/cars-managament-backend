package kazmierczak.jan.routes;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.domain.config.AppSpringConfig;
import kazmierczak.jan.filter.CorsFilter;
import kazmierczak.jan.transformer.JsonTransformer;
import kazmierczak.jan.types.SortItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

@RequiredArgsConstructor
public class Routing {
    private CarsService carsService;

    public void initRoutes() {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppSpringConfig.class);
        context.refresh();
        carsService = context.getBean("carsService", CarsService.class);
        var corsFilter = new CorsFilter();
        corsFilter.apply();

        /**
         * this request returns list of all cars
         */
        path("/cars", () -> {
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return carsService.getAllCars();
                    }, new JsonTransformer()
            );

            /**
             * this request returns list of cars filter by parameters
             */
            path("/filter", () -> {
                get("/:model/:minPrice/:maxPrice/:color/:minMileage/:maxMileage/:components",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var model = request.params(":model");
                            var minPrice = request.params(":minPrice");
                            var maxPrice = request.params(":maxPrice");
                            var color = request.params(":color");
                            var minMileage = request.params(":minMileage");
                            var maxMileage = request.params(":maxMileage");
                            var components = request.params(":components");
                            List<String> componentsList = new LinkedList<>();
                            if (!components.equals("component,component2")) {
                                componentsList = Arrays.stream(components.split("[,]")).collect(Collectors.toList());
                            }
                            if (maxPrice.equals("max-price")) {
                                maxPrice = String.valueOf(carsService.theGreatestPrice());
                            }
                            if (maxMileage.equals("max-mileage")) {
                                maxMileage = String.valueOf(carsService.theGreatestMileage());
                            }
                            return carsService.filterCarsByManyParameters(model, new BigDecimal(minPrice), new BigDecimal(maxPrice),
                                    color, Integer.parseInt(minMileage), Integer.parseInt(maxMileage), componentsList);
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns list of cars sorted by parameter
             */
            path("/sort", () -> {
                get("/:item/:order",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var sortItem = request.params(":item");
                            var order = request.params(":order");
                            return carsService.sort(SortItem.valueOf(sortItem), Boolean.parseBoolean(order));
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns the most expensive car
             */
            path("/price", () -> {
                get("/max",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.theMostExpensiveCar();
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns map of greatest price for each model
             */
            path("/model", () -> {
                get("/most-expensive",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.getModelsWithMostExpensiveCars();
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns list of cars with mileage greater than mileage from parameter
             */
            path("/mileage", () -> {
                get("/:mileage",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var mileage = request.params(":mileage");
                            return carsService.withMileageGreaterThan(Integer.parseInt(mileage));
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns list of cars grouped by colors
             */
            path("/color", () -> {
                get("/group",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.countCarsByColor();
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns list of cars with sorted components
             */
            path("/components", () -> {
                get("/sort",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.getCarsWithSortedComponents();
                        }, new JsonTransformer()
                );
            });

            /**
             * this request returns statisitcs of our cars list
             */
            path("/statistics", () -> {
                get("",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.getStats();
                        }, new JsonTransformer()
                );
            });
        });
    }
}
