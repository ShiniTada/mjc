package com.epam.esm.service.dto;

import java.util.List;

public class PaginationDto {
   private List<GiftCertificateDto> giftCertificateDtoList;
    private long totalNumberItems;

    public PaginationDto() {
    }

    public PaginationDto(List<GiftCertificateDto> giftCertificateDtoList, long totalNumberItems) {
        this.giftCertificateDtoList = giftCertificateDtoList;
        this.totalNumberItems = totalNumberItems;
    }

    public List<GiftCertificateDto> getGiftCertificateDtoList() {
        return giftCertificateDtoList;
    }

    public void setGiftCertificateDtoList(List<GiftCertificateDto> giftCertificateDtoList) {
        this.giftCertificateDtoList = giftCertificateDtoList;
    }

    public long getTotalNumberItems() {
        return totalNumberItems;
    }

    public void setTotalNumberItems(long totalNumberItems) {
        this.totalNumberItems = totalNumberItems;
    }
}
