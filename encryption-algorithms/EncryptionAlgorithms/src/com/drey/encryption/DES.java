package com.drey.encryption;


import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for the DES encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class DES extends Encryption {

	/**
	 * Constructor, string key.
	 * 
	 * @param keyStr
	 */
	public DES(String keyStr) {
		this.name = IDEA.class.getSimpleName();
		keySize = 8;
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

		return result.toString();
	}

	/**
	 * Key routines.
	 */
	private int[] encryptKeys = new int[32];
	private int[] decryptKeys = new int[32];

	protected void setKey(byte[] key) {
		for (int i = 0; i < 8; ++i)
			if (even(countOnes(key[i])))
				key[i] ^= 1;

		deskey(key, true, encryptKeys);
		deskey(key, false, decryptKeys);
	}

	/**
	 * Turn an 8-byte key into internal keys.
	 * 
	 * @param keyBlock
	 * @param encrypting
	 * @param KnL
	 */
	private void deskey(byte[] keyBlock, boolean encrypting, int[] KnL) {
		int i, j, l, m, n;
		int[] pc1m = new int[56];
		int[] pcr = new int[56];
		int[] kn = new int[32];

		for (j = 0; j < 56; ++j) {
			l = DESInterface.pc1[j];
			m = l & 07;
			pc1m[j] = ((keyBlock[l >>> 3] & DESInterface.bytebit[m]) != 0) ? 1 : 0;
		}

		for (i = 0; i < 16; ++i) {
			if (encrypting)
				m = i << 1;
			else
				m = (15 - i) << 1;
			n = m + 1;
			kn[m] = kn[n] = 0;
			for (j = 0; j < 28; ++j) {
				l = j + DESInterface.totrot[i];
				if (l < 28)
					pcr[j] = pc1m[l];
				else
					pcr[j] = pc1m[l - 28];
			}
			for (j = 28; j < 56; ++j) {
				l = j + DESInterface.totrot[i];
				if (l < 56)
					pcr[j] = pc1m[l];
				else
					pcr[j] = pc1m[l - 28];
			}
			for (j = 0; j < 24; ++j) {
				if (pcr[DESInterface.pc2[j]] != 0)
					kn[m] |= DESInterface.bigbyte[j];
				if (pcr[DESInterface.pc2[j + 24]] != 0)
					kn[n] |= DESInterface.bigbyte[j];
			}
		}
		cookey(kn, KnL);
	}

	/**
	 * 
	 * @param raw
	 * @param KnL
	 */
	private void cookey(int[] raw, int KnL[]) {
		int raw0, raw1;
		int rawi, KnLi;
		int i;

		for (i = 0, rawi = 0, KnLi = 0; i < 16; ++i) {
			raw0 = raw[rawi++];
			raw1 = raw[rawi++];
			KnL[KnLi] = (raw0 & 0x00fc0000) << 6;
			KnL[KnLi] |= (raw0 & 0x00000fc0) << 10;
			KnL[KnLi] |= (raw1 & 0x00fc0000) >>> 10;
			KnL[KnLi] |= (raw1 & 0x00000fc0) >>> 6;
			++KnLi;
			KnL[KnLi] = (raw0 & 0x0003f000) << 12;
			KnL[KnLi] |= (raw0 & 0x0000003f) << 16;
			KnL[KnLi] |= (raw1 & 0x0003f000) >>> 4;
			KnL[KnLi] |= (raw1 & 0x0000003f);
			++KnLi;
		}
	}

	/**
	 * Block encryption routines.
	 */
	private int[] tempInts = new int[2];

	/**
	 * Encrypt a block of eight bytes.
	 * 
	 * @param clearText
	 * @param clearOff
	 * @param cipherText
	 * @param cipherOff
	 */
	private void encrypt(byte[] clearText, int clearOff, byte[] cipherText, int cipherOff) {
		squashBytesToInts(clearText, clearOff, tempInts, 0, 2);
		des(tempInts, tempInts, encryptKeys);
		spreadIntsToBytes(tempInts, 0, cipherText, cipherOff, 2);
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
		squashBytesToInts(cipherText, cipherOff, tempInts, 0, 2);
		des(tempInts, tempInts, decryptKeys);
		spreadIntsToBytes(tempInts, 0, clearText, clearOff, 2);
	}

	/**
	 * The DES function.
	 * 
	 * @param inInts
	 * @param outInts
	 * @param keys
	 */
	private void des(int[] inInts, int[] outInts, int[] keys) {
		int fval, work, right, leftt;
		int round;
		int keysi = 0;

		leftt = inInts[0];
		right = inInts[1];

		work = ((leftt >>> 4) ^ right) & 0x0f0f0f0f;
		right ^= work;
		leftt ^= (work << 4);

		work = ((leftt >>> 16) ^ right) & 0x0000ffff;
		right ^= work;
		leftt ^= (work << 16);

		work = ((right >>> 2) ^ leftt) & 0x33333333;
		leftt ^= work;
		right ^= (work << 2);

		work = ((right >>> 8) ^ leftt) & 0x00ff00ff;
		leftt ^= work;
		right ^= (work << 8);
		right = (right << 1) | ((right >>> 31) & 1);

		work = (leftt ^ right) & 0xaaaaaaaa;
		leftt ^= work;
		right ^= work;
		leftt = (leftt << 1) | ((leftt >>> 31) & 1);

		for (round = 0; round < 8; ++round) {
			work = (right << 28) | (right >>> 4);
			work ^= keys[keysi++];
			fval = DESInterface.SP7[work & 0x0000003f];
			fval |= DESInterface.SP5[(work >>> 8) & 0x0000003f];
			fval |= DESInterface.SP3[(work >>> 16) & 0x0000003f];
			fval |= DESInterface.SP1[(work >>> 24) & 0x0000003f];
			work = right ^ keys[keysi++];
			fval |= DESInterface.SP8[work & 0x0000003f];
			fval |= DESInterface.SP6[(work >>> 8) & 0x0000003f];
			fval |= DESInterface.SP4[(work >>> 16) & 0x0000003f];
			fval |= DESInterface.SP2[(work >>> 24) & 0x0000003f];
			leftt ^= fval;
			work = (leftt << 28) | (leftt >>> 4);
			work ^= keys[keysi++];
			fval = DESInterface.SP7[work & 0x0000003f];
			fval |= DESInterface.SP5[(work >>> 8) & 0x0000003f];
			fval |= DESInterface.SP3[(work >>> 16) & 0x0000003f];
			fval |= DESInterface.SP1[(work >>> 24) & 0x0000003f];
			work = leftt ^ keys[keysi++];
			fval |= DESInterface.SP8[work & 0x0000003f];
			fval |= DESInterface.SP6[(work >>> 8) & 0x0000003f];
			fval |= DESInterface.SP4[(work >>> 16) & 0x0000003f];
			fval |= DESInterface.SP2[(work >>> 24) & 0x0000003f];
			right ^= fval;
		}

		right = (right << 31) | (right >>> 1);
		work = (leftt ^ right) & 0xaaaaaaaa;
		leftt ^= work;
		right ^= work;
		leftt = (leftt << 31) | (leftt >>> 1);
		work = ((leftt >>> 8) ^ right) & 0x00ff00ff;
		right ^= work;
		leftt ^= (work << 8);
		work = ((leftt >>> 2) ^ right) & 0x33333333;
		right ^= work;
		leftt ^= (work << 2);
		work = ((right >>> 16) ^ leftt) & 0x0000ffff;
		leftt ^= work;
		right ^= (work << 16);
		work = ((right >>> 4) ^ leftt) & 0x0f0f0f0f;
		leftt ^= work;
		right ^= (work << 4);
		outInts[0] = right;
		outInts[1] = leftt;
	}

}