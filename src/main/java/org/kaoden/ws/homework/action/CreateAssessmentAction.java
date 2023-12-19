package org.kaoden.ws.homework.action;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.action.argument.CreateAssessmentActionArgument;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.AssessmentService;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.entry.EntryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAssessmentAction {

    final EntryService entryService;
    final AssessmentService assessmentService;

    public EntryAssessment addAssessment(CreateAssessmentActionArgument argument) {
        Long entryId = argument.getEntryId();
        if (!entryService.exists(entryId)) {
            throw new NotFoundException("Entry with that ID: " + entryId + " doesn't exist");
        }
        Entry entry = entryService.getExisting(entryId);
        return assessmentService.create(entry, CreateAssessmentArgument.builder()
                                                                       .entry(entry)
                                                                       .value(argument.getValue())
                                                                       .comment(argument.getComment())
                                                                       .build());
    }

}
