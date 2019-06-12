package datastore.impl;

import datastore.Id;
import datastore.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class RepositoryImpl<T> implements Repository<T> {
    protected Map<String, T> dataStore = new HashMap<>();

    public Optional<T> find(String id) {
        return Optional.ofNullable(this.dataStore.get(id));
    }

    public T save(T object) {
        String id = "";
        Field field;

        try { field = object.getClass().getDeclaredField("id"); }
        catch (NoSuchFieldException e) {
            field = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class)).findAny()
                .orElseThrow(() -> new RuntimeException("Object don`t have property `id` or a @Id notation"));
        }

        try {
            field.setAccessible(true);
            if((id = (String)field.get(object)) == null) {
                id = UUID.randomUUID().toString();
            }
            field.set(object, id);
        } catch (IllegalAccessException e) { }

        this.dataStore.put(id, object);
        return object;
    }

    public T delete(String id) {
        return this.dataStore.remove(id);
    }

    public List<T> getAll() {
        return this.dataStore.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }
}
