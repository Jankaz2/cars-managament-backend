package kazmierczak.jan;

import kazmierczak.jan.routes.Routing;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(8080);
        var routing = new Routing();
        routing.initRoutes();
    }
}
