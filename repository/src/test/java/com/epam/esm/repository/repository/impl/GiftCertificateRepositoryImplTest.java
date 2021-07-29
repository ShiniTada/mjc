

package com.epam.esm.repository.repository.impl;

import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestRepositoryConfig.class)
@Transactional
public class GiftCertificateRepositoryImplTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    public void createGiftCertificateTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("name_test");
        giftCertificate.setDescription("description_test");
        giftCertificate.setPrice(BigDecimal.valueOf(10.0));
        giftCertificate.setDurationInDays(10);
        GiftCertificate actual = giftCertificateRepository.createGiftCertificate(giftCertificate);
        assertNotNull(actual);
        assertTrue(actual.getId() > 0);
    }

    @Test
    public void findAllGiftCertificatesPositiveTest() {
        insertCertificate("certificate1", "description1",
                BigDecimal.valueOf(10.0), 1, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate2", "description2",
                BigDecimal.valueOf(20.0), 2, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate3", "description3",
                BigDecimal.valueOf(30.0), 3, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findAllGiftCertificates(1, 5);
        assertEquals(3, giftCertificateList.size());
    }

    @Test
    public void findAllGiftCertificatesNegativeTest() {
        insertCertificate("certificate1", "description1",
                BigDecimal.valueOf(10.0), 1, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate2", "description2",
                BigDecimal.valueOf(20.0), 2, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate3", "description3",
                BigDecimal.valueOf(30.0), 3, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findAllGiftCertificates(1, 5);
        //
        assertEquals(2, giftCertificateList.size());
    }


    @Test
    public void findCertificatesWithParamsPositiveTest() {
        insertCertificate("certificate1", "description1",
                BigDecimal.valueOf(10.0), 1, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate2", "description2",
                BigDecimal.valueOf(20.0), 2, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        insertCertificate("certificate3", "description3",
                BigDecimal.valueOf(30.0), 3, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        List<Specification> specifications = specificationBuilder.buildSpecification(null, "certificate1", null, null, null);
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findCertificatesWithParams(1, 10, specifications);
        assertEquals("description1", giftCertificateList.get(0).getDescription());
    }

    @Test
    public void findCertificatesWithParamsNegativeTest() {
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        List<Specification> specifications = specificationBuilder.buildSpecification(null, "certificate1", null, null, null);
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findCertificatesWithParams(1, 10, specifications);
        assertNotEquals(3, giftCertificateList.size());
    }

    @Test
    public void findGiftCertificateByIdTest() {
        insertCertificate("certificate1", "description1",
                BigDecimal.valueOf(10.0), 1, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        GiftCertificate actual = giftCertificateRepository.findGiftCertificateById(1);
        assertNotNull(actual);
        assertEquals("certificate1", actual.getName());
        assertEquals("description1", actual.getDescription());
        assertEquals(BigDecimal.valueOf(10.0), actual.getPrice());
    }

    @Test
    public void updateGiftCertificateTest() {
        GiftCertificate giftCertificate = insertCertificate("certificate1", "description1",
                BigDecimal.valueOf(10.0), 1, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        giftCertificate.setName("update");
        giftCertificateRepository.updateGiftCertificate(giftCertificate);
        GiftCertificate giftCertificateNew = giftCertificateRepository.findGiftCertificateById(giftCertificate.getId());
        assertEquals(giftCertificateNew.getName(), "update");
    }

    private GiftCertificate insertCertificate(String name, String description, BigDecimal price, int durationInDays, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setName(name);
        giftCertificate1.setDescription(description);
        giftCertificate1.setPrice(price);
        giftCertificate1.setDurationInDays(durationInDays);
        giftCertificate1.setCreateDate(createDate);
        giftCertificate1.setLastUpdateDate(lastUpdateDate);
        return giftCertificateRepository.createGiftCertificate(giftCertificate1);
    }
}



