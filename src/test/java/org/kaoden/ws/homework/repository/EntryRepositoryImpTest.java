package org.kaoden.ws.homework.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.repository.entry.EntryRepositoryImp;

import static org.assertj.core.api.Assertions.assertThat;

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
    void deleteEntry() {
        // Arrange
        Long id = 0L;
        repository.create(getEntry());

        // Act
        repository.delete(id);

        // Assert
        assertThat(repository.exists(id)).isEqualTo(false);
    }
}