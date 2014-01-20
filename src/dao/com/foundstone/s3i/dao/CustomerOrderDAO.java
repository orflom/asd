/*
 * Created on Jan 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao;

import java.util.List;

import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.User;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public interface CustomerOrderDAO {
	public List getCustomerOrders();
	public CustomerOrder getCustomerOrder(long orderId);
	public List getCustomerOrdersByUser(User user);
	public void saveCustomerOrder(CustomerOrder order);
	public void removeOrderByOrderNumber(long l);
	
}