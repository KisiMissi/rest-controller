package org.kaoden.ws.homework.api.entry.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateEntryDTOValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void missingNameShouldFailValidation() {
        // Arrange
        UpdateEntryDTO entryDTO = UpdateEntryDTO.builder()
                                                .description("test-description")
                                                .links(List.of("test-link", "test-link-2"))
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
        UpdateEntryDTO entryDTO = UpdateEntryDTO.builder()
                                                .name("test")
                                                .links(List.of("test-link", "test-link2"))
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
        UpdateEntryDTO entryDTO = UpdateEntryDTO.builder()
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
        assertThat(failedFields).isEqualTo(Lists.newArrayList("links"));
    }

    @Test
    void missingAllFieldsShouldFailValidation() {
        // Arrange
        UpdateEntryDTO entryDTO = new UpdateEntryDTO();

        // Act
        List<String> failedFields = validator.validate(entryDTO)
                                             .stream()
                                             .map(x -> x.getPropertyPath()
                                                        .toString())
                                             .sorted()
                                             .toList();

        // Assert
        assertThat(failedFields).isEqualTo(Lists.newArrayList("description", "links", "name"));
    }

}