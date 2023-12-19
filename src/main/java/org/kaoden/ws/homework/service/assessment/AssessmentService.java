package org.kaoden.ws.homework.service.assessment;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssessmentService {
    EntryAssessment create(Entry entry, CreateAssessmentArgument argument);

    Page<EntryAssessment> getAll(Long entryId, Integer value, Pageable pageable);

    void delete(Long assessmentId);
}
