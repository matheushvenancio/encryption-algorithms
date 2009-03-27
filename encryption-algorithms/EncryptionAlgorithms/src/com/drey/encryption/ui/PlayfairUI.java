package com.drey.encryption.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.drey.encryption.Playfair;

public class PlayfairUI extends JPanel implements ActionListener {
	Playfair p = new Playfair("");
	JButton texB, encB, decB, keyB, kchB, offB, clrB;
	JLabel texL, decL, encL, keyL, code[] = new JLabel[25];
	JTextField texT, decT, encT, keyT;

	public PlayfairUI() {
		int d = 10, x0 = d, y0 = d;
		int wl = 80, wb = 180, wt = 2 * wb + d, h = 28;
		int x1 = d + x0 + wl, x2 = d + x1 + wb;
		int x3 = d + x2 + wb, x4 = d + x3 + wb;
		int y1 = d + h + y0, y2 = d + h + y1;
		int y3 = d + h + y2, y4 = d + h + y3;
		int xmax = x4 + 5 * h + 5 * d + 10, ymax = 5 * h + 6 * d + 33;
		int pos = (getToolkit().getScreenSize().width - xmax) / 2;

		setLayout(null);

		texB = Button(x3, y0, wb, h, 'e', "Klartext erstellen");
		encB = Button(x3, y1, wb, h, 'v', "Klartext verschlüsseln");
		decB = Button(x3, y2, wb, h, 'c', "Crypttext entschlüsseln");
		clrB = Button(x3, y3, wb, h, 'l', "Textfelder leeren");
		offB = Button(x3, y4, wb, h, 's', "Fenster schließen");
		keyB = Button(x1, y4, wb, h, 'd', "Schlüssel definieren");
		kchB = Button(x2, y4, wb, h, 'n', "Schlüssel ändern");

		texL = Label(x0, y0, wl, h, 4, "Originaltext :");
		decL = Label(x0, y1, wl, h, 4, "Klartext :");
		encL = Label(x0, y2, wl, h, 4, "Crypttext :");
		keyL = Label(x0, y3, wl, h, 4, "Schlüssel :");

		texT = TextField(x1, y0, wt, h);
		decT = TextField(x1, y1, wt, h);
		encT = TextField(x1, y2, wt, h);
		keyT = TextField(x1, y3, wt, h);

		for (int i = 0; i < 25; i++)
			code[i] = Label(i % 5 * (h + d) + x4, i / 5 * (h + d) + d, h, h, 0, "");

		change(keyL);
		change(keyT);
		change(keyB);
		changeKey(true);

		//setTitle("Crypter - Playfair-Algorithmus");
	//	setDefaultCloseOperation(EXIT_ON_CLOSE);
	//	setBounds(pos, pos, xmax, ymax);
	//	setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent evt) {
		JButton b = (JButton) evt.getSource();

		if (b == texB)
			decT.setText(splitText(p.decrypt(texT.getText())));
		if (b == encB) {
			encT.setText(splitText(p.encrypt(decT.getText())));
			decT.setText(splitText(p.decrypt(decT.getText())));
		}
		if (b == decB) {
			decT.setText(splitText(p.decrypt(encT.getText())));
			encT.setText(splitText(p.encrypt(encT.getText())));
		}
		if (b == clrB) {
			texT.setText("");
			encT.setText("");
			decT.setText("");
		}
		if (b == keyB) changeKey(true);
		if (b == kchB) changeKey(false);
		if (b == offB) System.exit(0);
	}

	public String splitText(String text) {
		String t = "";
		for (int n = 0; n < text.length() - 1; n++)
			t += text.charAt(n) + ((n % 4 == 3) ? " " : "");
		return t + ((text != "") ? text.charAt(text.length() - 1) + "" : "");
	}

	public void changeKey(boolean set) {
		if (set) p.setKey(keyT.getText());
		
		change(texL); change(decL);
		change(encL); change(keyL);
		change(texT); change(decT);
		change(encT); change(keyT);
		change(clrB); change(texB);
		change(decB); change(encB);
		change(keyB); change(kchB);
		
		for (int i = 0; i < 25; i++) {
			change(code[i]);
			code[i].setText(p.getChar(i) + "");
		}
	}

	public JLabel Label(int x, int y, int w, int h, int a, String l) {
		JLabel L = new JLabel(l);
		L.setHorizontalAlignment(a);
		L.setBounds(x, y, w, h);
		add(L);
		//getContentPane().add(L);
		return L;
	}

	public JTextField TextField(int x, int y, int w, int h) {
		JTextField T = new JTextField();
		T.setBounds(x, y, w, h);
		add(T);
		//getContentPane().add(T);
		return T;
	}

	public JButton Button(int x, int y, int w, int h, char m, String l) {
		JButton B = new JButton(l);
		B.addActionListener(this);
		B.setBounds(x, y, w, h);
		B.setMnemonic(m);
		add(B);
		//getContentPane().add(B);
		return B;
	}

	public void change(JComponent C) {
		C.setEnabled(!C.isEnabled());
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new PlayfairUI();
	}
}