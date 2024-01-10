package org.kaoden.ws.homework.service.assessment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.annotation.SecurityAuditCreation;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.assessment.argument.SearchAssessmentArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssessmentServiceImpl implements AssessmentService {

    final AssessmentRepository repository;

    @Override
    @SecurityAuditCreation
    public EntryAssessment create(Entry entry, CreateAssessmentArgument argument) {
        return repository.save(EntryAssessment.builder()
                                              .entry(entry)
                                              .value(argument.getValue())
                                              .comment(argument.getComment())
                                              .build());
    }

    @Override
    public Page<EntryAssessment> getAll(SearchAssessmentArgument searchArgument, Pageable pageable) {
        Long entryId = searchArgument.getEntryId();
        Integer value = searchArgument.getValue();

        return value != null
                ? repository.findEntryAssessmentByEntry_IdAndValue(entryId, value, pageable)
                : repository.findEntryAssessmentByEntry_Id(entryId, pageable);
    }

    @Override
    public void delete(Long assessmentId) {
        repository.deleteById(assessmentId);
    }
}
