package com.assignment.company.dto;

import java.util.Map;

public record Product(Map<String, String> attributes) {

    public String getAttributeValue(String attributeName) {
        return attributes.get(attributeName);
    }
}
