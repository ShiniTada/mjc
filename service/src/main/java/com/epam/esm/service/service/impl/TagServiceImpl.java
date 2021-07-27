package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.converter.TagConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tag service implementation
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagConverter tagConverter;

    private static final String TAG_IS_EXIST = "tag.exist";
    private static final String TAG_IS_NOT_FOUND = "tag.not.found";
    private static final String TAG_IS_EMPTY = "tag.empty";

    /**
     * Instantiates a new tag service.
     *
     * @param tagRepository the tag repository
     */
    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagConverter tagConverter) {
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Transactional
    @Override
    public TagDto createTag(TagDto tagDto) {

        if (tagRepository.findTagByName(tagDto.getName()) != null) {
            throw new ServiceException(TAG_IS_EXIST);
        } else {
            Tag tag = new Tag();
            tag.setName(tagDto.getName());
            tag = tagRepository.createTag(tag);
            return tagConverter.convertToTagDto(tag);
        }
    }

    @Override
    public List<TagDto> findAllTags(int page, int size) {
        List<Tag> tags;
        tags = tagRepository.findAllTags(page, size);
        if (tags == null) {
            throw new ResourceNotFoundException(TAG_IS_NOT_FOUND);
        } else {
            return tags.stream().map(tagConverter::convertToTagDto).collect(Collectors.toList());
        }
    }

    @Override
    public TagDto findTagById(long id) {

        Tag tag = tagRepository.findTagById(id);
        if (tag != null) {
            return tagConverter.convertToTagDto(tag);
        } else {
            throw new ResourceNotFoundException(TAG_IS_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void deleteTag(long id) {
        Tag tag = tagRepository.findTagById(id);
        if (tag != null) {
            tagRepository.deleteTag(tag);
        } else {
            throw new ResourceNotFoundException(TAG_IS_NOT_FOUND);
        }
    }

    @Override
    public TagDto findTagByName(String name) {
        if (name == null) {
            throw new ServiceException(TAG_IS_EMPTY);
        } else {
            Tag tag = tagRepository.findTagByName(name);
            if (tag != null) {
                return tagConverter.convertToTagDto(tag);
            } else {
                throw new ResourceNotFoundException(TAG_IS_NOT_FOUND);
            }
        }
    }

    @Override
    public List<Tag> createRelation(GiftCertificate giftCertificate) {
        List<Tag> currentDtoTags = giftCertificate.getTags();
        List<Tag> entityTags = new ArrayList<>();
        for (Tag tag : currentDtoTags) {
            Tag tagName = tagRepository.findTagByName(tag.getName());
            if (tagName != null) {
                tagName.getCertificates().add(giftCertificate);
                entityTags.add(tagName);
            } else {
                Tag tagWithoutId = new Tag(tag.getName());
                List<GiftCertificate> tagCertificates = new ArrayList<>();
                tagCertificates.add(giftCertificate);
                tagWithoutId.setCertificates(tagCertificates);
                Tag tagWithId = tagRepository.createTag(tagWithoutId);
                entityTags.add(tagWithId);
            }
        }
        return entityTags;
    }

    @Override
    public TagDto findPopularTag() {
        Tag tag = tagRepository.findPopularTag();
        if (tag != null) {
            return tagConverter.convertToTagDto(tag);
        } else {
            throw new ResourceNotFoundException(TAG_IS_NOT_FOUND);
        }
    }
}

