package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.OrderRepository;
import com.epam.esm.service.converter.OrderConverter;
import com.epam.esm.service.converter.UserConverter;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order service implementation
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_IS_NOT_FOUND = "order.not.found";
    private static final String USER_IS_NOT_FOUND = "user.not.found";
    private static final String CERTIFICATE_IS_NOT_FOUND = "certificate.not.found";

    private final GiftCertificateService giftCertificateService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;

    /**
     * Instantiates a new order service.
     *
     * @param giftCertificateService giftCertificate service
     * @param userService            user service
     * @param orderRepository        order repository
     * @param orderConverter         order converter
     * @param userConverter          user converter
     */
    @Autowired
    public OrderServiceImpl(GiftCertificateService giftCertificateService, UserService userService,
                            OrderRepository orderRepository, OrderConverter orderConverter, UserConverter userConverter) {
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userConverter = userConverter;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto, Long userId) {
        UserDto userDto = userService.findUserById(userId);
        if (userDto == null) {
            throw new ServiceException(USER_IS_NOT_FOUND);
        }
        Long certificateId = orderDto.getCertificateDto().getId();
        GiftCertificateDto giftCertificateDto = giftCertificateService.findGiftCertificateById(certificateId);
        if (giftCertificateDto == null) {
            throw new ServiceException(CERTIFICATE_IS_NOT_FOUND);
        }
        orderDto.setUserEmail(userDto.getEmail());
        orderDto.setCertificateDto(giftCertificateDto);
        orderDto.setOrderTime(LocalDateTime.now());
        orderDto.setPrice(giftCertificateDto.getPrice());
        Order order = orderConverter.convertToOrder(orderDto);
        Order orderNew = orderRepository.createOrder(order);
        return orderConverter.convertToOrderDto(orderNew);
    }

    @Override
    public List<OrderDto> findAllOrders(int page, int size, long userId) {
        UserDto userDto = userService.findUserById(userId);
        if (userDto != null) {
            User user = userConverter.convertToUser(userDto);
            List<Order> orders = orderRepository.findAllOrders(page, size, user);
            if (CollectionUtils.isNotEmpty(orders)) {
                List<OrderDto> orderDtoList = orders.stream().map(orderConverter::convertToOrderDto).collect(Collectors.toList());
                return orderDtoList;
            } else {
                throw new ResourceNotFoundException(ORDER_IS_NOT_FOUND);
            }
        } else {
            throw new ResourceNotFoundException(USER_IS_NOT_FOUND);
        }
    }

    @Override
    public OrderDto findOrderById(long id) {
        Order order = orderRepository.findOrderById(id);
        if (order != null) {
            return orderConverter.convertToOrderDto(order);
        } else {
            throw new ResourceNotFoundException(ORDER_IS_NOT_FOUND);
        }
    }
}
