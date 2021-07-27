package com.epam.esm.service.service;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.dto.TagDto;

import java.util.List;

/**
 * The interface tag service
 */
public interface TagService {

    /**
     * Tag creating
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    TagDto createTag(TagDto tagDto);

    /**
     * Find all tags
     *
     * @return tag dto list
     */
    List<TagDto> findAllTags(int page, int size);

    /**
     * Find tag by id
     *
     * @param id the tag id
     * @return the tag dto
     */
    TagDto findTagById(long id);

    /**
     * Delete tag
     *
     * @param id the tag id
     */
    void deleteTag(long id);

    /**
     * Find tag by tag name
     *
     * @param name the tag name
     * @return the tag dto
     */
    TagDto findTagByName(String name);

    /**
     * Relation creating
     *
     * @param giftCertificate the gift certificate Dto
     */
    List<Tag> createRelation(GiftCertificate giftCertificate);

    /**
     * Find popular tag
     *
     * @return tagDto
     */
    TagDto findPopularTag();
}
