package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class InConditionTest {

    private final Condition condition = new InCondition("someName", List.of("a", "b", "c"));

    @Test
    void shouldReturnTrue() {
        assertThat(condition.matches("b")).isTrue();
    }

    @Test
    void shouldReturnFalse() {
        assertThat(condition.matches("d")).isFalse();
    }
}