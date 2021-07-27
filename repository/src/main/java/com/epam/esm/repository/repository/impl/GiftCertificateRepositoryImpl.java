package com.epam.esm.repository.repository.impl;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.impl.CertificatesBySpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Gift certificate repository implementation
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private static final String SELECT_CERTIFICATES_WITH_PARAMS = "SELECT c FROM GiftCertificate c";

    @PersistenceContext
    private Session session;

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        session.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates(int page, int size) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        criteriaQuery.from(GiftCertificate.class);
        return session.createQuery(criteriaQuery).setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
    }

    @Override
    public List<GiftCertificate> findCertificatesWithParams(int page, int size, List<Specification> specifications) {
        CertificatesBySpecification specification = new CertificatesBySpecification();
        TypedQuery<GiftCertificate> query = session.createQuery(SELECT_CERTIFICATES_WITH_PARAMS + specification.buildQuery(specifications), GiftCertificate.class);
        query = specification.buildParams(query, specifications);
        return query.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
    }

    @Override
    public GiftCertificate findGiftCertificateById(long id) {
        return session.find(GiftCertificate.class, id);

    }

    @Transactional
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        giftCertificate.setLastUpdateDate(lastUpdateDate);
        session.merge(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(GiftCertificate giftCertificate) {
        session.remove(giftCertificate);
    }
}

