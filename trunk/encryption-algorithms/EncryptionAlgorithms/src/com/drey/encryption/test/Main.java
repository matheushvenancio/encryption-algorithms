package com.drey.encryption.test;

import com.drey.encryption.IDEA;

public class Main {
	
	public static void main(String[] args) {
		/*
		String text = "balloon";
		String key = "criptare";
		Playfair p = new Playfair(key);
		
		System.out.println("Text: " + text);
		System.out.println("Key: " + key + "\n");
		
		System.out.println(p.showMatrix());
		System.out.println("Replaced: " + p.showWithSpaces(p.replacedText(text)));
		System.out.println("Encrypted: " + p.encrypt(text));
		
		//System.out.println(p.showMatrixPosition());

		 */
		/*
		Hill hill = new Hill("GYBNQKURP");
		
		System.out.println(hill.showKeyMatrix());
		
		System.out.println(hill.encrypt("ACT"));
		*/
		
		String toEncrypt = "123456789";//90123";
		String key = "123";//23456";//78901234567";
		IDEA idea = new IDEA(key);
		
		String encrypted = idea.encrypt(toEncrypt);
		String decrypted = idea.decrypt(encrypted);
		
		System.out.println("Key: " + key);
		System.out.println("Text: " + toEncrypt);
		System.out.println("Ecrypted: " + encrypted);
		System.out.println("Decrypted: " + decrypted);
	}
	
}