package com.assignment.company.model.condition;

import java.util.Objects;

public class LessCondition extends Condition {

    private final double value;

    public LessCondition(final String attributeName, final double value) {
        super(attributeName);
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final LessCondition that))
            return false;
        if (!super.equals(o))
            return false;

        return super.equals(that) && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public boolean matches(final String value) {
        return this.value > Double.parseDouble(value);
    }
}
