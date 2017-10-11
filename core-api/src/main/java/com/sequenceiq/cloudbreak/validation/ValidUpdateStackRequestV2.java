package com.sequenceiq.cloudbreak.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateStackRequestV2Validator.class)
public @interface ValidUpdateStackRequestV2 {

    String message() default "Update stack request is not valid.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}