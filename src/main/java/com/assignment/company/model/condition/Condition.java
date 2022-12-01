package com.assignment.company.model.condition;

import java.util.Objects;

public abstract class Condition {
    public final String attributeName;

    protected Condition(final String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof final Condition condition))
            return false;
        return attributeName.equals(condition.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName);
    }

    public abstract boolean matches(final String attributeValue);
}
