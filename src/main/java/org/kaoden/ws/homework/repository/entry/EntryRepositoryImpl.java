package org.kaoden.ws.homework.repository.entry;

import com.querydsl.jpa.JPAExpressions;
import jakarta.persistence.EntityManager;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryRepositoryImpl extends BaseRepositoryImpl<Entry, Long> implements EntryRepository {

    public EntryRepositoryImpl(EntityManager entityManager) {
        super(Entry.class, entityManager);
    }

    @Override
    public Page<Entry> findEntriesByNameContainingIgnoreCase(String name, Pageable pageable) {
        List<Entry> entries = queryFactory.selectFrom(entry)
                                          .where(entry.name.containsIgnoreCase(name))
                                          .stream()
                                          .toList();

        return new PageImpl<>(entries, pageable, entries.size());
    }

    @Override
    public Page<Entry> findEntriesByDescriptionContainingIgnoreCase(String description, Pageable pageable) {
        List<Entry> entries = queryFactory.selectFrom(entry)
                                          .where(entry.description.containsIgnoreCase(description))
                                          .stream()
                                          .toList();

        return new PageImpl<>(entries, pageable, entries.size());
    }

    @Override
    public Long getEntriesAverageValueIs5() {
        return queryFactory.select(entry.count())
                           .from(entry)
                           .where(JPAExpressions.select(entryAssessment.value.avg())
                                                .from(entryAssessment)
                                                .where(entryAssessment.entry.id.eq(entry.id))
                                                .eq(5.0))
                           .fetchFirst();
    }

    @Override
    public Long getEntriesAverageValueEqualOrHigherThan4() {
        return queryFactory.select(entry.count())
                           .from(entry)
                           .where(JPAExpressions.select(entryAssessment.value.avg())
                                                .from(entryAssessment)
                                                .where(entryAssessment.entry.id.eq(entry.id))
                                                .goe(4.0))
                           .fetchFirst();
    }

    @Override
    public Long getEntriesWithValuesLessThan4() {
        return queryFactory.select(entry.count())
                           .from(entry)
                           .where(JPAExpressions.select(entryAssessment.value.avg())
                                                .from(entryAssessment)
                                                .where(entryAssessment.entry.id.eq(entry.id))
                                                .lt(4.0))
                           .fetchFirst();
    }

    @Override
    public Long getUnratedEntries() {
        return queryFactory.select(entry.count())
                           .from(entry)
                           .where(JPAExpressions.selectFrom(entryAssessment)
                                                .where(entryAssessment.entry.eq(entry))
                                                .notExists())
                           .fetchFirst();
    }

}
