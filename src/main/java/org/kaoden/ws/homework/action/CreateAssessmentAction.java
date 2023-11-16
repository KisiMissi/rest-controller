package org.kaoden.ws.homework.action;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.action.argument.CreateAssessmentActionArgument;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.AssessmentService;
import org.kaoden.ws.homework.service.assessment.argument.AssessmentArgument;
import org.kaoden.ws.homework.service.entry.EntryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAssessmentAction {

    final EntryService entryService;
    final AssessmentService assessmentService;

    public EntryAssessment addAssessment(Long entryId, CreateAssessmentActionArgument argument) {
        if (entryService.exists(entryId)) {
            return assessmentService.create(entryId, AssessmentArgument.builder()
                                                                       .entryId(entryId)
                                                                       .value(argument.getValue())
                                                                       .comment(argument.getComment())
                                                                       .build());
        } else
            throw new NotFoundException("Entry with that ID: " + entryId + " doesn't exist");
    }

}
