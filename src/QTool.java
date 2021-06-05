import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private boolean timerun = true;

	public QTool() {
		createMoreButton();
		createPlayButton();
		createPauseButton();
		createTimer();
		createFinishButton();
	}

	public JButton createHomeButton() {
		ImageIcon homeIcon = new ImageIcon(
				new ImageIcon("images/homeBtn.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		homeButton = new JButton();
		homeButton.setIcon(homeIcon);
		homeButton.setHorizontalAlignment(SwingConstants.CENTER);
		homeButton.setBorder(null);
		homeButton.setContentAreaFilled(false);
		return this.homeButton;
	}

	public void addHomeButtonListener(JPanel panel) {
		class ClisckListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, ""); // show what?!
			}
		}
	}

	public JButton createMoreButton() {
		ImageIcon moreIcon = new ImageIcon(
				new ImageIcon("images/more.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		moreButton = new JButton(); // "#"
		moreButton.setIcon(moreIcon);
		moreButton.setBorder(null);
		moreButton.setOpaque(false);
		moreButton.setContentAreaFilled(false);
		return this.moreButton;
	}

	public void addMoreButtonListener(JPanel cardPanel, JPanel actionPanel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (cardPanel.getLayout());

			public void actionPerformed(ActionEvent e) {
				if (actionPanel instanceof QuestionPanel) {
					cardLayout.show(cardPanel, "2");
				} else if (actionPanel instanceof QToolPanel) {
					cardLayout.show(cardPanel, "1");
				}
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}
	
	public void addMoreButtonListener(JPanel panel) { // for AnswerKeyPanel
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "1");
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}

	public JButton createPlayButton() {
		ImageIcon playIcon = new ImageIcon(
				new ImageIcon("images/play.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		playButton = new JButton(); // "|>"
		playButton.setIcon(playIcon);
		playButton.setBorder(null);
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				timerun = true;
			}
		}
		ClickListener listener = new ClickListener();
		playButton.addActionListener(listener);
		return this.playButton;
	}

	public JButton createPauseButton() {
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/pause.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		pauseButton = new JButton(); // "||"
		pauseButton.setIcon(pauseIcon);
		pauseButton.setBorder(null);
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				timerun = false;
			}
		}
		ClickListener listener = new ClickListener();
		pauseButton.addActionListener(listener);
		return this.pauseButton;
	}

	public JLabel createTimer() {
		ImageIcon timerIcon = new ImageIcon(
				new ImageIcon("images/timer.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		timeLabel = new JLabel();
		timeLabel.setIcon(timerIcon);
		timeLabel.setVerticalTextPosition(SwingConstants.CENTER);
		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		timeLabel.setText("01:20:00");

		Timer timer;
		int delay = 1000;
		int period = 1000;
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			int interval = 4800;

			public void run() {
				if (timerun == true) {
					int sec = interval % 60;
					int min = interval / 60 % 60;
					int hour = interval / 60 / 60;
					String time = String.format("%02d:%02d:%02d", hour, min, sec);
					timeLabel.setText(time);
					interval--;
				}
				if (interval == 1)
					timer.cancel();

			}
		}, delay, period);

		return this.timeLabel;
	}

	public JButton createFinishButton() {
		ImageIcon finishIcon = new ImageIcon(
				new ImageIcon("images/finish.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishButton = new JButton(); // "Fin"
		finishButton.setIcon(finishIcon);
		finishButton.setBorder(null);
		finishButton.setOpaque(false);
		finishButton.setContentAreaFilled(false);
		return this.finishButton;
	}

	public void addFinishButtonListener(JPanel panel, QuestionPanel questionPanel, String test) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			Answer answer = questionPanel.getAnswer();
			
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "Finish and calculate the score?", "Confirm message",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (input == 0) {
					answer.insertUserAnswers(test);
					checkAnswer(test);
					cardLayout.show(panel, "3");
				}
			}
			
			public Double checkAnswer(String test) {
				ArrayList<String> userAns = answer.getUserAnswer();
				ArrayList<String> corrAns = answer.getCorrectAnswers(test);
				ArrayList<Boolean> ifCorrect = answer.getIfCorrect(corrAns, userAns);
				return answer.getScore(ifCorrect, corrAns, userAns);
			}
		}
		ClickListener listener = new ClickListener();
		finishButton.addActionListener(listener);
	}

	public JPanel createUpToolPanel(JButton moreButton, JButton playButton, JButton pauseButton, JLabel timeLabel) {
		JPanel up_toolPanel = new JPanel();
		up_toolPanel.setBackground(Color.decode("#F8EFD4"));
		up_toolPanel.setLayout(new BoxLayout(up_toolPanel, BoxLayout.X_AXIS));
		up_toolPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		up_toolPanel.add(moreButton);
		up_toolPanel.add(Box.createGlue());
		up_toolPanel.add(playButton);
		up_toolPanel.add(Box.createRigidArea(new Dimension(15, 0)));
		up_toolPanel.add(pauseButton);
		up_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		up_toolPanel.add(timeLabel);
		up_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		return up_toolPanel;
	}
}
