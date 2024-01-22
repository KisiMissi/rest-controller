package org.kaoden.ws.homework.api.statistics.mapper;

import org.kaoden.ws.homework.api.statistics.dto.StatisticsDto;
import org.kaoden.ws.homework.model.Statistics;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {

    StatisticsDto toDto(Statistics statistics);

}
