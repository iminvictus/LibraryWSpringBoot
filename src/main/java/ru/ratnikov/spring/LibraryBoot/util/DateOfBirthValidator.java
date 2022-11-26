package ru.ratnikov.spring.LibraryBoot.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {
    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))/02/29)$"
                    + "|^(((19|2[0-9])[0-9]{2})/02/(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})/(0[13578]|10|12)/(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})/(0[469]|11)/(0[1-9]|[12][0-9]|30))$");

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date != null)
            return false;
        else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                String s = formatter.format(date);
                return DATE_PATTERN.matcher(s).matches();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
