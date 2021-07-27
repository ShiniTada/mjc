package com.epam.esm.service.service;

import com.epam.esm.service.dto.OrderDto;

import java.util.List;

/**
 * The interface order service
 */
public interface OrderService {

    /**
     * Find all orders of user
     *
     * @param page   page
     * @param size   size
     * @param userId user id
     * @return orderdtos list
     */
    List<OrderDto> findAllOrders(int page, int size, long userId);

    /**
     * Find order by id
     *
     * @param id order id
     * @return orderDto
     */
    OrderDto findOrderById(long id);

    /**
     * Create order
     *
     * @param orderDto order dto
     * @param userId   user id
     * @return order dto
     */
    OrderDto createOrder(OrderDto orderDto, Long userId);
}
