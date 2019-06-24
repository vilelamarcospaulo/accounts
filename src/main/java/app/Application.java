package app;

import applicationconf.EmbeddedJettyFactoryConstructor;
import applicationconf.RequestLogFactory;
import controllers.exceptions.BussinessOutputException;
import controllers.exceptions.NotFoundItemException;
import controllers.response.ErrorResponse;
import datastore.impl.AccountRepositoryImpl;
import datastore.impl.UserRepositoryImpl;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.AbstractNCSARequestLog;
import routes.AccountRoutes;
import routes.UserRoutes;
import spark.Spark;
import spark.embeddedserver.EmbeddedServers;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

import javax.validation.ValidationException;
import java.io.IOException;

public class Application {
    private static EmbeddedJettyFactory createEmbeddedJettyFactoryWithRequestLog(Logger logger) {
        AbstractNCSARequestLog requestLog = new RequestLogFactory(logger).create();
        return new EmbeddedJettyFactoryConstructor(requestLog).create();
    }

    public static void main(String[] args) {
        Integer port = 8000;

        // Repository Dependencies
        AccountRepositoryImpl.initializeInstance();
        UserRepositoryImpl.initializeInstance();

        // server configuration
        Spark.port(port);

        EmbeddedJettyFactory factory = createEmbeddedJettyFactoryWithRequestLog(Logger.getLogger(Application.class));
        EmbeddedServers.add(EmbeddedServers.Identifiers.JETTY, factory);

        // server pages
        Spark.notFound("");

        //Exceptions
        Spark.exception(ValidationException.class, ((validationException, request, response) -> {
            response.status(400);
            response.body(new ErrorResponse(validationException.getMessage()).toJson());
        }));


        Spark.exception(IOException.class, ((validationException, request, response) -> {
            response.status(400);
            response.body(ErrorResponse.NO_BODY.toJson());
        }));

        Spark.exception(NotFoundItemException.class, ((exception, request, response) -> {
            response.status(404);
        }));

        Spark.exception(BussinessOutputException.class, ((exception, request, response) -> {
            response.status(412);
            response.body(new ErrorResponse(exception.getMessage()).toJson());
        }));

        Spark.exception(Exception.class, ((exception, request, response) -> {
            response.status(500);
            response.body(new ErrorResponse(exception.getMessage()).toJson());
        }));

        //routes
        Spark.get("/hello", (request, response) -> "world");
        UserRoutes.initRoutes();
        AccountRoutes.initRoutes();

        // Init message
        System.out.println(String.format("Server running on port: %d", port));
    }
}
