import java.awt.EventQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.TextArea;
import java.awt.Choice;

public class ClientLogin {

	private JFrame frame;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField yVal;
	private JTextField xVal;
	private JTextField width;
	private JTextField height;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLogin window = new ClientLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientLogin() {
		frame = new JFrame();
		frame.setBounds(100, 100, 375, 160);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);;
		
		JLabel lblIPAddress = new JLabel("IP Address");
		lblIPAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIPAddress.setBounds(33, 23, 67, 14);
		frame.getContentPane().add(lblIPAddress);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPortNumber.setBounds(33, 48, 78, 14);
		frame.getContentPane().add(lblPortNumber);
		
		ipTextField = new JTextField();
		ipTextField.setBounds(121, 21, 185, 20);
		frame.getContentPane().add(ipTextField);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setBounds(121, 46, 185, 20);
		frame.getContentPane().add(portTextField);
		portTextField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setMnemonic('C');
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String serverAddress = ipTextField.getText();
					int port_server = Integer.parseInt(portTextField.getText());

					Socket socket = new Socket(serverAddress, port_server);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

					/**
					 * Resizes the window and creates all new buttons
					 */

					frame.setBounds(100, 100, 500, 500);
					lblIPAddress.setVisible(false);
					lblPortNumber.setVisible(false);
					btnConnect.setVisible(false);
					ipTextField.setText("");
					portTextField.setText("");
					ipTextField.setVisible(false);
					portTextField.setVisible(false);


					/**
					 * Fills the text box
					 */

					String notes = "";
					for (int i = 0; i < 100; i++) {
						notes = notes + "hello" + '\n';
					}

					TextArea displayArea = new TextArea();
					displayArea.setBounds(20, 10, 437, 210);
					frame.getContentPane().add(displayArea);
					displayArea.setText(notes);
					
					yVal = new JTextField();
					yVal.setBounds(187, 226, 46, 20);
					frame.getContentPane().add(yVal);
					yVal.setColumns(10);
					
					xVal = new JTextField();
					xVal.setBounds(68, 226, 46, 20);
					frame.getContentPane().add(xVal);
					xVal.setColumns(10);
					
					width = new JTextField();
					width.setColumns(10);
					width.setBounds(299, 226, 46, 20);
					frame.getContentPane().add(width);
					
					height = new JTextField();
					height.setColumns(10);
					height.setBounds(411, 226, 46, 20);
					frame.getContentPane().add(height);
					
					JLabel lblXValue = new JLabel("X-Value");
					lblXValue.setBounds(20, 229, 46, 14);
					frame.getContentPane().add(lblXValue);
					
					JLabel lblYValue = new JLabel("Y-Value");
					lblYValue.setBounds(124, 229, 54, 14);
					frame.getContentPane().add(lblYValue);
					
					JLabel lblWidth = new JLabel("Width");
					lblWidth.setBounds(243, 229, 46, 14);
					frame.getContentPane().add(lblWidth);
					
					JLabel lblHeight = new JLabel("Height");
					lblHeight.setBounds(355, 229, 46, 14);
					frame.getContentPane().add(lblHeight);
					
					TextArea textArea = new TextArea();
					textArea.setBounds(20, 299, 269, 126);
					frame.getContentPane().add(textArea);
					
					JLabel lblMessage = new JLabel("Message");
					lblMessage.setBounds(20, 267, 46, 14);
					frame.getContentPane().add(lblMessage);
					
					Choice choice = new Choice();
					choice.setBounds(299, 267, 158, 20);
					frame.getContentPane().add(choice);
					choice.add("Red");
					choice.add("Blue");
					choice.add("Green");
					
					JLabel lblColor = new JLabel("Color");
					lblColor.setBounds(243, 267, 46, 14);
					frame.getContentPane().add(lblColor);

					
					JButton btnPost = new JButton("POST");
					btnPost.setMnemonic('P');
					btnPost.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					btnPost.setBounds(299, 299, 158, 23);

					frame.getContentPane().add(btnPost);
					
					JButton btnGet = new JButton("GET");
					btnGet.setMnemonic('G');
					btnGet.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					btnGet.setBounds(299, 333, 158, 23);
					frame.getContentPane().add(btnGet);

					JButton btnPin = new JButton("PIN");
					btnPin.setMnemonic('I');
					btnPin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					btnPin.setBounds(299, 367, 73, 23);
					frame.getContentPane().add(btnPin);
					
					JButton btnUnpin = new JButton("UNPIN");
					btnUnpin.setMnemonic('U');
					btnUnpin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					btnUnpin.setBounds(382, 367, 75, 23);
					frame.getContentPane().add(btnUnpin);

					JButton btnDisconnect = new JButton("DISCONNECT");
					btnDisconnect.setMnemonic('D');
					btnDisconnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							frame.setBounds(100, 100, 375, 160);
							displayArea.setVisible(false);
							lblXValue.setVisible(false);
							xVal.setVisible(false);
							yVal.setVisible(false);
							lblYValue.setVisible(false);
							height.setVisible(false);
							lblHeight.setVisible(false);
							width.setVisible(false);
							lblWidth.setVisible(false);
							lblMessage.setVisible(false);
							textArea.setVisible(false);
							lblColor.setVisible(false);
							choice.setVisible(false);
							btnPost.setVisible(false);
							btnGet.setVisible(false);
							btnPin.setVisible(false);
							btnUnpin.setVisible(false);
							btnDisconnect.setVisible(false);

							ipTextField.setVisible(true);
							lblIPAddress.setVisible(true);
							portTextField.setVisible(true);
							lblPortNumber.setVisible(true);
							btnConnect.setVisible(true);
						}
					});
					btnDisconnect.setBounds(299, 401, 158, 23);
					frame.getContentPane().add(btnDisconnect);
								
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null,
						"Incorrect IP Address or Port Number.",
						"IP/Port Error",
						JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		
		btnConnect.setBounds(135, 77, 89, 23);
		frame.getContentPane().add(btnConnect);
	}
}
