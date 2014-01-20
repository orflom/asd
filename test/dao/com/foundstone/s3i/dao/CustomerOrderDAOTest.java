package com.foundstone.s3i.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.CustomerOrderItem;
import com.foundstone.s3i.model.Product;
import com.foundstone.s3i.model.Role;
import com.foundstone.s3i.model.User;

public class CustomerOrderDAOTest extends BaseDAOTestCase {
	private CustomerOrderDAO dao = null;

	private UserDAO userDAO = null;

	private CustomerOrder order = null;

	private ProductDAO productDAO = null;

	//    private RoleDAO rdao = null;
	private Role role = null;

	protected void setUp() throws Exception {

		super.setUp();
		dao = (CustomerOrderDAO) ctx.getBean("customerOrderDAO");
		userDAO = (UserDAO) ctx.getBean("userDAO");
		productDAO = (ProductDAO) ctx.getBean("productDAO");

		try {
			dao.getCustomerOrder(123456791);
		} catch (Exception e) {

			User user = userDAO.getUser("draphael");
			Product prod1 = productDAO
					.getProductByTitle("Hacking Exposed: Network Security Secrets & Solutions, Fourth Edition (Hacking Exposed)");
			Product prod2 = productDAO
					.getProductByTitle("Security Engineering: A Guide to Building Dependable Distributed Systems");
			Product prod3 = productDAO
					.getProductByTitle("Cryptography and Network Security: Principles and Practice (3rd Edition)");
			Product prod4 = productDAO
					.getProductByTitle("Ethereal Packet Sniffing");
			Product prod5 = productDAO
					.getProductByTitle("Exploiting Software : How to Break Code");

			List customerOrderItems = new ArrayList();

			CustomerOrderItem item1 = new CustomerOrderItem();
			CustomerOrderItem item2 = new CustomerOrderItem();
			CustomerOrderItem item3 = new CustomerOrderItem();
			CustomerOrderItem item4 = new CustomerOrderItem();
			CustomerOrderItem item5 = new CustomerOrderItem();

			item1.setTaxPercentage(.0825F);
			item2.setTaxPercentage(.0825F);
			item3.setTaxPercentage(.0825F);
			item4.setTaxPercentage(.0825F);
			item5.setTaxPercentage(.0825F);

			item1.setTotalSalePrice(15.50F);
			item2.setTotalSalePrice(25.50F);
			item3.setTotalSalePrice(35.50F);
			item4.setTotalSalePrice(45.50F);
			item5.setTotalSalePrice(55.50F);

			item1.setProduct(prod1);
			item2.setProduct(prod2);
			item3.setProduct(prod3);
			item4.setProduct(prod4);
			item5.setProduct(prod5);

			customerOrderItems.add(item1);
			customerOrderItems.add(item2);
			customerOrderItems.add(item3);
			customerOrderItems.add(item4);
			customerOrderItems.add(item5);

			CustomerOrder order = new CustomerOrder();

			order.setOrderNumber(123456791);
			order.setCreditCardNumber("1234-1234-1234-1234");
			order.setOrderEntries(customerOrderItems);
			order.setUser(user);
			for (int i = 0; i < 100; i++) {
				SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
				long oNum = secureRandom.nextLong();
				dao.saveCustomerOrder(order);
			}
		}
	}

	protected void tearDown() throws Exception {

		dao.removeOrderByOrderNumber(123456791);
		super.tearDown();
		dao = null;
	}

	public void testGetOrder() throws Exception {
		CustomerOrder customerOrder = dao.getCustomerOrder(123456791);
		assertEquals(123456791, customerOrder.getOrderNumber());
		assertEquals(5, customerOrder.getOrderEntries().size());
	}

	public void testGetOrderInvalid() throws Exception {
		try {
			User user = new User();
			user.setUsername("fooUser");
			List list = dao.getCustomerOrdersByUser(user);

			fail("'badusername' found in database, failing test...");
		} catch (DataAccessException d) {
			assertTrue(d != null);
		}
	}

	public void testSaveOrderDuplicate() throws Exception {
		order = dao.getCustomerOrder(123456791);
		try {
			dao.saveCustomerOrder(order);
			fail("saveUser didn't throw DataIntegrityViolationException");
		} catch (DataIntegrityViolationException e) {
			assertNotNull(e);
			log.debug("expected exception: " + e.getMessage());
		}

	}

