package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.GiftCertificateService;
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
 * Gift certificate rest controller.
 */
@Validated
@RestController
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Create gift certificate
     *
     * @param giftCertificateDto the gift certificate dto
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/certificates")
    public ResponseEntity<GiftCertificateDto> createGiftCertificate(@Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto giftCertificateDtoNew = giftCertificateService.createGiftCertificate(giftCertificateDto);
        buildCertificateLinks(giftCertificateDtoNew);
        buildTagsLinks(giftCertificateDtoNew);
        return new ResponseEntity<>(giftCertificateDtoNew, HttpStatus.CREATED);
    }

    /**
     * Find gift certificate by certificate id
     *
     * @param id the certificate id
     * @return response entity
     */
    @GetMapping(value = "/certificates/{id}")
    public ResponseEntity<GiftCertificateDto> findGiftCertificateById(@Min(1) @PathVariable long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findGiftCertificateById(id);
        buildCertificateLinks(giftCertificateDto);
        buildTagsLinks(giftCertificateDto);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    /**
     * Find all certificates
     *
     * @param page        page
     * @param size        size
     * @param tags        tags list
     * @param name        part of certificate name
     * @param description part of certificate description
     * @param dateSort    sort by date
     * @param nameSort    sort by name
     * @return response entity
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/certificates")
    public ResponseEntity<PaginationDto> findCertificates(
            @Valid @RequestParam(value = VALUE_PAGE, required = false, defaultValue = DEFAULT_PAGE)
            @Min(1) int page,
            @Valid @RequestParam(value = VALUE_SIZE, required = false, defaultValue = DEFAULT_SIZE)
            @Min(1) int size,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "dateSort", required = false) String dateSort,
            @RequestParam(value = "nameSort", required = false) String nameSort
    ) {
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateService.findCertificates(page, size, tags, name, description, dateSort, nameSort);
        giftCertificateDtos.forEach(giftCertificateDto -> {
            buildCertificateLinks(giftCertificateDto);
            buildTagsLinks(giftCertificateDto);
        });
        PaginationDto paginationDto = giftCertificateService.createPaginationDto(giftCertificateDtos);
        return new ResponseEntity<>(paginationDto, HttpStatus.OK);
    }

    /**
     * Update gift certificate
     *
     * @param id                 the certificate id
     * @param giftCertificateDto the gift certificate dto
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "/certificates/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@Min(1) @PathVariable long id,
                                                                    @RequestBody(required = false) GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto certificateDto = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        certificateDto.add(linkTo(methodOn(GiftCertificateController.class).updateGiftCertificate(certificateDto.getId(), certificateDto)).withSelfRel());
        certificateDto.add(linkTo(methodOn(GiftCertificateController.class).findGiftCertificateById(certificateDto.getId())).withRel(CURRENT_CERTIFICATE).withType(HttpMethod.GET.name()));
        certificateDto.add(linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(certificateDto.getId())).withRel(DELETE_CERTIFICATE).withType(HttpMethod.DELETE.name()));
        buildTagsLinks(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    /**
     * Delete gift certificate
     *
     * @param id the certificate id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/certificates/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@Min(1) @PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void buildCertificateLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).findGiftCertificateById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).updateGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel(UPDATE_CERTIFICATE).withType(HttpMethod.PATCH.name()));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(giftCertificateDto.getId())).withRel(DELETE_CERTIFICATE).withType(HttpMethod.DELETE.name()));

    }

    private void buildTagsLinks(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = giftCertificateDto.getTags();
        tags.forEach(tag -> {
            tag.add(linkTo(methodOn(TagController.class).findTagById(tag.getId())).withSelfRel());
            tag.add(linkTo(methodOn(TagController.class).deleteTag(tag.getId())).withRel(DELETE_TAG).withType(HttpMethod.DELETE.name()));
        });
    }
}
