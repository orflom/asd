/*
 * Created on Jan 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.model;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author davidraphael
 * 
 * @struts.form include-all="true" extends="BaseForm"
 */
public class CustomerOrderItem implements Comparable {
	
	int id = 0;

	float taxPercentage = 0;

	float totalSalePrice = 0;

	Product product = null;

	private transient long uid = System.currentTimeMillis(); 
	
	//CustomerOrder parentOrder = null;

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	//	/**
	//	 * @return Returns the parentOrder.
	//	 */
	//	public CustomerOrder getParentOrder() {
	//		return parentOrder;
	//	}
	//	/**
	//	 * @param parentOrder The parentOrder to set.
	//	 */
	//	public void setParentOrder(CustomerOrder parentOrder) {
	//		this.parentOrder = parentOrder;
	//	}
	/**
	 * @return Returns the product.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            The product to set.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return Returns the taxPercentage.
	 */
	public float getTaxPercentage() {
		return taxPercentage;
	}

	/**
	 * @param taxPercentage
	 *            The taxPercentage to set.
	 */
	public void setTaxPercentage(float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	/**
	 * @return Returns the totalSalePrice.
	 */
	public float getTotalSalePrice() {
		return totalSalePrice;
	}

	/**
	 * @param totalSalePrice
	 *            The totalSalePrice to set.
	 */
	public void setTotalSalePrice(float totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}

	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		CustomerOrderItem myClass = (CustomerOrderItem) object;
		return new CompareToBuilder().append(this.product, myClass.product)
				.append(this.taxPercentage, myClass.taxPercentage).append(
						this.uid, myClass.uid).append(this.id, myClass.id)
				.append(this.totalSalePrice, myClass.totalSalePrice)
				.toComparison();
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CustomerOrderItem)) {
			return false;
		}
		CustomerOrderItem rhs = (CustomerOrderItem) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.product, rhs.product).append(this.taxPercentage,
				rhs.taxPercentage).append(this.uid, rhs.uid).append(this.id,
				rhs.id).append(this.totalSalePrice, rhs.totalSalePrice)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(427328969, -1850407961).appendSuper(
				super.hashCode()).append(this.product).append(
				this.taxPercentage).append(this.uid).append(this.id).append(
				this.totalSalePrice).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.id).append("totalSalePrice",
						this.totalSalePrice).append("taxPercentage",
						this.taxPercentage).append("uid", this.uid).append(
						"product", this.product).toString();
	}
}