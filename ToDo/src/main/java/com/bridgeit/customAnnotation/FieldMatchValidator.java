package com.bridgeit.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bridgeit.entity.User;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	@Override
	public boolean isValid(Object userObject, ConstraintValidatorContext context) {
		User user = (User) userObject;
		if (user.getPassword().equals(user.getConfirmPassword()))
			return true;
		return false;
	}

}
