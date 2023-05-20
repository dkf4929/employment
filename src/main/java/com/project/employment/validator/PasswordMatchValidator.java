package com.project.employment.validator;

import com.project.employment.annotation.PasswordMatch;
import com.project.employment.dto.MemberSaveDto;
import com.project.request.MemberRq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(PasswordMatch constraint) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        MemberRq request = (MemberRq) obj;
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
