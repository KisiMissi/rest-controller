package org.kaoden.ws.homework.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.kaoden.ws.homework.service.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.argument.UpdateEntryArgument;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryServiceImpTest {

    final Entry testEntry = Entry.builder()
                                 .id(0L)
                                 .name("test")
                                 .description("test-description")
                                 .link("test-link")
                                 .build();

    @Mock
    EntryRepository repository;
    @InjectMocks
    EntryServiceImp service;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEntry() {
        // Arrange
        CreateEntryArgument argument = CreateEntryArgument.builder()
                                                          .name("test")
                                                          .description("test-description")
                                                          .link("test-link")
                                                          .build();
        Entry entry = Entry.builder()
                           .id(0L)
                           .name("test")
                           .description("test-description")
                           .link("test-link")
                           .build();
        when(repository.create(testEntry)).thenReturn(entry);

        // Act
        Entry actualEntry = service.create(argument);

        // Assert
        assertThat(actualEntry).isEqualTo(testEntry);
    }

    @Test
    void getExistingEntryById() {
        // Arrange
        Long id = 0L;
        when(repository.findById(id)).thenReturn(testEntry);

        // Act
        Entry actualEntry = service.getExisting(id);

        // Assert
        assertThat(actualEntry).isEqualTo(testEntry);
    }

    @Test
    void searchEntryByName() {
        // Arrange
        String name = "Test";
        List<Entry> expectedList = Collections.singletonList(testEntry);
        when(repository.findByName(name)).thenReturn(expectedList);

        // Act
        List<Entry> actualList = service.getAll(name);

        // Assert
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void getAllEntries() {
        // Arrange
        List<Entry> expectedList = Collections.singletonList(testEntry);
        when(repository.getAll()).thenReturn(expectedList);

        // Act
        List<Entry> actualList = service.getAll(null);

        // Assert
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void updateEntry() {
        // Arrange
        Long id = 0L;
        UpdateEntryArgument argument = UpdateEntryArgument.builder()
                                                          .name("test-update")
                                                          .description("test-description")
                                                          .link("test-link")
                                                          .build();
        Entry expectedEntry = Entry.builder()
                                   .id(id)
                                   .name("test-update")
                                   .description("test-description")
                                   .link("test-link")
                                   .build();
        when(repository.update(id, expectedEntry)).thenReturn(expectedEntry);

        // Act
        Entry actualEntry = service.update(id, argument);

        // Assert
        assertThat(actualEntry).isEqualTo(expectedEntry);
    }

    @Test
    void deleteEntry() {
        // Arrange
        Long id = 0L;

        // Act
        service.delete(id);

        // Assert
        verify(repository).delete(id);
    }
}