package com.drey.encryption;

/**
 * Implementation for the HILL encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class Hill extends Encryption {

	private int[][] keyMatrix;
	private int set;

	@SuppressWarnings("unused")
	private Hill() {
		// default constructor hidden
	}

	public Hill(String key) {
		this.name = Hill.class.getSimpleName();
		if (key != null && Math.sqrt(key.length()) == Math.floor(Math.sqrt(key.length()))) {
			set = (int) Math.sqrt(key.length());
			
			keyMatrix = new int[set][set];

			int n = 0;
			int ii = 0, jj = 0;
			while (n < key.length()) {
				char c = key.toUpperCase().charAt(n++);
				keyMatrix[ii][jj] = c - 'A';
				jj++;
				if (jj >= set) {
					ii++;
					jj = 0;
				}
			}

		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String encrypt(String text) {
		StringBuilder toReturn = new StringBuilder();
		int n = 0;
		int j = 0;
		int[] textPart = new int[set];
		int mod = text.length() % set;
		if (mod!=0){
			mod = set - mod;
			for (int i=0; i<mod; i++){
				text = text.concat("x");
			}
		}
		while (n < text.length()) {
			char c = text.toUpperCase().charAt(n++);
			textPart[j++] = c - 'A';
			if (j >= set) {

				int[] multiplyResult = multiplyMatrixWithVector(keyMatrix, textPart);
				multiplyResult = modulo26(multiplyResult);

				for (int i = 0; i < multiplyResult.length; i++) {
					toReturn.append((char) (multiplyResult[i] + 'A'));
				}

				j = 0;
			}
		}
		return toReturn.toString();
	}

	public String showKeyMatrix() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Key Matrix:\n");
		for (int i = 0; i < set; i++) {
			for (int j = 0; j < set; j++) {
				toReturn.append(keyMatrix[i][j] + " ");
			}
			toReturn.append("\n");
		}
		return toReturn.toString();
	}

	private int[] multiplyMatrixWithVector(int[][] matrix, int[] vector) {
		int[] result = new int[vector.length];
		int i, j;

		if (matrix[1].length != vector.length) {
			System.out.println("Matrix and vector are not compatible!");
			System.exit(2);
		}

		for (i = 0; i <= matrix.length - 1; i++) {
			result[i] = 0;
			for (j = 0; j <= vector.length - 1; j++)
				result[i] += matrix[i][j] * vector[j];
		}
		return result;
	}

	private int[] modulo26(int[] vector) {
		int[] result = new int[vector.length];
		for (int i = 0; i < vector.length; i++) {
			result[i] = vector[i] - (int) Math.floor((float) vector[i] / 26) * 26;
		}
		return result;
	}

	@Override
	public String decrypt(String text) {
		// TODO: not implemented yet
		return null;
	}

	@Override
	protected void setKey(byte[] key) {
		// not used
	}
}