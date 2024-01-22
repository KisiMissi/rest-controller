package org.kaoden.ws.homework.repository.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AssessmentRepository extends BaseRepository<EntryAssessment, Long> {

    Page<EntryAssessment> findEntryAssessmentByEntryId(Long entryId, Pageable pageable);

    Page<EntryAssessment> findEntryAssessmentByEntryIdAndValue(Long entryId, Integer value, Pageable pageable);

    Double getAverageValue();
}
