import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Style.*;


public class LoginPanel extends JPanel {
	private static final int FEILD_WIDTH = 10;
	private JPanel loginPanel, userIDPanel, passwordPanel;
	private JLabel loginImg, userIDLabel, passwordLabel;
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JButton loginButton;

	public LoginPanel() {
		createComp();
	}

	public void createComp() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database;
			String username = "MG05";
			String password = "9mMuzQ";
			Connection conn = DriverManager.getConnection(url, username, password);
			
			ImageIcon loginIcon = new ImageIcon(new ImageIcon("images/login.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			loginImg = new JLabel("LOGIN", loginIcon, SwingConstants.CENTER);
			loginImg.setVerticalTextPosition(SwingConstants.CENTER);
			loginImg.setHorizontalTextPosition(SwingConstants.RIGHT);
			loginImg.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			loginImg.setForeground(Color.decode("#FDA172"));
			
			ImageIcon idIcon = new ImageIcon(new ImageIcon("images/ID.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
			userIDLabel = new JLabel("User ID", idIcon, SwingConstants.CENTER);
			userIDLabel.setVerticalTextPosition(SwingConstants.CENTER);
			userIDLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			userIDField = new JTextField(FEILD_WIDTH);
			
			ImageIcon passwordIcon = new ImageIcon(new ImageIcon("images/password.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
			passwordLabel = new JLabel("Password", passwordIcon, SwingConstants.CENTER);
			passwordLabel.setVerticalTextPosition(SwingConstants.CENTER);
			passwordLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			passwordField = new JPasswordField(FEILD_WIDTH);
			
			loginButton = new JButton("Log in");
			loginButton.setPreferredSize(new Dimension(100, 22));
			loginButton.setBorder(new BubbleBorder(Color.decode("#FDA172"), 2, 20, 0));
			loginButton.setContentAreaFilled(false);
			
			userIDPanel = new JPanel();
			userIDPanel.setBackground(Color.decode("#F8EFD4"));
			userIDPanel.add(userIDLabel);
			userIDPanel.add(userIDField);
	
			passwordPanel = new JPanel();
			passwordPanel.setBackground(Color.decode("#F8EFD4"));
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordField);
			
			loginPanel = new JPanel(new GridBagLayout());
			loginPanel.setBackground(Color.decode("#F8EFD4"));
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			gbc1.insets = new Insets(0, 0, 10, 0);
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			loginPanel.add(userIDPanel, gbc1);
			gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			gbc1.insets = new Insets(0, 0, 10, 0);
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			loginPanel.add(passwordPanel, gbc1);
			gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			loginPanel.add(loginButton, gbc1);
			
			setBackground(Color.decode("#F8EFD4"));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.gridx = 0;
			gbc2.gridy = 0;
			gbc2.weightx = 1.0;
			gbc2.weighty = 1.0;
			gbc2.insets = new Insets(20, 0 ,0 ,0);
			gbc2.anchor = GridBagConstraints.NORTH;
			add(loginImg, gbc2);
			gbc2.gridx = 0;
			gbc2.gridy = 1;
			gbc2.weightx = 1.0;
			gbc2.weighty = 1.0;
			gbc2.anchor = GridBagConstraints.NORTH;
			add(loginPanel, gbc2);
			
			class ButtonListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					try {
						Statement stat = conn.createStatement();
						String userID = userIDField.getText();
						String password	= passwordField.getText();
						
						String query1="SELECT COUNT(*) FROM Member WHERE ID ='" + userID + "'";
						
						stat.execute(query1);
						
						ResultSet result = stat.getResultSet();
						result.next();
						int count = Integer.parseInt(result.getString(1));
						
						//ResultSetMetaData metaData = result.getMetaData();
						/*int count = 0;
						while (result.next()) {
							count++;
						}
						System.out.println(count);
						*/
						
						if(count!=0) {
							String query = "SELECT Password FROM Member WHERE ID ='" + userID + "'";
							stat.execute(query);
							result = stat.getResultSet();
							result.next();
							if(result.getString(1).equals(password)) {
								JOptionPane jop = new JOptionPane();
								jop.showMessageDialog(null,"Login","Error",JOptionPane.PLAIN_MESSAGE);
								//System.out.println("login!");
								userIDField.setText(null);
								passwordField.setText(null);
							}
							else {
								userIDField.setText(null);
								passwordField.setText(null);
								JOptionPane jop1 = new JOptionPane();
								jop1.showMessageDialog(null,"The password is wrong","Error",JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							userIDField.setText(null);
							passwordField.setText(null);
							JOptionPane jop2 = new JOptionPane();
							jop2.showMessageDialog(null,"The username is wrong","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
					catch(Exception a) {
						a.getMessage();
						System.out.print("wrong!");
					}
					
				}
			}
			ActionListener listener = new ButtonListener();
			loginButton.addActionListener(listener);
		}
		catch(Exception e) {
			e.getMessage();
		}
	}

}

