package com.assignment.company.util;

import static com.assignment.company.util.RuleParser.AND_OPERATOR;
import static com.assignment.company.util.RuleParser.BETWEEN_OPERATOR;
import static com.assignment.company.util.RuleParser.CONDITION_SEPARATOR;
import static com.assignment.company.util.RuleParser.IN_OPERATOR;
import static com.assignment.company.util.RuleParser.SCORE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.assignment.company.exception.UnableToParseConditionException;
import com.assignment.company.model.Rule;
import com.assignment.company.model.condition.BetweenCondition;
import com.assignment.company.model.condition.InCondition;
import com.assignment.company.model.condition.SimpleCondition;
import com.assignment.company.util.RuleParser;

class RuleParserTest {

    private final RuleParser ruleParser = new RuleParser();

    @Test
    void parseRuleWithSingleCondition() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "conditionValue1";
        final SimpleCondition.Operator operator1 = SimpleCondition.Operator.LESS;

        //when
        final Rule result = ruleParser.toRule(String.format(
                "%s %s %s %s %s",
                attributeName1,
                operator1.sign,
                conditionValue1,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactly(new SimpleCondition(
                attributeName1,
                conditionValue1,
                operator1
        ));
    }

    @Test
    void parseRuleWithUnknownCondition() {
        //given
        final String unknownSign = "unknownSign";

        //when
        assertThatExceptionOfType(UnableToParseConditionException.class)
                .isThrownBy(() -> {
                    ruleParser.toRule(String.format(
                            "%s %s %s %s %s",
                            "attributeName1",
                            unknownSign,
                            "conditionValue1",
                            SCORE_SEPARATOR,
                            "10"
                    ));
                });
    }

    @Test
    void parseRuleWithSeveralSimpleConditions() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "conditionValue1";
        final SimpleCondition.Operator operator1 = SimpleCondition.Operator.LESS;
        final String attributeName2 = "attributeName2";
        final String conditionValue2 = "conditionValue2";
        final SimpleCondition.Operator operator2 = SimpleCondition.Operator.EQUALS;
        //when

        final Rule result = ruleParser.toRule(String.format(
                "%s %s %s %s %s %s %s %s %s",
                attributeName1,
                operator1.sign,
                conditionValue1,
                CONDITION_SEPARATOR,
                attributeName2,
                operator2.sign,
                conditionValue2,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactlyInAnyOrder(
                new SimpleCondition(attributeName1, conditionValue1, operator1),
                new SimpleCondition(attributeName2, conditionValue2, operator2)
        );
    }

    @Test
    void parseRuleWithSimpleAndBetweenConditions() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "conditionValue1";
        final SimpleCondition.Operator operator1 = SimpleCondition.Operator.LESS;
        final String betweenAttributeName = "betweenAttributeName";
        final String betweenAttributeValue1 = "10.0";
        final String betweenAttributeValue2 = "20.0";

        //when
        final Rule result = ruleParser.toRule(String.format(
                "%s %s %s %s %s %s %s %s %s %s %s",
                attributeName1,
                operator1.sign,
                conditionValue1,
                CONDITION_SEPARATOR,
                betweenAttributeName,
                BETWEEN_OPERATOR,
                betweenAttributeValue1,
                AND_OPERATOR,
                betweenAttributeValue2,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactlyInAnyOrder(
                new SimpleCondition(attributeName1, conditionValue1, operator1),
                new BetweenCondition(
                        betweenAttributeName,
                        Double.parseDouble(betweenAttributeValue1),
                        Double.parseDouble(betweenAttributeValue2)
                )
        );
    }

    @Test
    void parseRuleWithInAndSimpleConditions() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "conditionValue1";
        final SimpleCondition.Operator operator1 = SimpleCondition.Operator.LESS;
        final String inAttributeName = "inAttributeName";
        final String inAttributeValue1 = "inAttributeValue1";
        final String inAttributeValue2 = "inAttributeValue2";

        //when
        final Rule result = ruleParser.toRule(String.format(
                "%s %s (%s, %s) %s %s %s %s %s %s",
                inAttributeName,
                IN_OPERATOR,
                inAttributeValue1,
                inAttributeValue2,
                CONDITION_SEPARATOR,
                attributeName1,
                operator1.sign,
                conditionValue1,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactlyInAnyOrder(
                new SimpleCondition(attributeName1, conditionValue1, operator1),
                new InCondition(inAttributeName, List.of(inAttributeValue1, inAttributeValue2))
        );
    }
}