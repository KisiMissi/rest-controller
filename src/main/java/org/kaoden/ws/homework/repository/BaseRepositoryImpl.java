package org.kaoden.ws.homework.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.kaoden.ws.homework.model.QEntry;
import org.kaoden.ws.homework.model.QEntryAssessment;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    protected final QEntry entry = QEntry.entry;
    protected final QEntryAssessment entryAssessment = QEntryAssessment.entryAssessment;

    private final EntityManager entityManager;
    protected final JPAQueryFactory queryFactory;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
