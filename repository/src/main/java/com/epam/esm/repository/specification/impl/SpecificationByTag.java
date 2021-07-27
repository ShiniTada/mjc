package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.BY_TAG;


public class SpecificationByTag implements Specification {

    private final List<String> tags;

    public SpecificationByTag(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String buildHqlRequest() {
        return BY_TAG;
    }
}
