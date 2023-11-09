package org.kaoden.ws.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.dto.EntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EntryRepository repository;

    static final String URL = "/entries-controller/";
    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    static final ObjectWriter OW = new ObjectMapper().writer();


    private EntryDTO getEntryDto(String name) {
        return EntryDTO.builder()
                .name(name)
                .build();
    }

    private Entry getEntry(String name) {
        return Entry.builder()
                .id(0L)
                .name(name)
                .build();
    }

    @Test
    void createEntryInRep() throws Exception {
        mockMvc.perform(post(URL + "creat")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(OW.writeValueAsString(getEntryDto("Test"))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllEntries() throws Exception {
        mockMvc.perform(get(URL + "all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchEntryById() throws Exception {
        // Arrange
        Long id = 0L;
        String name = "Test";
        Entry testEntry = getEntry(name);
        when(repository.findById(id)).thenReturn(testEntry);
        String expectedEntry = OW.writeValueAsString(getEntryDto(name));

        // Act
        MvcResult result = mockMvc.perform(get(URL + "search-id/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedEntry);
    }

    @Test
    void searchByName() throws Exception {
        // Arrange
        String name = "Test";
        Entry testEntry = getEntry(name);
        when(repository.findByName(name)).thenReturn(List.of(testEntry));
        String expectedList = OW.writeValueAsString(List.of(getEntryDto(name)));

        // Act
        MvcResult result = mockMvc.perform(get(URL + "search-name/" + name))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedList);
    }

    @Test
    void updateEntry() throws Exception {
        Long id = 0L;
        String name = "Test";
        Entry testEntry = getEntry(name);
        doNothing().when(repository).update(id, testEntry);

        // Act
        mockMvc.perform(post(URL + id + "/update")
                        .content(OW.writeValueAsString(getEntryDto(name)))
                        .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
        // Assert
        verify(repository, times(1)).update(id, testEntry);
    }

    @Test
    void deleteEntry() throws Exception {
        // Arrange
        Long id = 0L;
        doNothing().when(repository).delete(id);

        // Act
        mockMvc.perform(post(URL + id + "/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        // Assert
        verify(repository, times(1)).delete(id);
    }
}