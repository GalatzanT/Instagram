package galasefu.lab6incercare1.domain.validators;

import galasefu.lab6incercare1.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator user) throws ValidationException {
        if (user.getPrenume() == null || user.getPrenume().isEmpty()) {
            throw new ValidationException("First name cannot be null or empty.");
        }
        if (user.getNume() == null || user.getNume().isEmpty()) {
            throw new ValidationException("Last name cannot be null or empty.");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long.");
        }
    }
}
