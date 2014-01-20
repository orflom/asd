/*
 * Created on Feb 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.service.impl;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.foundstone.s3i.dao.CustomerOrderDAO;
import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.CustomerOrderManager;
import com.foundstone.s3i.service.OrderExistsException;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
class CustomerOrderManagerImpl implements CustomerOrderManager {

	private CustomerOrderDAO dao;

	private Log log = LogFactory.getLog(CustomerOrderManager.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#setCustomerOrderManagerDAO(com.foundstone.s3i.dao.CustomerOrderDAO)
	 */
	public void setCustomerOrderDAO(CustomerOrderDAO dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#getCustomerOrder(long)
	 */
	public CustomerOrder getCustomerOrder(long orderNumber) {
		// TODO Auto-generated method stub
		return dao.getCustomerOrder(orderNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#getCustomerOrders()
	 */
	public List getCustomerOrders() {
		// TODO Auto-generated method stub
		return dao.getCustomerOrders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#saveCustomerOrder(com.foundstone.s3i.model.CustomerOrder)
	 */
	public void saveCustomerOrder(CustomerOrder order)
			throws OrderExistsException {
		dao.saveCustomerOrder(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#removeOrder(long)
	 */
	public void removeOrder(long orderNumber) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.service.CustomerOrderManager#getCustomerOrdersByUser(com.foundstone.s3i.model.User)
	 */
	public List getCustomerOrdersByUser(User user) {

		return dao.getCustomerOrdersByUser(user);
	}

	public List getCustomerOrdersByUser(User user, Principal principal) {
		if (principal.getName().equals(user.getUsername())) {
			return dao.getCustomerOrdersByUser(user);
		} else {
			return null;
		}
	}
}