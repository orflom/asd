package com.foundstone.s3i.dao;

import java.util.List;

import com.foundstone.s3i.model.User;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="UserDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @author <a href="http://www.foundstone.com">David Raphael</a>
 */
public interface UserDAO {
    /**
     * Gets users information based on login name.
     * @param username the current username
     * @return user populated user object
     */
    public User getUser(String username);

    /**
     * Gets a list of users based on parameters passed in.
     *
     * @return List populated list of users
     */
    public List getUsers();

    /**
     * Saves a user's information
     * @param user the object to be saved
     */
    public void saveUser(User user);

    /**
     * Updates a user's information
     * @param user the object to be saved
     */
    public void updateUser(User user);
    
    /**
     * Removes a user from the database by id
     * @param username the user's username
     */
    public void removeUser(String username);

    /**
     * Removes all cookies for a specified username
     * @param username
     */
    public void removeUserCookies(String username);
}
