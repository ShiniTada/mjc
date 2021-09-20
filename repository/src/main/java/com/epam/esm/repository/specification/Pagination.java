package com.epam.esm.repository.specification;

import com.epam.esm.repository.entity.GiftCertificate;

import java.util.List;

public class Pagination {
    private List<GiftCertificate> giftCertificateList;
    private long totalNumberItems;

    public Pagination() {
    }

    public Pagination(List<GiftCertificate> giftCertificateList, long totalNumberItems) {
        this.giftCertificateList = giftCertificateList;
        this.totalNumberItems = totalNumberItems;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    public long getTotalNumberItems() {
        return totalNumberItems;
    }

    public void setTotalNumberItems(long totalNumberItems) {
        this.totalNumberItems = totalNumberItems;
    }
}
