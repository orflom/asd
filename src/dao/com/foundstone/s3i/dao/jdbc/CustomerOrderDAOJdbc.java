/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.foundstone.s3i.dao.CustomerOrderDAO;
import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.CustomerOrderItem;
import com.foundstone.s3i.model.Product;
import com.foundstone.s3i.model.User;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomerOrderDAOJdbc extends JdbcDaoSupport implements
		CustomerOrderDAO {

	private Log log = LogFactory.getLog(CustomerOrderDAO.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.CustomerOrderDAO#getCustomerOrders()
	 */
	public List getCustomerOrders() {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from orders";
		List list = jt.query(query, new CustomerOrderRowMapper());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.CustomerOrderDAO#getCustomerOrder(long)
	 */
	public CustomerOrder getCustomerOrder(long orderNumber) {

		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from orders where order_number = "
				+ orderNumber;
		//System.out.println("\ngetCustomerOrder query: " + query + "\n");
		List list = jt.query(query, new CustomerOrderRowMapper());
		if (list.size() != 0) {
			return (CustomerOrder) list.get(0);
		} else {
			throw new ObjectRetrievalFailureException("Can't find order",
					CustomerOrder.class);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.CustomerOrderDAO#getCustomerOrdersByUser(com.foundstone.s3i.model.User)
	 */
	public List getCustomerOrdersByUser(User user) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from orders left outer join users on orders.users_id_fk = users.id "
				+ "where users.username = '" + user.getUsername() +"'";
		List list = jt.query(query, new CustomerOrderRowMapper());
		
		return list;
	}

	//Helper Method
	private List getCustomerOrderItems(CustomerOrder order) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select order_items.* from order_items "
				+ "left outer join orders on order_items.orders_id_fk = orders.id "
				+ "where orders.order_number = " + order.getOrderNumber();
		List list = jt.query(query, new CustomerOrderItemRowMapper());
		//System.out.println("executing: " + query);
		return list;
	}

	//This class helps facilitate the production of Orders
	private class CustomerOrderRowMapper implements RowMapper {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 *      int)
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

			String userQuery = "select * from users where users.id = "
					+ rs.getInt("USERS_ID_FK");
			JdbcTemplate jt = new JdbcTemplate(getDataSource());
			List list = jt.query(userQuery, new UserRowMapper());
			User user = (User) list.get(0);

			CustomerOrder order = new CustomerOrder();
			order.setId(rs.getInt("ID"));
			order.setOrderNumber(rs.getLong("ORDER_NUMBER"));
			order.setSalesTax(rs.getDouble("SALES_TAX"));
			order.setCreditCardNumber(rs.getString("CREDIT_CARD"));
			order.setBankAccountNumber(rs.getString("BANK_ACCOUNT"));
			order.setUser(user);
			
			List customerOrderItems = getCustomerOrderItems(order);
			//System.out.println("\nCustomerOrderItems: " + customerOrderItems);
			order.setOrderEntries(customerOrderItems);

			return order;
		}

	}

	private class CustomerOrderItemRowMapper implements RowMapper {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 *      int)
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String prodQuery = "select * from products where id = "
					+ rs.getInt("PRODUCT_ID_FK");
			JdbcTemplate jt = new JdbcTemplate(getDataSource());
			ProductRowMapper mapper = new ProductRowMapper();
			List list = jt.query(prodQuery, mapper);
			Product product = (Product) list.get(0);

			CustomerOrderItem item = new CustomerOrderItem();
			item.setId(rs.getInt("ID"));
			item.setTotalSalePrice(rs.getFloat("TOTAL_SALE_PRICE"));
			item.setProduct(product);

			//orderQuery = "select * from "
			//jt.
			return item;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.CustomerOrderDAO#saveCustomerOrder(com.foundstone.s3i.model.CustomerOrder)
	 */
	public void saveCustomerOrder(CustomerOrder order) {

		String query = "insert into orders "
				+ "(order_number, users_id_fk, sales_tax, credit_card, total, bank_account) " + "values ("
				+ order.getOrderNumber() + ", " + order.getUser().getId()
				+ ", " + order.getSalesTax() + ", '" + order.getCreditCardNumber()
				+ "', '" + order.getTotal() + "', " + order.getBankAccountNumber() + ")";
		//log.debug("Inserting order: " + order);
		//log.debug("On User: " + order.getUser());
		
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		
		jt.execute(query);
		//System.out.println("Executing: " + query);
		List tmpOrderEntries = order.getOrderEntries();
		order = getCustomerOrder(order.getOrderNumber());

		List listOfEntries = order.getOrderEntries();
		//System.out.println("number of items: " + listOfEntries.size());
		
		Iterator iter = tmpOrderEntries.iterator();
		while (iter.hasNext()) {
			CustomerOrderItem item = (CustomerOrderItem) iter.next();
			saveCustomerItem(item, order);
		}
	}

	/**
	 * @param item
	 */
	private void saveCustomerItem(CustomerOrderItem item,
			CustomerOrder parentOrder) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "insert into order_items "
				+ "(total_sale_price, orders_id_fk, product_id_fk) "
				+ " values( "
				+ item.getTotalSalePrice() + ", " + parentOrder.getId() + ", "
				+ item.getProduct().getId() + ")";
		//log.debug(query + " :: inserted with Order Number: " + parentOrder.getId());
		jt.execute(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.CustomerOrderDAO#removeOrderByOrderNumber(long)
	 */
	public void removeOrderByOrderNumber(long l) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "delete from orders where order_number = " + l;
		jt.execute(query);
	}
}