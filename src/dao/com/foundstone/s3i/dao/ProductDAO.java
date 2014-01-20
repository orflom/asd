/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao;

import java.util.List;

import com.foundstone.s3i.model.Product;

/**
 * @author davidraphael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ProductDAO {
	public List getProducts();
	public List getProductsWithoutDescritions();
	public Product getProduct(int id);
	public Product getProductByTitle(String title);
	public Product getProductById(String id);
	public List getProductsByTitleKeyWords(String[] keywords); 
	public void saveFeedback(Product prod);
}
