package org.kaoden.ws.homework.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.service.entry.EntryServiceImp;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        Entry mockEntry = Mockito.mock(Entry.class);
        when(repository.create(testEntry)).thenReturn(mockEntry);

        // Act
        Entry actualEntry = service.create(argument);

        // Assert
        assertThat(actualEntry).isEqualTo(mockEntry);
        verifyNoInteractions(mockEntry);
    }

    @Test
    void getExistingEntryById() {
        // Arrange
        Long id = 0L;
        when(repository.exists(id)).thenReturn(true);
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
        when(repository.exists(0L)).thenReturn(true);
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
        when(repository.exists(id)).thenReturn(true);
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
        when(repository.exists(id)).thenReturn(true);

        // Act
        service.delete(id);

        // Assert
        verify(repository).delete(id);
    }
}