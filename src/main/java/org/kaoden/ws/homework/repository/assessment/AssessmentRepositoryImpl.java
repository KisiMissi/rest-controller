package org.kaoden.ws.homework.repository.assessment;

import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Repository
@FieldDefaults(level = PRIVATE)
public class AssessmentRepositoryImpl implements AssessmentRepository {

    static Long storageId = 0L;
    static final Map<Long, EntryAssessment> STORAGE = new HashMap<>();

    @Override
    public EntryAssessment create(EntryAssessment assessment) {
        STORAGE.put(assessment.getId(), assessment);
        return assessment;
    }

    @Override
    public List<EntryAssessment> getAll(Long entryId) {
        return STORAGE.values()
                      .stream()
                      .filter(assessment -> Objects.equals(assessment.getEntryId(), entryId))
                      .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        STORAGE.remove(id);
    }

    @Override
    public Long getFreeId() {
        return storageId++;
    }
}
