import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class HomePanel extends JPanel {
	private JLabel imgLabel;
	private JButton testButton;
	private JButton noteButton;
	private JButton analysisButton;
	private JPanel operatePanel;
	
	public HomePanel() {
		createComp();
	}
	
	public void createComp() {
	ImageIcon homeIcon = new ImageIcon(
			new ImageIcon("images/home.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
	imgLabel = new JLabel("HOME", homeIcon, SwingConstants.CENTER);
	imgLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
	imgLabel.setHorizontalTextPosition(SwingConstants.CENTER);
	imgLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
	imgLabel.setForeground(Color.decode("#FDA172"));
	
	ImageIcon testIcon = new ImageIcon(
			new ImageIcon("images/test.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	testButton = new JButton("Test", testIcon);
	testButton.setPreferredSize(new Dimension(85, 80));
	testButton.setBorder(BorderFactory.createMatteBorder(5, 1, 1, 1, Color.decode("#FDA172")));
	testButton.setVerticalTextPosition(SwingConstants.TOP);
	testButton.setHorizontalTextPosition(SwingConstants.CENTER);
	testButton.setContentAreaFilled(false);
	
	ImageIcon noteIcon = new ImageIcon(
			new ImageIcon("images/note.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	noteButton = new JButton("Note", noteIcon);
	noteButton.setPreferredSize(new Dimension(85, 80));
	noteButton.setBorder(BorderFactory.createMatteBorder(5, 1, 1, 1, Color.decode("#FDA172")));
	noteButton.setVerticalTextPosition(SwingConstants.TOP);
	noteButton.setHorizontalTextPosition(SwingConstants.CENTER);
	noteButton.setContentAreaFilled(false);
	
	ImageIcon analysisIcon = new ImageIcon(
			new ImageIcon("images/analysis.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	analysisButton = new JButton("Analysis", analysisIcon);
	analysisButton.setPreferredSize(new Dimension(85, 80));
	analysisButton.setBorder(BorderFactory.createMatteBorder(5, 1, 1, 1, Color.decode("#FDA172")));
	analysisButton.setVerticalTextPosition(SwingConstants.TOP);
	analysisButton.setHorizontalTextPosition(SwingConstants.CENTER);
	analysisButton.setContentAreaFilled(false);
	
	operatePanel = new JPanel(new GridBagLayout());
	GridBagConstraints gbc1 = new GridBagConstraints();
	gbc1.gridx = 0;
	gbc1.gridy = 0;
	gbc1.weightx = 1.0;
	gbc1.weighty = 1.0;
	operatePanel.add(testButton, gbc1);
	
	gbc1 = new GridBagConstraints();
	gbc1.gridx = 2;
	gbc1.gridy = 0;
	gbc1.weightx = 1.0;
	gbc1.weighty = 1.0;
	operatePanel.add(noteButton, gbc1);
	
	gbc1 = new GridBagConstraints();
	gbc1.gridx = 1;
	gbc1.gridy = 1;
	gbc1.weightx = 1.0;
	gbc1.weighty = 1.0;
	gbc1.insets = new Insets(20, 0, 0, 0);
	operatePanel.add(analysisButton, gbc1);
	
	setLayout(new GridBagLayout());
	GridBagConstraints gbc2 = new GridBagConstraints();
	gbc2.gridx = 0;
	gbc2.gridy = 0;
	gbc2.weightx = 1.0;
	gbc2.weighty = 1.0;
	add(imgLabel, gbc2);
	
	gbc2 = new GridBagConstraints();
	gbc2.gridx = 1;
	gbc2.gridy = 0;
	gbc2.weightx = 1.0;
	gbc2.weighty = 1.0;
	add(operatePanel, gbc2);
	}
}
