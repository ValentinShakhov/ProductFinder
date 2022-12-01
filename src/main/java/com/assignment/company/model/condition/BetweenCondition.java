package com.assignment.company.model.condition;

public class BetweenCondition extends Condition {

    private final double firstConditionValue;
    private final double secondConditionValue;

    public BetweenCondition(
            String attributeName,
            double firstConditionValue,
            double secondConditionValue
    ) {
        super(attributeName);
        this.firstConditionValue = firstConditionValue;
        this.secondConditionValue = secondConditionValue;
    }

    @Override
    public boolean matches(final String value) {
        final double valueAsDouble = Double.parseDouble(value);
        return firstConditionValue <= valueAsDouble && valueAsDouble <= secondConditionValue;
    }
}
