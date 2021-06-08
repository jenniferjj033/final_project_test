import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.*;

public class QuestionPanel extends JPanel {
	private JPanel up_toolPanel, down_toolPanel;
	private JPanel questionPanel, answerPanel;
	private JButton moreButton, playButton, pauseButton;
	private JLabel timeLabel;
	private JButton finishButton, backButton, nextButton;
	private JTextArea questionArea;
	private JButton markButton;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private ArrayList<String> chosenAnsNum; // 使用者當題答案
	private ArrayList<String> ifMarked;
	private JButton aButton, bButton, cButton, dButton, eButton;
	private JButton[] xButtons;
	private int number; // 題號
	private int option;
	private Connection conn;
	private QTool qTool;
	private Answer answer;
	private String test;
	
	private JButton[] numButtons;
	private JPanel buttonPanel;
	private int size;
	

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

		this.test = test;
		chosenAnsNum = new ArrayList<String>();
		qTool = new QTool();
		answer = new Answer();
		answer.setNumbers(test);
		ifMarked = answer.getIfMarked();
		number = 1;
		
		createBackButton();
		createNextButton();
		createMarkButton();
		createQuestionArea();
		createAnswerComp();
		createQToolPanel();
		setQuestionLayout();
		
		createButtons();
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

	public String getTest() {
		return this.test;
	}

	public void updateNumber(int num) {
		this.number = num;
	}

	public void updateUserAnswers() {
		String a = "";
		for (int i = 0; i < chosenAnsNum.size(); i++) {
			switch (chosenAnsNum.get(i)) {
			case "0":
				a += "A";
				break;
			case "1":
				a += "B";
				break;
			case "2":
				a += "C";
				break;
			case "3":
				a += "D";
				break;
			case "4":
				a += "E";
				break;
			}
		}

		if (a != "" && a != answer.getUserAnswers().get(number - 1)) {
			answer.setUserAnswers(number - 1, a);
		}
		chosenAnsNum.clear();
	}

	public void repaintQuestionPanel() {
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
		createMarkButton();
		createQuestionArea();
		createAnswerComp();
		setQuestionLayout();
		validate();
		repaint();
	}

