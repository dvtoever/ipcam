package nl.vantoever.service;

import nl.vantoever.domain.AlarmEvent;
import nl.vantoever.repository.AlarmEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dvtoever on 3-1-2016.
 */
@Service
public class AlarmEventService {

    private final Logger log = LoggerFactory.getLogger(AlarmEventService.class);

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    public void logEventStart() {
        final List<AlarmEvent> alarmEvents = alarmEventRepository.findMostRecentUnfinishedAlarmEvent();
        if (!alarmEvents.isEmpty()) {
            log.warn("Last event was unfinished. Setting start and stop timestamps to same value and creating new event");

            final AlarmEvent alarmEvent = alarmEvents.get(0);
            alarmEvent.setEventStop(alarmEvent.getEventStart());
            alarmEventRepository.save(alarmEvent);
        }

        alarmEventRepository.save(new AlarmEvent(new Date()));
    }

    public void logEventStop() {
        final List<AlarmEvent> alarmEvents = alarmEventRepository.findMostRecentUnfinishedAlarmEvent();

        if (!alarmEvents.isEmpty()) {
            alarmEvents.get(0).setEventStop(new Date());
            alarmEventRepository.save(alarmEvents);
        } else {
            log.warn("An event can only be stopped if it was started first. Doing nothing now");
        }
    }

}
