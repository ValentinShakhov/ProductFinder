package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GreaterConditionTest {

    private final Condition condition = new GreaterCondition("someName", 10d);

    @Test
    void shouldReturnTrue() {
        assertThat(condition.matches("15")).isTrue();
    }

    @Test
    void shouldReturnFalse() {
        assertThat(condition.matches("5")).isFalse();
    }
}