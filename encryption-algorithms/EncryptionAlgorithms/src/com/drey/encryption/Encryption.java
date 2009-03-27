package com.drey.encryption;

public abstract class Encryption extends Utils {

	protected String name;
	
	/**
	 * How big a block is
	 */
	protected int blockSize;

	/**
	 * How big a key is. Key-less ciphers use 0. Variable-length-key ciphers
	 * also use 0.
	 */
	protected int keySize;

	/**
	 * Encryption method.
	 * 
	 * @param text
	 * @return
	 */
	public abstract String encrypt(String text);

	/**
	 * Decryption method.
	 * 
	 * @param text
	 * @return
	 */
	public abstract String decrypt(String text);

	/**
	 * Utility routine to turn a string into a key of the right length.
	 * 
	 * @param keyStr
	 * @return
	 */
	protected byte[] makeKey(String keyStr) {
		byte[] key;
		if (keySize == 0) {
			key = new byte[keyStr.length()];
		} else {
			key = new byte[keySize];
		}
		int i, j;

		for (j = 0; j < key.length; ++j) {
			key[j] = 0;
		}

		for (i = 0, j = 0; i < keyStr.length(); ++i, j = (j + 1) % key.length) {
			key[j] ^= (byte) keyStr.charAt(i);
		}

		return key;
	}

	public String getName() {
		return name;
	}
}