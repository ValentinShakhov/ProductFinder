package com.assignment.company.model.condition;

import java.util.Objects;

public class EqualCondition extends Condition {

    private final String value;

    public EqualCondition(final String attributeName, final String value) {
        super(attributeName);
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final EqualCondition that))
            return false;
        if (!super.equals(o))
            return false;

        return super.equals(that) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public boolean matches(final String value) {
        return this.value.equals(value);
    }
}
