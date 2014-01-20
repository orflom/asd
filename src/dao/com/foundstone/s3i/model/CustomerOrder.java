/*
 * Created on Jan 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.model;

import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.foundstone.s3i.Constants;

/**
 * @author davidraphael
 * 
 * 
 * @struts.form include-all="true" extends="BaseForm"
 */
public class CustomerOrder implements Comparable {

	String bankAccountNumber = null;

	String creditCardNumber = null;

	String expiration = null;

	int id = 0;

	String magicCoupon = "";

	List orderEntries = null;

	long orderNumber = 0;

	String paymentType = Constants.PAYMENT_TYPE_CC;

	double salesTax = 0;

	char shipped = 'N';

	String total = "0";

	double totalAmount = 0.0d;

	User user = null;

	public CustomerOrder() {
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		CustomerOrder myClass = (CustomerOrder) object;
		return new CompareToBuilder().append(this.shipped, myClass.shipped)
				.append(this.total, myClass.total).append(this.user,
						myClass.user).append(this.salesTax, myClass.salesTax)
				.append(this.creditCardNumber, myClass.creditCardNumber)
				.append(this.orderNumber, myClass.orderNumber).append(this.id,
						myClass.id).append(this.orderEntries,
						myClass.orderEntries).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CustomerOrder)) {
			return false;
		}
		CustomerOrder rhs = (CustomerOrder) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.shipped, rhs.shipped).append(this.total, rhs.total)
				.append(this.user, rhs.user)
				.append(this.salesTax, rhs.salesTax).append(
						this.creditCardNumber, rhs.creditCardNumber).append(
						this.orderNumber, rhs.orderNumber).append(this.id,
						rhs.id).append(this.orderEntries, rhs.orderEntries)
				.isEquals();
	}

	/**
	 * @return Returns the bankAccountNumber.
	 */
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	/**
	 * @return Returns the creditCardNumber.
	 * 
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String getExpiration() {
		return expiration;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	public String getMagicCoupon() {
		return magicCoupon;
	}

	/**
	 * @return Returns the orderEntries.
	 */
	public List getOrderEntries() {
		return orderEntries;
	}

	/**
	 * @return Returns the orderNumber.
	 */
	public long getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @return Returns the paymentType.
     *
     * @struts.validator type="required"
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @return Returns the salesTax.
	 */
	public double getSalesTax() {
		return salesTax;
	}

	/**
	 * @return Returns the shipped.
	 */
	public char getShipped() {
		return shipped;
	}

	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(218592677, -440652729).appendSuper(
				super.hashCode()).append(this.shipped).append(this.total)
				.append(this.user).append(this.salesTax).append(
						this.creditCardNumber).append(this.orderNumber).append(
						this.id).append(this.orderEntries).toHashCode();
	}

	/**
	 * @param bankAccountNumber
	 *            The bankAccountNumber to set.
	 */
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	/**
	 * @param creditCardNumber
	 *            The creditCardNumber to set.
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setMagicCoupon(String magicCoupon) {
		this.magicCoupon = magicCoupon;
	}

	/**
	 * @param orderEntries
	 *            The orderEntries to set.
	 */
	public void setOrderEntries(List orders) {
		this.orderEntries = orders;
	}

	/**
	 * @param orderNumber
	 *            The orderNumber to set.
	 */
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @param paymentType
	 *            The paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @param salesTax
	 *            The salesTax to set.
	 */
	public void setSalesTax(double salesTax) {
		this.salesTax = salesTax;
	}

	/**
	 * @param shipped
	 *            The shipped to set.
	 */
	public void setShipped(char shipped) {
		this.shipped = shipped;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @param totalAmount
	 *            The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @param user
	 *            The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("orderEntries", this.orderEntries).append(
						"orderNumber", this.orderNumber).append("id", this.id)
				.append("creditCardNumber", this.creditCardNumber).append(
						"user", this.user).append("salesTax", this.salesTax)
				.append("shipped", this.shipped).append("total", this.total)
				.toString();
	}
}