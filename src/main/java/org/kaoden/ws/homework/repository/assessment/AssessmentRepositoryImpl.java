package org.kaoden.ws.homework.repository.assessment;

import jakarta.persistence.EntityManager;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssessmentRepositoryImpl extends BaseRepositoryImpl<EntryAssessment, Long> implements AssessmentRepository {

    public AssessmentRepositoryImpl(EntityManager entityManager) {
        super(EntryAssessment.class, entityManager);
    }

    @Override
    public Page<EntryAssessment> findEntryAssessmentByEntryId(Long entryId, Pageable pageable) {
        List<EntryAssessment> entryAssessments = queryFactory.selectFrom(entryAssessment)
                                                             .where(entryAssessment.entry.id.eq(entryId))
                                                             .offset(pageable.getOffset())
                                                             .stream()
                                                             .toList();

        return new PageImpl<>(entryAssessments, pageable, entryAssessments.size());
    }

    @Override
    public Page<EntryAssessment> findEntryAssessmentByEntryIdAndValue(Long entryId, Integer value, Pageable pageable) {
        List<EntryAssessment> entryAssessments = queryFactory.selectFrom(entryAssessment)
                                                             .where(entryAssessment.entry.id.eq(entryId)
                                                                                            .and(entryAssessment.value.eq(value)))
                                                             .stream()
                                                             .toList();

        return new PageImpl<>(entryAssessments, pageable, entryAssessments.size());
    }

    @Override
    public Double getAverageValue() {
        return queryFactory.select(entryAssessment.value.avg())
                           .from(entryAssessment)
                           .fetchFirst();
    }
}
