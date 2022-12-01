package com.assignment.company.model;

import java.util.Collection;
import java.util.Collections;

import com.assignment.company.model.condition.Condition;

public record Rule(Collection<Condition> conditions, int score) {

    public int getScore() {
        return score;
    }

    public Collection<Condition> getConditions() {
        return Collections.unmodifiableCollection(conditions);
    }
}
