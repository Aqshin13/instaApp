package com.company.service;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(
        validatedBy = {ValidUniqueUsername.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "Username is in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
