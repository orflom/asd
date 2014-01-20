package com.foundstone.s3i.util;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import junit.framework.TestCase;

public class CryptoUtilsTest extends TestCase {

	private CryptoUtils util;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(CryptoUtilsTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// Call all setters for CryptoUtils
		util = new CryptoUtils();
		util.setAlgorithm("AES"); // Advanced Encryption Standard
		util.setCipherMode("CBC"); // Cipher Block Chaining
		util.setKeyAlias("HACME_KEY"); // 
		util.setKeyStoreName("HACME_KS"); // 
		util.setKeyStoreType("JCEKS"); // SunJCE KeyStore Implementation
		util.setPaddingScheme("PKCS5Padding");// Public Key Crypto Standard #5
		util.setPassword("password");
		util.setProvider("SunJCE"); // Sun implementation of JCE

		// Create a brand new Key for this test
		util.generateKey(128, SecureRandom.getInstance("SHA1PRNG", "SUN"));
	}

	public void testEncryptAndDecrypt() throws GeneralSecurityException {
		// encrypt the plaintext
		String ciphertext = util.encrypt("A plaintext phrase".getBytes());

		// decrypt the ciphertext using the same IV
		byte[] plaintext = util.decrypt(ciphertext);

		// compare original phrase with decrypted text
		assertTrue("A plaintext phrase".equals(new String(plaintext)));

	}

}