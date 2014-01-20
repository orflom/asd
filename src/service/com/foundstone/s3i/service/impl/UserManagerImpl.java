package com.foundstone.s3i.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import com.foundstone.s3i.dao.UserDAO;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.UserExistsException;
import com.foundstone.s3i.service.UserManager;


/**
 * Implementation of UserManager interface.</p>
 * 
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class UserManagerImpl implements UserManager {
    private UserDAO dao;
    private Log log = LogFactory.getLog(UserManager.class);

    /**
     * Set the DAO for communication with the data layer.
     * @param dao
     */
    public void setUserDAO(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.foundstone.s3i.service.UserManager#getUser(java.lang.String)
     */
    public User getUser(String username) {
        return dao.getUser(username);
    }

    /**
     * @see com.foundstone.s3i.service.UserManager#getUsers(com.foundstone.s3i.model.User)
     */
    public List getUsers() {
        return dao.getUsers();
    }

    /**
     * @see com.foundstone.s3i.service.UserManager#saveUser(com.foundstone.s3i.model.User)
     */
    public void saveUser(User user) throws UserExistsException {
        try {
            dao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User '" + user.getUsername() + 
                                          "' already exists!");
        }
    }

    /**
     * @see com.foundstone.s3i.service.UserManager#removeUser(java.lang.String)
     */
    public void removeUser(String username) {
        if (log.isDebugEnabled()) {
            log.debug("removing user: " + username);
        }

        dao.removeUser(username);
    }

}
