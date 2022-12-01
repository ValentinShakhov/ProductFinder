package com.assignment.company.model.condition;

public class BetweenCondition extends Condition {

    private final double firstValue;
    private final double secondValue;

    public BetweenCondition(
            String attributeName,
            double firstValue,
            double secondValue
    ) {
        super(attributeName);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    @Override
    public boolean matches(final String value) {
        final double valueAsDouble = Double.parseDouble(value);
        return firstValue <= valueAsDouble && valueAsDouble <= secondValue;
    }
}
