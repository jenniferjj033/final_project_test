import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.*;

public class QuestionPanel extends JPanel {
	private JPanel up_toolPanel;
	private JPanel down_toolPanel;
	private JPanel questionPanel;
	private JPanel answerPanel;
	private JButton moreButton;
	private JButton playButton;
	private JButton pauseButton;
	private JLabel timeLabel;
	private JButton finishButton;
	private JButton backButton;
	private JButton nextButton;
	private JTextArea questionArea;
	private JButton markButton;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private ArrayList<String> chosenAnsNum; // 使用者當題答案
	private JButton aButton, bButton, cButton, dButton, eButton;
	private JButton[] xButtons;
	private JButton[] numButtons; // 切換題號按鈕
	private int number; // 題號
	private Connection conn;
	private QTool qTool;
	private Answer answer;
	
	public QuestionPanel(String test) {
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

		chosenAnsNum = new ArrayList<String>();
		number = 1;
		qTool = new QTool();
		answer = new Answer();
		createBackButton(test);
		createNextButton(test);
		createMarkButton();
		createQuestionArea(test);
		createAnswerComp(test);
		createQToolPanel();
		setLayout();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public Answer getAnswer() {
		return this.answer;
	}
	
	public JLabel getTimeLabel() {
		return this.timeLabel;
	}
	
	public void updateNumber(int num) {
		this.number = num;
	}
	
	public void repaintPanel(String test) {
		int last = 0;
		int first = 0;
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Number FROM " + test + " WHERE Number <> 0";
			ResultSet result = stat.executeQuery(query);
			result.next();
			first = result.getInt(1);
			result.last();
			last = result.getInt(1);
			result.close();
		} catch (Exception e) {
			System.out.printf("repaint panel %s\n", e.getMessage());
		}

		if (number == first) {
			backButton.setVisible(false);
			nextButton.setVisible(true);
		} else if (number == last) {
			backButton.setVisible(true);
			nextButton.setVisible(false);
		} else {
			backButton.setVisible(true);
			nextButton.setVisible(true);
		}
		removeAll();
		createQuestionArea(test);
		createAnswerComp(test);
		setLayout();
		validate();
		repaint();
	}
	
