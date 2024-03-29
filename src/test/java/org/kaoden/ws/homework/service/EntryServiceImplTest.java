package org.kaoden.ws.homework.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.service.entry.EntryServiceImpl;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.SearchEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryServiceImplTest {

    final Entry testEntry = Entry.builder()
                                 .id(0L)
                                 .name("test")
                                 .description("test-description")
                                 .links(List.of("test-link"))
                                 .build();

    @Mock
    Pageable pageable;
    @Mock
    EntryRepository repository;
    @InjectMocks
    EntryServiceImpl service;

    @Captor
    ArgumentCaptor<Entry> argumentCaptor;

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
                                                          .links(List.of("test-link", "test-link-2"))
                                                          .build();
        Entry mockEntry = Mockito.mock(Entry.class);
        when(repository.save(any(Entry.class))).thenReturn(mockEntry);

        // Act
        Entry actualEntry = service.create(argument);

        // Assert
        verify(repository).save(argumentCaptor.capture());
        Entry argumentCaptorValue = argumentCaptor.getValue();
        assertThat(argumentCaptorValue.getName()).isEqualTo("test");
        assertThat(argumentCaptorValue.getDescription()).isEqualTo("test-description");
        assertThat(argumentCaptorValue.getLinks()).isEqualTo(List.of("test-link", "test-link-2"));

        assertThat(actualEntry).isEqualTo(mockEntry);
        verifyNoInteractions(mockEntry);
    }

    @Test
    void getExistingEntryById() {
        // Arrange
        Long id = 0L;
        when(repository.findById(id)).thenReturn(Optional.of(testEntry));

        // Act
        Entry actualEntry = service.getExisting(id);

        // Assert
        assertThat(actualEntry).isEqualTo(testEntry);
    }

    @Test
    void searchEntryByName() {
        // Arrange
        String name = "Test";
        SearchEntryArgument argument = SearchEntryArgument.builder().name(name).build();
        List<Entry> expectedList = Collections.singletonList(testEntry);
        when(repository.findEntriesByNameContainingIgnoreCase(name, pageable)).thenReturn(new PageImpl<>(expectedList));

        // Act
        List<Entry> actualList = service.getAll(argument, pageable)
                                        .toList();

        // Assert
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void getAllEntries() {
        // Arrange
        SearchEntryArgument argument = SearchEntryArgument.builder().build();
        List<Entry> expectedList = Collections.singletonList(testEntry);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(expectedList));

        // Act
        List<Entry> actualList = service.getAll(argument, pageable)
                                        .toList();

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
                                                          .links(List.of("test-link"))
                                                          .build();
        Entry expectedEntry = Entry.builder()
                                   .id(id)
                                   .name("test-update")
                                   .description("test-description")
                                   .links(List.of("test-link"))
                                   .build();
        when(repository.findById(id)).thenReturn(Optional.of(testEntry));

        // Act
        service.update(id, argument);

        // Assert
        verify(repository).save(expectedEntry);
    }

    @Test
    void deleteEntry() {
        // Arrange
        Long id = 0L;
        when(repository.existsById(id)).thenReturn(true);

        // Act
        service.delete(id);

        // Assert
        verify(repository).deleteById(id);
    }
}