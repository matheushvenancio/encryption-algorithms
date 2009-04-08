package com.drey.encryption;

public class Utils {

	/**
	 * Squash bytes down to shorts.
	 * 
	 * @param inBytes
	 * @param inOff
	 * @param outShorts
	 * @param outOff
	 * @param shortLen
	 */
	protected static void squashBytesToShorts(byte[] inBytes, int inOff, int[] outShorts, int outOff, int shortLen) {
		for (int i = 0; i < shortLen; ++i) {
			outShorts[outOff + i] = ((inBytes[inOff + i * 2] & 0xff) << 8) | ((inBytes[inOff + i * 2 + 1] & 0xff));
		}
	}

	/**
	 * Spread shorts into bytes.
	 * 
	 * @param inShorts
	 * @param inOff
	 * @param outBytes
	 * @param outOff
	 * @param shortLen
	 */
	protected static void spreadShortsToBytes(int[] inShorts, int inOff, byte[] outBytes, int outOff, int shortLen) {
		for (int i = 0; i < shortLen; ++i) {
			outBytes[outOff + i * 2] = (byte) ((inShorts[inOff + i] >>> 8) & 0xff);
			outBytes[outOff + i * 2 + 1] = (byte) ((inShorts[inOff + i]) & 0xff);
		}
	}

	/**
	 * Squash bytes down to shorts, little endian.
	 * 
	 * @param inBytes
	 * @param inOff
	 * @param outShorts
	 * @param outOff
	 * @param shortLen
	 */
	protected static void squashBytesToShortsLittle(byte[] inBytes, int inOff, int[] outShorts, int outOff, int shortLen) {
		for (int i = 0; i < shortLen; ++i) {
			outShorts[outOff + i] = ((inBytes[inOff + i * 2] & 0xff)) | ((inBytes[inOff + i * 2 + 1] & 0xff) << 8);
		}
	}

	/**
	 * Spread shorts into bytes, little endian.
	 * 
	 * @param inShorts
	 * @param inOff
	 * @param outBytes
	 * @param outOff
	 * @param shortLen
	 */
	protected static void spreadShortsToBytesLittle(int[] inShorts, int inOff, byte[] outBytes, int outOff, int shortLen) {
		for (int i = 0; i < shortLen; ++i) {
			outBytes[outOff + i * 2] = (byte) ((inShorts[inOff + i]) & 0xff);
			outBytes[outOff + i * 2 + 1] = (byte) ((inShorts[inOff + i] >>> 8) & 0xff);
		}
	}

	/**
	 * Squash bytes down to int's.
	 * 
	 * @param inBytes
	 * @param inOff
	 * @param outInts
	 * @param outOff
	 * @param intLen
	 */
	public static void squashBytesToInts(byte[] inBytes, int inOff, int[] outInts, int outOff, int intLen) {
		for (int i = 0; i < intLen; ++i)
			outInts[outOff + i] = ((inBytes[inOff + i * 4] & 0xff) << 24) | ((inBytes[inOff + i * 4 + 1] & 0xff) << 16)
					| ((inBytes[inOff + i * 4 + 2] & 0xff) << 8) | ((inBytes[inOff + i * 4 + 3] & 0xff));
	}

	/**
	 * Spread int's into bytes.
	 * 
	 * @param inInts
	 * @param inOff
	 * @param outBytes
	 * @param outOff
	 * @param intLen
	 */
	public static void spreadIntsToBytes(int[] inInts, int inOff, byte[] outBytes, int outOff, int intLen) {
		for (int i = 0; i < intLen; ++i) {
			outBytes[outOff + i * 4] = (byte) ((inInts[inOff + i] >>> 24) & 0xff);
			outBytes[outOff + i * 4 + 1] = (byte) ((inInts[inOff + i] >>> 16) & 0xff);
			outBytes[outOff + i * 4 + 2] = (byte) ((inInts[inOff + i] >>> 8) & 0xff);
			outBytes[outOff + i * 4 + 3] = (byte) ((inInts[inOff + i]) & 0xff);
		}
	}

	/**
	 * Test is a number is even.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean even(long x) {
		return (x & 1) == 0;
	}

	/**
	 * Test is a number is odd.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean odd(long x) {
		return (x & 1) != 0;
	}

	/**
	 * Count the number of 1-bits in a byte.
	 * 
	 * @param x
	 * @return
	 */
	public static int countOnes(byte x) {
		return countOnes(x & 0xffL);
	}

	/**
	 * Count the number of 1-bits in an int.
	 * 
	 * @param x
	 * @return
	 */

	public static int countOnes(int x) {
		return countOnes(x & 0xffffffffL);
	}

	/**
	 * Count the number of 1-bits in a long.
	 * 
	 * @param x
	 * @return
	 */
	public static int countOnes(long x) {
		int count = 0;
		while (x != 0) {
			if (odd(x))
				++count;
			x >>>= 1;
		}
		return count;
	}

}