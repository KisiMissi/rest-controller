package org.kaoden.ws.homework.service.assessment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssessmentServiceImpl implements AssessmentService {

    final AssessmentRepository repository;

    @Override
    public EntryAssessment create(Entry entry, CreateAssessmentArgument argument) {
        return repository.save(EntryAssessment.builder()
                                                .entry(entry)
                                                .value(argument.getValue())
                                                .comment(argument.getComment())
                                                .build());
    }

    @Override
    public List<EntryAssessment> getAll(Long entryId) {
        return repository.findEntryAssessmentByEntry_Id(entryId);
    }

    @Override
    public void delete(Long assessmentId) {
        repository.deleteById(assessmentId);
    }
}
