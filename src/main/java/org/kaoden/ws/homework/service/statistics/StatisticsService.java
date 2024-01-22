package org.kaoden.ws.homework.service.statistics;

import org.kaoden.ws.homework.model.Statistics;
import org.kaoden.ws.homework.service.statistics.argument.UpdateStatisticsArgument;

public interface StatisticsService {

    Statistics get();

    void update(UpdateStatisticsArgument argument);

}
