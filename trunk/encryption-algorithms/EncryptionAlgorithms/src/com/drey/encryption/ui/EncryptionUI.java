package com.drey.encryption.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.drey.encryption.DES;
import com.drey.encryption.Encryption;
import com.drey.encryption.Hill;
import com.drey.encryption.IDEA;
import com.drey.encryption.Playfair;

public class EncryptionUI extends JFrame implements ActionListener {

	private List<Encryption> encryptionAlgorithms = new ArrayList<Encryption>();

	private JComboBox comboBox = new JComboBox();

	private JPanel playfairUIPanel = new PlayfairUI();

	private DefaultUI defaultUIPanel = new DefaultUI();

	public EncryptionUI() {

		initEncryptionAlgorithmsList();
		initComboBoxItems();

		comboBox.addActionListener(this);
		comboBox.setPreferredSize(new Dimension(200, 20));

		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel("Algoritmul: "));
		topPanel.add(comboBox);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.add(playfairUIPanel);
		bottomPanel.add(defaultUIPanel);

		playfairUIPanel.setVisible(false);
		defaultUIPanel.setVisible(false);

		this.getContentPane().add(topPanel);
		this.getContentPane().add(bottomPanel);
		

		// this.setResizable(false);

		this.setLayout(new GridLayout(0, 1));
		this.getContentPane().setSize(800, 605);
		this.setVisible(true);
		this.repaint();
		this.pack();
		this.setTitle("Algoritmi de criptare");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {

			System.out.println("index: " + comboBox.getSelectedIndex() + "   " + comboBox.getSelectedItem());

			Encryption alg = getAlgorithm(comboBox.getSelectedItem().toString());
			
			if (alg instanceof Playfair) {
				playfairUIPanel.setVisible(true);
				defaultUIPanel.setVisible(false);
			} else {
				playfairUIPanel.setVisible(false);
				defaultUIPanel.setVisible(true);
				defaultUIPanel.setEncryption(alg);
			}
			//pack();
		}

	}

	private Encryption getAlgorithm(String name) {
		for (Encryption e : encryptionAlgorithms) {
			if (e.getName().compareTo(name) == 0) {
				return e;
			}
		}
		return null;
	}

	private void initEncryptionAlgorithmsList() {
		String key = "keys";

		encryptionAlgorithms.add(new Playfair(key));
		encryptionAlgorithms.add(new Hill(key));
		encryptionAlgorithms.add(new IDEA(key));
		encryptionAlgorithms.add(new DES(key));
	}

	private void initComboBoxItems() {
		for (Encryption e : encryptionAlgorithms) {
			comboBox.addItem(e.getName());
		}
	}
	
	public static void main(String arg[]) {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		new EncryptionUI();
	}
}
