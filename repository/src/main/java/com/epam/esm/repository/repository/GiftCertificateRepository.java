package com.epam.esm.repository.repository;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.specification.Pagination;
import com.epam.esm.repository.specification.Specification;

import java.util.List;

/**
 * The interface gift certificate repository
 */
public interface GiftCertificateRepository {
    /**
     * Create gift certificate
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Find all certificates
     *
     * @param page page
     * @param size size
     * @return pagination
     */
    Pagination findAllGiftCertificates(int page, int size);

    /**
     * Find certificates with parameters
     *
     * @param page           page
     * @param size           size
     * @param specifications specifications list
     * @return Pagination
     */
    Pagination findCertificatesWithParams(int page, int size, List<Specification> specifications);

    /**
     * Find gift certificate by certificate id
     *
     * @param id the certificate id
     * @return the gift certificate
     */
    GiftCertificate findGiftCertificateById(long id);

    /**
     * Update the gift certificate
     *
     * @param giftCertificate the gift certificate
     */
    void updateGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Delete gift certificate
     *
     * @param giftCertificate the gift certificate
     */
    void deleteGiftCertificate(GiftCertificate giftCertificate);

    /*long getTotalNumberItems();*/
}
