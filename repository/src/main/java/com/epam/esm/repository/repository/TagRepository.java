package com.epam.esm.repository.repository;

import com.epam.esm.repository.entity.Tag;

import java.util.List;

/**
 * The interface tag repository
 */
public interface TagRepository {

    /**
     * Create tag
     *
     * @param tag the tag
     * @return the tag
     */
    Tag createTag(Tag tag);

    /**
     * Find all tags
     *
     * @param page page
     * @param size size
     * @return tags list
     */
    List<Tag> findAllTags(int page, int size);

    /**
     * Find tag by id
     *
     * @param id the tag id
     * @return the tag
     */
    Tag findTagById(long id);

    /**
     * Find tag by tag name
     *
     * @param tagName the tag name
     * @return the tag
     */
    Tag findTagByName(String tagName);

    /**
     * Delete tag
     *
     * @param tag the tag id
     */
    void deleteTag(Tag tag);

    /**
     * Find popular tag
     *
     * @return tag
     */
    Tag findPopularTag();
}