	public void createBackButton(String test) {
		ImageIcon backIcon = new ImageIcon(
				new ImageIcon("images/back.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setVisible(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String a = null;
				for (int i = 0; i < chosenAnsNum.size(); i++) {
					switch (chosenAnsNum.get(i)) {
					case "0":
						a += "A";
					case "1":
						a += "B";
					case "2":
						a += "C";
					case "3":
						a += "D";
					case "4":
						a += "E";
					}
				}
				answer.setUserAnswers(number, a);

				number--;
				repaintPanel(test);
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createNextButton(String test) {
		ImageIcon nextIcon = new ImageIcon(
				new ImageIcon("images/next.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		nextButton = new JButton(); // "->"
		nextButton.setIcon(nextIcon);
		nextButton.setBorder(null);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String a = null;
				for (int i = 0; i < chosenAnsNum.size(); i++) {
					switch (chosenAnsNum.get(i)) {
					case "0":
						a += "A";
					case "1":
						a += "B";
					case "2":
						a += "C";
					case "3":
						a += "D";
					case "4":
						a += "E";
					}
				}
				answer.setUserAnswers(number, a);

				number++;
				repaintPanel(test);

			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createMarkButton() {
		ImageIcon beforeMark = new ImageIcon(
				new ImageIcon("images/beforeMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon afterMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setIcon(beforeMark);
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			int click = 0;

			public void actionPerformed(ActionEvent e) {
				click++;
				if (click % 2 == 1) {
					markButton.setIcon(afterMark);
				} else {
					markButton.setIcon(beforeMark);
				}
			}
		}
		ClickListener listener = new ClickListener();
		markButton.addActionListener(listener);
	}

	public void createQuestionArea(String test) {
		questionArea = new JTextArea();
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(questionArea);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#524632")));
		scrollPane.setPreferredSize(new Dimension(200, 150));
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		markButton.setAlignmentY(Component.TOP_ALIGNMENT);
		questionPanel = new JPanel();
		questionPanel.setBackground(Color.decode("#F8EFD4"));
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
		questionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		questionPanel.add(scrollPane);
		questionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		questionPanel.add(markButton);
		questionPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		try {
			Statement stat = conn.createStatement();
			String Question = "SELECT Question FROM " + test + " WHERE Number = " + number;
			stat.execute(Question);
			ResultSet result = stat.getResultSet();
			result.next();

			Statement stat2 = conn.createStatement();
			String Question2 = "SELECT Groupnumber FROM "+ test +" WHERE Number = " + number;
			stat2.execute(Question2);
			ResultSet result2 = stat2.getResultSet();
			result2.next();

			if (result2.getInt(1) != 0) {

				Statement stat3 = conn.createStatement();
				String Question3 = "SELECT Question FROM " + test + " WHERE Number = 0 AND Groupnumber = "
						+ result2.getInt(1);
				stat3.execute(Question3);
				ResultSet result3 = stat3.getResultSet();
				result3.next();
				questionArea.setText(String.format("%s\n\nQ%d. %s\n", result3.getString(1), number, result.getString(1)));
			} else {
				questionArea.setText(String.format("Q%d. %s", number, result.getString(1)));
			}

		} catch (Exception e) {
			System.out.printf("create question area %s\n", e.getMessage());
		}
	}

	public void createAnswerComp(String test) {
		try {
			int option = 4;
			answerButtons = new ArrayList<JButton>();
			Statement stat = conn.createStatement();

			String queryA = "SELECT A FROM " + test + " WHERE Number = " + number;
			ResultSet resultA = stat.executeQuery(queryA);
			resultA.next();
			aButton = new JButton(resultA.getString(1));
			answerButtons.add(aButton);
			resultA.close();

			String queryB = "SELECT B FROM " + test + " WHERE Number = " + number;
			ResultSet resultB = stat.executeQuery(queryB);
			resultB.next();
			bButton = new JButton(resultB.getString(1));
			answerButtons.add(bButton);
			resultB.close();

			String queryC = "SELECT C FROM " + test + " WHERE Number = " + number;
			ResultSet resultC = stat.executeQuery(queryC);
			resultC.next();
			cButton = new JButton(resultC.getString(1));
			answerButtons.add(cButton);
			resultC.close();

			String queryD = "SELECT D FROM " + test + " WHERE Number = " + number;
			ResultSet resultD = stat.executeQuery(queryD);
			resultD.next();
			dButton = new JButton(resultD.getString(1));
			answerButtons.add(dButton);
			resultD.close();

			String queryE = "SELECT E FROM " + test + " WHERE Number = " + number;
			ResultSet resultE = stat.executeQuery(queryE);
			resultE.next();
			if (resultE.getString(1) != null) {
				option = 5;
				eButton = new JButton(resultE.getString(1));
				answerButtons.add(eButton);
			}
			resultE.close();

			ImageIcon beforeX = new ImageIcon(
					new ImageIcon("images/beforeX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			ImageIcon afterX = new ImageIcon(
					new ImageIcon("images/afterX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			xButtons = new JButton[option];
			class X_ClickListener implements ActionListener {
				boolean[] click = { false, false, false, false, false };

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < xButtons.length; i++) {
						if (xButtons[i].getModel().isArmed()) {
							if (click[i] == false) {
								xButtons[i].setIcon(afterX);
								answerButtons.get(i).setBorder(null);
								chosenAnsNum.remove(String.valueOf(i));
								click[i] = true;
							} else {
								xButtons[i].setIcon(beforeX);
								click[i] = false;
							}
						}
					}
					// System.out.println(chosenAnsNum);
				}
			}
			X_ClickListener xListener = new X_ClickListener();
			for (int i = 0; i < option; i++) {
				xButtons[i] = new JButton(); // "X"
				xButtons[i].setBorder(null);
				xButtons[i].setContentAreaFilled(false);
				xButtons[i].setIcon(beforeX);
				xButtons[i].addActionListener(xListener);
			}

			class Ans_ClickListener implements ActionListener {
				boolean[] click = { false, false, false, false, false };

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < answerButtons.size(); i++) {
						if (answerButtons.size() == 4) {
							if (answerButtons.get(i).getModel().isArmed()) {
								if (click[i] == false) {
									answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
									xButtons[i].setIcon(beforeX);
									chosenAnsNum.clear();
									chosenAnsNum.add(String.valueOf(i));
									click[i] = true;
								} else {
									answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
									chosenAnsNum.remove(String.valueOf(i));
									click[i] = false;
								}
							} else {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
							}
						} else {
							if (answerButtons.get(i).getModel().isArmed()) {
								if (click[i] == false) {
									answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
									xButtons[i].setIcon(beforeX);
									chosenAnsNum.add(String.valueOf(i));
									Collections.sort(chosenAnsNum);
									click[i] = true;
								} else {
									answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
									chosenAnsNum.remove(String.valueOf(i));
									click[i] = false;
								}
							}
						}
					}
					// System.out.println(chosenAnsNum);
				}
			}
			Ans_ClickListener ansListener = new Ans_ClickListener();
			for (JButton b : answerButtons) {
				b.setHorizontalAlignment(SwingConstants.LEFT);
				b.setContentAreaFilled(false);
				b.setPreferredSize(new Dimension(900, 70));
				b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
				b.addActionListener(ansListener);
			}

			answerLabel = new JLabel[option];
			for (int i = 0; i < option; i++) {
				answerLabel[i] = new JLabel();
				answerLabel[i].setBorder(null);
			}
			answerLabel[0].setText("A.");
			answerLabel[1].setText("B.");
			answerLabel[2].setText("C.");
			answerLabel[3].setText("D.");
			if (option == 5) {
				answerLabel[4].setText("E.");
			}

			answerPanel = new JPanel(new GridBagLayout());
			answerPanel.setBackground(Color.decode("#F8EFD4"));
			GridBagConstraints gbc;
			for (int i = 0; i < option; i++) {
				gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				answerPanel.add(answerLabel[i], gbc);

				gbc = new GridBagConstraints();
				gbc.gridx = 1;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				answerPanel.add(answerButtons.get(i), gbc);

				gbc = new GridBagConstraints();
				gbc.gridx = 3;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				answerPanel.add(xButtons[i], gbc);
			}
		} catch (Exception e) {
			System.out.printf("create answer comp %s\n", e.getMessage());
		}
	}

	public void createQToolPanel() {
		moreButton = qTool.createMoreButton();
		playButton = qTool.createPlayButton();
		pauseButton = qTool.createPauseButton();
		timeLabel = qTool.createTimer();
		finishButton = qTool.createFinishButton();

		up_toolPanel = qTool.createUpToolPanel(moreButton, playButton, pauseButton, timeLabel);

		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(25, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(finishButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(up_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(answerPanel);
		add(Box.createGlue());
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}
}
