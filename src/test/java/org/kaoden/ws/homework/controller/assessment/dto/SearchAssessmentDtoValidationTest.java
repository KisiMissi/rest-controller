package org.kaoden.ws.homework.controller.assessment.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SearchAssessmentDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void missingEntryIdShouldFailValidation() {
        // Arrange
        SearchAssessmentDto searchAssessmentDto = new SearchAssessmentDto();

        // Act
        List<String> failedFields = validator.validate(searchAssessmentDto).stream()
                .map(x -> x.getPropertyPath().toString())
                .sorted()
                .toList();

        // Assert
        assertThat(failedFields).isEqualTo(List.of("entryId"));
    }
}