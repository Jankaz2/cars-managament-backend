package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppSpringConfig.class);
        context.refresh();
        var carsService = context.getBean("carsService", CarsService.class);
    }
}
