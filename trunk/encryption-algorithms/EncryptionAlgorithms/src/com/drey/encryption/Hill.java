package com.drey.encryption;

/**
 * Implementation for the Hill encryption algorithm.
 * 
 * @author Tolnai Andrei Ciprian
 * 
 */
public class Hill {
	
	private int [][] keyMatrix;
	private int set;
	
	@SuppressWarnings("unused")
	private Hill(){
		// default constructor hidden
	}
	
	public Hill(String key){
		if (key!=null && Math.sqrt(key.length())==Math.floor(Math.sqrt(key.length()))){
			set = (int)Math.sqrt(key.length());
			int letterPosition[] = new int[26];
			for (char c = 'A'; c <= 'Z'; c++) {
				letterPosition[c - 'A'] = c-'A';
			}
			
			keyMatrix = new int[set][set];
			
			int n = 0;
			int ii=0, jj=0;
			while (n < key.length()) {
				char c = key.toUpperCase().charAt(n++);
				keyMatrix[ii][jj] = letterPosition[c - 'A'];
				jj++;
				if (jj>set){
					ii++;
					jj = 0;
				}
			}
			
			for (int i=0; i<set; i++) {
				for (int j=0; j<set; j++) {
					System.out.print(keyMatrix[i][j] + " ");
				}
				System.out.print("\n");
			}
		}
	}
}