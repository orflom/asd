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
 * 
 */
public class Feedback implements Comparable {

		public int id = 0;
		public String feedback = "";
		public Product parent = null;
		
		public Product getParent() {
			return parent;
		}
		public void setParent(Product parent) {
			this.parent = parent;
		}
		public String getFeedback() {
			return feedback;
		}
		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Feedback myClass = (Feedback) object;
		return new CompareToBuilder().append(this.feedback, myClass.feedback)
				.append(this.id, myClass.id).toComparison();
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Feedback)) {
			return false;
		}
		Feedback rhs = (Feedback) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.feedback, rhs.feedback).append(this.id, rhs.id).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1795993821, 1625799485).appendSuper(
				super.hashCode()).append(this.feedback).append(this.id)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.id).append("feedback", this.feedback)
				.toString();
	}
}

