package com.rest.ai.myCallimo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class NotEmptyListValidator implements ConstraintValidator<ValidationListInteger, List<Integer>> {
    @Override
    public void initialize(ValidationListInteger constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Integer> integers, ConstraintValidatorContext constraintValidatorContext) {
        return integers.size() > 0 && integers.stream().allMatch(Objects::nonNull);
    }
}
