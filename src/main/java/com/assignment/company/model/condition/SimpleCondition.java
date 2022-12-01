package com.assignment.company.model.condition;

import java.util.Arrays;
import java.util.Objects;

public class SimpleCondition extends Condition {
    public final String conditionValue;
    public final Operator operator;

    public SimpleCondition(
            final String attributeName,
            final String conditionValue,
            final Operator operator
    ) {
        super(attributeName);
        this.conditionValue = conditionValue;
        this.operator = operator;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final SimpleCondition that))
            return false;
        if (!super.equals(o))
            return false;

        return super.equals(that) && conditionValue.equals(that.conditionValue) && operator == that.operator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), conditionValue, operator);
    }

    @Override
    public boolean matches(final String value) {
        if (value == null) {
            return false;
        }

        return switch (operator) {
            case LESS -> Double.parseDouble(conditionValue) > Double.parseDouble(value);
            case GREATER -> Double.parseDouble(conditionValue) < Double.parseDouble(value);
            case EQUALS -> conditionValue.equalsIgnoreCase(value);
        };
    }

    public enum Operator {
        LESS("<"),
        GREATER(">"),
        EQUALS("==");

        public final String sign;

        Operator(final String sign) {
            this.sign = sign;
        }

        public static Operator of(final String value) {
            return Arrays.stream(values()).filter(val -> val.sign.equals(value))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unrecognized operator"));
        }
    }
}
