package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import static com.epam.esm.repository.specification.ParameterName.BY_DESCRIPTION;


public class SpecificationByDescription implements Specification {

    private final String partDescription;

    public SpecificationByDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getPartDescription() {
        return partDescription;
    }

    @Override
    public String buildHqlRequest() {
        return BY_DESCRIPTION;
    }
}
