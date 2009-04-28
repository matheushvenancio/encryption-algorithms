package com.drey.encryption;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Implementation for the RSA encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class RSA extends Encryption {

	private final static BigInteger one = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();

	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;

	/**
	 * Generate an N-bit (roughly) public and private key
	 * 
	 * @param N
	 */
	private RSA(int N) {
		BigInteger p = BigInteger.probablePrime(N / 2, random);
		BigInteger q = BigInteger.probablePrime(N / 2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

		modulus = p.multiply(q);
		// common value in practice = 2^16 + 1
		publicKey = new BigInteger("65537");
		privateKey = publicKey.modInverse(phi);
	}

	@Override
	public String decrypt(String text) {
		BigInteger encrypted = new BigInteger(text.getBytes());
		return new String(encrypted.modPow(privateKey, modulus).toByteArray());
	}

	@Override
	public String encrypt(String text) {
		BigInteger message = new BigInteger(text.getBytes());
		return new String(message.modPow(publicKey, modulus).toByteArray());
	}

	@Override
	protected void setKey(byte[] key) {

	}
}