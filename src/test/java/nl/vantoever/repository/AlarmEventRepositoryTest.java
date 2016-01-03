package nl.vantoever.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import jersey.repackaged.com.google.common.collect.Lists;
import nl.vantoever.TestApplication;
import nl.vantoever.domain.AlarmEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by dvtoever on 3-1-2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class AlarmEventRepositoryTest {

    @Autowired
    private DataSource datasource;

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    private Date startTestDate;
    private Date endTestDate;

    @Before
    public void setUp() throws Exception {
        startTestDate = new Date();
        endTestDate = new Date(startTestDate.getTime() + 5000);
        final Operation operation = sequenceOf(CommonOperations.DELETE_ALL,
                insertInto("alarm_event")
                        .columns("id", "event_start", "event_stop")
                        .values(1L, startTestDate, endTestDate)
                        .values(2L, startTestDate, null)
                        .values(3L, startTestDate, endTestDate)
                        .build()
        );

        final DbSetup dbSetup = new DbSetup(new DataSourceDestination(datasource), operation);
        dbSetup.launch();

    }

    @Test
    public void testFindLatestUnfinishedEvent() throws Exception {
        List<AlarmEvent> unfinishedAlarmEvents = alarmEventRepository.findMostRecentUnfinishedAlarmEvent();

        assertThat(unfinishedAlarmEvents.size(), is(1));

        final AlarmEvent alarmEvent = unfinishedAlarmEvents.get(0);
        assertThat(alarmEvent.getId(), is(2L));
        assertThat(alarmEvent.getEventStart().getTime(), is(startTestDate.getTime()));
        assertNull(alarmEvent.getEventStop());
    }

    @Test
    public void testFindAll() {
        Iterable<AlarmEvent> iterable = alarmEventRepository.findAll();
        List<AlarmEvent> events = Lists.newArrayList(iterable);

        assertThat(events.size(), is(3));
    }
}