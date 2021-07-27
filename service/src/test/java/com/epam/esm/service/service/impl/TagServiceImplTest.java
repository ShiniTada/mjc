

package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.converter.TagConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagRepository, tagConverter);
    }


    @AfterEach
    public void tearDown() {
        tagService = null;
    }

    @Test
    public void createTagTest1() {
        TagDto tagDto = createTagDto(1L, "name");
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagByName(tagDto.getName())).thenReturn(null);
        when(tagRepository.createTag(any())).thenReturn(tag);
        when(tagConverter.convertToTagDto(tag)).thenReturn(tagDto);
        tagService.createTag(tagDto);
        verify(tagRepository).createTag(any());
    }

    @Test
    public void createTagTest2() {
        TagDto tagDto = createTagDto(1L, "name");
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagByName(tag.getName())).thenReturn(tag);
        assertThrows(ServiceException.class, () -> tagService.createTag(tagDto));
    }


    @Test
    public void findAllTagsTest1() {
        int page = 1;
        int size = 1;
        List<Tag> tagList = createListTag(1L, "name");
        when(tagRepository.findAllTags(page, size)).thenReturn(tagList);
        tagService.findAllTags(page, size);
        verify(tagRepository).findAllTags(page, size);
    }

    @Test
    public void findAllTagsTest2() {
        int page = 1;
        int size = 1;
        List<Tag> tagList = null;
        when(tagRepository.findAllTags(page, size)).thenReturn(tagList);
        assertThrows(ResourceNotFoundException.class, () -> tagService.findAllTags(page, size));
    }


    @Test
    public void findTagByIdTest1() {
        TagDto tagDto = createTagDto(1L, "name");
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagById(tag.getId())).thenReturn(tag);
        tagService.findTagById(tagDto.getId());
        verify(tagRepository).findTagById(tag.getId());
    }

    @Test
    public void findTagByIdTest2() {
        TagDto tagDto = createTagDto(1L, "name");
        Tag tag = null;
        when(tagRepository.findTagById(tagDto.getId())).thenReturn(tag);
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(tagDto.getId()));
    }

    @Test
    public void deleteTagTest1() {
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagById(tag.getId())).thenReturn(tag);
        doNothing().when(tagRepository).deleteTag(tag);
        tagService.deleteTag(tag.getId());
        verify(tagRepository).deleteTag(tag);
    }

    @Test
    public void deleteTagTest2() {
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagById(tag.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag(tag.getId()));
    }

    @Test
    public void findTagByNameTest1() {
        String name = "name";
        Tag tag = createTag(1L, "name");
        when(tagRepository.findTagByName(name)).thenReturn(tag);
        when(tagConverter.convertToTagDto(tag)).thenReturn(any());
        tagService.findTagByName(name);
        verify(tagRepository).findTagByName(name);

    }

    @Test
    public void findTagByNameTest2() {
        String name = "name";
        when(tagRepository.findTagByName(name)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagByName(name));
    }

    @Test
    public void findTagByNameTest3() {
        String name = null;
        assertThrows(ServiceException.class, () -> tagService.findTagByName(name));
    }

    @Test
    public void findPopularTagTest1() {
        TagDto tagDto = createTagDto(1L, "name");
        Tag tag = createTag(1L, "name");
        when(tagRepository.findPopularTag()).thenReturn(tag);
        when(tagConverter.convertToTagDto(tag)).thenReturn(tagDto);
        tagService.findPopularTag();
        verify(tagRepository).findPopularTag();
    }

    @Test
    public void findPopularTagTest2() {
        when(tagRepository.findPopularTag()).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> tagService.findPopularTag());
    }

    private Tag createTag(long id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }

    private TagDto createTagDto(long id, String name) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);
        return tagDto;
    }

    private List<Tag> createListTag(long id, String name) {
        List<Tag> tagList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        tagList.add(tag);
        return tagList;
    }
}


