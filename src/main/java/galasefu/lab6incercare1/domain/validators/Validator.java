package galasefu.lab6incercare1.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
