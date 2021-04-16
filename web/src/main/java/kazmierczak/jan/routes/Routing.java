package kazmierczak.jan.routes;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.filter.CorsFilter;
import kazmierczak.jan.transformer.JsonTransformer;
import kazmierczak.jan.types.Color;
import kazmierczak.jan.types.SortItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
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

        path("/cars", () -> {
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return carsService.getAllCars();
                    }, new JsonTransformer()
            );

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
                            var componentsList = Arrays.stream(components.split("[,]")).collect(Collectors.toList());
                            if (model == null || model.length() == 0) {
                                model = "AUDI";
                            }
                            return carsService.filterCarsByManyParameters(model, new BigDecimal(minPrice), new BigDecimal(maxPrice),
                                    Color.valueOf(color), Integer.parseInt(minMileage), Integer.parseInt(maxMileage), componentsList);
                        }, new JsonTransformer()
                );
            });

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

            path("/price", () -> {
                get("/max",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.theMostExpensiveCar();
                        }, new JsonTransformer()
                );

                get("/:min/:max",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var min = request.params(":min");
                            var max = request.params(":max");
                            return carsService.inPriceRange(new BigDecimal(min), new BigDecimal(max));
                        }, new JsonTransformer()
                );
            });

            path("/model", () -> {
                get("/most_expensive",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.getModelsWithMostExpensiveCars();
                        }, new JsonTransformer()
                );
            });

            path("/mileage", () -> {
                get("/:mileage",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var mileage = request.params(":mileage");
                            return carsService.withMileageGreaterThan(Integer.parseInt(mileage));
                        }, new JsonTransformer()
                );
            });

            path("/color", () -> {
                get("/group",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.countCarsByColor();
                        }, new JsonTransformer()
                );
            });

            path("/components", () -> {
                get("/sort",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.getCarsWithSortedComponents();
                        }, new JsonTransformer()
                );
            });

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
