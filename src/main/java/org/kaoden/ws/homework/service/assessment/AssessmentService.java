package org.kaoden.ws.homework.service.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.argument.AssessmentArgument;

import java.util.List;

public interface AssessmentService {
    EntryAssessment create(Long entryId, AssessmentArgument argument);

    List<EntryAssessment> getAll(Long entryId);

    void delete(Long assessmentId);
}
