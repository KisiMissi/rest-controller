package org.kaoden.ws.homework.repository.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<EntryAssessment, Long> {

    Page<EntryAssessment> findEntryAssessmentByEntry_Id(Long entryId, Pageable pageable);

    Page<EntryAssessment> findEntryAssessmentByEntry_IdAndValue(Long entryId, Integer value, Pageable pageable);

}
