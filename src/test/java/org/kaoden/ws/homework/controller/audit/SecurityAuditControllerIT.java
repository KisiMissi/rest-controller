package org.kaoden.ws.homework.controller.audit;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaoden.ws.homework.controller.audit.dto.SearchSecurityAuditDto;
import org.kaoden.ws.homework.controller.audit.dto.SecurityAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        SearchSecurityAuditDto searchDto = SearchSecurityAuditDto.builder()
                                                                 .info("ip: 0:0:0:0:0:0:0:1")
                                                                 .build();
        SecurityAuditDto expectedDto = SecurityAuditDto.builder()
                                                       .id(1L)
                                                       .assessmentId(1L)
                                                       .info("ip: 0:0:0:0:0:0:0:1, user-agent: PostmanRuntime/7.36.0")
                                                       .createdAt(LocalDateTime.parse("1970-01-01T00:00:00"))
                                                       .build();

        // Act
        List<SecurityAuditDto> result = client.method(HttpMethod.GET)
                                              .uri("/{url}/all", URL)
                                              .bodyValue(searchDto)
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
        SearchSecurityAuditDto searchDto = SearchSecurityAuditDto.builder()
                                                                 .info("user-agent: PostmanRuntime/7.36.0")
                                                                 .build();
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
        List<SecurityAuditDto> result = client.method(HttpMethod.GET)
                                              .uri("/{url}/all", URL)
                                              .bodyValue(searchDto)
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