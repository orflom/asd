/*
 * Created on Jan 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * Product class
 * 
 * This class is used to generate the Struts Validator Form as well as play the
 * role of a DTO (POJO)
 * 
 * 
 * <p><a href="Product.java.html"><i>View Source</i></a></p>
 * 
 * @author David Raphael
 * 
 * @struts.form include-all="true" extends="BaseForm"
 */

public class Product implements Comparable {
	int id = 0;
	private String title;
	private String description;
	private int popularity;
	private double price = 0;
	private String vendor;
	private String category;
	private String publisher;
	private String isbn;
	private String author;
	private int quantity = 0;
	private transient long uid = System.currentTimeMillis();
	private transient String keyWords;
	private String imgUrl = null;
	List feedbacks = new ArrayList();

	public List getFeedback() {
		return feedbacks;
	}
	public void setFeedback(List feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public void addFeedback(Feedback feedback){
		feedbacks.add(feedback);		
	}
	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the isbn.
	 */
	public String getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn The isbn to set.
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return Returns the popularity.
	 */
	public int getPopularity() {
		return popularity;
	}
	/**
	 * @param popularity The popularity to set.
	 */
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return Returns the publisher.
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher The publisher to set.
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return Returns the quantity.
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the vendor.
	 */
	public String getVendor() {
		return vendor;
	}
	/**
	 * @param vendor The vendor to set.
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}

	

	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Product myClass = (Product) object;
		return new CompareToBuilder().append(this.vendor, myClass.vendor)
				.append(this.title, myClass.title).append(this.category,
						myClass.category).append(this.uid, myClass.uid).append(
						this.publisher, myClass.publisher).append(this.isbn,
						myClass.isbn).append(this.feedbacks, myClass.feedbacks)
				.append(this.imgUrl, myClass.imgUrl)
				.append(this.id, myClass.id).append(this.price, myClass.price)
				.append(this.description, myClass.description).append(
						this.keyWords, myClass.keyWords).append(this.quantity,
						myClass.quantity).append(this.popularity,
						myClass.popularity).append(this.author, myClass.author)
				.toComparison();
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Product)) {
			return false;
		}
		Product rhs = (Product) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.vendor, rhs.vendor).append(this.title, rhs.title).append(
				this.category, rhs.category).append(this.uid, rhs.uid).append(
				this.publisher, rhs.publisher).append(this.isbn, rhs.isbn)
				.append(this.feedbacks, rhs.feedbacks).append(this.imgUrl,
						rhs.imgUrl).append(this.id, rhs.id).append(this.price,
						rhs.price).append(this.description, rhs.description)
				.append(this.keyWords, rhs.keyWords).append(this.quantity,
						rhs.quantity).append(this.popularity, rhs.popularity)
				.append(this.author, rhs.author).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(565519285, -437526869).appendSuper(
				super.hashCode()).append(this.vendor).append(this.title)
				.append(this.category).append(this.uid).append(this.publisher)
				.append(this.isbn).append(this.feedbacks).append(this.imgUrl)
				.append(this.id).append(this.price).append(this.description)
				.append(this.keyWords).append(this.quantity).append(
						this.popularity).append(this.author).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("vendor", this.vendor).append("id", this.id).append(
						"quantity", this.quantity).append("publisher",
						this.publisher).append("price", this.price).append(
						"description", this.description).append("keyWords",
						this.keyWords).append("popularity", this.popularity)
				.append("isbn", this.isbn).append("title", this.title).append(
						"imgUrl", this.imgUrl).append("feedback",
						this.getFeedback()).append("author", this.author)
				.append("category", this.category).append("uid", this.uid)
				.toString();
	}
}
