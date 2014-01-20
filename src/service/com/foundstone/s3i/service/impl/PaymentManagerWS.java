package com.foundstone.s3i.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.foundstone.s3i.service.PaymentException;
import com.foundstone.s3i.service.PaymentManager;
import com.foundstone.s3i.service.ws.ArrayOfAnyType;
import com.foundstone.s3i.service.ws.WS_AccountManagement;
import com.foundstone.s3i.service.ws.WS_AccountManagementLocator;
import com.foundstone.s3i.service.ws.WS_AccountManagementSoap;
import com.foundstone.s3i.service.ws.WS_UserManagement;
import com.foundstone.s3i.service.ws.WS_UserManagementLocator;
import com.foundstone.s3i.service.ws.WS_UserManagementSoap;

public class PaymentManagerWS implements PaymentManager {

	private String accountURL;

	private String hacmeBankAccountNumber;

	private String hacmeBankLogin;

	private String hacmeBankPassword;

	protected transient final Log log = LogFactory.getLog(getClass());

	private String userURL;

	/**
	 * @return Returns the accountURL.
	 */
	public String getAccountURL() {
		return accountURL;
	}

	/**
	 * @return Returns the hacmeBankAccountNumber.
	 */
	public String getHacmeBankAccountNumber() {
		return hacmeBankAccountNumber;
	}

	/**
	 * @return Returns the hacmeBankLogin.
	 */
	public String getHacmeBankLogin() {
		return hacmeBankLogin;
	}

	/**
	 * @return Returns the hacmeBankPassword.
	 */
	public String getHacmeBankPassword() {
		return hacmeBankPassword;
	}

	/**
	 * Logs in to HacmeBank and obtains sessionId.
	 * 
	 * @return
	 */
	private String getHacmeBankSessionId() throws PaymentException {
		String sessionId = null;

		// Make a service
		WS_UserManagement service = new WS_UserManagementLocator();

		// Now use the service to get a stub which implements the SDI.
		try {
			WS_UserManagementSoap port = service
					.getWS_UserManagementSoap(new URL(getUserURL()));

			log.debug("Initiating HacmeBank user login via web service.");

			// Attempt to login with configured credentials
			sessionId = port.login(getHacmeBankLogin(), getHacmeBankPassword());

			if (sessionId == null || sessionId.equals("0")) {
				// if the supplied login credentials did not work, go ahead and
				// create the new login and bank account at HacmeBank.
				sessionId = initializeHacmeBank();
				if (sessionId == null || sessionId.equals("0")) {
					throw new PaymentException(
							"Unable to automate creation of HacmeBank login.");
				}
			}
		} catch (MalformedURLException mue) {
			log.error("Malformed URL for Hacme Bank web services", mue);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", mue);
		} catch (RemoteException re) {
			log.error("RemoteException when attempting to use Hacme Bank"
					+ " web services", re);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", re);
		} catch (ServiceException se) {
			log.error("ServiceException when attempting to use Hacme Bank"
					+ " web services", se);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", se);
		}

		return sessionId;
	}

	/**
	 * @return Returns the userURL.
	 */
	public String getUserURL() {
		return userURL;
	}

	/**
	 * Uses a HacmeBank web service in order to process bank account transfers.
	 * 
	 * @see com.foundstone.s3i.service.PaymentManager#processBankAccountPayment(java.lang.Integer,
	 *      java.lang.Double)
	 */
	public void processBankAccountPayment(String accountNumber, Double amount)
			throws PaymentException {
		//FIXME: need to write a unit test for this class

		// Make a service
		WS_AccountManagement service = new WS_AccountManagementLocator();

		// Now use the service to get a stub which implements the SDI.
		try {
			WS_AccountManagementSoap port = service
					.getWS_AccountManagementSoap(new URL(getAccountURL()));

			log.debug("HacmeBank bank account payment initiated.");

			// Make the payment using the web service
			int result = port.transferFunds(getHacmeBankSessionId(),
					accountNumber, getHacmeBankAccountNumber(), amount
							.intValue(),
					"Bank Account purchase from Hacme Books.");

			if (result == 0) {
				throw new PaymentException("Invalid payment data");
			}

		} catch (MalformedURLException mue) {
			log.error("Malformed URL for Hacme Bank web services", mue);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", mue);
		} catch (RemoteException re) {
			log.error("RemoteException when attempting to use Hacme Bank"
					+ " web services", re);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", re);
		} catch (ServiceException se) {
			log.error("ServiceException when attempting to use Hacme Bank"
					+ " web services", se);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", se);
		}
	}

