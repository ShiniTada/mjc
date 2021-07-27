

package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.service.converter.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateConverter giftCertificateConverter;

    @BeforeEach
    public void setUp() {
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, tagService, giftCertificateConverter);
    }

    @AfterEach
    public void tearDown() {
        giftCertificateService = null;
    }

    @Test
    public void createGiftCertificateTest1() {
        GiftCertificateDto giftCertificateDto = createGiftCertificateDto(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        TagDto tagDto = createTagDto(1L, "name");
        List<TagDto> tagDtoList = createListTagDto(tagDto);
        giftCertificateDto.setTags(tagDtoList);
        GiftCertificate giftCertificate = createGiftCertificate(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        Tag tag = createTag(1L, "name");
        List<Tag> tagList = createListTag(tag);
        giftCertificate.setTags(tagList);
        when(giftCertificateConverter.convertToGiftCertificate(giftCertificateDto)).thenReturn(giftCertificate);
        when(tagService.createRelation(giftCertificate)).thenReturn(tagList);
        when(giftCertificateRepository.createGiftCertificate(any())).thenReturn(giftCertificate);
        giftCertificateService.createGiftCertificate(giftCertificateDto);
        verify(giftCertificateRepository).createGiftCertificate(any());
    }


    @Test
    public void findGiftCertificateByIdTest1() {
        GiftCertificate giftCertificate = createGiftCertificate(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        when(giftCertificateRepository.findGiftCertificateById(giftCertificate.getId())).thenReturn(giftCertificate);
        giftCertificateService.findGiftCertificateById(giftCertificate.getId());
        verify(giftCertificateRepository).findGiftCertificateById(giftCertificate.getId());
    }

    @Test
    public void findGiftCertificateByIdTest2() {
        long id = 1;
        when(giftCertificateRepository.findGiftCertificateById(id)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.findGiftCertificateById(id);
        });
    }

    @Test
    public void updateGiftCertificateTest1() {
        GiftCertificateDto giftCertificateDto = createGiftCertificateDto(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        GiftCertificate giftCertificate = createGiftCertificate(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        when(giftCertificateRepository.findGiftCertificateById(giftCertificate.getId())).thenReturn(giftCertificate);
        doNothing().when(giftCertificateRepository).updateGiftCertificate(giftCertificate);
        when(giftCertificateConverter.convertToGiftCertificateDto(giftCertificate)).thenReturn(giftCertificateDto);
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
        verify(giftCertificateRepository).updateGiftCertificate(any());
    }

    @Test
    public void updateGiftCertificateTest2() {
        GiftCertificateDto giftCertificateDto = createGiftCertificateDto(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        when(giftCertificateRepository.findGiftCertificateById(giftCertificateDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.updateGiftCertificate(giftCertificateDto);
        });
    }

    @Test
    public void deleteGiftCertificateTest1() {
        GiftCertificate giftCertificate = createGiftCertificate(1L, "name", "description",
                BigDecimal.valueOf(10.0), 10, LocalDateTime.of(2021, 6, 10, 15, 0, 0),
                LocalDateTime.of(2021, 6, 10, 15, 0, 0));
        when(giftCertificateRepository.findGiftCertificateById(giftCertificate.getId())).thenReturn(giftCertificate);
        doNothing().when(giftCertificateRepository).deleteGiftCertificate(giftCertificate);
        giftCertificateService.deleteGiftCertificate(giftCertificate.getId());
        verify(giftCertificateRepository).deleteGiftCertificate(giftCertificate);
    }

    @Test
    public void deleteGiftCertificateTest2() {
        long id = 1;
        when(giftCertificateRepository.findGiftCertificateById(id)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.deleteGiftCertificate(id);
        });
    }

    @Test
    public void findCertificatesTest1() {
        when(giftCertificateRepository.findCertificatesWithParams(anyInt(), anyInt(), anyList())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.findCertificates(1, 10, null, "name", null, null, null);
        });
    }

    @Test
    public void findCertificatesTest2() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);
        when(giftCertificateRepository.findAllGiftCertificates(1, 10)).thenReturn(giftCertificateList);
        giftCertificateService.findCertificates(1, 10, null, null, null, null, null);
        verify(giftCertificateRepository).findAllGiftCertificates(1, 10);
    }

    private GiftCertificateDto createGiftCertificateDto(long id, String name, String description, BigDecimal price,
                                                        int durationInDays, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(id);
        giftCertificateDto.setName(name);
        giftCertificateDto.setDescription(description);
        giftCertificateDto.setPrice(price);
        giftCertificateDto.setDurationInDays(durationInDays);
        giftCertificateDto.setCreateDate(createDate);
        giftCertificateDto.setLastUpdateDate(lastUpdateDate);
        return giftCertificateDto;
    }

    private GiftCertificate createGiftCertificate(long id, String name, String description, BigDecimal price,
                                                  int durationInDays, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDurationInDays(durationInDays);
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);
        return giftCertificate;
    }

    private TagDto createTagDto(long id, String name) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);
        return tagDto;
    }

    private Tag createTag(long id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }

    private List<TagDto> createListTagDto(TagDto tagDto) {
        List<TagDto> tagDtoList = new ArrayList<>();
        tagDtoList.add(tagDto);
        return tagDtoList;
    }

    private List<Tag> createListTag(Tag tag) {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        return tagList;
    }
}


