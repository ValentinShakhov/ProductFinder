package com.assignment.company.client;

import java.util.Collection;

import com.assignment.company.dto.Product;

public interface SalesmanApiClient {

    Collection<Product> getProducts();
}
