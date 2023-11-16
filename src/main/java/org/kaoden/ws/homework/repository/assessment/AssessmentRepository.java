package org.kaoden.ws.homework.repository.assessment;

import org.kaoden.ws.homework.model.EntryAssessment;

import java.util.List;

public interface AssessmentRepository {
    EntryAssessment create(EntryAssessment assessment);

    List<EntryAssessment> getAll(Long id);

    void delete(Long id);

    Long getFreeId();
}
