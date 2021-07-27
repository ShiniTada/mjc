package com.epam.esm.service.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Tag dto.
 */
@Relation(collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    @NotBlank(message = "validation.tag.name")
    @Pattern(regexp = ".{3,50}", message = "validation.tag.name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
