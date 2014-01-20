package com.foundstone.s3i.service;

import java.util.List;

import com.foundstone.s3i.dao.ProductDAO;
import com.foundstone.s3i.model.Product;

public interface ProductsManager {

    public void setProductDAO(ProductDAO dao);
    
    public Product getProduct(String title);
    public Product getProduct(int id);
    public Product getProductById(String id);
    public List getProductByKeywords(String[] keyWords);
    public List getProducts();
    public List getProductsWithoutDescriptions();
    public void saveFeedback(Product product);

}
