package com.foundstone.s3i.service;

public interface PaymentManager {

	public void processCreditCardPayment(String ccNumber, String expiration,
			Double amount) throws PaymentException;

	public void processBankAccountPayment(String accountNumber, Double amount)
			throws PaymentException;

}