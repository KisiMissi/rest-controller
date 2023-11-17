package org.kaoden.ws.homework.controller.entry.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CreateEntryDTOValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void missingNameShouldFailValidation() {
        // Arrange
        CreateEntryDTO entryDTO = CreateEntryDTO.builder()
                                                .description("test-description")
                                                .link("test-link")
                                                .build();

        // Act
        List<String> failedFields = validator.validate(entryDTO)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("name"));
    }

    @Test
    void missingDescriptionShouldFailValidation() {
        // Arrange
        CreateEntryDTO entryDTO = CreateEntryDTO.builder()
                                                .name("test")
                                                .link("test-link")
                                                .build();

        // Act
        List<String> failedFields = validator.validate(entryDTO)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("description"));
    }

    @Test
    void missingLinkShouldFailValidation() {
        // Arrange
        CreateEntryDTO entryDTO = CreateEntryDTO.builder()
                                                .name("test")
                                                .description("test-description")
                                                .build();

        // Act
        List<String> failedFields = validator.validate(entryDTO)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("link"));
    }

    @Test
    void missingAllFieldsShouldFailValidation() {
        // Arrange
        CreateEntryDTO entryDTO = new CreateEntryDTO();

        // Act
        List<String> failedFields = validator.validate(entryDTO)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .sorted()
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("description", "link", "name"));
    }
}