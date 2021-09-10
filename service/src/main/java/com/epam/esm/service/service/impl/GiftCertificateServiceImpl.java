package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationBuilder;
import com.epam.esm.service.converter.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.service.TagService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gift certificate service implementation
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String THE_CERTIFICATE_IS_NOT_FOUND = "certificate.not.found";
    private static final String DATA_IS_NOT_VALID = "data.not.valid";


    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;
    private final GiftCertificateConverter giftCertificateConverter;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateRepository giftCertificate repository
     * @param tagService                tag service
     * @param giftCertificateConverter  giftCertificate converter
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagService tagService,
                                      GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagService = tagService;
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Transactional
    @Override
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateConverter.convertToGiftCertificate(giftCertificateDto);
        List<Tag> tags = tagService.createRelation(giftCertificate);
        giftCertificate.setTags(tags);
        LocalDateTime createDate = LocalDateTime.now();
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(createDate);
        giftCertificate = giftCertificateRepository.createGiftCertificate(giftCertificate);
        return giftCertificateConverter.convertToGiftCertificateDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findGiftCertificateById(id);
        if (giftCertificate != null) {
            return giftCertificateConverter.convertToGiftCertificateDto(giftCertificate);
        } else {
            throw new ResourceNotFoundException(THE_CERTIFICATE_IS_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        long id = giftCertificateDto.getId();
        String nameUpdate = giftCertificateDto.getName();
        String descriptionUpdate = giftCertificateDto.getDescription();
        BigDecimal priceUpdate = giftCertificateDto.getPrice();
        int durationUpdate = giftCertificateDto.getDurationInDays();
        GiftCertificate giftCertificate = giftCertificateRepository.findGiftCertificateById(id);
        if (giftCertificate != null) {
            checkFieldsToUpdate(nameUpdate, descriptionUpdate, priceUpdate, durationUpdate, giftCertificate);
            giftCertificateRepository.updateGiftCertificate(giftCertificate);
            GiftCertificate giftCertificateNew = giftCertificateRepository.findGiftCertificateById(giftCertificate.getId());
            return giftCertificateConverter.convertToGiftCertificateDto(giftCertificateNew);
        } else {
            throw new ResourceNotFoundException(THE_CERTIFICATE_IS_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void deleteGiftCertificate(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findGiftCertificateById(id);
        if (giftCertificate != null) {
            giftCertificateRepository.deleteGiftCertificate(giftCertificate);
        } else {
            throw new ResourceNotFoundException(THE_CERTIFICATE_IS_NOT_FOUND);
        }
    }

    @Override
    public List<GiftCertificateDto> findCertificates(int page, int size, List<String> tagNames, String partName, String partDescription, String dateSort, String nameSort) {
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        List<Specification> specifications = specificationBuilder.buildSpecification(tagNames, partName, partDescription, dateSort, nameSort);
        List<GiftCertificate> giftCertificates;
        if (CollectionUtils.isNotEmpty(specifications)) {
            giftCertificates = giftCertificateRepository.findCertificatesWithParams(page, size, specifications);
        } else {
            giftCertificates = giftCertificateRepository.findAllGiftCertificates(page, size);
        }
        if (giftCertificates == null || giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(THE_CERTIFICATE_IS_NOT_FOUND);
        } else {
            return giftCertificates.stream().map(giftCertificateConverter::convertToGiftCertificateDto)
                    .collect(Collectors.toList());
        }
    }
    @Override
    public PaginationDto createPaginationDto(List<GiftCertificateDto> giftCertificateDtoList){
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setGiftCertificateDtoList(giftCertificateDtoList);
        long totalNumberItems = giftCertificateRepository.getTotalNumberItems();
        paginationDto.setTotalNumberItems(totalNumberItems);
        return paginationDto;
    }

    private void checkFieldsToUpdate(String nameUpdate, String descriptionUpdate, BigDecimal priceUpdate, int durationUpdate, GiftCertificate giftCertificate) {
        if (nameUpdate != null) {
            if (nameUpdate.matches(".{3,50}")) {
                giftCertificate.setName(nameUpdate);
            } else {
                throw new ServiceException(DATA_IS_NOT_VALID);
            }
        }

        if (descriptionUpdate != null) {
            if (descriptionUpdate.matches(".{3,250}")) {
                giftCertificate.setDescription(descriptionUpdate);
            } else {
                throw new ServiceException(DATA_IS_NOT_VALID);
            }
        }
        if (priceUpdate != null) {
            if (priceUpdate.compareTo(BigDecimal.ZERO) > 0) {
                giftCertificate.setPrice(priceUpdate);
            } else {
                throw new ServiceException(DATA_IS_NOT_VALID);
            }
        }

        if (durationUpdate != 0) {
            if (durationUpdate > 0) {
                giftCertificate.setDurationInDays(durationUpdate);
            } else {
                throw new ServiceException(DATA_IS_NOT_VALID);
            }
        }
    }
}
