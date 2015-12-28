package nl.vantoever.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by dvtoever on 28-12-2015.
 */
@Service
public class ImageGrabService {

    @Value("${record.outputdir}")
    private String videoOutputDirectory;

    private boolean recording = false;

    /**
     * This method needs to be async, because otherwise it blocks the only thread there is. This would prevent
     * the ftp server to finish the login attempt and the scheduled job to check if recording needs to be stopped.
     *
     * @param imageUrl The video url that needs to be recorded
     */
    @Async
    public void startRecording(String imageUrl) {
        recording = true;
        URL url = null;
        try {
            url = new URL(imageUrl);
            String fileName = url.getFile();
            String destName = "./" + fileName.substring(fileName.lastIndexOf("/"));
            System.out.println(destName);

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(String
                    .format("%s/%s-capture.mjpg", videoOutputDirectory, getTimestamp()));

            byte[] b = new byte[2048];
            int length;

            while (recording && (length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop the recording
     */
    public void stopRecording() {
        this.recording = false;
    }

    private String getTimestamp() {
        return String.valueOf(new Date().getTime());
    }
}
