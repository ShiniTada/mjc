package com.epam.esm.repository.repository;

import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.User;

import java.util.List;

/**
 * The interface order repository
 */
public interface OrderRepository {
    /**
     * Create order
     *
     * @param order order
     * @return order
     */
    Order createOrder(Order order);

    /**
     * Find all orders of user
     *
     * @param page page
     * @param size size
     * @param user user
     * @return orders list
     */
    List<Order> findAllOrders(int page, int size, User user);

    /**
     * Find order by id
     *
     * @param id order id
     * @return order
     */
    Order findOrderById(long id);
}
