package org.iesvdm.mhm.notations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.iesvdm.mhm.constrains.EmailCustomValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Aplicable a campos
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailCustomValidator.class) // Clase validadora

public @interface EmailValid {
    String message() default "Email inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
