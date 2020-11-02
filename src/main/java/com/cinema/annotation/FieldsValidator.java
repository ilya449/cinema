package com.cinema.annotation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValidator implements ConstraintValidator<FieldsConstraint, Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldsConstraint constraintAnnotation) {
        this.firstField = constraintAnnotation.field();
        this.secondField = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Object firstFieldValue = new BeanWrapperImpl(obj).getPropertyValue(firstField);
        Object secondFieldValue = new BeanWrapperImpl(obj).getPropertyValue(secondField);
        return Objects.equals(firstFieldValue, secondFieldValue);
    }
}
