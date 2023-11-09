package org.kaoden.ws.homework.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryRepositoryImpTest {

    @Autowired
    EntryRepository repository;

    private Entry getEntry() {
        return Entry.builder()
                .name("Test")
                .build();
    }

    @Test
    void creatNull() {
        assertThrows(NullPointerException.class, () ->  repository.creat(null));
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
    void deleteNonexistentEntry() {
        // Arrange
        Long id = 0L;
        Entry testEntry = getEntry();

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> repository.delete(id));

        // Assert
        assertEquals("Impossible delete entry with this ID: "  + id, exception.getMessage());
    }
}