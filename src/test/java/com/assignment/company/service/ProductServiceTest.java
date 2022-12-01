package com.assignment.company.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.company.client.SalesmanApiClient;
import com.assignment.company.dto.Product;
import com.assignment.company.model.Rule;
import com.assignment.company.repository.RuleRepository;
import com.assignment.company.service.ProductService;
import com.assignment.company.util.ScoreCalculator;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private SalesmanApiClient salesmanApiClient;

    @Mock
    private ScoreCalculator scoreCalculator;

    @InjectMocks
    private ProductService productService;

    @Mock
    private Product product1;

    @Mock
    private Product product2;

    @Mock
    private Rule rule1;

    @Mock
    private Rule rule2;

    @Test
    void shouldReturnOneProduct() {
        //given
        when(salesmanApiClient.getProducts()).thenReturn(List.of(product1, product2));
        when(ruleRepository.getRules()).thenReturn(List.of(rule1, rule2));
        when(scoreCalculator.calculateEffectiveScore(product1, rule1)).thenReturn(5);
        when(scoreCalculator.calculateEffectiveScore(product1, rule2)).thenReturn(6);
        when(scoreCalculator.calculateEffectiveScore(product2, rule1)).thenReturn(15);
        when(scoreCalculator.calculateEffectiveScore(product2, rule2)).thenReturn(-6);

        //when
        final Collection<Product> result = productService.findProducts(10);

        //then
        assertThat(result).containsExactly(product1);
    }
}