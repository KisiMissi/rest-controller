package org.kaoden.ws.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.EntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIT {

    static final String URL = "entries";
    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    static final ObjectWriter WRITER = new ObjectMapper().writer();

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntryRepository repository;

    private Entry getEntry(Long id, String name) {
        return Entry.builder()
                    .id(id)
                    .name(name)
                    .description("test-description")
                    .link("test-link")
                    .build();
    }

    private EntryDTO getEntryDto(Long id, String name) {
        return EntryDTO.builder()
                       .id(id)
                       .name(name)
                       .description("test-description")
                       .link("test-link")
                       .build();
    }

    @Test
    void createEntryInRep() throws Exception {
        // Arrange
        String createEntry = WRITER.writeValueAsString(CreateEntryDTO.builder()
                                                                     .name("test")
                                                                     .description("test-description")
                                                                     .link("test-link")
                                                                     .build());
        String expectedEntry = WRITER.writeValueAsString(getEntry(0L, "test"));

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
        // Arrange
        String name1 = "test-1";
        String name2 = "test-2";
        Entry entry1 = getEntry(0L, name1);
        Entry entry2 = getEntry(1L, name2);
        repository.create(entry1);
        repository.create(entry2);
        String expectedList = WRITER.writeValueAsString(List.of(entry1, entry2));

        // Act
        MvcResult result = mockMvc.perform(get("/{url}/all", URL))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedList);
    }

    @Test
    void searchByName() throws Exception {
        // Arrange
        String name1 = "test-1";
        String name2 = "test-2";
        String name3 = "tEsT-2";
        Entry entry1 = getEntry(0L, name1);
        Entry entry2 = getEntry(1L, name2);
        Entry entry3 = getEntry(2L, name3);
        repository.create(entry1);
        repository.create(entry2);
        repository.create(entry3);
        String expectedList = WRITER.writeValueAsString(List.of(entry2, entry3));


        // Act
        MvcResult result = mockMvc.perform(get("/{url}/all?name={name}", URL, name2))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedList);
    }

    @Test
    void searchEntryById() throws Exception {
        // Arrange
        Long id = 0L;
        String name = "test";
        String expectedEntry = WRITER.writeValueAsString(getEntryDto(id, name));
        repository.create(getEntry(id, "test"));

        // Act
        MvcResult result = mockMvc.perform(get("/{url}/{id}", URL, id))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void updateEntry() throws Exception {
        // Arrange
        Long id = 0L;
        String updateEntry = WRITER.writeValueAsString(UpdateEntryDTO.builder()
                                                                     .name("test-update")
                                                                     .description("update-description")
                                                                     .link("update-link")
                                                                     .build());
        Entry expectedEntryObj = Entry.builder()
                                      .id(id)
                                      .name("test-update")
                                      .description("update-description")
                                      .link("update-link")
                                      .build();
        String expectedEntry = WRITER.writeValueAsString(expectedEntryObj);
        repository.create(expectedEntryObj);

        // Act
        MvcResult result = mockMvc.perform(post("/{url}/{id}/update", URL, id)
                                          .contentType(APPLICATION_JSON_UTF8)
                                          .content(updateEntry))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Assert
        assertThat(result.getResponse()
                         .getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void deleteEntry() throws Exception {
        // Arrange
        Long id = 0L;
        repository.create(getEntry(id, "test"));

        // Assert
        mockMvc.perform(post("/{url}/{id}/delete", URL, id))
               .andDo(print())
               .andExpect(status().isOk());
    }
}