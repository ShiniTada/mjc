package com.epam.esm.web.controller;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;

import static com.epam.esm.web.controller.ParamName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * User rest controller
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    /**
     * Instantiates a new User controller
     *
     * @param userService  userService
     * @param orderService orderService
     */
    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    /**
     * Find all users
     *
     * @param page page
     * @param size size
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(@Valid @RequestParam(value = VALUE_PAGE, required = false, defaultValue = DEFAULT_PAGE)
                                                      @Min(1) int page,
                                                      @Valid @RequestParam(value = VALUE_SIZE, required = false, defaultValue = DEFAULT_SIZE)
                                                      @Min(1) int size) {
        List<UserDto> userDtos = userService.findAllUsers(page, size);
        userDtos.forEach(this::buildUserLinks);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * Find user by email
     *
     * @param email email
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or (@userSecurity.hasUserEmail(authentication, #email) " +
            "and hasAuthority('USER'))")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@Valid @Email @PathVariable String email) {
        UserDto userDto = userService.findUserByEmail(email);
        buildUserLinks(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Find user by id
     *
     * @param id user id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or (@userSecurity.hasUserId(authentication, #id) " +
            "and hasAuthority('USER'))")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findUserById(@Valid @Min(1) @PathVariable long id) {
        UserDto userDto = userService.findUserById(id);
        buildUserLinks(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Find all orders of user
     *
     * @param page page
     * @param size size
     * @param id   user id
     * @return response entity
     */
    @PreAuthorize("hasAuthority('ADMIN') or (@userSecurity.hasUserId(authentication, #id) " +
            "and hasAuthority('USER'))")
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> findAllOrdersOfUser(@Valid @RequestParam(value = VALUE_PAGE, required = false, defaultValue = DEFAULT_PAGE)
                                                              @Min(1) int page,
                                                              @Valid @RequestParam(value = VALUE_SIZE, required = false, defaultValue = DEFAULT_SIZE)
                                                              @Min(1) int size,
                                                              @Min(1) @PathVariable long id) {
        List<OrderDto> orderDtoList = orderService.findAllOrders(page, size, id);
        orderDtoList.forEach(this::buildOrderLinks);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto, @Min(1) @PathVariable long id) {
        orderDto = orderService.createOrder(orderDto, id);
        buildOrderLinks(orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    private void buildUserLinks(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).findUserByEmail(userDto.getEmail())).withSelfRel());
    }

    private void buildOrderLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).findOrderById(orderDto.getId())).withSelfRel());
    }
}
