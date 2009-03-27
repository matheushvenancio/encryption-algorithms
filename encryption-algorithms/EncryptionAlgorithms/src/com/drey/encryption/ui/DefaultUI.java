package com.drey.encryption.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.drey.encryption.Encryption;

public class DefaultUI extends JPanel implements ActionListener {

	private static final String ENCRYPT = "Encrypt";
	private static final String DECRYPT = "Decrypt";
	
	private Encryption encryption = null;

	private JButton encryptButton = new JButton(ENCRYPT);
	private JButton decryptButton = new JButton(DECRYPT);

	private JLabel inputTextLabel = new JLabel ("Input text: ");
	private JTextField inputTextField = new JTextField();

	private JLabel outputTextLabel = new JLabel("Output text:");
	private JTextField outputTextFieldLabel = new JTextField();

	private void init() {
		inputTextField.setPreferredSize(new Dimension(200, 20));
		outputTextFieldLabel.setPreferredSize(new Dimension(200, 20));
		outputTextFieldLabel.setEnabled(false);
		
		encryptButton.addActionListener(this);
		decryptButton.addActionListener(this);
	}

	public DefaultUI() {
		init();

		JPanel topPanel = new JPanel();
		topPanel.add(inputTextLabel);
		topPanel.add(inputTextField);

		JPanel middlePanel = new JPanel();
		middlePanel.add(outputTextLabel);
		middlePanel.add(outputTextFieldLabel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(encryptButton);
		bottomPanel.add(decryptButton);

		this.add(topPanel);
		this.add(middlePanel);
		this.add(bottomPanel);

		this.setLayout(new GridLayout(0, 1));
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();

			System.out.println(b.getText());

			if (encryption!=null){
				if (b.getText() == ENCRYPT){
					outputTextFieldLabel.setText(encryption.encrypt(inputTextField.getText()));
				} else {
					outputTextFieldLabel.setText(encryption.decrypt(inputTextField.getText()));
				}
				
			}
		}

	}

	public void setEncryption(Encryption e) {
		this.encryption = e;
	}
}