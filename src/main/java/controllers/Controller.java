package controllers;

import controllers.exceptions.BussinessOutputException;
import controllers.request.RequestObject;
import domain.exceptions.BussinessException;
import service.exceptions.ServiceException;
import utils.JsonUtils;

import java.io.IOException;
import java.util.function.Function;

public abstract class Controller {
    protected static <T extends RequestObject, R> Object execute (
            String jsonInput,
            Class<T> inputClazz,
            Function<T, R> toCommand,
            ProcessCommand<R, Object> operation
    ) throws IOException {
        try {
            T input = JsonUtils.readFromJson(jsonInput, inputClazz);
            input.validate();

            R command = toCommand.apply(input);
            return operation.apply(command);
        } catch (ServiceException exception) {
            throw new BussinessOutputException(exception.fullMessage());
        } catch (BussinessException exception) {
            throw new BussinessOutputException(exception.fullMessage());
        }
    }
}
