/*
 * Created on Feb 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.foundstone.s3i.dao.ProductDAO;
import com.foundstone.s3i.model.Product;
import com.foundstone.s3i.service.ProductsManager;

/**
 * @author davidraphael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductsManagerImpl implements ProductsManager {

	private ProductDAO dao;
	private Log log = LogFactory.getLog(ProductsManager.class);
	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#setProductDAO(com.foundstone.s3i.dao.ProductDAO)
	 */
	public void setProductDAO(ProductDAO dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#getProduct(java.lang.String)
	 */
	public Product getProduct(String title) {
		return dao.getProductByTitle(title);
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#getProducts()
	 */
	public List getProducts() {
		return dao.getProducts();
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#getProduct(int)
	 */
	public Product getProduct(int id) {
		return dao.getProduct(id);
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#getProductByKeywords(java.lang.String[])
	 */
	public List getProductByKeywords(String[] keyWords) {
		// TODO Auto-generated method stub
		return dao.getProductsByTitleKeyWords(keyWords);
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#saveFeedback(com.foundstone.s3i.model.Product)
	 */
	public void saveFeedback(Product product) {
		dao.saveFeedback(product);
		
	}

	public Product getProductById(String id) {
		// TODO Auto-generated method stub
		return dao.getProductById(id);
	}

	/* (non-Javadoc)
	 * @see com.foundstone.s3i.service.ProductsManager#getProductsWithoutDescriptions()
	 */
	public List getProductsWithoutDescriptions() {
		// TODO Auto-generated method stub
		return dao.getProductsWithoutDescritions();
	}
}
