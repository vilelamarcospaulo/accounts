package controllers;

import domain.exceptions.BussinessException;
import service.exceptions.ServiceException;

@FunctionalInterface
public interface ProcessCommand<T, R> {
    R apply(T t) throws ServiceException, BussinessException;
}