import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import style.*;

public class QToolPanel extends JPanel {
	private JButton[] numButtons;
	private JPanel up_toolPanel;
	private JPanel buttonPanel;
	private JButton finishButton;
	private JButton moreButton;
	private JButton playButton;
	private JButton pauseButton;
	private JLabel timeLabel;
	private QTool qTool;
	private String test;

	public QToolPanel(String test) {
		try {
			qTool = new QTool();
			this.test = test;
			createButtons(test);
			createUpToolPanel();
			setLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public void createButtons(String test) {
		int size = 0;
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "MG05";
		String url = server + database;
		String username = "MG05";
		String password = "9mMuzQ";
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT COUNT(*) FROM " + test + " WHERE Number <> 0";
			String query2 = "SELECT Number FROM " + test + " WHERE Number <> 0";

			ResultSet result = stat.executeQuery(query);
			result.next();
			size = Integer.parseInt(result.getString(1));
			result.close();

			ResultSet result2 = stat.executeQuery(query2);
			numButtons = new RoundButton[size];
			GridLayout gridLayout = new GridLayout(5, 10);
			gridLayout.setHgap(50);
			gridLayout.setVgap(50);
			buttonPanel = new JPanel(gridLayout);
			buttonPanel.setPreferredSize(new Dimension(1000, 500));
			buttonPanel.setBackground(Color.decode("#F8EFD4"));
			for (int i = 0; i < size; i++) {
				result2.next();
				numButtons[i] = new RoundButton(String.format("%d", result2.getInt(1)));
				numButtons[i].setPreferredSize(new Dimension(15, 15));
				buttonPanel.add(numButtons[i]);
			}

			result2.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void addQNumListener(JPanel panel, QuestionPanel questionPanel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < numButtons.length; i++) {
					if (numButtons[i].getModel().isArmed()) {
						questionPanel.updateNumber(i + 1);
						questionPanel.repaintPanel(test);
					}
				}
				cardLayout.show(panel, "1");
			}
		}
		
		ClickListener listener = new ClickListener();
		for (int i = 0; i < numButtons.length; i++) {
			numButtons[i].addActionListener(listener);
		}
	}

	public void createUpToolPanel() {
		moreButton = qTool.createMoreButton();
		playButton = qTool.createPlayButton();
		pauseButton = qTool.createPauseButton();
		timeLabel = qTool.createTimer();
		finishButton = qTool.createFinishButton();
		up_toolPanel = qTool.createUpToolPanel(moreButton, playButton, pauseButton, timeLabel);

	}

	public void setLayout() {
		setLayout(new GridBagLayout());
		setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		add(up_toolPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		// gbc.ipadx = 320;
		// gbc.ipady = 160;
		gbc.insets = new Insets(0, 50, 0, 50);
		gbc.anchor = GridBagConstraints.CENTER;
		add(buttonPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(finishButton, gbc);
	}

}
