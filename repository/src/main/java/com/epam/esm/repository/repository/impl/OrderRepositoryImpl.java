package com.epam.esm.repository.repository.impl;

import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.OrderRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.USER;

/**
 * Order repository implementation
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private Session session;

    @Override
    public Order createOrder(Order order) {
        session.persist(order);
        return order;
    }

    @Override
    public List<Order> findAllOrders(int page, int size, User user) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> findQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = findQuery.from(Order.class);
        Predicate userOrders = criteriaBuilder.equal(root.get(USER), user);
        findQuery.select(root).where(userOrders);
        TypedQuery<Order> query = session.createQuery(findQuery);
        return query.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
    }

    @Override
    public Order findOrderById(long id) {
        return session.find(Order.class, id);
    }
}
