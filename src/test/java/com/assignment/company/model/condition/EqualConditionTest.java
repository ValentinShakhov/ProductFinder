package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EqualConditionTest {

    private final Condition condition = new EqualCondition("someName", "value");

    @Test
    void shouldReturnTrue() {
        assertThat(condition.matches("value")).isTrue();
    }

    @Test
    void shouldReturnFalse() {
        assertThat(condition.matches("otherValue")).isFalse();
    }
}