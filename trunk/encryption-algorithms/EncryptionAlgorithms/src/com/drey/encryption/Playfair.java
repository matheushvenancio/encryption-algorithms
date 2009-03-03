package com.drey.encryption;

/**
 * Implementation for PLAYFAIR encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class Playfair {

	private int matrix[] = new int[25];
	private int letterPosition[] = new int[26];

	@SuppressWarnings("unused")
	private String key;

	@SuppressWarnings("unused")
	private Playfair() {
		// constructor hidden
	}

	public Playfair(String key) {
		char c;
		int n = 0, i = 0;
		String keyABC = key.toUpperCase().replaceAll("J", "I");
		this.key = key;
		for (c = 'A'; c <= 'Z'; c++) {
			setPosFree(c);
			keyABC += ((c == 'J') ? "" : "" + c);
		}

		while (n < keyABC.length()) {
			c = keyABC.charAt(n++);
			if (isLetter(c) && isPosFree(c)) {
				setChar(i, c);
				setPos(c, i);
				i++;
			}
		}
		setPos('J', getPos('I'));
	}

	private char getChar(int index) {
		return (char) (matrix[index] + 'A');
	}

	private int getPos(char c) {
		return letterPosition[c - 'A'];
	}

	private void setChar(int index, char c) {
		matrix[index] = c - 'A';
	}

	private void setPos(char c, int index) {
		letterPosition[c - 'A'] = index;
	}

	private void setPosFree(char c) {
		setPos(c, -1);
	}

	private boolean isPosFree(char c) {
		return (getPos(c) == -1);
	}

	private boolean isLetter(char c) {
		return (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'));
	}

	private String swap(int pos1, int pos2, int d) {
		int x1 = pos1 % 5;
		int y1 = pos1 / 5;
		int x2 = pos2 % 5;
		int y2 = pos2 / 5;

		if (x1 != x2 && y1 == y2) {
			x1 = (x1 + d) % 5;
			x2 = (x2 + d) % 5;
		}
		if (x1 == x2 && y1 != y2) {
			y1 = (y1 + d) % 5;
			y2 = (y2 + d) % 5;
		}
		if (x1 != x2 && y1 != y2) {
			int x = x1;
			x1 = x2;
			x2 = x;
		}
		
		return getChar(x1 + y1 * 5) + "" + getChar(x2 + y2 * 5);
	}

	private String _encrypt(String text, int direction) {
		String toReturn = "";
		int i = 0;
		while (i < text.length()) {
			toReturn += swap(getPos(text.charAt(i++)), getPos(text.charAt(i++)), direction + 5);
		}
		return toReturn;
	}

	public String encrypt(String text) {
		return _encrypt(replacedText(text).toUpperCase(), 1);
	}

	public String decrypt(String text) {
		return _encrypt(cryptText(text), -1).toUpperCase();
	}

	public String replacedText(String text) {
		char c;
		int n = 0;
		int p;
		String t = "";
		text = text.toLowerCase();
		while (n < text.length()) {
			c = text.charAt(n++);
			if (c == 'j') {
				c = 'i';
			}
			if (isLetter(c)) {
				p = t.length() - 1;
				t += (p >= 0 && c == t.charAt(p) && p % 2 == 0) ? "x" + c : "" + c;
			}
		}
		return (t.length() % 2 == 0) ? t : t + "x";
	}

	private String cryptText(String text) {
		char c;
		int n = 0;
		String t = "";
		text = text.toUpperCase();
		while (n < text.length()) {
			c = text.charAt(n++);
			t += (isLetter(c)) ? c + "" : "";
		}
		return (t.length() % 2 == 0) ? t : t + "X";
	}

	public String showMatrix() {
		String t = "Matrix: \n";
		for (int n = 0; n < 25; n++)
			t += getChar(n) + ((n % 5 == 4) ? "\n" : " ");
		return t;
	}

	public String showMatrixPosition() {
		String t = "Positions in matrix: \n";
		for (char c = 'A'; c <= 'Z'; c++)
			t += c + ": " + getPos(c) + (((c - 'A') % 7 == 6) ? "\n" : "\t");
		return t + "\n";
	}

	public String showWithSpaces(String text) {
		String toReturn = "";
		for (int n = 0; n < text.length(); n++)
			toReturn += text.charAt(n) + ((n % 2 == 1) ? " " : "") + ((n % 60 == 59) ? "\n" : "");
		return toReturn + "\n";
	}

}