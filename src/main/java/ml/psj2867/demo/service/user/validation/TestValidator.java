package ml.psj2867.demo.service.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TestValidator implements ConstraintValidator<TestValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // context.a
        return false;
    }
   
}