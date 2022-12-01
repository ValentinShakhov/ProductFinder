package com.assignment.company.service;

import static com.assignment.company.service.RuleParser.AND_OPERATOR;
import static com.assignment.company.service.RuleParser.BETWEEN_OPERATOR;
import static com.assignment.company.service.RuleParser.CONDITION_SEPARATOR;
import static com.assignment.company.service.RuleParser.IN_OPERATOR;
import static com.assignment.company.service.RuleParser.SCORE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.assignment.company.exception.UnableToParseConditionException;
import com.assignment.company.model.Rule;
import com.assignment.company.model.condition.BetweenCondition;
import com.assignment.company.model.condition.EqualCondition;
import com.assignment.company.model.condition.InCondition;
import com.assignment.company.model.condition.LessCondition;

class RuleParserTest {

    private final RuleParser ruleParser = new RuleParser();

    @Test
    void parseRuleWithSingleCondition() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "10.50";

        //when
        final Rule result = ruleParser.parse(String.format(
                "%s < %s %s %s",
                attributeName1,
                conditionValue1,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactly(new LessCondition(
                attributeName1,
                Double.parseDouble(conditionValue1)
        ));
    }

    @Test
    void parseRuleWithUnknownCondition() {
        //given
        final String unknownSign = "unknownSign";

        //when
        assertThatExceptionOfType(UnableToParseConditionException.class)
                .isThrownBy(() -> ruleParser.parse(String.format(
                        "%s %s %s %s %s",
                        "attributeName1",
                        unknownSign,
                        "conditionValue1",
                        SCORE_SEPARATOR,
                        "10"
                )));
    }

    @Test
    void parseRuleWithSeveralSimpleConditions() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "10.50";
        final String attributeName2 = "attributeName2";
        final String conditionValue2 = "conditionValue2";
        //when

        final Rule result = ruleParser.parse(String.format(
                "%s < %s %s %s == %s %s %s",
                attributeName1,
                conditionValue1,
                CONDITION_SEPARATOR,
                attributeName2,
                conditionValue2,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactlyInAnyOrder(
                new LessCondition(attributeName1, Double.parseDouble(conditionValue1)),
                new EqualCondition(attributeName2, conditionValue2)
        );
    }

    @Test
    void parseRuleWithSimpleAndBetweenConditions() {
        //given
        final String score = "10";
        final String attributeName1 = "attributeName1";
        final String conditionValue1 = "10.50";
        final String betweenAttributeName = "betweenAttributeName";
        final String betweenAttributeValue1 = "10.0";
        final String betweenAttributeValue2 = "20.0";

        //when
        final Rule result = ruleParser.parse(String.format(
                "%s < %s %s %s %s %s %s %s %s %s",
                attributeName1,
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
                new LessCondition(attributeName1, Double.parseDouble(conditionValue1)),
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
        final String conditionValue1 = "10.50";
        final String inAttributeName = "inAttributeName";
        final String inAttributeValue1 = "inAttributeValue1";
        final String inAttributeValue2 = "inAttributeValue2";

        //when
        final Rule result = ruleParser.parse(String.format(
                "%s %s (%s, %s) %s %s < %s %s %s",
                inAttributeName,
                IN_OPERATOR,
                inAttributeValue1,
                inAttributeValue2,
                CONDITION_SEPARATOR,
                attributeName1,
                conditionValue1,
                SCORE_SEPARATOR,
                score
        ));

        //then
        assertThat(result.score()).isEqualTo(Integer.parseInt(score));
        assertThat(result.conditions()).containsExactlyInAnyOrder(
                new LessCondition(attributeName1, Double.parseDouble(conditionValue1)),
                new InCondition(inAttributeName, List.of(inAttributeValue1, inAttributeValue2))
        );
    }
}