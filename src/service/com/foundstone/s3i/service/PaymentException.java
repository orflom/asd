package com.foundstone.s3i.service;

public class PaymentException extends Exception {

	public PaymentException() {
		super();
	}

	public PaymentException(String arg0) {
		super(arg0);
	}

	public PaymentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PaymentException(Throwable arg0) {
		super(arg0);
	}
}
