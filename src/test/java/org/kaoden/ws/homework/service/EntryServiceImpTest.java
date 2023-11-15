package org.kaoden.ws.homework.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.kaoden.ws.homework.repository.EntryRepositoryImp;
import org.kaoden.ws.homework.service.argument.EntryArgument;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryServiceImpTest {

    @Mock
    static EntryRepository repository = new EntryRepositoryImp();
    static EntryService service;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
        service = new EntryServiceImp(repository);
    }

    private Entry getEntry() {
        return Entry.builder()
                    .id(0L)
                    .name("Test")
                    .build();
    }

    private EntryArgument getArgument() {
        return EntryArgument.builder()
                            .name("Test")
                            .build();
    }

    @Test
    void createEntry() {
        // Arrange
        Entry expectedEntry = getEntry();
        when(repository.create(expectedEntry)).thenReturn(expectedEntry);

        // Act
        Entry actualEntry = service.create(getArgument());

        // Assert
        assertThat(actualEntry).isEqualTo(expectedEntry);
    }

    @Test
    void getExistingEntryById() {
        // Arrange
        Long id = 0L;
        Entry expectedEntry = getEntry();
        when(repository.findById(id)).thenReturn(expectedEntry);

        // Act
        Entry actualEntry = service.getExisting(id);

        // Assert
        assertThat(actualEntry).isEqualTo(expectedEntry);
    }

    @Test
    void searchEntryByName() {
        // Arrange
        String name = "Test";
        List<Entry> expectedList = Collections.singletonList(getEntry());
        when(repository.findByName(name)).thenReturn(expectedList);

        // Act
        List<Entry> actualList = service.search(name);

        // Assert
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void getAllEntries() {
        // Arrange
        List<Entry> expectedList = Collections.singletonList(getEntry());
        when(repository.getAll()).thenReturn(expectedList);

        // Act
        List<Entry> actualList = service.getAll();

        // Assert
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void updateEntry() {
        // Arrange
        Long id = 0L;
        EntryArgument argument = getArgument();
        Entry expectedEntry = getEntry();
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
        doNothing().when(repository).delete(id);

        // Act
        service.delete(id);

        // Assert
        verify(repository, times(1)).delete(id);
    }
}