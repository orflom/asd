package com.foundstone.s3i.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.apache.commons.lang.builder.CompareToBuilder;
/**
 * User class
 * 
 * 
 * <p><a href="User.java.html"><i>View Source</i></a></p>
 * 
 * @author David Raphael
 * @struts.form include-all="true" extends="BaseForm"
 */
public class User implements Comparable, Serializable {
    //~ Instance fields
    // ========================================================

    protected String username;
    protected String password;
    protected String confirmPassword;
    protected String firstName;
    protected String lastName;
    //protected Address address = new Address();\
    //protected Set addresses = new HashSet();
    protected String phoneNumber;
    protected String email;
    protected String passwordHint;
    protected Integer version;
    protected Set roles = new HashSet();
    protected String ssn;
    protected int id;

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
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
    /**
     * Returns the username.
     * 
     * @return String
     * 
     * @struts.validator type="required"
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password.
     * @return String
     * 
     * @struts.validator type="required"
     * @struts.validator type="twofields" msgkey="errors.twofields"
     * @struts.validator-args arg1resource="userForm.password"
     * @struts.validator-args arg1resource="userForm.confirmPassword"
     * @struts.validator-var name="secondProperty" value="confirmPassword"
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the confirmedPassword.
     * @return String
     * 
     * @struts.validator type="required"
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Returns the firstName.
     * @return String
     * 
     * @struts.validator type="required"
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastName.
     * @return String
     * 
     * @struts.validator type="required"
     */
    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }


    
    /**
     * Returns the email.  This is an optional field for specifying a
     * different e-mail than the username.
     * 
     * @return String
     * 
     * @struts.validator type="required"
     * @struts.validator type="email"
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phoneNumber.
     * 
     * @return String
     * 
     * @struts.validator type="mask" msgkey="errors.phone"
     * @struts.validator-var name="mask" value="${phone}"
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }



    /**
     * Returns the passwordHint.
     * 
     * @return String
     * 
     * @struts.validator type="required"
     */
    public String getPasswordHint() {
        return passwordHint;
    }

    /**
     * Returns the user's roles.
     * 
     * @return Set
     * 
     */
    public Set getRoles() {
        return roles;
    }

    /**
     * Adds a role for the user
     *
     * @param rolename
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * Sets the username.
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the confirmedPassword.
     * @param confirmPassword The confirmed password to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Sets the firstName.
     * 
     * @param firstName The firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName.
     * @param lastName The lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    /**
//     * Sets the address.
//     * @param address The address to set
//     */
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    /**
     * Sets the email.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the phoneNumber.
     * @param phoneNumber The phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    /**
     * @param passwordHint The password hint to set
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    /**
     * Sets the roles.
     * 
     * @param roles The roles to set
     */
    public void setRoles(Set roles) {
        this.roles = roles;
    }

    /**
     * @return Returns the updated version.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * The updated version to set.
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }


	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		User myClass = (User) object;
		return new CompareToBuilder().append(this.ssn, myClass.ssn).append(
				this.password, myClass.password).append(this.passwordHint,
				myClass.passwordHint).append(this.confirmPassword,
				myClass.confirmPassword)
				.append(this.username, myClass.username).append(this.email,
						myClass.email).append(this.phoneNumber,
						myClass.phoneNumber).append(this.roles, myClass.roles)
				.append(this.id, myClass.id).append(this.firstName,
						myClass.firstName)
				.append(this.version, myClass.version).append(this.lastName,
						myClass.lastName).toComparison();
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof User)) {
			return false;
		}
		User rhs = (User) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.ssn, rhs.ssn).append(this.password, rhs.password).append(
				this.passwordHint, rhs.passwordHint).append(
				this.confirmPassword, rhs.confirmPassword).append(
				this.username, rhs.username).append(this.email, rhs.email)
				.append(this.phoneNumber, rhs.phoneNumber).append(this.roles,
						rhs.roles).append(this.id, rhs.id).append(
						this.firstName, rhs.firstName).append(this.version,
						rhs.version).append(this.lastName, rhs.lastName)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1208801825, -128368871).appendSuper(
				super.hashCode()).append(this.ssn).append(this.password)
				.append(this.passwordHint).append(this.confirmPassword).append(
						this.username).append(this.email).append(
						this.phoneNumber).append(this.roles).append(this.id)
				.append(this.firstName).append(this.version).append(
						this.lastName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.id).append("roles", this.roles).append(
						"firstName", this.firstName).append("ssn", this.ssn)
				.append("lastName", this.lastName).append("passwordHint",
						this.passwordHint).append("version", this.version)
				.append("username", this.username).append("fullName",
						this.getFullName()).append("email", this.email).append(
						"phoneNumber", this.phoneNumber).append("password",
						this.password).append("confirmPassword",
						this.confirmPassword).toString();
	}
}
