package com.epam.esm.service.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.PaginationDto;

import java.util.List;

/**
 * The interface gift certificate service
 */
public interface GiftCertificateService {
    /**
     * Gift certificate creating
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Find gift certificate by certificate id
     *
     * @param id the certificate id
     * @return the gift certificate dto
     */
    GiftCertificateDto findGiftCertificateById(long id);

    /**
     * Update the gift certificate
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Delete gift certificate
     *
     * @param id the certificate id
     */
    void deleteGiftCertificate(long id);

    /**
     * Find all cerificates
     *
     * @param page        page
     * @param size        size
     * @param tags        tags list
     * @param name        part of certificate name
     * @param description part of certificate description
     * @param dateSort    sort by date
     * @param nameSort    sort by name
     * @return PaginationDto
     */
    PaginationDto findCertificates(int page, int size, List<String> tags, String name, String description, String dateSort, String nameSort);

   // PaginationDto createPaginationDto(List<GiftCertificateDto> giftCertificateDtos);
}
