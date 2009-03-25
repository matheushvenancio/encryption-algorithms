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

}