package nl.vantoever.service;

import nl.vantoever.service.events.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dvtoever on 28-12-2015.
 */
@Service
public class RecordOnTriggerService {

    private final Logger log = LoggerFactory.getLogger(RecordOnTriggerService.class);

    @Value("${record.url}")
    public String cameraUrl;

    @Value("${record.stopafter}")
    private String stopAfter;

    @Autowired
    private Environment environment;

    @Autowired
    private ImageGrabService imageGrabService;

    /**
     * Timestamp marking the last received alarm
     */
    private long lastAlarmReceivedTimestamp = 0L;

    /**
     * true if we are recording, false otherwise
     */
    private boolean isRecording = false;

    /**
     * This method gets called when an alarm was triggered, so we start recording
     */
    @EventListener
    public void handleLoginEvent(LoginEvent loginEvent) {
        log.info("Received login event for user: " + loginEvent.getUsername());
        this.lastAlarmReceivedTimestamp = now();

        if (!isRecording) {
            imageGrabService.startRecording(cameraUrl);
            this.isRecording = true;
            log.info("Starting recording because an alarm was received");
        }
    }

    /**
     * Checks twice per second if recording should be stopped
     */
    @Scheduled(fixedRate = 500)
    public void checkStopRecording() {
        boolean shouldStopRecording = lastAlarmReceivedTimestamp + Integer.valueOf(stopAfter) < now();
        if (shouldStopRecording && isRecording) {
            imageGrabService.stopRecording();
            this.isRecording = false;
            log.info("Stopping recording because last alarm has expired");
        }
    }

    private long now() {
        return new Date().getTime();
    }
}
