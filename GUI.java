import java.awt.EventQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.TextArea;
import java.awt.Choice;
import java.awt.GridLayout;


public class PINClient {

	private int maxWidth;
	private int maxHeight;
	private String password = "aKLASUgfokblasdfkokasdfkmaskdkliskLKHN";
	private JFrame frame;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField yVal;
	private JTextField xVal;
	private JTextField width;
	private JTextField height;
	private int x, y, w, h;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PINClient window = new PINClient();
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
	public PINClient() {
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

					out.println("aKLASUgfokblasdfkokasdfkmaskdkliskLKHN");

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

					TextArea displayArea = new TextArea();
					displayArea.setBounds(20, 10, 437, 210);
					frame.getContentPane().add(displayArea);
					displayArea.setEditable(false);
					
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
					lblMessage.setBounds(20, 267, 60, 14);
					frame.getContentPane().add(lblMessage);
					
					Choice choice = new Choice();
					choice.setBounds(299, 267, 158, 20);
					frame.getContentPane().add(choice);
					
					String colors = in.readLine();
					Scanner color = new Scanner(colors);
					while(color.hasNext()) {
						choice.add(color.next());
					}
					
					JLabel lblColor = new JLabel("Color");
					lblColor.setBounds(243, 267, 46, 14);
					frame.getContentPane().add(lblColor);

					maxWidth = Integer.parseInt(in.readLine());
					maxHeight = Integer.parseInt(in.readLine());

					JButton btnPost = new JButton("POST");
					btnPost.setMnemonic('P');
					btnPost.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								x = Integer.parseInt(xVal.getText());
								if (x < 0 || x > maxWidth) throw new NullPointerException();

								y = Integer.parseInt(yVal.getText());
								if (y < 0 || y > maxHeight) throw new IOException();

								w = Integer.parseInt(width.getText());
								if (w <= 0 || w > maxWidth) throw new IndexOutOfBoundsException();

								h = Integer.parseInt(height.getText());
								if (h <= 0 || h > maxHeight) throw new ArithmeticException();
							
							} catch(NumberFormatException n) {
								JOptionPane.showMessageDialog(null,
									"Enter an integer value",
									"Invalid Input",
									JOptionPane.ERROR_MESSAGE);

							} catch(NullPointerException b) {
								JOptionPane.showMessageDialog(null,
									"Enter a x value between 0-" + maxWidth + ").",
									"Invalid Coordinate",
									JOptionPane.ERROR_MESSAGE);

							} catch(IOException c) {
								JOptionPane.showMessageDialog(null,
									"Enter a y value between 0-" + maxHeight + ").",
									"Invalid Coordinate",
									JOptionPane.ERROR_MESSAGE);

							} catch(IndexOutOfBoundsException d) {
								JOptionPane.showMessageDialog(null,
									"Enter a width between 0-" + maxWidth + ").",
									"Invalid Width",
									JOptionPane.ERROR_MESSAGE);

							} catch(ArithmeticException d) {
								JOptionPane.showMessageDialog(null,
									"Enter a height between 0-" + maxWidth + ").",
									"Invalid Height",
									JOptionPane.ERROR_MESSAGE);
							}

							if (xVal.getText().equals("") || yVal.getText().equals("") ||
								width.getText().equals("") || height.getText().equals("")) {
									displayArea.setText("Message was not posted.");

							} else {
								String post = "POST " + x + " " + y + " " + w + " " + h + " " + choice.getSelectedItem() + " " + textArea.getText();
								out.println(post);

								try {
									displayArea.setText(in.readLine());

								} catch (IOException error) {
									JOptionPane.showMessageDialog(null,
										"Message could not be posted.",
										"Post Error",
										JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					});
					btnPost.setBounds(299, 299, 158, 23);

					frame.getContentPane().add(btnPost);
					
					JButton btnGet = new JButton("GET");
					btnGet.setMnemonic('G');
					btnGet.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JPanel panel = new JPanel(new GridLayout(0, 1));
							JTextField xcoor = new JTextField();
							JTextField ycoor = new JTextField();
							JTextField msg = new JTextField();
							Choice list = new Choice();
							list.add("");
							Scanner color = new Scanner(colors);
							while(color.hasNext()) {
								list.add(color.next());
							}
							panel.add(new JLabel("X-value"));
							panel.add(xcoor);
							panel.add(new JLabel("Y-value"));
							panel.add(ycoor);
							panel.add(new JLabel("Color"));
							panel.add(list);
							panel.add(new JLabel("Message"));
							panel.add(msg);

							int result = JOptionPane.showConfirmDialog(null, panel, "PIN",
										JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

							if (result == JOptionPane.OK_OPTION) {
								String get = ("");

								if (xcoor.getText().equals("") && ycoor.getText().equals("")) {
									get = get + "GET color=" + list.getSelectedItem()
											  + " contains=" + " refersTo=" + msg.getText();

								} else {
									try {
										x = Integer.parseInt(xcoor.getText());
										if (x < 0 || x > maxWidth) throw new NumberFormatException();

										y = Integer.parseInt(ycoor.getText());
										if (y < 0 || y > maxHeight) throw new NumberFormatException();

										get = get + "GET color=" + list.getSelectedItem()
											  + " contains=" + x + " " + y + " refersTo=" +msg.getText();

									} catch (NumberFormatException error) {
										JOptionPane.showMessageDialog(null,
											"Enter valid coordinates.",
											"Invalid Coordinates",
											JOptionPane.ERROR_MESSAGE);
									}
								}
								out.println(get);

								try {
									displayArea.setText(in.readLine());
	
								} catch (IOException error) {
									JOptionPane.showMessageDialog(null,
										"Could not find messages.",
										"GET Error",
										JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					});
					btnGet.setBounds(299, 333, 73, 23);
					frame.getContentPane().add(btnGet);

					JButton btnClear = new JButton("CLEAR");
					btnClear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							out.println("CLEAR");

							try {
								displayArea.setText(in.readLine());

							} catch (IOException error) {
								JOptionPane.showMessageDialog(null,
									"Could not find messages.",
									"GET Error",
									JOptionPane.ERROR_MESSAGE);
							}

						}
					});
					btnClear.setMnemonic('C');
					btnClear.setBounds(382, 333, 75, 23);
					frame.getContentPane().add(btnClear);

