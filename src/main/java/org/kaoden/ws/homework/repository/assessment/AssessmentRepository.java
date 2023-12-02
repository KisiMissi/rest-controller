package org.kaoden.ws.homework.repository.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<EntryAssessment, Long> {

    List<EntryAssessment> findEntryAssessmentByEntry_Id(Long entryId);

}
