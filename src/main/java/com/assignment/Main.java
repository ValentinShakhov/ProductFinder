package com.assignment;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

import com.assignment.company.client.SalesmanApiStub;
import com.assignment.company.repository.FileBasedRuleRepository;
import com.assignment.company.service.ProductService;
import com.assignment.company.util.ScoreCalculator;
import com.assignment.company.util.RuleParser;

public class Main {

    private static final String COST_ATTRIBUTE_NAME = "cost";
    private static final String OUTPUT_FORMAT = "total: %s, avg: %s";

    public static void main(String[] args) throws IOException {
        final String rulesFileName = args[0];
        final String sampleProductsFileName = args[1];
        final int minScore = Integer.parseInt(args[2]);

        final ProductService productService = setupProductService(rulesFileName, sampleProductsFileName);

        final DoubleSummaryStatistics productsStatistics = productService.findProducts(minScore).stream()
                .map(product -> product.getAttributeValue(COST_ATTRIBUTE_NAME))
                .collect(Collectors.summarizingDouble(Double::valueOf));

        System.out.printf(OUTPUT_FORMAT, productsStatistics.getSum(), productsStatistics.getAverage());
    }

    private static ProductService setupProductService(final String rulesFileName, final String sampleProductsFileName)
            throws IOException {
        final FileBasedRuleRepository ruleRepository = new FileBasedRuleRepository(new RuleParser());
        final SalesmanApiStub salesmanApiClient = new SalesmanApiStub();

        ruleRepository.init(rulesFileName);
        salesmanApiClient.init(sampleProductsFileName);

        return new ProductService(ruleRepository, salesmanApiClient, new ScoreCalculator());
    }
}
