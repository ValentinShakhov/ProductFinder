package com.assignment.company.service;

import java.util.Collection;
import java.util.stream.Collectors;

import com.assignment.company.client.SalesmanApiClient;
import com.assignment.company.dto.Product;
import com.assignment.company.util.ScoreCalculator;
import com.assignment.company.repository.RuleRepository;

public record ProductService(RuleRepository ruleRepository,
                             SalesmanApiClient salesmanApiClient,
                             ScoreCalculator scoreCalculator) {

    public Collection<Product> findProducts(final int minScore) {
        return salesmanApiClient.getProducts().stream()
                .filter(product -> hasSufficientScore(product, minScore))
                .collect(Collectors.toList());
    }

    private boolean hasSufficientScore(final Product product, final int minScore) {
        final int effectiveScoresSum = ruleRepository.getRules().stream()
                .map(rule -> scoreCalculator.calculateEffectiveScore(product, rule))
                .mapToInt(it -> it)
                .sum();

        return effectiveScoresSum >= minScore;
    }
}
