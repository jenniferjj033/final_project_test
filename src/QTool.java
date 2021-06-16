import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class QTool {
	private JButton homeButton;
	private JButton moreButton;
	private JButton playButton;
	private JButton pauseButton;
	private JLabel timeLabel;
	private JButton finishButton;
	private Timer timer;
	private FinishAction finishAction;
	private int delay, period;
	private String time;
	private boolean timerun;
	private int clickMore;

	public QTool() {
		delay = 1000;
		period = 1000;
		timerun = true;
		clickMore = 0;

		createButton();
	}

	public void createButton() {
		homeButton = new JButton();
		ImageIcon homeIcon = new ImageIcon(
				new ImageIcon("images/homeBtn.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		homeButton.setIcon(homeIcon);
		homeButton.setHorizontalAlignment(SwingConstants.CENTER);
		homeButton.setBorder(null);
		homeButton.setContentAreaFilled(false);

		moreButton = new JButton(); // "#"
		ImageIcon moreIcon = new ImageIcon(
				new ImageIcon("images/more.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		moreButton.setIcon(moreIcon);
		moreButton.setBorder(null);
		moreButton.setOpaque(false);
		moreButton.setContentAreaFilled(false);

		playButton = new JButton(); // "|>"
		ImageIcon playIcon = new ImageIcon(
				new ImageIcon("images/play.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		playButton.setIcon(playIcon);
		playButton.setBorder(null);
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);

		pauseButton = new JButton(); // "||"
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/pause.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		pauseButton.setIcon(pauseIcon);
		pauseButton.setBorder(null);
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);

		timeLabel = new JLabel();
		ImageIcon timerIcon = new ImageIcon(
				new ImageIcon("images/timer.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		timeLabel.setIcon(timerIcon);
		timeLabel.setVerticalTextPosition(SwingConstants.CENTER);
		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));

		finishButton = new JButton(); // "Fin"
		ImageIcon finishIcon = new ImageIcon(
				new ImageIcon("images/finish.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishButton.setIcon(finishIcon);
		finishButton.setBorder(null);
		finishButton.setOpaque(false);
		finishButton.setContentAreaFilled(false);
	}

	public void setClickMore(int num) {
		this.clickMore = num;
	}

	public void setTimerun(boolean b) {
		this.timerun = b;
	}

	public void setTimeLabelText(String time) {
		this.time = time;
	}

	public void setTime(int size) {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int interval = size * 96; // 4800s/50Q -> 96s/Q
			int sec, min, hour;

			public void run() {
				if (timerun == true) {
					sec = interval % 60;
					min = interval / 60;
					// min = interval / 60 % 60;
					// hour = interval / 60 / 60;
					time = String.format("%02d:%02d", min, sec);
					timeLabel.setText(time);
					interval--;
				}
				if (interval < 0) {
					timer.cancel();
					finishAction.finish();
				}
			}
		}, delay, period);
	}

	public JButton getHomeButton() {
		return this.homeButton;
	}

	public JButton getMoreButton() {
		return this.moreButton;
	}

	public JButton getPlayButton() {
		return this.playButton;
	}

	public JButton getPauseButton() {
		return this.pauseButton;
	}

	public JLabel getTimeLabel() {
		return this.timeLabel;
	}

	public JButton getFinishButton() {
		return this.finishButton;
	}

	public void addQuestionPanelListener(QuestionPanel questionPanel) { // for QuestionPanel

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (clickMore % 2 == 0) {
					questionPanel.updateUserAnswers();
					questionPanel.repaintNumBtnPanel();
				} else {
					questionPanel.repaintQuestionPanel(); // moreBtn click twice and finishButton invisible?
				}
				clickMore++;
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);

		class ClickListener1 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				timerun = true;
				moreButton.setVisible(true);
				finishButton.setVisible(true);
				if (clickMore % 2 == 0) {
					questionPanel.repaintQuestionPanel();
				} else {
					questionPanel.repaintNumBtnPanel();
				}
			}
		}
		ClickListener1 listener1 = new ClickListener1();
		playButton.addActionListener(listener1);

		class ClickListener2 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				List<Component> components = Arrays.asList(questionPanel.getComponents());
				if (components.contains(questionPanel.getAnswerPanel())) {
					questionPanel.updateUserAnswers();
				}
				timerun = false;
				moreButton.setVisible(false);
				finishButton.setVisible(false);
				questionPanel.setPauseLayout();
			}
		}
		ClickListener2 listener2 = new ClickListener2();
		pauseButton.addActionListener(listener2);

		JButton[] numButtons = questionPanel.getNumButtons();
		class ClickListener3 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < numButtons.length; i++) {
					if (numButtons[i].getModel().isArmed()) {
						questionPanel.updateNumber(i);
						questionPanel.repaintQuestionPanel();
					}
				}
				clickMore++;
			}
		}
		ClickListener3 listener3 = new ClickListener3();
		for (int i = 0; i < numButtons.length; i++) {
			numButtons[i].addActionListener(listener3);
		}

	}

	public void addMoreButtonListener(JPanel panel, AnswerKeyPanel ansKeyPanel) { // AnswerKeyPanel
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				if (ansKeyPanel.getName().equals("answerKeyPanel")) {
					ansKeyPanel.insertNote();
					cardLayout.show(panel, "ansListPanel");
				} else if (ansKeyPanel.getName().equals("noteKeyPanel")) {
					ansKeyPanel.insertNote();
					cardLayout.show(panel, "noteQPanel");
				} else if (ansKeyPanel.getName().equals("listKeyPanel")) {
					ansKeyPanel.insertNote();
					cardLayout.show(panel, "listQPanel");
				}
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}

	public void addHomeButtonListener(JPanel panel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "homePanel");
			}
		}
		ClickListener listener = new ClickListener();
		homeButton.addActionListener(listener);
	}

	public void addHomeButtonListener(JPanel panel, SubjectPanel subjectPanel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				subjectPanel.setBtnPanel();
				subjectPanel.repaintPanel();
				cardLayout.show(panel, "homePanel");
			}
		}
		ClickListener listener = new ClickListener();
		homeButton.addActionListener(listener);
	}

	public void addFinishButtonListener(JPanel panel, QuestionPanel questionPanel, ToAnswerPanel toAnswerPanel) {
		finishAction = new FinishAction(panel, questionPanel, toAnswerPanel);

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				finishAction.finish();
			}
		}
		ClickListener listener = new ClickListener();
		finishButton.addActionListener(listener);
	}

	public class FinishAction {
		CardLayout cardLayout;
		JPanel panel;
		QuestionPanel questionPanel;
		ToAnswerPanel toAnswerPanel;
		String[] options;

		public FinishAction(JPanel panel, QuestionPanel questionPanel, ToAnswerPanel toAnswerPanel) {
			this.panel = panel;
			this.questionPanel = questionPanel;
			this.toAnswerPanel = toAnswerPanel;
			cardLayout = (CardLayout) (panel.getLayout());
			options = new String[3];
			options[0] = "Done";
			options[1] = "Exit";
			options[2] = "Cancel";
		}

		public void finish() {
			Answer answer = questionPanel.getAnswer();
			int size = answer.getNumbers().size();
			int input = JOptionPane.showOptionDialog(null, "Finish and calculate the score? / Or Exit this page?",
					"Confirm message", 0, JOptionPane.QUESTION_MESSAGE, null, options, null);
			if (input == 0) {
				questionPanel.updateUserAnswers();
				timer.cancel();
				answer.insertUserAnswers();
				answer.insertIfMarked();
				answer.countScore();
				String score = String.format("%.1f / %d分", answer.getScore(), size * 2);
				String time = questionPanel.getTimeLabel().getText() + " / " + size + "題";
				String pass;
				if (answer.getScore() < (size * 2 * 0.6)) {
					pass = "FALSE";
				} else {
					pass = "TRUE";
				}

				toAnswerPanel.updateScore(score);
				toAnswerPanel.updateTime(time);
				toAnswerPanel.updateSubject(QuestionPanel.getSubject());

				insertTestInfo(RangePanel.getTestID(), questionPanel.getYear(), QuestionPanel.getSubject(), score, pass,
						time, getDate());

				Viewer.destroy(questionPanel);

				cardLayout.show(panel, "toAnsPanel");
			} else if (input == 1) {
				Viewer.destroy(questionPanel);
				cardLayout.show(panel, "homePanel");
			}
		}

		public void insertTestInfo(int testID, String year, String subject, String score, String pass, String time,
				String date) {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";

			String query = "INSERT INTO AllTests VALUES (?, ?, ?, ?, ?, ?, ?)";
			try {
				Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stat = conn.prepareStatement(query);
				stat.setString(1, LoginPanel.getUserID());
				stat.setInt(2, testID);
				stat.setString(3, String.format("%s - %s", year, subject));
				stat.setString(4, score);
				stat.setString(5, pass);
				stat.setString(6, time);
				stat.setString(7, date);
				stat.executeUpdate();
			} catch (Exception e) {
				System.out.printf("<QTool> insert test info: %s\n", e.getMessage());
			}
		}

		public String getDate() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			return dtf.format(now);
		}

	}

}
