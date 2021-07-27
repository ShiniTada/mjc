package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.specification.Specification;

import javax.persistence.TypedQuery;
import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.*;

public class CertificatesBySpecification {

    public StringBuilder buildQuery(List<Specification> specifications) {
        StringBuilder hqlRequest = new StringBuilder("");
        boolean flagTags = true;
        boolean flagName = true;
        boolean isTwoSort = false;

        for (Specification specification : specifications) {
            if (specification instanceof SpecificationByTag) {
                if (flagTags) {
                    hqlRequest.append(specification.buildHqlRequest());
                    flagTags = false;
                }
            }

            if (specification instanceof SpecificationByName) {
                if (!flagTags) {
                    hqlRequest.append(AND).append(specification.buildHqlRequest());
                } else {
                    hqlRequest.append(WHERE).append(specification.buildHqlRequest());
                    flagName = false;
                }
            }
            if (specification instanceof SpecificationByDescription) {
                if (!flagTags || !flagName) {
                    hqlRequest.append(AND).append(specification.buildHqlRequest());
                } else {
                    hqlRequest.append(WHERE).append(specification.buildHqlRequest());
                }
            }
            if (specification instanceof SpecificationGroupById) {
                if (!flagTags) {
                    hqlRequest.append(specification.buildHqlRequest());
                }
            }

            if (specification instanceof SpecificationSortByDate) {
                if (isTwoSort) {
                    hqlRequest.append(DELIMITER).append(specification.buildHqlRequest()).append(((SpecificationSortByDate) specification).getDateSort());
                } else {
                    hqlRequest.append(ORDER_BY).append(specification.buildHqlRequest()).append(((SpecificationSortByDate) specification).getDateSort());
                    isTwoSort = true;
                }
            }
            if (specification instanceof SpecificationSortByName) {
                if (isTwoSort) {
                    hqlRequest.append(DELIMITER).append(specification.buildHqlRequest()).append(((SpecificationSortByName) specification).getNameSort());
                } else {
                    hqlRequest.append(ORDER_BY).append(specification.buildHqlRequest()).append(((SpecificationSortByName) specification).getNameSort());
                }
            }
        }

        return hqlRequest;
    }

    public TypedQuery<GiftCertificate> buildParams(TypedQuery<GiftCertificate> query, List<Specification> specifications) {

        String hqlRequest = buildQuery(specifications).toString();

        for (Specification specification : specifications) {

            if (specification instanceof SpecificationByTag) {
                query.setParameter(TAGS, ((SpecificationByTag) specification).getTags());
                query.setParameter(TAG_SIZE, (long) ((SpecificationByTag) specification).getTags().size());
            }
            if (specification instanceof SpecificationByName && hqlRequest.contains(PART_NAME)) {
                query.setParameter(PART_NAME, ((SpecificationByName) specification).getPartName());
            }
            if (specification instanceof SpecificationByDescription && hqlRequest.contains(PART_DESCRIPTION)) {
                query.setParameter(PART_DESCRIPTION, ((SpecificationByDescription) specification).getPartDescription());
            }
        }
        return query;
    }
}
