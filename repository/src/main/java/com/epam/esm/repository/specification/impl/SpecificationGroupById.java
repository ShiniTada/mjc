package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

import static com.epam.esm.repository.specification.ParameterName.GROUP_BY_ID;

public class SpecificationGroupById implements Specification {

    @Override
    public String buildHqlRequest() {
        return GROUP_BY_ID;
    }

}
