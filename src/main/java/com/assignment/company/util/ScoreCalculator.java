package com.assignment.company.util;

import java.util.Collection;

import com.assignment.company.model.condition.Condition;
import com.assignment.company.dto.Product;
import com.assignment.company.model.Rule;

public class ScoreCalculator {

    public int calculateEffectiveScore(final Product product, final Rule rule) {
        final Collection<Condition> allConditions = rule.getConditions();

        if (allConditions.isEmpty()) {
            return 0;
        }

        final Collection<Condition> matchedConditions = allConditions.stream()
                .filter(condition -> matches(product, condition))
                .toList();
        final int matchPercentage = matchedConditions.size() * 100 / allConditions.size();

        return matchPercentage * rule.getScore();
    }

    private boolean matches(final Product product, final Condition condition) {
        final String attributeName = condition.attributeName;
        final String attributeValue = product.getAttributeValue(attributeName);

        return condition.matches(attributeValue);
    }
}
