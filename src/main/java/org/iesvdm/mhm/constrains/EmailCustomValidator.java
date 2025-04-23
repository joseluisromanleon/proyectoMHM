package org.iesvdm.mhm.constrains;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.iesvdm.mhm.notations.EmailValid;

public class EmailCustomValidator implements ConstraintValidator<EmailValid, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}