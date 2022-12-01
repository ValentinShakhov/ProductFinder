package com.assignment.company.exception;

public class UnableToParseConditionException extends RuntimeException {

    public UnableToParseConditionException(final String conditionString) {
        super(String.format("Unable to parse condition: %s", conditionString));
    }
}
