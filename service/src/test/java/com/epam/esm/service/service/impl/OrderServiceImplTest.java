

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
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GiftCertificateService giftCertificateService;
    @Mock
    private UserService userService;
    @Mock
    private OrderConverter orderConverter;
    @Mock
    private UserConverter userConverter;

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(giftCertificateService, userService, orderRepository, orderConverter, userConverter);
    }

    @AfterEach
    public void tearDown() {
        orderService = null;
    }

    @Test
    public void createOrderTest() {
        OrderDto orderDto = new OrderDto();
        UserDto userDto = new UserDto();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(1L);
        Order order = new Order();
        orderDto.setCertificateDto(giftCertificateDto);
        when(userService.findUserById(1L)).thenReturn(userDto);
        when(giftCertificateService.findGiftCertificateById(1)).thenReturn(giftCertificateDto);
        when(orderConverter.convertToOrder(orderDto)).thenReturn(order);
        when(orderRepository.createOrder(order)).thenReturn(order);
        when(orderConverter.convertToOrderDto(order)).thenReturn(orderDto);
        orderService.createOrder(orderDto, 1L);
        verify(orderRepository).createOrder(order);
    }

    @Test
    public void findAllOrdersTest1() {
        when(userService.findUserById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findAllOrders(1, 10, 1L);
        });
    }

    @Test
    public void findAllOrdersTest2() {
        UserDto userDto = new UserDto();
        User user = new User();
        when(userService.findUserById(1L)).thenReturn(userDto);
        when(userConverter.convertToUser(userDto)).thenReturn(user);
        when(orderRepository.findAllOrders(1, 10, user)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findAllOrders(1, 10, 1L);
        });
    }

    @Test
    public void findOrderByIdTest1() {
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        when(orderRepository.findOrderById(1)).thenReturn(order);
        when(orderConverter.convertToOrderDto(order)).thenReturn(orderDto);
        orderService.findOrderById(1);
        verify(orderRepository).findOrderById(1);
    }

    @Test
    public void findOrderByIdTest2() {
        when(orderRepository.findOrderById(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findOrderById(1);
        });
    }
}


