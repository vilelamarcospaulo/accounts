package domain;

import domain.accountoperations.AccountOperation;
import domain.exceptions.BussinessException;

import java.util.concurrent.locks.ReentrantLock;

public abstract class ConcurrencyItem {
    private ReentrantLock mutex;

    public ConcurrencyItem() {
        this.mutex = new ReentrantLock();
    }

    private void lock() throws InterruptedException {
        this.mutex.lock();
    }

    private void unlock() {
        this.mutex.unlock();
    }

    protected AccountOperation executeWithLock(SupplierWithExceptions<AccountOperation> operationSupplier) throws BussinessException {
        try {
            this.lock();
            return operationSupplier.get();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.unlock();
        }
    }

}
