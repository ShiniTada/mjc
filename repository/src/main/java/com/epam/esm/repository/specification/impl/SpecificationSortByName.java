package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import static com.epam.esm.repository.specification.ParameterName.SORT_BY_NAME;


public class SpecificationSortByName implements Specification {

    private final String nameSort;

    public SpecificationSortByName(String nameSort) {
        this.nameSort = nameSort;
    }

    public String getNameSort() {
        return nameSort;
    }

    @Override
    public String buildHqlRequest() {
        return SORT_BY_NAME;
    }
}
