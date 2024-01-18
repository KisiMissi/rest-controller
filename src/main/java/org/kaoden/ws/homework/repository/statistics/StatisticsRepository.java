package org.kaoden.ws.homework.repository.statistics;

import org.kaoden.ws.homework.model.Statistics;
import org.kaoden.ws.homework.repository.BaseRepository;

public interface StatisticsRepository extends BaseRepository<Statistics, Long> {

    Statistics getFirstBy();

}
