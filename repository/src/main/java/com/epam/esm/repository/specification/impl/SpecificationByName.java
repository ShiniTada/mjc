package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import static com.epam.esm.repository.specification.ParameterName.BY_NAME;


public class SpecificationByName implements Specification {
    private final String partName;

    public SpecificationByName(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
    }

    @Override
    public String buildHqlRequest() {
        return BY_NAME + " OR c.description LIKE CONCAT('%', :partName, '%')) ";
    }
}
