package com.assignment.company.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.assignment.company.dto.Product;
import com.assignment.company.model.Rule;
import com.assignment.company.model.condition.Condition;
import com.assignment.company.model.condition.EqualCondition;

class ScoreCalculatorTest {

    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    @Test
    void shouldCalculateEffectiveScore() {
        //given
        final String someAttributeName = "name1";
        final String matchingAttributeValue = "value1";
        final String otherAttributeName = "name2";
        final String nonMatchingAttributeValue = "value2";
        final String actualNonMatchingAttributeValue = "value3";
        final int ruleScore = 10;

        final Condition matchingCondition = new EqualCondition(someAttributeName, matchingAttributeValue);
        final Condition nonMatchingCondition = new EqualCondition(otherAttributeName, nonMatchingAttributeValue);
        final Rule rule = new Rule(List.of(matchingCondition, nonMatchingCondition), ruleScore);
        final Product product = new Product(Map.of(
                someAttributeName,
                matchingAttributeValue,
                otherAttributeName,
                actualNonMatchingAttributeValue
        ));

        //when
        final int result = scoreCalculator.calculateEffectiveScore(product, rule);

        //then
        assertThat(result).isEqualTo(List.of(matchingCondition).size() * 100 / List.of(
                matchingCondition,
                nonMatchingCondition
        ).size() * ruleScore / 100);
    }

    @Test
    void shouldReturnZero() {
        //given
        final Rule rule = new Rule(Collections.emptyList(), 10);
        final Product product = new Product(Collections.emptyMap());

        //when
        final int result = scoreCalculator.calculateEffectiveScore(product, rule);

        //then
        assertThat(result).isEqualTo(0);
    }
}