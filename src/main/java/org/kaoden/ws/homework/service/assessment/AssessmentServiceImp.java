package org.kaoden.ws.homework.service.assessment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.service.assessment.argument.AssessmentArgument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssessmentServiceImp implements AssessmentService {

    final AssessmentRepository repository;

    @Override
    public EntryAssessment create(Long entryId, AssessmentArgument argument) {
        return repository.create(EntryAssessment.builder()
                                                .id(repository.getFreeId())
                                                .entryId(entryId)
                                                .value(argument.getValue())
                                                .comment(argument.getComment())
                                                .build());
    }

    @Override
    public List<EntryAssessment> getAll(Long entryId) {
        return repository.getAll(entryId);
    }

    @Override
    public void delete(Long assessmentId) {
        repository.delete(assessmentId);
    }
}
