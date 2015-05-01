package com.fiubapp.fiubapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String PHONE_PATTERN =
            "^[0-9]{7,}$";

    public PhoneNumberValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}