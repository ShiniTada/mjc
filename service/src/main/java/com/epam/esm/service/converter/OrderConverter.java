package com.epam.esm.service.converter;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Order converter
 */
@Component
public class OrderConverter {

    private final UserService userService;
    private final GiftCertificateConverter giftCertificateConverter;
    private final UserConverter userConverter;

    /**
     * Instantiates a new Order converter
     *
     * @param userService              user service
     * @param giftCertificateConverter giftCertificate converter
     * @param userConverter            user converter
     */
    @Autowired
    public OrderConverter(UserService userService, GiftCertificateConverter giftCertificateConverter, UserConverter userConverter) {
        this.userService = userService;
        this.giftCertificateConverter = giftCertificateConverter;
        this.userConverter = userConverter;
    }

    /**
     * Order convert to OrderDto
     *
     * @param order order
     * @return orderDto
     */
    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setOrderTime(order.getOrderTime());
        UserDto userDto = userConverter.convertToUserDto(order.getUser());
        orderDto.setUserEmail(userDto.getEmail());
        GiftCertificateDto giftCertificateDto = giftCertificateConverter.convertToGiftCertificateDto(order.getCertificate());
        orderDto.setCertificateDto(giftCertificateDto);
        return orderDto;
    }

    /**
     * OrderDto convert to order
     *
     * @param orderDto orderDto
     * @return order
     */
    public Order convertToOrder(OrderDto orderDto) {
        Order order = new Order();
        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }
        order.setPrice(orderDto.getPrice());
        order.setOrderTime(orderDto.getOrderTime());
        UserDto userDto = userService.findUserByEmail(orderDto.getUserEmail());
        User user = userConverter.convertToUser(userDto);
        order.setUser(user);
        GiftCertificate giftCertificate = giftCertificateConverter.convertToGiftCertificate(orderDto.getCertificateDto());
        order.setCertificate(giftCertificate);
        return order;
    }
}
