package nl.vantoever.repository;

import nl.vantoever.domain.AlarmEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dvtoever on 3-1-2016.
 */
@Repository
public interface AlarmEventRepository extends CrudRepository<AlarmEvent, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM alarm_event e WHERE e.event_stop IS NULL ORDER BY e.event_start DESC LIMIT 1")
    List<AlarmEvent> findMostRecentUnfinishedAlarmEvent();
}
