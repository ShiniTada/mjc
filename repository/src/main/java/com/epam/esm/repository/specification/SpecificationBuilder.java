package com.epam.esm.repository.specification;

import com.epam.esm.repository.specification.impl.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.ASC;
import static com.epam.esm.repository.specification.ParameterName.DESC;

/**
 * Specification builder
 */
@Component
public class SpecificationBuilder {

    /**
     * Build specification
     *
     * @param tags            tags list
     * @param partName        part of certificate name
     * @param partDescription part of certificate description
     * @param dateSort        sort by date
     * @param nameSort        sort by name
     * @return specifications list
     */
    public List<Specification> buildSpecification(List<String> tags, String partName, String partDescription, String dateSort, String nameSort) {

        List<Specification> specifications = new ArrayList<>();

        if (tags != null && !tags.isEmpty()) {
            specifications.add(new SpecificationByTag(tags));
        }

        if (partName != null) {
            specifications.add(new SpecificationByName(partName));
        }

        if (partDescription != null) {
            specifications.add(new SpecificationByDescription(partDescription));
        }
        if (tags != null && !tags.isEmpty()) {
            specifications.add(new SpecificationGroupById());
        }

        if (dateSort != null && (dateSort.equals(ASC) || dateSort.equals(DESC))) {
            specifications.add(new SpecificationSortByDate(dateSort));
        }

        if (nameSort != null && (nameSort.equals(ASC) || nameSort.equals(DESC))) {
            specifications.add(new SpecificationSortByName(nameSort));
        }

        return specifications;
    }
}
