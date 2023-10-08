package com.project.employment.validator;

import com.project.employment.annotation.PasswordMatch;
import com.project.employment.member.MemberInsertRq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(PasswordMatch constraint) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        MemberInsertRq request = (MemberInsertRq) obj;
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
