package org.kaoden.ws.homework.api.statistics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.api.statistics.dto.StatisticsDto;
import org.kaoden.ws.homework.api.statistics.mapper.StatisticsMapper;
import org.kaoden.ws.homework.service.statistics.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("statistics")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Tag(name = "Statistics service", description = "Statistics of Utility Storage")
public class StatisticsController {

    StatisticsService service;
    StatisticsMapper mapper;

    @GetMapping("info")
    @Operation(description = "Get statistics of Utility Storage")
    public StatisticsDto getStatistics() {
        return mapper.toDto(service.get());
    }

}
