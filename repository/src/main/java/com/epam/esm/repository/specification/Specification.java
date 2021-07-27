package com.epam.esm.repository.specification;

/**
 * The interface specification
 */
public interface Specification {
    /**
     * Build hql request
     *
     * @return hql request
     */
    String buildHqlRequest();
}
