package org.kaoden.ws.homework.api.audit;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaoden.ws.homework.api.audit.dto.SecurityAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DBUnitExtension.class)
@Testcontainers
@AutoConfigureWebTestClient
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityAuditControllerIT {

    static final String URL = "audits";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    WebTestClient client;

    @Test
    @DataSet("audit\\GET_ALL_IP.json")
    void getAllByIpInfoShouldReturnOneSecurityAudits() {
        // Arrange
        SecurityAuditDto expectedDto = SecurityAuditDto.builder()
                                                       .id(1L)
                                                       .assessmentId(1L)
                                                       .info("ip: 0:0:0:0:0:0:0:1, user-agent: PostmanRuntime/7.36.0")
                                                       .createdAt(LocalDateTime.parse("1970-01-01T00:00:00"))
                                                       .build();

        // Act
        List<SecurityAuditDto> result = client.get()
                                              .uri(uriBuilder -> uriBuilder.path(URL)
                                                                           .path("/all")
                                                                           .queryParam("info", "ip: 0:0:0:0:0:0:0:1")
                                                                           .build())
                                              .exchange()
                                              .expectStatus()
                                              .isOk()
                                              .expectBodyList(SecurityAuditDto.class)
                                              .returnResult()
                                              .getResponseBody();

        // Arrange
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedDto);
    }

    @Test
    @DataSet("audit\\GET_ALL_USER_AGENT.json")
    void getAllByUserAgentInfoShouldReturnTwoSecurityAudits() {
        // Arrange
        SecurityAuditDto expectedDto1 = SecurityAuditDto.builder()
                                                        .id(1L)
                                                        .assessmentId(1L)
                                                        .info("ip: 0:0:0:0:0:0:0:1, user-agent: PostmanRuntime/7.36.0")
                                                        .createdAt(LocalDateTime.parse("1970-01-01T00:00:00"))
                                                        .build();
        SecurityAuditDto expectedDto2 = SecurityAuditDto.builder()
                                                        .id(2L)
                                                        .assessmentId(1L)
                                                        .info("ip: 0:0:0:0:0:0:0:2, user-agent: PostmanRuntime/7.36.0")
                                                        .createdAt(LocalDateTime.parse("1970-01-02T00:00:00"))
                                                        .build();

        // Act
        List<SecurityAuditDto> result = client.get()
                                              .uri(uriBuilder -> uriBuilder.path(URL)
                                                                           .path("/all")
                                                                           .queryParam("info", "user-agent: PostmanRuntime/7.36.0")
                                                                           .build())
                                              .exchange()
                                              .expectStatus()
                                              .isOk()
                                              .expectBodyList(SecurityAuditDto.class)
                                              .returnResult()
                                              .getResponseBody();

        // Arrange
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedDto1);
        assertThat(result).contains(expectedDto2);
    }

    @Test
    @DataSet("audit\\DELETE.json")
    @ExpectedDataSet("audit\\DELETE_EXPECTED.json")
    void deleteSecurityAudit() {
        // Act
        client.post()
              .uri(uriBuilder ->
                      uriBuilder.path(URL)
                                .path("/delete")
                                .queryParam("id", 1)
                                .build())
              .exchange()
              .expectStatus()
              .isOk();
    }
}