	/**
	 * Uses a HacmeBank web service in order to process credit card payments.
	 * 
	 * @see com.foundstone.s3i.service.PaymentManager#processCreditCardPayment(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Double)
	 */
	public void processCreditCardPayment(String ccNumber, String expiration,
			Double amount) throws PaymentException {
		//FIXME: need to write a unit test for this class

		// Make a service
		WS_AccountManagement service = new WS_AccountManagementLocator();

		// Now use the service to get a stub which implements the SDI.
		try {
			WS_AccountManagementSoap port = service
					.getWS_AccountManagementSoap(new URL(getAccountURL()));

			log.debug("HacmeBank credit card payment initiated.");

			// Make the payment using the web service
			int result = port.makePayment_Using_CreditCard(
					getHacmeBankSessionId(), String.valueOf(ccNumber), String
							.valueOf(expiration), getHacmeBankAccountNumber(),
					amount.intValue(), "Credit card purchase at HacmeBooks");

			if (result == 0) {
				throw new PaymentException("Invalid payment data");
			}
		} catch (MalformedURLException mue) {
			log.error("Malformed URL for Hacme Bank web services", mue);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", mue);
		} catch (RemoteException re) {
			log.error("RemoteException when attempting to use Hacme Bank"
					+ " web services", re);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", re);
		} catch (ServiceException se) {
			log.error("ServiceException when attempting to use Hacme Bank"
					+ " web services", se);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", se);
		}
	}

	/**
	 * @param accountURL
	 *            The accountURL to set.
	 */
	public void setAccountURL(String accountURL) {
		this.accountURL = accountURL;
	}

	/**
	 * @param hacmeBankAccountNumber
	 *            The hacmeBankAccountNumber to set.
	 */
	public void setHacmeBankAccountNumber(String booksAccountNumber) {
		this.hacmeBankAccountNumber = booksAccountNumber;
	}

	/**
	 * @param hacmeBankLogin
	 *            The hacmeBankLogin to set.
	 */
	public void setHacmeBankLogin(String hacmeBankLogin) {
		this.hacmeBankLogin = hacmeBankLogin;
	}

	/**
	 * @param hacmeBankPassword
	 *            The hacmeBankPassword to set.
	 */
	public void setHacmeBankPassword(String hacmeBankPassword) {
		this.hacmeBankPassword = hacmeBankPassword;
	}

	/**
	 * @param userURL
	 *            The userURL to set.
	 */
	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	/**
	 * Creates a new user login and bank account at HacmeBank. Should only be
	 * executed after a login attempt fails.
	 * 
	 * @return
	 */
	private String initializeHacmeBank() throws PaymentException {
		String sessionId = null;

		// Make a service
		WS_UserManagement userService = new WS_UserManagementLocator();
		WS_AccountManagement acctService = new WS_AccountManagementLocator();

		// Now use the service to get a stub which implements the SDI.
		try {
			WS_UserManagementSoap userPort = userService
					.getWS_UserManagementSoap(new URL(getUserURL()));
			WS_AccountManagementSoap acctPort = acctService
					.getWS_AccountManagementSoap(new URL(getAccountURL()));

			log.debug("Creating HacmeBank login and account for loginId: "
					+ getHacmeBankLogin());

			// SessionID is blank because it makes no sense at this point - you
			// don't have an account yet so how can you have a session?
			userPort.createUser("", "Hacme Books", getHacmeBankLogin(),
					getHacmeBankPassword());

			// Attempt to login with new credentials
			sessionId = userPort.login(getHacmeBankLogin(),
					getHacmeBankPassword());

			// determine "userId" for the new login
			ArrayOfAnyType array = userPort.getUserDetail_using_loginID(
					sessionId, getHacmeBankLogin());

			// zero-element (first) in array should be the userID
			String userId = array.getAnyType(0).toString();

			// create a new Hacme Bank account for Hacme Bookstore
			acctPort.createAccount(sessionId, getHacmeBankAccountNumber(),
					userId, "USD", "123 Main Street", "0", "Platinum");
		} catch (MalformedURLException mue) {
			log.error("Malformed URL for Hacme Bank web services", mue);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", mue);
		} catch (RemoteException re) {
			log.error("RemoteException when attempting to use Hacme Bank"
					+ " web services", re);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", re);
		} catch (ServiceException se) {
			log.error("ServiceException when attempting to use Hacme Bank"
					+ " web services", se);
			throw new PaymentException(
					"Communication with Hacme Bank web services failed.", se);
		}
		return sessionId;
	}

}