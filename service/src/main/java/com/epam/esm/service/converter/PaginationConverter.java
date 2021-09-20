package com.epam.esm.service.converter;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.specification.Pagination;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PaginationConverter {
    private final GiftCertificateConverter giftCertificateConverter;

    @Autowired
    public PaginationConverter(GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateConverter = giftCertificateConverter;
    }

    public PaginationDto convertToPaginationDto(Pagination pagination) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setTotalNumberItems(pagination.getTotalNumberItems());
        List<GiftCertificate> giftCertificateList = pagination.getGiftCertificateList();
        if (giftCertificateList != null) {
            List<GiftCertificateDto> giftCertificateDtoList = giftCertificateList
                    .stream()
                    .filter(Objects::nonNull)
                    .map(giftCertificateConverter::convertToGiftCertificateDto)
                    .collect(Collectors.toList());
            paginationDto.setGiftCertificateDtoList(giftCertificateDtoList);
        }
        return paginationDto;
    }
}
