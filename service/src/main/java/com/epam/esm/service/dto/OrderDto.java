package com.epam.esm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order dto
 */
@Relation(collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;
    private BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderTime;
    @Email
    private String userEmail;
    private GiftCertificateDto certificateDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public GiftCertificateDto getCertificateDto() {
        return certificateDto;
    }

    public void setCertificateDto(GiftCertificateDto certificateDto) {
        this.certificateDto = certificateDto;
    }
}
