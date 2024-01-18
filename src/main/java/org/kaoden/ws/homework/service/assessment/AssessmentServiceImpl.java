package org.kaoden.ws.homework.service.assessment;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.annotation.SecurityAuditCreation;
import org.kaoden.ws.homework.annotation.Statistics;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.assessment.argument.SearchAssessmentArgument;
import org.kaoden.ws.homework.service.assessment.statistics.AssessmentStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AssessmentServiceImpl implements AssessmentService {

    final AssessmentRepository repository;

    @Override
    @Statistics
    @Transactional
    @SecurityAuditCreation
    public EntryAssessment create(Entry entry, CreateAssessmentArgument argument) {
        return repository.save(EntryAssessment.builder()
                                              .entry(entry)
                                              .value(argument.getValue())
                                              .comment(argument.getComment())
                                              .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntryAssessment> getAll(SearchAssessmentArgument searchArgument, Pageable pageable) {
        Long entryId = searchArgument.getEntryId();
        Integer value = searchArgument.getValue();

        return value != null
                ? repository.findEntryAssessmentByEntryIdAndValue(entryId, value, pageable)
                : repository.findEntryAssessmentByEntryId(entryId, pageable);
    }

    @Override
    @Statistics
    @Transactional
    public void delete(Long assessmentId) {
        repository.deleteById(assessmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentStatistics getAssessmentStatistics() {
        return AssessmentStatistics.builder()
                                   .totalAssessments(repository.count())
                                   .averageValue(repository.getAverageValue())
                                   .build();
    }
}
