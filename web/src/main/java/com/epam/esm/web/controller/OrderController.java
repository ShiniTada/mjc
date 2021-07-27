package com.epam.esm.web.controller;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Order rest controller
 */
@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Instantiates a new Order controller
     *
     * @param orderService order service
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * Find order by id
     *
     * @param orderId order id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderById(@Min(1) @PathVariable long orderId) {
        OrderDto orderDto = orderService.findOrderById(orderId);
        buildOrderLinks(orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    private void buildOrderLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).findOrderById(orderDto.getId())).withSelfRel());
    }
}
