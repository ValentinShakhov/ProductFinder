package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.assignment.company.model.condition.BetweenCondition;
import com.assignment.company.model.condition.Condition;

class BetweenConditionTest {

    private final Condition condition = new BetweenCondition("someName", 0, 10);

    @Test
    void shouldReturnTrue() {
        assertThat(condition.matches("5.50")).isTrue();
    }

    @Test
    void shouldReturnFalse() {
        assertThat(condition.matches("15.50")).isFalse();
    }
}