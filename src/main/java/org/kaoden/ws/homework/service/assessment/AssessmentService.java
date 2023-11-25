package org.kaoden.ws.homework.service.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;

import java.util.List;

public interface AssessmentService {
    EntryAssessment create(Long entryId, CreateAssessmentArgument argument);

    List<EntryAssessment> getAll(Long entryId);

    void delete(Long assessmentId);
}
