import validationservice.ValidationConfigurationException;
import validationservice.ValidationError;
import validationservice.ValidationResult;
import models.Employee;
import models.InvalidBoundsModel;
import models.InvalidTypeModel;
import models.User;
import validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    private static Set<String> codes(ValidationResult result) {
        return result.getErrors().stream()
                .map(e -> e.fieldName() + ":" + e.annotationName())
                .collect(Collectors.toSet());
    }

    @Test
    void validObjectHasNoErrors() {
        ValidationResult result = Validator.validate(new User("Anna", "anna@mail.com", 20));

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void notNullDetectsNull() {
        ValidationResult result = Validator.validate(new User(null, "anna@mail.com", 20));

        assertEquals(Set.of("name:NotNull"), codes(result));
    }

    @Test
    void sizeDetectsViolationAndErrorHasAllFields() {
        ValidationResult result = Validator.validate(new User("A", "anna@mail.com", 20));

        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals("name", error.fieldName());
        assertEquals("Size", error.annotationName());
        assertEquals("Имя должно содержать от 2 до 50 символов", error.message());
    }

    @Test
    void rangeDetectsViolation() {
        ValidationResult result = Validator.validate(new User("Anna", "anna@mail.com", 200));

        assertEquals(Set.of("age:Range"), codes(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "@mail.com", "anna@", "anna@mail", "an na@mail.com", "   "})
    void emailDetectsInvalidFormat(String email) {
        ValidationResult result = Validator.validate(new User("Anna", email, 20));

        assertEquals(Set.of("email:Email"), codes(result));
    }

    @Test
    void violationsOfDifferentFieldsAreCollectedTogether() {
        ValidationResult result = Validator.validate(new User("A", "bad", 200));

        assertEquals(3, result.getErrors().size());
        assertEquals(Set.of("name:Size", "email:Email", "age:Range"), codes(result));
    }

    @ParameterizedTest
    @CsvSource({
            "2,   20,  true",   // минимальная длина имени
            "50,  20,  true",   // максимальная длина имени
            "1,   20,  false",  // имя короче
            "51,  20,  false",  // имя длиннее
            "2,   0,   true",   // минимальный возраст
            "2,   150, true",   // максимальный возраст
            "2,   -1,  false",  // возраст ниже
            "2,   151, false"   // возраст выше
    })
    void boundaryValues(int nameLength, int age, boolean expectedValid) {
        User user = new User("x".repeat(nameLength), "anna@mail.com", age);

        assertEquals(expectedValid, Validator.validate(user).isValid());
    }

    @Test
    void validateNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Validator.validate(null));
    }

    @Test
    void invalidBoundsThrowConfigurationException() {
        assertThrows(ValidationConfigurationException.class,
                () -> Validator.validate(new InvalidBoundsModel()));
    }

    @Test
    void annotationOnUnsupportedTypeThrowsEvenForNullValue() {
        assertThrows(ValidationConfigurationException.class,
                () -> Validator.validate(new InvalidTypeModel()));
    }

    @Test
    void privateInheritedFieldsAreValidatedAndStaticIsIgnored() {
        ValidationResult result = Validator.validate(new Employee(null, "anna@mail.com", 20, -5));

        assertEquals(Set.of("name:NotNull", "salary:Range"), codes(result));
    }

    @Test
    void errorsListCannotBeModified() {
        List<ValidationError> errors = Validator.validate(new User("A", "bad", 200)).getErrors();

        assertFalse(errors.isEmpty());
        assertThrows(UnsupportedOperationException.class,
                () -> errors.add(new ValidationError("x", "NotNull", "message")));
    }
}