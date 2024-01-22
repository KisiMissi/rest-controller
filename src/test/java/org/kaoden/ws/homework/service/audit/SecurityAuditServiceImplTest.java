package org.kaoden.ws.homework.service.audit;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.SecurityAudit;
import org.kaoden.ws.homework.repository.audit.SecurityAuditRepository;
import org.kaoden.ws.homework.service.audit.argument.CreateSecurityAuditArgument;
import org.kaoden.ws.homework.service.audit.argument.SearchSecurityAuditArgument;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = PRIVATE)
class SecurityAuditServiceImplTest {

    @Mock
    Pageable pageable;
    @Mock
    SecurityAuditRepository repository;
    @InjectMocks
    SecurityAuditServiceImpl service;

    @Captor
    ArgumentCaptor<SecurityAudit> argumentCaptor;

    @BeforeEach
    void setMockAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSecurityAudit() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        CreateSecurityAuditArgument argument = CreateSecurityAuditArgument.builder()
                                                                          .assessmentId(1L)
                                                                          .info("test-info")
                                                                          .createdAt(createdAt)
                                                                          .build();

        // Act
        service.create(argument);

        // Assert
        verify(repository).save(argumentCaptor.capture());
        SecurityAudit actual = argumentCaptor.getValue();
        assertThat(actual.getAssessmentId()).isEqualTo(1L);
        assertThat(actual.getInfo()).isEqualTo("test-info");
        assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void getAllSecurityAuditsWhitEmptySearchArgument() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        SearchSecurityAuditArgument argument = SearchSecurityAuditArgument.builder()
                                                                          .build();
        List<SecurityAudit> securityAudits = List.of(SecurityAudit.builder()
                                                                  .assessmentId(1L)
                                                                  .info("test-info")
                                                                  .createdAt(createdAt)
                                                                  .build());
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(securityAudits));

        // Act
        List<SecurityAudit> actual = service.getAll(argument, pageable)
                                            .toList();

        // Assert
        assertThat(actual).isEqualTo(securityAudits);
    }

    @Test
    void getAllSecurityAuditsByInfo() {
        String testInfo = "test-info";
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        SearchSecurityAuditArgument argument = SearchSecurityAuditArgument.builder()
                                                                          .info(testInfo)
                                                                          .build();
        List<SecurityAudit> securityAudits = List.of(SecurityAudit.builder()
                                                                  .assessmentId(1L)
                                                                  .info(testInfo)
                                                                  .createdAt(createdAt)
                                                                  .build());
        when(repository.findSecurityAuditsByInfoContainingIgnoreCase(argument.getInfo(), pageable)).thenReturn(new PageImpl<>(securityAudits));

        // Act
        List<SecurityAudit> actual = service.getAll(argument, pageable)
                                            .toList();

        // Assert
        assertThat(actual).isEqualTo(securityAudits);
    }

    @Test
    void deleteByIdShouldBePassed() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);

        // Act
        service.delete(1L);

        // Assert
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteNonExistedSecurityAuditShouldThrowException() {
        // Act
        Long id = 1L;
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.delete(id));

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Security audit with that: " + id + " is not found");
    }

}