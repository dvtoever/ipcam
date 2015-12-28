package nl.vantoever.service.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by dvtoever on 28-12-2015.
 */
@Service
public class FtpService {

    private FtpServer server;

    @Autowired
    private FakeUserManager fakeUserManager;

    @PostConstruct
    public void start() throws FtpException {
        final FtpServerFactory serverFactory = new FtpServerFactory();
        final ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(2221);
        final Listener listener = listenerFactory.createListener();
        serverFactory.addListener("defaultListener", listener);
        serverFactory.setUserManager(fakeUserManager);

        server = serverFactory.createServer();
        server.start();
    }


    @PreDestroy
    public void stop() {
        server.stop();
    }

}
