package org.kaoden.ws.homework.api.statistics;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaoden.ws.homework.api.statistics.dto.StatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import static lombok.AccessLevel.PRIVATE;

@Testcontainers
@AutoConfigureWebTestClient
@FieldDefaults(level = PRIVATE)
@ExtendWith(DBUnitExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerIT {

    static final String URL = "statistics";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    WebTestClient client;

    @Test
    @DataSet("statistics\\INFO.json")
    void getStatistics() {
        // Arrange
        StatisticsDto expected = StatisticsDto.builder()
                                              .totalEntries(1L)
                                              .totalAssessments(1L)
                                              .averageValue(1.0)
                                              .entriesAverageValueIs5(1L)
                                              .percentageOfEntriesAverageValueIs5(1.0)
                                              .entriesAverageValueEqualOrHigherThan4(1L)
                                              .percentageOfEntriesAverageValueEqualOrHigherThan4(1.0)
                                              .entriesWithValuesLessThan4(1L)
                                              .percentageOfEntriesWithValuesLessThan4(1.0)
                                              .unratedEntries(1L)
                                              .build();

        // Act
        StatisticsDto actual = client.get()
                                     .uri("/{url}/info", URL)
                                     .exchange()
                                     .expectStatus()
                                     .isOk()
                                     .expectBody(StatisticsDto.class)
                                     .returnResult()
                                     .getResponseBody();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}