package com.drey.encryption.test;

import com.drey.encryption.DES;

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
		
		String toEncrypt = "1234567890";//90123";
		String key = "123";//23456";//78901234567";
		DES des = new DES(key);
		
		String encrypted = des.encrypt(toEncrypt);
		String decrypted = des.decrypt(encrypted);
		
		System.out.println("Key: " + key);
		System.out.println("Text     : " + toEncrypt);
		System.out.println("Ecrypted : " + encrypted);
		System.out.println("Decrypted: " + decrypted);
	}
	
}