package org.kaoden.ws.homework.controller.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.controller.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = PRIVATE)
class AssessmentControllerIT {

    static final String URL = "assessment";
    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    static final ObjectWriter WRITER = new ObjectMapper().writer();

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    AssessmentRepository assessmentRepository;

    @Test
    void create() throws Exception {
        // Arrange
        entryRepository.create(Mockito.mock(Entry.class));
        String testAssessment = WRITER.writeValueAsString(CreateAssessmentDto.builder()
                                                                             .entryId(0L)
                                                                             .value(1)
                                                                             .comment("test-1")
                                                                             .build());
        String expectedAssessment = WRITER.writeValueAsString(AssessmentDto.builder()
                                                                           .id(0L)
                                                                           .entryId(0L)
                                                                           .value(1)
                                                                           .comment("test-1")
                                                                           .build());

        // Act
        MvcResult result = mockMvc.perform(post("/{url}/create", URL)
                                          .contentType(APPLICATION_JSON_UTF8)
                                          .content(testAssessment))
                                  .andDo(print())
                                  .andExpect(status().isCreated())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedAssessment);
    }

    @Test
    void getAll() throws Exception {
        // Arrange
        entryRepository.create(Mockito.mock(Entry.class));
        assessmentRepository.create(EntryAssessment.builder()
                                                   .id(0L)
                                                   .entryId(0L)
                                                   .value(1)
                                                   .comment("test-1")
                                                   .build());
        assessmentRepository.create(EntryAssessment.builder()
                                                   .id(1L)
                                                   .entryId(0L)
                                                   .value(5)
                                                   .comment("test-5")
                                                   .build());
        AssessmentDto expectedAssessment1 = AssessmentDto.builder()
                                                         .id(0L)
                                                         .entryId(0L)
                                                         .value(1)
                                                         .comment("test-1")
                                                         .build();
        AssessmentDto expectedAssessment2 = AssessmentDto.builder()
                                                         .id(1L)
                                                         .entryId(0L)
                                                         .value(5)
                                                         .comment("test-5")
                                                         .build();
        String expectedAssessmentList = WRITER.writeValueAsString(List.of(expectedAssessment1, expectedAssessment2));

        // Act
        MvcResult result = mockMvc.perform(get("/{url}/all", URL)
                                          .param("entryId", "0"))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedAssessmentList);
    }

    @Test
    void delete() throws Exception {
        // Arrange
        assessmentRepository.create(EntryAssessment.builder()
                                                   .id(0L)
                                                   .entryId(0L)
                                                   .value(1)
                                                   .comment("test-1")
                                                   .build());

        // Act
        mockMvc.perform(post("/{url}/delete", URL)
                       .param("id", "0"))
               .andDo(print())
               .andExpect(status().isOk());

        // Assert
        assertThat(assessmentRepository.getAll(0L)).isEmpty();
    }
}