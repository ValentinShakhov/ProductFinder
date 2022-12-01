package com.assignment.company.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.assignment.company.dto.Product;

public class SalesmanApiStub implements SalesmanApiClient {

    private final Collection<Product> products = new ArrayList<>();

    private static Product toProduct(final String productString) {
        return new Product(Arrays.stream(productString.split(";"))
                .map(attribute -> attribute.split(":"))
                .collect(Collectors.toMap(it -> it[0], it -> it[1])));
    }

    @Override
    public Collection<Product> getProducts() {
        return Collections.unmodifiableCollection(products);
    }

    public void init(final String productsFileName) throws IOException {
        try (final FileReader fileReader = new FileReader(productsFileName)) {
            new BufferedReader(fileReader).lines()
                    .map(SalesmanApiStub::toProduct)
                    .forEach(products::add);
        }
    }
}
