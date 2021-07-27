package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import static com.epam.esm.repository.specification.ParameterName.SORT_BY_DATE;


public class SpecificationSortByDate implements Specification {
    private final String dateSort;

    public SpecificationSortByDate(String dateSort) {
        this.dateSort = dateSort;
    }

    public String getDateSort() {
        return dateSort;
    }

    @Override
    public String buildHqlRequest() {
        return SORT_BY_DATE;
    }
}