					JButton btnPin = new JButton("PIN");
					btnPin.setMnemonic('I');
					btnPin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JPanel panel = new JPanel(new GridLayout(0, 1));
							JTextField xcoor = new JTextField();
							JTextField ycoor = new JTextField();
							panel.add(new JLabel("X-value"));
							panel.add(xcoor);
							panel.add(new JLabel("Y-value"));
							panel.add(ycoor);

							int result = JOptionPane.showConfirmDialog(null, panel, "PIN",
										JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
							
							if (result == JOptionPane.OK_OPTION) {
								try {
									x = Integer.parseInt(xcoor.getText());
									if (x < 0 || x > maxWidth) throw new NumberFormatException();

									y = Integer.parseInt(ycoor.getText());
									if (y < 0 || y > maxHeight) throw new NumberFormatException();

								} catch (NumberFormatException error) {
									JOptionPane.showMessageDialog(null,
										"Enter valid coordinates.",
										"Invalid Coordinates",
										JOptionPane.ERROR_MESSAGE);
								}

								if(xcoor.getText().equals("") || ycoor.getText().equals("")) {
									displayArea.setText("Message was not pinned.");

								} else {
									String pin = ("PIN " + x + " " + y);
									out.println(pin);

									try {
										displayArea.setText(in.readLine());
		
									} catch (IOException error) {
										JOptionPane.showMessageDialog(null,
											"Message could not be pinned.",
											"PIN Error",
											JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						}
					});
					btnPin.setBounds(299, 367, 73, 23);
					frame.getContentPane().add(btnPin);
					
					JButton btnUnpin = new JButton("UNPIN");
					btnUnpin.setMnemonic('U');
					btnUnpin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JPanel panel = new JPanel(new GridLayout(0, 1));
							JTextField xcoor = new JTextField();
							JTextField ycoor = new JTextField();
							panel.add(new JLabel("X-value"));
							panel.add(xcoor);
							panel.add(new JLabel("Y-value"));
							panel.add(ycoor);

							int result = JOptionPane.showConfirmDialog(null, panel, "PIN",
										JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
							
							if (result == JOptionPane.OK_OPTION) {
								try {
									x = Integer.parseInt(xcoor.getText());
									if (x < 0 || x > maxWidth) throw new NumberFormatException();

									y = Integer.parseInt(ycoor.getText());
									if (y < 0 || y > maxHeight) throw new NumberFormatException();

								} catch (NumberFormatException error) {
									JOptionPane.showMessageDialog(null,
										"Enter valid coordinates.",
										"Invalid Coordinates",
										JOptionPane.ERROR_MESSAGE);
								}

								if(xcoor.getText().equals("") || ycoor.getText().equals("")) {
									displayArea.setText("Message was not unpinned.");

								} else {
									String unpin = ("UNPIN " + x + " " + y);
									out.println(unpin);

									try {
										displayArea.setText(in.readLine());

									} catch (IOException error) {
										JOptionPane.showMessageDialog(null,
											"Message could not be unpinned.",
											"UNPIN Error",
											JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						}
					});
					btnUnpin.setBounds(382, 367, 75, 23);
					frame.getContentPane().add(btnUnpin);

					JButton btnDisconnect = new JButton("DISCONNECT");
					btnDisconnect.setMnemonic('D');
					btnDisconnect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							out.println("DISCONNECT");

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
							btnClear.setVisible(false);
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
