package com.drey.encryption;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for the IDEA encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class IDEA extends Encryption {

	/**
	 * Constructor, string key.
	 * 
	 * @param keyStr
	 */
	public IDEA(String keyStr) {
		this.name = IDEA.class.getSimpleName();
		keySize = 16;
		blockSize = 8;
		setKey(keyStr);
	}

	private final String charToAdd = " ";
	
	@Override
	public String decrypt(String text) {
		StringBuffer result = new StringBuffer();

		int charactersAdded = 0;
		List<String> encryptedTextList = new ArrayList<String>();
		if (text != null) {
			if (text.length() == 8) {
				encryptedTextList.add(text);
			} else {
				int mod = text.length() % blockSize;
				if (mod != 0) {
					mod = blockSize - mod;
					for (int i = 0; i < mod; i++) {
						charactersAdded++;
						text = text.concat(charToAdd);
					}
					/*
					int x = text.length() + charactersAdded;
					int y = x-blockSize;
					String s = text.substring(y, text.length());
					
					for (int i=0; i<charactersAdded; i++){
						s = s.concat(charToAdd);
					}
					System.out.println(s);
					
					byte[] s2 = new byte[s.getBytes().length];
					decrypt(s.getBytes(), 2, s2, 2);
					
					System.out.println(new String (s2));
					*/
				}

				for (int i = 0; i < text.length() / blockSize; i++) {
					encryptedTextList.add(text.substring(i * blockSize, i * blockSize + blockSize));
				}
			}
			
			for (String encryptedText : encryptedTextList) {
				byte[] encryptedTextByte = encryptedText.getBytes();
				byte[] decryptedTextByte = new byte[encryptedTextByte.length];

				decrypt(encryptedTextByte, 0, decryptedTextByte, 0);

				result.append(new String(decryptedTextByte));
			}
		}

		//return result.toString().substring(0, result.length()-charactersAdded);
		return result.toString();
	}

	@Override
	public String encrypt(String text) {
		StringBuffer result = new StringBuffer();

		int charactersAdded = 0;
		List<String> clearTextList = new ArrayList<String>();
		if (text != null) {
			if (text.length() == 8) {
				clearTextList.add(text);
			} else {
				int mod = text.length() % blockSize;
				if (mod != 0) {
					mod = blockSize - mod;
					for (int i = 0; i < mod; i++) {
						text = text.concat(charToAdd); //add white spaces to the end
						charactersAdded++;
					}
				}
				
				for (int i = 0; i < text.length() / blockSize; i++) {
					clearTextList.add(text.substring(i * blockSize, i * blockSize + blockSize));
				}
			}

			for (String clearText : clearTextList) {
				byte[] clearTextByte = clearText.getBytes();
				byte[] encryptedTextByte = new byte[clearTextByte.length];

				encrypt(clearTextByte, 0, encryptedTextByte, 0);

				result.append(new String(encryptedTextByte));
			}
		}

		//return result.toString().substring(0, result.length()-charactersAdded);
		return result.toString();
	}

	/**
	 * Utility routine to set the key from a string.
	 * 
	 * @param keyStr
	 */
	private void setKey(String keyStr) {
		setKey(makeKey(keyStr));
	}

	/**
	 * Key routines
	 */
	private int[] encryptKeys = new int[52];
	private int[] decryptKeys = new int[52];

	/**
	 * Block encryption routines.
	 */
	private int[] tempShorts = new int[4];

	/**
	 * Encrypt a block of eight bytes.
	 * 
	 * @param clearText
	 * @param clearOff
	 * @param cipherText
	 * @param cipherOff
	 */
	private void encrypt(byte[] clearText, int clearOff, byte[] cipherText, int cipherOff) {
		squashBytesToShorts(clearText, clearOff, tempShorts, 0, 4);
		idea(tempShorts, tempShorts, encryptKeys);
		spreadShortsToBytes(tempShorts, 0, cipherText, cipherOff, 4);
	}

	/**
	 * Decrypt a block of eight bytes.
	 * 
	 * @param cipherText
	 * @param cipherOff
	 * @param clearText
	 * @param clearOff
	 */
	private void decrypt(byte[] cipherText, int cipherOff, byte[] clearText, int clearOff) {
		squashBytesToShorts(cipherText, cipherOff, tempShorts, 0, 4);
		idea(tempShorts, tempShorts, decryptKeys);
		spreadShortsToBytes(tempShorts, 0, clearText, clearOff, 4);
	}

	/**
	 * Set the key.
	 * 
	 * @param key
	 */
	private void setKey(byte[] key) {
		int k1, k2, j;
		int t1, t2, t3;

		// Encryption keys. The first 8 key values come from the 16
		// user-supplied key bytes.
		for (k1 = 0; k1 < 8; ++k1) {
			encryptKeys[k1] = ((key[2 * k1] & 0xff) << 8) | (key[2 * k1 + 1] & 0xff);
		}

		// Subsequent key values are the previous values rotated to the
		// left by 25 bits.
		for (; k1 < 52; ++k1) {
			encryptKeys[k1] = ((encryptKeys[k1 - 8] << 9) | (encryptKeys[k1 - 7] >>> 7)) & 0xffff;
		}

		// Decryption keys. These are the encryption keys, inverted and
		// in reverse order.
		k1 = 0;
		k2 = 51;
		t1 = mulinv(encryptKeys[k1++]);
		t2 = -encryptKeys[k1++];
		t3 = -encryptKeys[k1++];
		decryptKeys[k2--] = mulinv(encryptKeys[k1++]);
		decryptKeys[k2--] = t3;
		decryptKeys[k2--] = t2;
		decryptKeys[k2--] = t1;
		for (j = 1; j < 8; ++j) {
			t1 = encryptKeys[k1++];
			decryptKeys[k2--] = encryptKeys[k1++];
			decryptKeys[k2--] = t1;
			t1 = mulinv(encryptKeys[k1++]);
			t2 = -encryptKeys[k1++];
			t3 = -encryptKeys[k1++];
			decryptKeys[k2--] = mulinv(encryptKeys[k1++]);
			decryptKeys[k2--] = t2;
			decryptKeys[k2--] = t3;
			decryptKeys[k2--] = t1;
		}
		t1 = encryptKeys[k1++];
		decryptKeys[k2--] = encryptKeys[k1++];
		decryptKeys[k2--] = t1;
		t1 = mulinv(encryptKeys[k1++]);
		t2 = -encryptKeys[k1++];
		t3 = -encryptKeys[k1++];
		decryptKeys[k2--] = mulinv(encryptKeys[k1++]);
		decryptKeys[k2--] = t3;
		decryptKeys[k2--] = t2;
		decryptKeys[k2--] = t1;
	}

	/**
	 * Run IDEA on one block.
	 * 
	 * @param inShorts
	 * @param outShorts
	 * @param keys
	 */
	private void idea(int[] inShorts, int[] outShorts, int[] keys) {
		int x1, x2, x3, x4, k, t1, t2;

		x1 = inShorts[0];
		x2 = inShorts[1];
		x3 = inShorts[2];
		x4 = inShorts[3];
		k = 0;
		for (int round = 0; round < 8; ++round) {
			x1 = multiplicationModulo65537(x1 & 0xffff, keys[k++]);
			x2 = x2 + keys[k++];
			x3 = x3 + keys[k++];
			x4 = multiplicationModulo65537(x4 & 0xffff, keys[k++]);
			t2 = x1 ^ x3;
			t2 = multiplicationModulo65537(t2 & 0xffff, keys[k++]);
			t1 = t2 + (x2 ^ x4);
			t1 = multiplicationModulo65537(t1 & 0xffff, keys[k++]);
			t2 = t1 + t2;
			x1 ^= t1;
			x4 ^= t2;
			t2 ^= x2;
			x2 = x3 ^ t1;
			x3 = t2;
		}
		outShorts[0] = multiplicationModulo65537(x1 & 0xffff, keys[k++]) & 0xffff;
		outShorts[1] = (x3 + keys[k++]) & 0xffff;
		outShorts[2] = (x2 + keys[k++]) & 0xffff;
		outShorts[3] = multiplicationModulo65537(x4 & 0xffff, keys[k++]) & 0xffff;
	}

	/**
	 * Multiplication modulo 65537.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static int multiplicationModulo65537(int a, int b) {
		int ab = a * b;
		if (ab != 0) {
			int lo = ab & 0xffff;
			int hi = ab >>> 16;
			return ((lo - hi) + (lo < hi ? 1 : 0)) & 0xffff;
		}
		if (a != 0) {
			return (1 - a) & 0xffff;
		}
		return (1 - b) & 0xffff;
	}

	/**
	 * The multiplicative inverse of x, modulo 65537. Uses Euclid's GCD
	 * algorithm. It is unrolled twice to avoid swapping the meaning of the
	 * registers each iteration, and some subtracts of t have been changed to
	 * adds.
	 * 
	 * @param x
	 * @return
	 */
	private static int mulinv(int x) {
		int t0, t1, q, y;
		if (x <= 1) {
			return x; // 0 and 1 are self-inverse
		}
		t0 = 1;
		t1 = 0x10001 / x; // since x >= 2, this fits into 16 bits
		y = (0x10001 % x) & 0xffff;
		for (;;) {
			if (y == 1) {
				return (1 - t1) & 0xffff;
			}
			q = x / y;
			x = x % y;
			t0 = (t0 + q * t1) & 0xffff;
			if (x == 1) {
				return t0;
			}
			q = y / x;
			y = y % x;
			t1 = (t1 + q * t0) & 0xffff;
		}
	}

}