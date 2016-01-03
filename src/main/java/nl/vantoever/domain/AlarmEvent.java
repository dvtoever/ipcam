package nl.vantoever.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dvtoever on 3-1-2016.
 */
@Entity
public class AlarmEvent {

    @Id
    private Long id;

    @Basic
    private Date eventStart;

    @Basic
    private Date eventStop;

    public AlarmEvent() {
    }

    public AlarmEvent(Date eventStart) {
        this.eventStart = eventStart;
    }

    public AlarmEvent(Date eventStart, Date eventStop) {
        this.eventStart = eventStart;
        this.eventStop = eventStop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventStop() {
        return eventStop;
    }

    public void setEventStop(Date eventStop) {
        this.eventStop = eventStop;
    }
}
