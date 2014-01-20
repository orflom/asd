package com.foundstone.s3i.service;

import java.security.Principal;
import java.util.List;

import com.foundstone.s3i.dao.CustomerOrderDAO;
import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.User;

public interface CustomerOrderManager {
	//~ Methods
	// ================================================================

	public void setCustomerOrderDAO(CustomerOrderDAO dao);

	/**
	 * 
	 * @param orderNumber
	 * @return
	 */
	public CustomerOrder getCustomerOrder(long orderNumber);

	/**
	 * 
	 * @return
	 */
	public List getCustomerOrders();


	public void saveCustomerOrder(CustomerOrder order) throws OrderExistsException;

	/**
	 * Removes a user from the database by their username
	 * 
	 * @param username
	 *            the user's username
	 */
	public void removeOrder(long orderNumber);

	/**
	 * @return
	 */
	public List getCustomerOrdersByUser(User user);

	public List getCustomerOrdersByUser(User user, Principal principal);

}