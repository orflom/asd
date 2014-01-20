package com.foundstone.s3i.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience class that initializes a Cipher for encryption and decryption
 * operations based on a Key that is stored on the filesystem in a KeyStore. All
 * setter methods should be called before calling encrypt and decrypt methods.
 * Ideally, setters should be called using the Spring application context and a
 * single instance of this class can be reused. This class defers all Exceptions
 * to the caller and is NOT optimized for performance.
 * <p>
 * 
 * CryptoUtils can also be used to generate a symmetric (secret) key and store
 * it in a KeyStore.
 * <p>
 * 
 * The architecture of this class has some limitations, but for the purpose of
 * demonstrating Java crytography it is adequate.
 * 
 * @author roman fail
 *  
 */
public class CryptoUtils {

	private static final int ivLen = 16;

	public static byte[] fromHexString(String encoded) {
		try {
			return Hex.decodeHex(encoded.toCharArray());
		} catch (DecoderException de) {
			throw new RuntimeException(de.getMessage(), de);
		}
	}

	public static String toHexString(byte[] unencoded) {
		return new String(Hex.encodeHex(unencoded));
	}

	/* Default to AES - Advanced Encryption Standard. Available in JDK 1.4.2+ */
	private String algorithm = "AES";

	/*
	 * Default to CBC - Cipher Block Chaining, which requires an initialization
	 * vector
	 */
	private String cipherMode = "CBC";

	private Key key;

	/* Default to HACME_KEY */
	private String keyAlias = "HACME_KEY";

	private boolean keyLoaded = false;

	/* Default to HACME_KS */
	private String keyStoreName = "HACME_KS";

	/* Default to JCEKS */
	private String keyStoreType = "JCEKS";

	private Log log = LogFactory.getLog(CryptoUtils.class);

	/* Default to PKCS5Padding */
	private String paddingScheme = "PKCS5Padding";

	private char[] password; // MANDATORY PARAMETER; no default provided

	/* Default to SunJCE */
	private String provider = "SunJCE";

	/**
	 * Decrypts ciphertext (in hexadecimal String format) with a previously
	 * loaded cryptograpic key and algorithm. It is assumed that the first 16
	 * bytes of ciphertext are the initialization vector. If there is no IV,
	 * please ensure the ciphertext is prefixed with 32 zeroes.
	 * 
	 * @param ciphertext
	 *            a String in hexadecimal format with the IV as first 16 bytes
	 * @return the plaintext bytes
	 * @throws GeneralSecurityException
	 */
	public byte[] decrypt(String ciphertext) throws GeneralSecurityException {
		if (!keyLoaded) {
			loadKey();
		}
		String transformation = algorithm + "/" + cipherMode + "/"
				+ paddingScheme;
		Cipher cipher = Cipher.getInstance(transformation, provider);

		String iv = null;
		String cipherString = null;
		try {
			iv = ciphertext.substring(0, ivLen * 2);
			cipherString = ciphertext.substring(ivLen * 2);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new GeneralSecurityException(
					"ciphertext was too short; decryption failed");
		}

		// check to see if the IV is all zeroes
		boolean blankIV = true;
		byte[] ivBytes = fromHexString(iv);
		for (int i = 0; i < ivLen; i++) {
			if (ivBytes[i] != 0) {
				blankIV = false;
			}
		}

		// Call appropriate init based on presence of IV
		if (blankIV) {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(
					fromHexString(iv)));
		}

		return cipher.doFinal(fromHexString(cipherString));
	}

	/**
	 * Encrypts plaintext with a previously loaded cryptograpic key and
	 * algorithm. If required, a 16-byte initialization vector is created based
	 * on the default SecureRandom implemenation. The initialization vector will
	 * be prepended to the returned ciphertext (possibly all zeros, if no IV was
	 * required).
	 * <p>
	 * 
	 * Note that the plaintext argument is a byte array rather than a String in
	 * order to avoid character-encoding issues. Also note that the default IV
	 * generation scheme may not meet your security requirements; it may be
	 * desirable to write your own implementation based on SecureRandom or
	 * another numbering scheme.
	 * 
	 * @param plaintext
	 * @return the encrypted bytes and initialization vector as a hexadecimal
	 *         String
	 * @throws GeneralSecurityException
	 */
	public String encrypt(byte[] plaintext) throws GeneralSecurityException {
		if (!keyLoaded) {
			loadKey();
		}
		String transformation = algorithm + "/" + cipherMode + "/"
				+ paddingScheme;
		Cipher cipher = Cipher.getInstance(transformation, provider);

		// Create an IV, which may or may not be used depending on the
		// particular algorith and block cipher mode.
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] iv = new byte[ivLen];
		random.nextBytes(iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

		byte[] ciphertext = cipher.doFinal(plaintext);
		byte[] cipherIV = cipher.getIV();

		return toHexString(cipherIV) + toHexString(ciphertext);
	}

	/**
	 * Generates a new SecretKey and adds it to a KeyStore, using names and
	 * parameters specified by the setter methods. A new KeyStore is created if
	 * necessary.
	 */
	public void generateKey(int keySize, SecureRandom random)
			throws GeneralSecurityException {
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm, provider);
		keygen.init(keySize, random);
		SecretKey key = keygen.generateKey();

		KeyStore ks = KeyStore.getInstance(keyStoreType, provider);
		try {
			// open existing KeyStore or create new
			if (new File(keyStoreName).exists()) {
				FileInputStream fis = new FileInputStream(keyStoreName);
				ks.load(fis, password);
				fis.close();
			} else {
				ks.load(null, password);
			}

			// add the new key to the keystore
			ks.setKeyEntry(keyAlias, key, password, null);
			
			// Persist KeyStore to filesystem
			FileOutputStream fos = new FileOutputStream(keyStoreName);
			ks.store(fos, password);
			fos.close();
		} catch (IOException ioe) {
			throw new GeneralSecurityException(ioe.getMessage());
		}
	}

	/* Loads the Key from Keystore */
	private void loadKey() throws GeneralSecurityException {
		// create a keystore instance with specified type
		KeyStore ks = null;
		ks = KeyStore.getInstance(keyStoreType);

		// Load the KeyStore from filesystem using the password
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(keyStoreName);
			ks.load(fis, password);
			fis.close();
		} catch (IOException ioe) {
			throw new GeneralSecurityException(ioe.getMessage());
		}

		key = ks.getKey(keyAlias, password);

		keyLoaded = true;
	}

	/**
	 * @param algorithm
	 *            The algorithm to set.
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @param cipherMode
	 *            The cipherMode to set.
	 */
	public void setCipherMode(String cipherMode) {
		this.cipherMode = cipherMode;
	}

	/**
	 * @param keyAlias
	 *            The keyAlias to set.
	 */
	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	/**
	 * @param keyStoreName
	 *            The keyStoreName to set.
	 */
	public void setKeyStoreName(String keyStoreName) {
		this.keyStoreName = keyStoreName;
	}

	/**
	 * @param keyStoreType
	 *            The keyStoreType to set.
	 */
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	/**
	 * @param paddingScheme
	 *            The paddingScheme to set.
	 */
	public void setPaddingScheme(String paddingScheme) {
		this.paddingScheme = paddingScheme;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password.toCharArray();
	}

	/**
	 * @param provider
	 *            The provider to set.
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
}