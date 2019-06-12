package controllers.request;

import spark.utils.StringUtils;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class RequestObject<T> {
    private Class<T> clazz;

    public RequestObject(Class<T> clazz){
        this.clazz = clazz;
    }

    public void validate() throws ValidationException  {
        Field[] allFields = this.clazz.getDeclaredFields();

        Stream<String> notNullErrors = this.validateErrors(this::hasNotNullNotation, this::fieldIsNull);
        Stream<String> emptyErrors = this.validateErrors(this::hasNotEmptyNotation, this::fieldIsEmpty);

        String errors = Stream.concat(notNullErrors, emptyErrors).collect(Collectors.joining(", "));

        if(!StringUtils.isEmpty(errors)) {
            throw new ValidationException(errors);
        }
    }

    private Stream<String> validateErrors(Predicate<Field> selection, Predicate<Field> validation) {
        return  Arrays.stream(this.clazz.getDeclaredFields())
                .filter(selection)
                .filter(validation)
                .map(this::fieldError);
    }

    private Boolean hasNotNullNotation(Field field) { return field.isAnnotationPresent(NotNull.class); }

    private Boolean hasNotEmptyNotation(Field field) { return field.isAnnotationPresent(NotEmpty.class); }

    private Boolean fieldIsNull(Field field) { return this.fieldValueIs(field, Objects::isNull); }

    private Boolean fieldIsEmpty(Field field) { return this.fieldValueIs(field, StringUtils::isEmpty); }

    private Boolean fieldValueIs(Field field, Predicate<Object> predicate) {
        try {
            return predicate.test(this.getFieldValue(field));
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    private Object getFieldValue(Field field) throws IllegalAccessException {
        Boolean oldAcessible = field.isAccessible();
        field.setAccessible(true);

        Object value = field.get(this);

        field.setAccessible(oldAcessible);

        return value;
    }

    private String fieldError(Field field) {
        return String.format("Field %s can`t be null or empty", field.getName());
    }
}
