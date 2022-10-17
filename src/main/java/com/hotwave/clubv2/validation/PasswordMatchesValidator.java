package com.hotwave.clubv2.validation;

import com.hotwave.clubv2.anno.PasswordMatches;
import com.hotwave.clubv2.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-10-16 23:57:00
 */

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private final static Logger logger = LoggerFactory.getLogger(PasswordMatchesValidator.class);
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        logger.info("是否相等 : {}",user.getPassword().equals(user.getMatchingPassword()));
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
