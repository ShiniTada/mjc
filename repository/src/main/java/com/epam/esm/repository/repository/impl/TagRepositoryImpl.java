package com.epam.esm.repository.repository.impl;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.repository.specification.ParameterName.NAME;


/**
 * Tag repository implementation
 */
@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String SELECT_POPULAR_TAG = "SELECT tag.id, tag.name FROM tag  JOIN tag_certificate ON " +
            "tag_certificate.id_tag = tag.id JOIN gift_certificate ON gift_certificate.id=tag_certificate.id_certificate " +
            "JOIN order_user ON order_user.id_certificate = gift_certificate.id JOIN user ON user.id = order_user.id_user " +
            "WHERE user.id = (SELECT id_user FROM order_user group by id_user order by SUM(order_user.price) DESC  limit 1) " +
            "GROUP BY tag.id ORDER BY COUNT(tag.id) DESC limit 1;";

    @PersistenceContext
    private Session session;

    @Override
    public Tag createTag(Tag tag) {
        session.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAllTags(int page, int size) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> tagCriteriaQuery =
                criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRootRoot = tagCriteriaQuery.from(Tag.class);
        tagCriteriaQuery.select(tagRootRoot);
        return session.createQuery(tagCriteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Tag findTagById(long id) {
        return session.find(Tag.class, id);
    }

    @Override
    public Tag findTagByName(String tagName) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(NAME), tagName));
        Tag tag;
        try {
            tag = session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return tag;
    }

    @Override
    public void deleteTag(Tag tag) {
        session.remove(tag);
    }

    @Override
    public Tag findPopularTag() {
        Tag tag;
        try {
            Query query = session.createNativeQuery(SELECT_POPULAR_TAG, Tag.class);
            tag = (Tag) query.getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return tag;
    }
}
