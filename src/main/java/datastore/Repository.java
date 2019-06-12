package datastore;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> find(String id);
    T save(T object);
    T delete(String id);
    List<T> getAll();
}
