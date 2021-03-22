package kazmierczak.jan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.filter.CorsFilter;
import kazmierczak.jan.transformer.JsonTransformer;
import kazmierczak.jan.types.SortItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.*;

@RequiredArgsConstructor
public class Routing {
    private CarsService carsService;

    public void initRoutes() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        });
    }
}
