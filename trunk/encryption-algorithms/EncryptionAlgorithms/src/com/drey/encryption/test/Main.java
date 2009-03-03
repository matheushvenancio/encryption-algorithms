package com.drey.encryption.test;

import com.drey.encryption.Playfair;

public class Main {
	
	public static void main(String[] args) {
		String text = "balloon";
		String key = "criptare";
		Playfair p = new Playfair(key);
		
		System.out.println("Text: " + text);
		System.out.println("Key: " + key + "\n");
		
		System.out.println(p.showMatrix());
		System.out.println("Replaced: " + p.showWithSpaces(p.replacedText(text)));
		System.out.println("Encrypted: " + p.encrypt(text));
		
		System.out.println(p.showMatrixPosition());
	}
	
}