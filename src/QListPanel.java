import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.BubbleBorder;

public class QListPanel extends JPanel {
	private JLabel titleLabel;
	private ArrayList<JButton> questionButtons;
	private ArrayList<JLabel> questionLabels;
	private JButton homeButton;
	private JPanel menuPanel;
	private Connection conn;
	private String test;
	
	public QListPanel(String test) {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database;
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.test = test;
		createTitleLabel();
		createQuestionButtons(test);
		createHomeButton();
		setLayout();
	}

	public void createTitleLabel() {
		ImageIcon loginIcon = new ImageIcon(
				new ImageIcon("images/login.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		titleLabel = new JLabel("ANSWER", loginIcon, SwingConstants.LEFT);
		titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		titleLabel.setForeground(Color.decode("#FDA172"));
	}

	public void createQuestionButtons(String test) {
		questionButtons = new ArrayList<JButton>();
		questionLabels = new ArrayList<JLabel>();

		menuPanel = new JPanel(new GridBagLayout());
		menuPanel.setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc;
		int i = 0;
		try {
			Statement stat = conn.createStatement();
			String query1 = "SELECT Number, Question, Answer, UserAnswer FROM " + test + " WHERE Number <> 0";
			boolean hasResultSet = stat.execute(query1);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					questionButtons.add(new JButton(
							String.format("%s", result.getString(2))));
					questionButtons.get(i).setHorizontalAlignment(SwingConstants.LEFT);
					questionButtons.get(i).setContentAreaFilled(false);
					questionButtons.get(i).setPreferredSize(new Dimension(1000, 50));
					if (result.getString(3).equals(result.getString(4))) {
						questionButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					} else {
						questionButtons.get(i).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
					}
					questionLabels.add(new JLabel(String.format("Q%d", result.getInt(1))));
					
					gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 0, 0, 10);
					menuPanel.add(questionLabels.get(i), gbc);
					
					gbc = new GridBagConstraints();
					gbc.gridx = 1;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 0, 0, 70);
					gbc.fill = GridBagConstraints.HORIZONTAL;
					menuPanel.add(questionButtons.get(i), gbc);
					
					i++;
				}
				result.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addQuestionButtonListener(JPanel panel, AnswerKeyPanel keyPanel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < questionButtons.size(); i++) {
					if (questionButtons.get(i).getModel().isArmed()) {
						keyPanel.updateNote(test);
						keyPanel.updateNumber(Integer.parseInt(questionLabels.get(i).getText().substring(1)));
						keyPanel.repaintPanel(test);
					}
				}
				cardLayout.show(panel, "2");
			}
		}
		ClickListener listener = new ClickListener();
		for (JButton b : questionButtons) {
			b.addActionListener(listener);
		}
	}

	public void createHomeButton() {
		QTool qTool = new QTool();
		homeButton = qTool.createHomeButton();
	}

	public void setLayout() {
		JScrollPane scrollPane = new JScrollPane(menuPanel);
		scrollPane.setAlignmentY(TOP_ALIGNMENT);
		scrollPane.setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(100, 600));
		
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(15, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		add(titleLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		add(scrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 15, 0);
		gbc.anchor = GridBagConstraints.SOUTH;
		add(homeButton, gbc);
	}
}
