package com.epam.esm.repository.repository.impl;

import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.EMAIL;
import static com.epam.esm.repository.specification.ParameterName.ID;


/**
 * User repository implementation
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private Session session;

    @Override
    public List<User> findAllUsers(int page, int size) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<User> userCriteriaQuery =
                criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot);
        return session.createQuery(userCriteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public User findUserByEmail(String email) {
        User user;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(EMAIL), email));
        try {
            user = session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public User findUserById(Long id) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(ID), id));
        User user;
        try {
            user = session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    @Override
    public User create(User user) {
        session.persist(user);
        return user;
    }
}
