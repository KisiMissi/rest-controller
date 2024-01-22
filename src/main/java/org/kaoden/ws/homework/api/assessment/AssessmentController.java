package org.kaoden.ws.homework.api.assessment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.action.assessments.CreateAssessmentAction;
import org.kaoden.ws.homework.api.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.api.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.api.assessment.dto.SearchAssessmentDto;
import org.kaoden.ws.homework.api.assessment.mapper.AssessmentMapper;
import org.kaoden.ws.homework.service.assessment.AssessmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("assessment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Assessment service", description = "Entry assessment")
public class AssessmentController {

    AssessmentMapper mapper;
    AssessmentService service;
    CreateAssessmentAction assessmentAction;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Creating new assessment for entry")
    public AssessmentDto create(@Valid @RequestBody CreateAssessmentDto assessment) {
        return mapper.toDto(assessmentAction.addAssessment(mapper.toModel(assessment)));
    }

    @GetMapping("all")
    @Operation(description = "Getting all assessments for the entry")
    public List<AssessmentDto> getAll(@Valid SearchAssessmentDto searchDto,
                                      @PageableDefault(sort = {"value"}, direction = DESC) Pageable pageable) {
        return mapper.toDtoList(service.getAll(mapper.toModel(searchDto), pageable));
    }

    @PostMapping("delete")
    @Operation(description = "Deleting assessment by id")
    public void delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
    }
}
