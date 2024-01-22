package org.kaoden.ws.homework.aspect;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.kaoden.ws.homework.event.UpdateStatisticsEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StatisticsAspect {

    ApplicationEventPublisher eventPublisher;

    @After("@annotation(org.kaoden.ws.homework.annotation.Statistics)")
    private void updateStatistics() {
        eventPublisher.publishEvent(new UpdateStatisticsEvent("Update statistics"));
    }

}
