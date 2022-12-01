package com.assignment.company.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.assignment.company.exception.UnableToParseConditionException;
import com.assignment.company.model.condition.BetweenCondition;
import com.assignment.company.model.condition.Condition;
import com.assignment.company.model.Rule;
import com.assignment.company.model.condition.InCondition;
import com.assignment.company.model.condition.SimpleCondition;

public class RuleParser {

    public static final String CONDITION_SEPARATOR = "&&";
    public static final String SCORE_SEPARATOR = "->";
    public static final String BETWEEN_OPERATOR = "BETWEEN";
    public static final String AND_OPERATOR = "AND";
    public static final String IN_OPERATOR = "IN";
    private static final String ATTRIBUTE_NAME_GROUP = "attributeName";
    private static final String OPERATOR_GROUP = "operator";
    private static final String ATTRIBUTE_VALUE_GROUP = "attributeValue";
    private static final String ATTRIBUTE_VALUE_FIRST_GROUP = "attributeValueFirst";
    private static final String ATTRIBUTE_VALUE_SECOND_GROUP = "attributeValueSecond";
    private static final Pattern SIMPLE_CONDITION_PATTERN = Pattern.compile(
            String.format(
                    "(?<%s>.+)\s(?<%s>[=]{2}|[<,>])\s(?<%s>.+)",
                    ATTRIBUTE_NAME_GROUP,
                    OPERATOR_GROUP,
                    ATTRIBUTE_VALUE_GROUP
            ));

    private static final Pattern BETWEEN_CONDITION_PATTERN = Pattern.compile(
            String.format(
                    "(?<%s>.+)\s%s\s(?<%s>.+)\s%s\s(?<%s>.+)",
                    ATTRIBUTE_NAME_GROUP,
                    BETWEEN_OPERATOR,
                    ATTRIBUTE_VALUE_FIRST_GROUP,
                    AND_OPERATOR,
                    ATTRIBUTE_VALUE_SECOND_GROUP
            ));

    private static final Pattern IN_CONDITION_PATTERN = Pattern.compile(
            String.format(
                    "(?<%s>.+)\s%s\s\\((?<%s>.+)\\)",
                    ATTRIBUTE_NAME_GROUP,
                    IN_OPERATOR,
                    ATTRIBUTE_VALUE_GROUP
            ));

    public Rule toRule(final String ruleString) {
        final String[] conditionsAndScore = ruleString.split(" " + SCORE_SEPARATOR + " ");

        final String conditionsString = conditionsAndScore[0];
        final String score = conditionsAndScore[1];

        final Collection<Condition> conditions = Arrays.stream(conditionsString.split(" " + CONDITION_SEPARATOR + " "))
                .map(this::toCondition)
                .toList();

        return new Rule(conditions, Integer.parseInt(score));
    }

    private Condition toCondition(final String conditionString) {
        final Optional<Condition> simpleCondition = extractSimpleCondition(conditionString);
        if (simpleCondition.isPresent()) {
            return simpleCondition.get();
        }

        final Optional<Condition> betweenCondition = extractBetweenCondition(conditionString);
        if (betweenCondition.isPresent()) {
            return betweenCondition.get();
        }

        final Optional<Condition> inCondition = extractInCondition(conditionString);
        if (inCondition.isPresent()) {
            return inCondition.get();
        }

        throw new UnableToParseConditionException(conditionString);
    }

    private Optional<Condition> extractSimpleCondition(final String conditionString) {
        final Matcher matcher = SIMPLE_CONDITION_PATTERN.matcher(conditionString);

        if (matcher.find()) {
            final String attributeName = matcher.group(ATTRIBUTE_NAME_GROUP);
            final String operator = matcher.group(OPERATOR_GROUP);
            final String attributeValue = matcher.group(ATTRIBUTE_VALUE_GROUP);

            return Optional.of(new SimpleCondition(
                    attributeName,
                    attributeValue,
                    SimpleCondition.Operator.of(operator)
            ));
        }

        return Optional.empty();
    }

    private Optional<Condition> extractBetweenCondition(final String conditionString) {
        final Matcher matcher = BETWEEN_CONDITION_PATTERN.matcher(conditionString);

        if (matcher.find()) {
            final String attributeName = matcher.group(ATTRIBUTE_NAME_GROUP);
            final double attributeValueFirst = Double.parseDouble(matcher.group(ATTRIBUTE_VALUE_FIRST_GROUP));
            final double attributeValueSecond = Double.parseDouble(matcher.group(ATTRIBUTE_VALUE_SECOND_GROUP));

            return Optional.of(new BetweenCondition(
                    attributeName,
                    attributeValueFirst,
                    attributeValueSecond

            ));
        }

        return Optional.empty();
    }

    private Optional<Condition> extractInCondition(final String conditionString) {
        final Matcher matcher = IN_CONDITION_PATTERN.matcher(conditionString);

        if (matcher.find()) {
            final String attributeName = matcher.group(ATTRIBUTE_NAME_GROUP);
            final String attributeValues = matcher.group(ATTRIBUTE_VALUE_GROUP);

            return Optional.of(new InCondition(
                    attributeName,
                    Arrays.stream(attributeValues.split(", ")).toList()
            ));
        }

        return Optional.empty();
    }
}
