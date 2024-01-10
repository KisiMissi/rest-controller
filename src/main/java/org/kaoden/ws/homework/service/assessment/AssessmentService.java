package org.kaoden.ws.homework.service.assessment;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.assessment.argument.SearchAssessmentArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssessmentService {
    EntryAssessment create(Entry entry, CreateAssessmentArgument argument);

    Page<EntryAssessment> getAll(SearchAssessmentArgument searchArgument, Pageable pageable);

    void delete(Long assessmentId);
}
