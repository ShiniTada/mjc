package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static com.epam.esm.web.controller.ParamName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Tag rest controller.
 */
@Validated
@RestController
@RequestMapping("/tags")
public class TagController {


    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag
     *
     * @param tagDto the tag dto
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {
        TagDto tagDtoNew = tagService.createTag(tagDto);
        buildTagLinks(tagDtoNew);
        return new ResponseEntity<>(tagDtoNew, HttpStatus.CREATED);
    }

    /**
     * Find all tags
     *
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<TagDto>> findAllTags(
            @Valid @RequestParam(value = VALUE_PAGE, required = false, defaultValue = DEFAULT_PAGE)
            @Min(1) int page,
            @Valid @RequestParam(value = VALUE_SIZE, required = false, defaultValue = DEFAULT_SIZE)
            @Min(1) int size) {
        List<TagDto> tagDtos = tagService.findAllTags(page, size);
        tagDtos.forEach(this::buildTagLinks);
        return new ResponseEntity<>(tagDtos, HttpStatus.OK);
    }

    /**
     * Find tag by tag id
     *
     * @param id the tag id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findTagById(@Min(1) @PathVariable long id) {
        TagDto tagDto = tagService.findTagById(id);
        buildTagLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Find tag by tag name
     *
     * @param name the tag name
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(params = {"name"})
    public ResponseEntity<TagDto> findTagByName(@RequestParam(required = false) String name) {
        TagDto tagDto = tagService.findTagByName(name);
        buildTagLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Delete tag
     *
     * @param id the tag id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTag(@Min(1) @PathVariable long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find popular tag
     *
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/popular-tag")
    public ResponseEntity<TagDto> findPopularTag() {
        TagDto tagDto = tagService.findPopularTag();
        buildTagLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    private void buildTagLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).findTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel(DELETE_TAG).withType(HttpMethod.DELETE.name()));
    }
}
