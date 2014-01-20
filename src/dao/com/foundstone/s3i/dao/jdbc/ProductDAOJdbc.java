/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.foundstone.s3i.dao.ProductDAO;
import com.foundstone.s3i.model.Feedback;
import com.foundstone.s3i.model.Product;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProductDAOJdbc extends JdbcDaoSupport implements ProductDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.ProductDAO#getProducts()
	 */
	public List getProducts() {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products";

		List list = jt.query(query, new ProductRowMapper());
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Product prod = (Product) iter.next();
			prod.setFeedback(getFeedBacks(prod));
		}
		return list;
	}

	public List getProductsWithoutDescritions() {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products";

		List list = jt.query(query, new ProductRowMapper());
		Iterator iter = list.iterator();
		
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.ProductDAO#getProduct(int)
	 */
	public Product getProduct(int id) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products where id = " + id;
		List list = jt.query(query, new ProductRowMapper());
		if (list.size() != 0) {
			Product product = (Product) list.get(0);
			product.setFeedback(getFeedBacks(product));
			return product;
		} else {
			throw new ObjectRetrievalFailureException(Product.class, Integer
					.toString(id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.ProductDAO#getProductByTitle(java.lang.String)
	 */
	public Product getProductByTitle(String title) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products where title = '" + title + "'";
		List list = jt.query(query, new ProductRowMapper());
		if (list.size() != 0) {
			Product product = (Product) list.get(0);
			product.setFeedback(getFeedBacks(product));
			return product;
		} else {
			throw new ObjectRetrievalFailureException(Product.class, title);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.ProductDAO#getProductsByTitleKeyWords(java.lang.String[])
	 */
	public List getProductsByTitleKeyWords(String[] keywords) {
		if (keywords == null || keywords.length == 0) {
			return getProducts();
		}
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products where "
				+ createCriteria(keywords);
		List list = jt.query(query, new ProductRowMapper());
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Product prod = (Product) iter.next();
			prod.setFeedback(getFeedBacks(prod));
		}
		return list;
	}

	/**
	 * @param keywords
	 * @return
	 */
	private String createCriteria(String[] keywords) {
		if(keywords == null) {
			return "1 = 1"; // return all rows
		}
		StringBuffer criteria = new StringBuffer();
		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			if (i == 0) {
				criteria.append("lower(title) like '%" + keyword.toLowerCase()
						+ "%'");
			} else {
				criteria.append(" and lower(title) like '%"
						+ keyword.toLowerCase() + "%'");
			}
		}
		return criteria.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.ProductDAO#saveFeedback(com.foundstone.s3i.model.Product)
	 */
	public void saveFeedback(Product prod) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products where id = '"
				+ prod.getId() + "'";
		
		List feedList = prod.getFeedback();
		ProductRowMapper mapper = new ProductRowMapper();
		List list = jt.query(query, mapper);

		Product product = (Product) list.get(0);
		product.setFeedback(feedList);
		System.out.println("HEre is the product inside of the DAO: " + product);
		saveFeedBacks(product.getFeedback(), product);

	}

	/**
	 * @param feedback
	 */
	private void saveFeedBacks(List feedback, Product parent) {
		if (feedback == null) {
			System.out.println("Not sure why the feedback object is null");
			return;
		}

		JdbcTemplate jt = new JdbcTemplate(getDataSource());

		String query = "delete from feedback where product_id_fk = "
				+ parent.getId();
		try {
			System.out.println("Deleting previous feedback...will readd.");
			System.out.println("feedback == " + feedback);
			jt.execute(query);			
		} catch (Exception e) {

		}
		
		System.out.println("Getting ready to iterate over feedbacks");
		Iterator iter = feedback.iterator();
		while (iter.hasNext()) {
			Feedback fb = (Feedback) iter.next();

			query = "insert into feedback (feedback, product_id_fk) values "
					+ "('" + fb.getFeedback() + "', " + parent.getId() + ")";
			jt.execute(query);
			System.out.println("Query for feedback inserts: " + query);
		}
	}

	/**
	 * @param product
	 */
	private List getFeedBacks(Product product) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());

		String query = "select feedback.* from feedback "
				+ "left outer join products on feedback.product_id_fk = products.id "
				+ "where products.id = " + product.getId();

		List list = jt.query(query, new ProductFeedbackRowMapper());
		return list;
	}

	public Product getProductById(String id) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String query = "select * from products where id = " + id;
		List list = jt.query(query, new ProductRowMapper());
		if (list.size() != 0) {
			Product product = (Product) list.get(0);
			product.setFeedback(getFeedBacks(product));
			return product;
		} else {
			throw new ObjectRetrievalFailureException(Product.class, id);
		}
	}
}