	public void createBackButton() {
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
				updateUserAnswers();
				number--;
				repaintQuestionPanel();
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createNextButton() {
		ImageIcon nextIcon = new ImageIcon(
				new ImageIcon("images/next.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		nextButton = new JButton(); // "->"
		nextButton.setIcon(nextIcon);
		nextButton.setBorder(null);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				updateUserAnswers();
				number++;
				repaintQuestionPanel();

			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createMarkButton() {
		ImageIcon beforeMark = new ImageIcon(
				new ImageIcon("images/beforeMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon qMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		ImageIcon afterMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
		if (ifMarked.get(number - 1) == "FALSE") {
			markButton.setIcon(beforeMark);
		} else {
			markButton.setIcon(afterMark);
		}

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (ifMarked.get(number - 1) == "FALSE") {
					markButton.setIcon(afterMark);
					numButtons[number - 1].setText("");
					numButtons[number - 1].setIcon(qMark);
					ifMarked.set(number - 1, "TRUE");
				} else {
					markButton.setIcon(beforeMark);
					numButtons[number - 1].setText(String.format("%s", number));
					numButtons[number - 1].setIcon(null);
					ifMarked.set(number - 1, "FALSE");
				}
			}
		}
		ClickListener listener = new ClickListener();
		markButton.addActionListener(listener);
	}

	public void createQuestionArea() {
		questionArea = new JTextArea();
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(questionArea);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#524632")));
		scrollPane.setPreferredSize(new Dimension(200, 120));
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

			questionArea.setText(String.format("Q%d. %s", number, result.getString(1)));
			questionArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		} catch (Exception e) {
			System.out.printf("create question area %s\n", e.getMessage());
		}
	}

	public void createXButton() {
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
							answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
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
	}

	public void createAnswerButton() {
		ImageIcon beforeX = new ImageIcon(
				new ImageIcon("images/beforeX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		ImageIcon afterX = new ImageIcon(
				new ImageIcon("images/afterX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		option = 4;

		try {
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

			for (JButton b : answerButtons) {
				b.setHorizontalAlignment(SwingConstants.LEFT);
				b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
				b.setContentAreaFilled(false);
				b.setPreferredSize(new Dimension(900, 70));
				b.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
			}

			boolean[] click = { false, false, false, false, false };
			String a = answer.getUserAnswers().get(number - 1);
			if (a != null) {
				for (int i = 0; i < a.length(); i++) {
					switch (a.charAt(i)) {
					case 'A':
						aButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[0] = true;
						chosenAnsNum.add("0");
						break;
					case 'B':
						bButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[1] = true;
						chosenAnsNum.add("1");
						break;
					case 'C':
						cButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[2] = true;
						chosenAnsNum.add("2");
						break;
					case 'D':
						dButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[3] = true;
						chosenAnsNum.add("3");
						break;
					case 'E':
						eButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[4] = true;
						chosenAnsNum.add("4");
						break;
					}
				}
			}

			class Ans_ClickListener implements ActionListener {

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
				b.addActionListener(ansListener);
			}
		} catch (Exception e) {
			System.out.printf("create answer comp %s\n", e.getMessage());
		}

	}

	public void createAnswerComp() {
		createAnswerButton();
		createXButton();
		answerLabel = new JLabel[option];
		for (int i = 0; i < option; i++) {
			answerLabel[i] = new JLabel();
			answerLabel[i].setBorder(null);
			answerLabel[i].setFont(new Font("微軟正黑體", Font.PLAIN, 16));
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
	}

	public void createQToolPanel() {
		moreButton = qTool.createMoreButton();
		playButton = qTool.createPlayButton(this);
		pauseButton = qTool.createPauseButton(this);
		timeLabel = qTool.createTimer();
		finishButton = qTool.createFinishButton();
		
		up_toolPanel = new JPanel();
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

		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(finishButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		
	}

	public void setQuestionLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(up_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(answerPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	public void setPauseLayout() {
		JPanel pausePanel = new JPanel(new BorderLayout());
		pausePanel.setBackground(Color.decode("#F8EFD4"));
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/rest.png").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
		JLabel pauseLabel = new JLabel(pauseIcon);
		pausePanel.add(pauseLabel, BorderLayout.CENTER);
		
		removeAll();
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 15)));
		add(up_toolPanel);
		add(pausePanel);
		add(Box.createGlue());
		validate();
		repaint();
	}
	
	public void createButtons() {
		size = 0;
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
			GridLayout gridLayout = new GridLayout(5, 10); // what if number > 50?
			gridLayout.setHgap(30);
			gridLayout.setVgap(30);
			buttonPanel = new JPanel(gridLayout);
			buttonPanel.setPreferredSize(new Dimension(1000, 500));
			buttonPanel.setBackground(Color.decode("#F8EFD4"));
			for (int i = 0; i < size; i++) {
				result2.next();
				numButtons[i] = new RoundButton(String.format("%d", result2.getInt(1)));
				numButtons[i].setPreferredSize(new Dimension(20, 20));
				numButtons[i].setFont(new Font("微軟正黑體", Font.PLAIN, 14));
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
						questionPanel.repaintQuestionPanel();
					}
				}
				cardLayout.show(panel, "questionPanel");
			}
		}
		
		ClickListener listener = new ClickListener();
		for (int i = 0; i < numButtons.length; i++) {
			numButtons[i].addActionListener(listener);
		}
	}
	
	public void repaintToolPanel() {
		removeAll();
		setToolLayout();
		validate();
		repaint();
	}

	public void setToolLayout() {
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
		gbc.insets = new Insets(0, 50, 0, 50);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
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
