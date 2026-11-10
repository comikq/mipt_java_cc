package validator;

import validationservice.ValidationConfigurationException;
import validationservice.ValidationError;
import validationservice.ValidationResult;
import annotations.Email;
import annotations.NotNull;
import annotations.Range;
import annotations.Size;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Validator {
    private Validator() {}
    public static ValidationResult validate(Object object) {
        if (object == null) {
            throw new NullPointerException("Validated Object must be non-null");
        }

        List<ValidationError> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();

        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) continue;
                if (!field.trySetAccessible()) {
                    throw new ValidationConfigurationException("Cannot access field "
                            + field.getName() + " of class " + clazz.getName());
                }
                Object value;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new ValidationConfigurationException(
                            "Cannot read field " + field.getName() + " of class " + clazz.getName(), e);
                }

                NotNull notNull = field.getAnnotation(NotNull.class);
                if (notNull != null && value == null) {
                    errors.add(new ValidationError(field.getName(), "NotNull", notNull.message()));
                }

                Size size = field.getAnnotation(Size.class);
                if (size != null) {
                    if (!CharSequence.class.isAssignableFrom(field.getType())) {
                        throw new ValidationConfigurationException(
                                "@Size is not supported on field " + field.getName()
                                        + " of class " + clazz.getName());
                    }
                    if (size.min() < 0 || size.min() > size.max()) {
                        throw new ValidationConfigurationException(
                                "Invalid @Size bounds on field " + field.getName() + " of class " + clazz.getName());
                    }
                    if (value != null) {
                        int length = ((CharSequence) value).length();
                        if (length < size.min() || length > size.max()) {
                            errors.add(new ValidationError(field.getName(), "Size", size.message()));
                        }
                    }
                }

                Range range = field.getAnnotation(Range.class);
                if (range != null) {
                    Class<?> type = field.getType();
                    if (!(Number.class.isAssignableFrom(type) || (type.isPrimitive() && type != boolean.class
                            && type != char.class))) {
                        throw new ValidationConfigurationException("@Range is not supported on field "
                                + field.getName() + " of class " + clazz.getName());
                    }
                    if (Double.isNaN(range.min()) || Double.isNaN(range.max())
                            || Double.isInfinite(range.min()) || Double.isInfinite(range.max())
                            || range.min() > range.max()) {
                        throw new ValidationConfigurationException(
                                "Invalid @Range bounds on field " + field.getName() + " of class " + clazz.getName());
                    }
                    if (value != null) {
                        double number = ((Number) value).doubleValue();
                        if (Double.isNaN(number) || number < range.min() || number > range.max()) {
                            errors.add(new ValidationError(field.getName(), "Range", range.message()));
                        }
                    }
                }

                Email email = field.getAnnotation(Email.class);
                if (email != null) {
                    if (!CharSequence.class.isAssignableFrom(field.getType())) {
                        throw new ValidationConfigurationException(
                                "@Email is not supported on field " + field.getName()
                                        + " of class " + clazz.getName());
                    }
                    if (value != null) {
                        String text = value.toString();
                        int at = text.indexOf("@");
                        if (text.isBlank() || text.contains(" ") || at <= 0 || at == text.length() - 1
                                || text.indexOf(".", at) == -1) {
                            errors.add(new ValidationError(field.getName(), "Email", email.message()));
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return new ValidationResult(errors);
    }
}