package com.assignment.company.model.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.assignment.company.model.condition.Condition;
import com.assignment.company.model.condition.SimpleCondition;

class SimpleConditionTest {

    private static Stream<Arguments> simpleConditionTestParameters() {
        return Stream.of(
                Arguments.of(SimpleCondition.Operator.EQUALS, "a", "a", true),
                Arguments.of(SimpleCondition.Operator.EQUALS, "a", "b", false),
                Arguments.of(SimpleCondition.Operator.LESS, "10.50", "5.50", true),
                Arguments.of(SimpleCondition.Operator.LESS, "10.50", "15.15", false),
                Arguments.of(SimpleCondition.Operator.GREATER, "10.50", "15.15", true),
                Arguments.of(SimpleCondition.Operator.GREATER, "10.50", "5.50", false)
        );
    }

    @ParameterizedTest
    @MethodSource("simpleConditionTestParameters")
    void testSimpleCondition(
            SimpleCondition.Operator operator,
            String conditionValue,
            String attributeValue,
            boolean expectedResult
    ) {
        final Condition condition = new SimpleCondition("someName", conditionValue, operator);

        assertThat(condition.matches(attributeValue)).isEqualTo(expectedResult);
    }
}