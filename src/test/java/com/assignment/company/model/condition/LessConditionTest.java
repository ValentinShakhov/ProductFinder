package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LessConditionTest {

    private final Condition condition = new LessCondition("someName", 10d);

    @Test
    void shouldReturnTrue() {
        assertThat(condition.matches("5")).isTrue();
    }

    @Test
    void shouldReturnFalse() {
        assertThat(condition.matches("15")).isFalse();
    }
}