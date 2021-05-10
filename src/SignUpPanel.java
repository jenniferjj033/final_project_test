import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.*;

import Style.*;

public class SignUpPanel extends JPanel{
	private static final int FEILD_WIDTH = 10;
	private JPanel signUpPanel, userIDPanel, passwordPanel;
	private JLabel signUpImg, userIDLabel, passwordLabel;
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JButton signUpButton;

	public SignUpPanel() {
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
			
			ImageIcon signUpIcon = new ImageIcon(new ImageIcon("images/signUp.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			signUpImg = new JLabel("SIGN UP", signUpIcon, SwingConstants.CENTER);
			signUpImg.setVerticalTextPosition(SwingConstants.CENTER);
			signUpImg.setHorizontalTextPosition(SwingConstants.RIGHT);
			signUpImg.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			signUpImg.setForeground(Color.decode("#FDA172"));
			
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
			
			signUpButton = new JButton("Sign up");
			signUpButton.setPreferredSize(new Dimension(100, 22));
			signUpButton.setBorder(new BubbleBorder(Color.decode("#FDA172"), 2, 20, 0));
			signUpButton.setContentAreaFilled(false);
			
			
			userIDPanel = new JPanel();
			userIDPanel.setBackground(Color.decode("#F8EFD4"));
			userIDPanel.add(userIDLabel);
			userIDPanel.add(userIDField);
	
			passwordPanel = new JPanel();
			passwordPanel.setBackground(Color.decode("#F8EFD4"));
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordField);
	
			signUpPanel = new JPanel(new GridBagLayout());
			signUpPanel.setBackground(Color.decode("#F8EFD4"));
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			gbc1.insets = new Insets(0, 0, 10, 0);
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			signUpPanel.add(userIDPanel, gbc1);
			gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			gbc1.insets = new Insets(0, 0, 10, 0);
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			signUpPanel.add(passwordPanel, gbc1);
			gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.weightx = 1.0;
			gbc1.weighty = 1.0;
			signUpPanel.add(signUpButton, gbc1);
			
			setBackground(Color.decode("#F8EFD4"));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.gridx = 0;
			gbc2.gridy = 0;
			gbc2.weightx = 1.0;
			gbc2.weighty = 1.0;
			gbc2.insets = new Insets(20, 0 ,0 ,0);
			gbc2.anchor = GridBagConstraints.NORTH;
			add(signUpImg, gbc2);
			gbc2.gridx = 0;
			gbc2.gridy = 1;
			gbc2.weightx = 1.0;
			gbc2.weighty = 1.0;
			gbc2.anchor = GridBagConstraints.NORTH;
			add(signUpPanel, gbc2);
			
			class ButtonListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					try {
						Statement stat = conn.createStatement();
						String ID = userIDField.getText();
						String password	= passwordField.getText();
						
						String query = "INSERT INTO Member VALUES('" + ID + "', '" + password + "');";
						stat.execute(query);
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null,"Sign up","Error",JOptionPane.PLAIN_MESSAGE);
						userIDField.setText(null);
						passwordField.setText(null);
						
					}
					catch(Exception a) {
						a.getMessage();
					}
				}
			}
			ActionListener listener = new ButtonListener();
			signUpButton.addActionListener(listener);
		}
		catch(Exception e) {
			e.getMessage();
		}
		
	}
}
