package com.assignment.company.model.condition;

import java.util.Collection;

public class InCondition extends Condition {

    private final Collection<String> values;

    public InCondition(
            String attributeName,
            Collection<String> values
    ) {
        super(attributeName);
        this.values = values;
    }

    @Override
    public boolean matches(final String value) {
        return values.contains(value);
    }
}
