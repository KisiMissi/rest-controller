package org.kaoden.ws.homework.api.assessment.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAssessmentDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void missingVEntryIdAndValueAndCommentShouldFailValidation() {
        // Arrange
        CreateAssessmentDto assessmentDto = new CreateAssessmentDto();

        // Act
        List<String> failedFields = validator.validate(assessmentDto)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .sorted()
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("comment", "entryId", "value"));
    }

    @Test
    void missingValueShouldFailValidation() {
        // Arrange
        CreateAssessmentDto assessmentDto = CreateAssessmentDto.builder()
                .entryId(1L)
                                                               .comment("test")
                                                               .build();

        // Act
        List<String> failedFields = validator.validate(assessmentDto)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("value"));
    }

    @Test
    void missingCommentShouldFailValidation() {
        // Arrange
        CreateAssessmentDto assessmentDto = CreateAssessmentDto.builder()
                .entryId(1L)
                                                               .value(5)
                                                               .build();

        // Act
        List<String> failedFields = validator.validate(assessmentDto)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("comment"));
    }

    @Test
    void valueLowerThanAllowedShouldFailValidation() {
        // Arrange
        CreateAssessmentDto assessment = CreateAssessmentDto.builder()
                .entryId(1L)
                                                            .value(0)
                                                            .comment("test")
                                                            .build();

        // Act
        List<String> actualMessages = validator.validate(assessment)
                                               .stream()
                                               .map(ConstraintViolation::getMessage)
                                               .toList();

        // Assert
        assertThat(actualMessages).isEqualTo(Lists.newArrayList("Value must be no less than 1"));
    }

    @Test
    void valueHigherThanAllowedShouldFailValidation() {
        // Arrange
        CreateAssessmentDto assessment = CreateAssessmentDto.builder()
                .entryId(1L)
                                                            .value(10)
                                                            .comment("test")
                                                            .build();

        // Act
        List<String> actualMessages = validator.validate(assessment)
                                               .stream()
                                               .map(ConstraintViolation::getMessage)
                                               .toList();

        // Assert
        assertThat(actualMessages).isEqualTo(Lists.newArrayList("Value must be no more than 5"));
    }
}