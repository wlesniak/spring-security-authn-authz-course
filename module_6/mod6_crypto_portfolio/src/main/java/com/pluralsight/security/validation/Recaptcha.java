package com.pluralsight.security.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecaptchaValidator.class)
public @interface Recaptcha {
	String message() default "Invalid Recaptcha";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