	public void testAddAndRemoveOrder() throws Exception {
		User user = userDAO.getUser("draphael");
		Product prod1 = productDAO
				.getProductByTitle("Hacking Exposed: Network Security Secrets & Solutions, Fourth Edition (Hacking Exposed)");
		Product prod2 = productDAO
				.getProductByTitle("Security Engineering: A Guide to Building Dependable Distributed Systems");
		Product prod3 = productDAO
				.getProductByTitle("Cryptography and Network Security: Principles and Practice (3rd Edition)");
		Product prod4 = productDAO
				.getProductByTitle("Ethereal Packet Sniffing");
		Product prod5 = productDAO
				.getProductByTitle("Exploiting Software : How to Break Code");

		List customerOrderItems = new ArrayList();

		CustomerOrderItem item1 = new CustomerOrderItem();
		CustomerOrderItem item2 = new CustomerOrderItem();
		CustomerOrderItem item3 = new CustomerOrderItem();
		CustomerOrderItem item4 = new CustomerOrderItem();
		CustomerOrderItem item5 = new CustomerOrderItem();

		customerOrderItems.add(item1);
		customerOrderItems.add(item2);
		customerOrderItems.add(item3);
		customerOrderItems.add(item4);
		customerOrderItems.add(item5);

		item1.setProduct(prod1);
		item2.setProduct(prod2);
		item3.setProduct(prod3);
		item4.setProduct(prod4);
		item5.setProduct(prod5);

		CustomerOrder order = new CustomerOrder();
		order.setOrderNumber(123456792);
		order.setCreditCardNumber("1234-1234-1234-1234");
		order.setOrderEntries(customerOrderItems);
		order.setUser(user);
		order.setTotal("$45.56");

		dao.saveCustomerOrder(order);

		dao.removeOrderByOrderNumber(123456792);

		try {
			order = dao.getCustomerOrder(123456792);
			fail("getCustomerOrder didn't throw DataAccessException");
		} catch (DataAccessException d) {
			assertNotNull(d);
		}
	}

	public void testGetOrders() {
		List orders = dao.getCustomerOrders();
		System.out.println(orders);
	}
	
	public void testRepeatedOrderSave() {

		User user = userDAO.getUser("draphael");
		Product prod1 = productDAO
				.getProductByTitle("Hacking Exposed: Network Security Secrets & Solutions, Fourth Edition (Hacking Exposed)");
		Product prod2 = productDAO
				.getProductByTitle("Security Engineering: A Guide to Building Dependable Distributed Systems");
		Product prod3 = productDAO
				.getProductByTitle("Cryptography and Network Security: Principles and Practice (3rd Edition)");
		Product prod4 = productDAO
				.getProductByTitle("Ethereal Packet Sniffing");
		Product prod5 = productDAO
				.getProductByTitle("Exploiting Software : How to Break Code");

		List customerOrderItems = new ArrayList();

		CustomerOrderItem item1 = new CustomerOrderItem();
		CustomerOrderItem item2 = new CustomerOrderItem();
		CustomerOrderItem item3 = new CustomerOrderItem();
		CustomerOrderItem item4 = new CustomerOrderItem();
		CustomerOrderItem item5 = new CustomerOrderItem();

		item1.setTaxPercentage(.0825F);
		item2.setTaxPercentage(.0825F);
		item3.setTaxPercentage(.0825F);
		item4.setTaxPercentage(.0825F);
		item5.setTaxPercentage(.0825F);

		item1.setTotalSalePrice(15.50F);
		item2.setTotalSalePrice(25.50F);
		item3.setTotalSalePrice(35.50F);
		item4.setTotalSalePrice(45.50F);
		item5.setTotalSalePrice(55.50F);

		item1.setProduct(prod1);
		item2.setProduct(prod2);
		item3.setProduct(prod3);
		item4.setProduct(prod4);
		item5.setProduct(prod5);

		customerOrderItems.add(item1);
		customerOrderItems.add(item2);
		customerOrderItems.add(item3);
		customerOrderItems.add(item4);
		customerOrderItems.add(item5);

		CustomerOrder order = new CustomerOrder();

		order.setOrderNumber(123456791);
		order.setCreditCardNumber("1234-1234-1234-1234");
		order.setOrderEntries(customerOrderItems);
		order.setUser(user);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			SecureRandom secureRandom;
			try {
				secureRandom = SecureRandom.getInstance("SHA1PRNG");
				long oNum = secureRandom.nextLong();
				order.setOrderNumber(oNum);
				dao.saveCustomerOrder(order);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
