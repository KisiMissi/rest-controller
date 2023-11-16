package org.kaoden.ws.homework.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.repository.entry.EntryRepositoryImp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryRepositoryImpTest {

    final EntryRepository repository = new EntryRepositoryImp();

    private Entry getEntry() {
        return Entry.builder()
                .id(0L)
                .name("Test")
                .build();
    }

    @Test
    void createEntry() {
        // Assert
        Entry expectedEntry = getEntry();

        // Act
        Entry actualEntry = repository.create(getEntry());

        // Assert
        assertThat(expectedEntry).isEqualTo(actualEntry);
    }

    @Test
    void findByIdNonexistentEntry() {
        // Arrange
        Long id = 0L;

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> repository.findById(id));

        // Assert
        assertEquals("There is no entry with this ID: " + id, exception.getMessage());
    }

    @Test
    void findByIdEntry() {
        // Arrange
        Long id = 0L;
        Entry expectedEntry = repository.create(getEntry());

        // Act
        Entry actualEntry = repository.findById(id);

        // Assert
        assertThat(actualEntry).isEqualTo(expectedEntry);
    }

    @Test
    void updateNonexistentEntry() {
        // Arrange
        Long id = 0L;
        Entry testEntry = getEntry();

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> repository.update(id, testEntry));

        // Assert
        assertEquals("Impossible update entry with this ID: "  + id, exception.getMessage());
    }

    @Test
    void updateEntry() {
        // Arrange
        Long id = 0L;
        Entry expectedEntry = Entry.builder().name("updated").build();
        repository.create(getEntry());

        // Act
        Entry actualEntry = repository.update(id, expectedEntry);

        // Assert
        assertThat(actualEntry).isEqualTo(expectedEntry);
    }

    @Test
    void deleteNonexistentEntry() {
        // Arrange
        Long id = 0L;
        Entry testEntry = getEntry();

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> repository.delete(id));

        // Assert
        assertEquals("Impossible delete entry with this ID: "  + id, exception.getMessage());
    }

    @Test
    void deleteEntry() {
        // Arrange
        Long id = 0L;
        repository.create(getEntry());

        // Act
        repository.delete(id);

        // Assert
        assertThrows(NotFoundException.class, () -> repository.findById(id));
    }
}