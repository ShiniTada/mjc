package com.epam.esm.service.converter;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

/**
 * Tag converter.
 */
@Component
public class TagConverter {
    /**
     * Tag convert to TagDto
     *
     * @param tag tag
     * @return tagDto
     */
    public TagDto convertToTagDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    /**
     * TagDto convert to Tag
     *
     * @param tagDto tagDto
     * @return tag
     */
    public Tag convertToTag(TagDto tagDto) {
        Tag tag = new Tag();
        if (tagDto.getId() != null) {
            tag.setId(tagDto.getId());
        }
        tag.setName(tagDto.getName());
        return tag;
    }
}
