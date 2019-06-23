package domain;

import domain.exceptions.BussinessException;

@FunctionalInterface
public interface SupplierWithExceptions<T> {
    public T get() throws BussinessException;
}