import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
		homeButton = new JButton();
		moreButton = new JButton(); // "#"
		playButton = new JButton(); // "|>"
		pauseButton = new JButton(); // "||"
		timeLabel = new JLabel();
		finishButton = new JButton(); // "Fin"
	}

	public JButton createHomeButton() {
		ImageIcon homeIcon = new ImageIcon(
				new ImageIcon("images/homeBtn.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		homeButton.setIcon(homeIcon);
		homeButton.setHorizontalAlignment(SwingConstants.CENTER);
		homeButton.setBorder(null);
		homeButton.setContentAreaFilled(false);
		return this.homeButton;
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

	public JButton createMoreButton() {
		ImageIcon moreIcon = new ImageIcon(
				new ImageIcon("images/more.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		moreButton.setIcon(moreIcon);
		moreButton.setBorder(null);
		moreButton.setOpaque(false);
		moreButton.setContentAreaFilled(false);
		return this.moreButton;
	}
	
	public void addMoreButtonListener(QuestionPanel questionPanel) { // for AnswerKeyPanel
		class ClickListener implements ActionListener {
			int click = 0;

			public void actionPerformed(ActionEvent e) {
				if (click % 2 == 0) {
					questionPanel.repaintToolPanel();
				} else {
					questionPanel.repaintQuestionPanel();
				}
				click++;
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}

	public void addMoreButtonListener(JPanel panel) { // for AnswerKeyPanel
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "ansListPanel");
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}

	public JButton createPlayButton(QuestionPanel questionPanel) {
		ImageIcon playIcon = new ImageIcon(
				new ImageIcon("images/play.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		playButton.setIcon(playIcon);
		playButton.setBorder(null);
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				timerun = true;
				moreButton.setVisible(true);
				questionPanel.repaintQuestionPanel();
			}
		}
		ClickListener listener = new ClickListener();
		playButton.addActionListener(listener);
		return this.playButton;
	}

	public JButton createPauseButton(QuestionPanel questionPanel) {
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/pause.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		pauseButton.setIcon(pauseIcon);
		pauseButton.setBorder(null);
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				timerun = false;
				moreButton.setVisible(false);
				questionPanel.setPauseLayout();
			}
		}
		ClickListener listener = new ClickListener();
		pauseButton.addActionListener(listener);
		return this.pauseButton;
	}

	public JLabel createTimer() {
		ImageIcon timerIcon = new ImageIcon(
				new ImageIcon("images/timer.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		timeLabel.setIcon(timerIcon);
		timeLabel.setVerticalTextPosition(SwingConstants.CENTER);
		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 16));
		return this.timeLabel;
	}
	
	public void setTime(int size) {
		Timer timer;
		int delay = 1000;
		int period = 1000;
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			int interval = size * 96; // 4800s/50Q -> 96s/Q

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
	}

	public JButton createFinishButton() {
		ImageIcon finishIcon = new ImageIcon(
				new ImageIcon("images/finish.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishButton.setIcon(finishIcon);
		finishButton.setBorder(null);
		finishButton.setOpaque(false);
		finishButton.setContentAreaFilled(false);
		return this.finishButton;
	}

	public void addFinishButtonListener(JPanel panel, QuestionPanel questionPanel, ToAnswerPanel toAnswerPanel) {
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			Answer answer = questionPanel.getAnswer();
			String test = questionPanel.getTest();
			String[] options = { "Finish", "Exit", "Cancel" };
			
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showOptionDialog(null, "Finish and calculate the score? / Or Exit this page?", "Confirm message",
						0, JOptionPane.QUESTION_MESSAGE, null, options, null);
				if (input == 0) {
					//answer.insertUserAnswers(test);
					//answer.insertIfMarked(test);
					toAnswerPanel.updateScore(answer.getScore(test));
					toAnswerPanel.updateTime(questionPanel.getTimeLabel().getText());
					toAnswerPanel.createPanel();
					cardLayout.show(panel, "toAnsPanel");
				} else if (input == 1) {
					cardLayout.show(panel, "homePanel");
				}
			}
		}
		ClickListener listener = new ClickListener();
		finishButton.addActionListener(listener);
	}

}
