/*
 * Created on Feb 7, 2005
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foundstone.s3i.model.Product;

public class ProductRowMapper implements RowMapper {

	private ProductDAOJdbc productRowMapper;

	/**
	 * @param jdbc
	 */
	ProductRowMapper() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 *      int)
	 */
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("ID"));
		product.setTitle(rs.getString("TITLE"));
		product.setDescription(DbUtils.getStringFromClob(rs
				.getClob("DESCRIPTION")));
		product.setPopularity(rs.getInt("POPULARITY"));
		
		product.setPrice(rs.getDouble("PRICE"));
		product.setVendor(rs.getString("VENDOR"));
		product.setCategory(rs.getString("CATEGORY"));
		product
				.setPublisher(DbUtils
						.getStringFromClob(rs.getClob("PUBLISHER")));
		product.setIsbn(rs.getString("ISBN"));
		product.setAuthor(rs.getString("AUTHOR"));
		product.setQuantity(rs.getInt("QUANTITY"));
		product.setImgUrl(rs.getString("IMGURL"));

		//product.setFeedback(getFeedBacks(product));
		return product;
	}





}