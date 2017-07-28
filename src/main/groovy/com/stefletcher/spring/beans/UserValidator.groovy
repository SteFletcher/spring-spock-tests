package com.stefletcher.spring.beans

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

/**
 * Created by ste on 13/07/17.
 */

class UserValidator implements Validator{
    @Override
    boolean supports(Class<?> clazz) {
        return User.class == clazz
    }

    @Override
    void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "email", "user.email.empty")
    }
}
