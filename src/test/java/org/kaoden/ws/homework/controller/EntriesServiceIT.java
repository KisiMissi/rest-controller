package org.kaoden.ws.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.dto.EntryDTO;
import org.kaoden.ws.homework.controller.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntriesServiceIT {

    static final String URL = "entries";
    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    static final ObjectWriter WRITER = new ObjectMapper().writer();

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntryRepository repository;

    private EntryDTO getEntryDto() {
        return EntryDTO.builder()
                       .id(0L)
                       .name("test")
                       .build();
    }

    private CreateEntryDTO getCreateEntryDto() {
        return CreateEntryDTO.builder()
                             .name("test")
                             .build();
    }

    private UpdateEntryDTO getUpdateEntryDto() {
        return UpdateEntryDTO.builder()
                             .name("test")
                             .build();
    }

    private Entry getEntry() {
        return Entry.builder()
                    .id(0L)
                    .name("test")
                    .build();
    }

    @Test
    void createEntryInRep() throws Exception {
        // Arrange
        String createEntry = WRITER.writeValueAsString(getCreateEntryDto());
        String expectedEntry = WRITER.writeValueAsString(getEntryDto());

        // Act
        MvcResult result = mockMvc.perform(post("/{url}/create", URL)
                                          .contentType(APPLICATION_JSON_UTF8)
                                          .content(createEntry))
                                  .andDo(print())
                                  .andExpect(status().isCreated())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void getAllEntries() throws Exception {
        mockMvc.perform(get("/{url}/all", URL))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    void searchByName() throws Exception {
        // Arrange
        String name = "test";
        String expectedList = WRITER.writeValueAsString(Collections.singletonList(getEntryDto()));
        repository.create(getEntry());

        // Act
        MvcResult result = mockMvc.perform(get("/{url}/all?name={name}", URL, name))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedList);
    }

    @Test
    void searchEntryById() throws Exception {
        // Arrange
        Long id = 0L;
        String testEntry = WRITER.writeValueAsString(getEntryDto());
        String expectedEntry = WRITER.writeValueAsString(getEntryDto());
        repository.create(getEntry());

        // Act
        MvcResult result = mockMvc.perform(get("/{url}/search-id/{id}", URL, id)
                                          .contentType(APPLICATION_JSON_UTF8)
                                          .content(testEntry))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void updateEntry() throws Exception {
        // Arrange
        Long id = 0L;
        String updateEntry = WRITER.writeValueAsString(getUpdateEntryDto());
        String expectedEntry = WRITER.writeValueAsString(getEntryDto());
        repository.create(getEntry());

        // Act
        MvcResult result = mockMvc.perform(post("/{url}/{id}/update", URL, id)
                                          .contentType(APPLICATION_JSON_UTF8)
                                          .content(updateEntry))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void deleteEntry() throws Exception {
        // Arrange
        Long id = 0L;
        repository.create(getEntry());

        // Assert
        mockMvc.perform(post("/{url}/{id}/delete", URL, id))
               .andDo(print())
               .andExpect(status().isOk());
    }
}