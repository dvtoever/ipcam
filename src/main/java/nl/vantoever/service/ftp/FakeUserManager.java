package nl.vantoever.service.ftp;

import nl.vantoever.service.events.LoginEvent;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * User manager that emits a LoginEvent object when it encountered a login attempt
 */
@Component
public class FakeUserManager implements UserManager {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public User getUserByName(String username) throws FtpException {
        publisher.publishEvent(new LoginEvent(username));
        return null;
    }

    @Override
    public String[] getAllUserNames() throws FtpException {
        return new String[0];
    }

    @Override
    public void delete(String s) throws FtpException {

    }

    @Override
    public void save(User user) throws FtpException {

    }

    @Override
    public boolean doesExist(String s) throws FtpException {
        return false;
    }

    @Override
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        return null;
    }

    @Override
    public String getAdminName() throws FtpException {
        return null;
    }

    @Override
    public boolean isAdmin(String s) throws FtpException {
        return false;
    }
}
