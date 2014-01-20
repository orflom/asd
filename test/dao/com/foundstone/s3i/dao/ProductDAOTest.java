package com.foundstone.s3i.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.foundstone.s3i.model.Feedback;
import com.foundstone.s3i.model.Product;

public class ProductDAOTest extends BaseDAOTestCase {
	private ProductDAO dao = null;

	private Product product = null;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (ProductDAO) ctx.getBean("productDAO");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dao = null;
	}

	public void testGetProductInvalid() throws Exception {
		try {
			product = dao.getProductByTitle("There is no book like this");
			fail("badness found in database, failing test...");
		} catch (DataAccessException d) {
			assertTrue(d != null);
		}
	}

	public void testGetProduct() throws Exception {
		product = dao
				.getProductByTitle("Hacking Exposed: Network Security Secrets "
						+ "& Solutions, Fourth Edition (Hacking Exposed)");
		System.out.println("Retrieved: " + product);
		assertNotNull(product);
	}

	public void testGetProductByKeywords() throws Exception {
		List list = dao.getProductsByTitleKeyWords(new String[] { "hacking",
				"exposed" });

		System.out.println("Retrieved: " + list.size());
		System.out.println("Retrieved the following from hacking exposed: "
				+ list);

		assertNotNull(list);
		assertEquals(3, list.size());
	}

	public void testGetAllProducts() throws Exception {
		List list = dao.getProducts();
		assertEquals(734, list.size());
	}

	public void testAddFeedback() throws Exception {
		product = dao
				.getProductByTitle("Hacking Exposed: Network Security Secrets "
						+ "& Solutions, Fourth Edition (Hacking Exposed)");
		System.out.println("Retrieved: " + product);
		assertNotNull(product);
		
		List feedback = product.getFeedback();
		Feedback feedback2 = new Feedback();
		feedback2.setFeedback("This was a great book.");
		product.addFeedback(feedback2);
		assertEquals(1, product.getFeedback().size());
		dao.saveFeedback(product);

	}

}