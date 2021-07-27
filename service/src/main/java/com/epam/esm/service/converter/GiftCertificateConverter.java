package com.epam.esm.service.converter;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Gift certificate converter.
 */
@Component
public class GiftCertificateConverter {

    private final TagConverter tagConverter;

    /**
     * Instantiates a new GiftCertificate converter
     *
     * @param tagConverter tag converter
     */
    @Autowired
    public GiftCertificateConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    /**
     * Gift certificate convert to gift certificate dto
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate dto
     */
    public GiftCertificateDto convertToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDurationInDays(giftCertificate.getDurationInDays());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            List<TagDto> tagDto = tags
                    .stream()
                    .filter(Objects::nonNull)
                    .map(tagConverter::convertToTagDto)
                    .collect(Collectors.toList());
            giftCertificateDto.setTags(tagDto);
        }
        return giftCertificateDto;
    }

    /**
     * Gift certificate dto convert to gift certificate
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate
     */
    public GiftCertificate convertToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (giftCertificateDto.getId() != null) {
            giftCertificate.setId(giftCertificateDto.getId());
        }
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDurationInDays(giftCertificateDto.getDurationInDays());
        giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
        giftCertificate.setTags(giftCertificateDto.getTags()
                .stream()
                .map(tagConverter::convertToTag)
                .collect(Collectors.toList()));
        return giftCertificate;
    }
}

