import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.*;

public class AnswerKeyPanel extends JPanel {
	private JPanel questionPanel, answerPanel, notePanel, down_toolPanel;
	private JButton moreButton;
	private JButton markButton;
	private JTextArea questionArea;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private JButton aButton, bButton, cButton, dButton, eButton;
	private JTextArea keyArea;
	private JTextArea noteArea;
	private JButton backButton;
	private JButton nextButton;
	private JButton deleteNoteBtn;
	private int number; // 題號
	private Connection conn;
	private QTool qTool;
	private String test;

	public AnswerKeyPanel(String test) {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database;
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.printf("constructor %s\n", e.getMessage());
		}

		qTool = new QTool();
		number = 1;
		moreButton = qTool.createMoreButton();
		this.test = test;

		createMarkButton();
		createQuestionArea();
		createAnswerComp();
		createNoteComp();
		createDeleteButton();
		createBackButton();
		createNextButton();
		createToolPanel();
		setLayout();
	}

	public void updateNumber(int num) {
		this.number = num;
	}

	public QTool getQTool() {
		return this.qTool;
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
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Mark FROM " + test + " WHERE Number = " + number;
			ResultSet result = stat.executeQuery(query);
			result.next();
			if (result.getString(1).equals("TRUE")) {
				markButton.setIcon(afterMark);
			}
		} catch (Exception e) {
			System.out.printf("create mark button %s\n", e.getMessage());
		}
	}

	public void createQuestionArea() {
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

			questionArea.setText(String.format("Q%d. %s", number, result.getString(1)));
			questionArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		} catch (Exception e) {
			System.out.printf("create question area %s\n", e.getMessage());
		}
	}

	public void createAnswerComp() {
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

			for (JButton b : answerButtons) {
				b.setHorizontalAlignment(SwingConstants.LEFT);
				b.setContentAreaFilled(false);
				b.setPreferredSize(new Dimension(900, 70));
				b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 2, 15, 0));
				b.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
			}

			String queryUserAns = "SELECT UserAnswer FROM " + test + " WHERE Number = " + number;
			ResultSet resultUserAns = stat.executeQuery(queryUserAns);
			resultUserAns.next();
			String userAnswer = resultUserAns.getString(1);
			resultUserAns.close();

			String queryCorrAns = "SELECT Answer From " + test + " WHERE Number = " + number;
			ResultSet resultCorrAns = stat.executeQuery(queryCorrAns);
			resultCorrAns.next();
			String correctAnswer = resultCorrAns.getString(1);
			resultCorrAns.close();

			for (int i = 0; i < correctAnswer.length(); i++) {
				switch (correctAnswer.charAt(i)) {
				case 'A':
					answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					break;
				case 'B':
					answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					break;
				case 'C':
					answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					break;
				case 'D':
					answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					break;
				case 'E':
					answerButtons.get(4).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					break;
				}
			}
			if (userAnswer != null) {
				if (!userAnswer.equals(correctAnswer)) {
					if (option == 4) {
						switch (userAnswer) {
						case "A":
							answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							break;
						case "B":
							answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							break;
						case "C":
							answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							break;
						case "D":
							answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							break;
						}
					} else {
						for (int i = 0; i < userAnswer.length(); i++) {
							switch (userAnswer.charAt(i)) {
							case 'A':
								answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
								break;
							case 'B':
								answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
								break;
							case 'C':
								answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
								break;
							case 'D':
								answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
								break;
							case 'E':
								answerButtons.get(4).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
								break;
							}
						}
					}
				}
			}

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
				gbc.insets = new Insets(0, 0, 5, 0);
				answerPanel.add(answerLabel[i], gbc);

				gbc = new GridBagConstraints();
				gbc.gridx = 1;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				gbc.insets = new Insets(0, 0, 5, 0);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				answerPanel.add(answerButtons.get(i), gbc);

				gbc = new GridBagConstraints();
				gbc.gridx = 3;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				answerPanel.add(new JLabel(), gbc);
			}
		} catch (Exception e) {
			System.out.printf("create answer comp %s\n", e.getMessage());
		}
	}

	public void createNoteComp() {
		keyArea = new JTextArea();
		keyArea.setEditable(false);
		keyArea.setLineWrap(true);
		keyArea.setBackground(Color.decode("#F8EFD4"));
		keyArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Answer, Degree, AnswerKey FROM " + test + " WHERE Number = " + number;
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				String answer = "";
				for (int i = 0; i < result.getString("Answer").length(); i++) {
					answer += String.format("(%s) ", result.getString("Answer").charAt(i));
				}
				keyArea.setText(String.format("* 正解: %s\n  答對率: %d%s\n\n* 詳解: \n%s\n\n* 你的筆記:", answer,
						result.getInt("Degree"), "%", result.getString("AnswerKey")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		noteArea = new JTextArea();
		noteArea.setEditable(true);
		noteArea.setLineWrap(true);
		noteArea.setBackground(Color.decode("#F8EFD4"));
		noteArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		noteArea.setText("");
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Note FROM " + test + " WHERE Number = " + number;
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				noteArea.append(result.getString(1));
			}
			result.close();
		} catch (Exception e) {
			System.out.printf("create note comp %s\n", e.getMessage());
		}

		Box box = Box.createVerticalBox();
		box.add(keyArea);
		box.add(noteArea);

		JScrollPane scrollPane = new JScrollPane(box);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#F8EFD4")));
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		scrollPane.setPreferredSize(new Dimension(1000, 200));

		notePanel = new JPanel();
		notePanel.setBackground(Color.decode("#F8EFD4"));
		notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.X_AXIS));
		notePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		notePanel.add(scrollPane);
		notePanel.add(Box.createRigidArea(new Dimension(10, 0)));
	}

	public void repaintPanel() {
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
		createNoteComp();
		setLayout();
		validate();
		repaint();
	}

	public void updateNote() {
		try {
			if (!noteArea.getText().equals("")) {
				Statement stat = conn.createStatement();
				String query = "UPDATE " + test + " SET Note = '" + noteArea.getText() + "' WHERE Number = " + number;
				stat.execute(query);
			}
		} catch (Exception e) {
			System.out.printf("update note: %s\n", e.getMessage());
		}
	}

	public void createDeleteButton() {
		deleteNoteBtn = new JButton("<html><u>Delete Note</u></html>");
		deleteNoteBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		deleteNoteBtn.setForeground(Color.gray);
		deleteNoteBtn.setBorder(null);
		deleteNoteBtn.setContentAreaFilled(false);
		
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				noteArea.setText("");
				try {
					Statement stat = conn.createStatement();
					String query = "UPDATE " + test + " SET Note = " + null + " WHERE Number = " + number;
					stat.execute(query);
				} catch (Exception ex) {
					System.out.printf("deleteNoteBtn listener: %s\n", ex.getMessage());
				}
			}
		}
		ClickListener listener = new  ClickListener();
		deleteNoteBtn.addActionListener(listener);
	}

	public void createBackButton() {
		ImageIcon backIcon = new ImageIcon(
				new ImageIcon("images/back.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.setVisible(false);

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					updateNote();

					Statement stat = conn.createStatement();
					String query2 = "SELECT Number FROM " + test;
					ResultSet result = stat.executeQuery(query2);
					while (result.next()) {
						if (result.getInt(1) == number) {
							result.previous();
							updateNumber(result.getInt(1));
							repaintPanel();
						}
					}
					result.close();
				} catch (Exception exception) {
					System.out.printf("back button listener %s\n", exception.getMessage());
				}
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
				try {
					updateNote();

					Statement stat = conn.createStatement();
					String query2 = "SELECT Number FROM " + test;
					ResultSet result = stat.executeQuery(query2);
					while (result.next()) {
						if (result.getInt(1) == number) {
							result.next();
							updateNumber(result.getInt(1));
							repaintPanel();
						}
					}
					result.close();
				} catch (Exception exception) {
					System.out.printf("next button listener %s\n", exception.getMessage());
				}
			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createToolPanel() {
		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(25, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(moreButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(answerPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(deleteNoteBtn);
		add(notePanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}
}
