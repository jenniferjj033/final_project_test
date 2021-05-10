import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class QuestionPanel extends JPanel{
	static final long startTime = System.currentTimeMillis();
	private JScrollPane scrollPane;
	private JPanel qToolUpPanel;
	private JPanel qToolDownPanel;
	private JPanel questionPanel;
	private JPanel answerPanel;
	private JButton otherQButton;
	private JButton startButton;
	private JButton stopButton;
	private JLabel timeLabel;
/*	private long elapsedTime;
	private long elapsedSeconds;
	private long secondsDisplay;
	private long elapsedMinutes;*/
	static int interval; 
	static Timer timer;
	private JButton backButton;
	private JButton finishButton;
	private JButton nextButton;
	private JTextArea questionArea;
	private JButton markButton;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private JButton aButton;
	private JButton bButton;
	private JButton cButton;
	private JButton dButton;
	private JButton[] xButtons;
	private int number = 1;
	
	
	
	public QuestionPanel() {
		createComp();
		createQToolButton();
		createStartButton();
		createStopButton();
		createBackButton();
		createNextButton();
		createFinishButton();
		createMarkButton();
		createAnswerButtons();
		createPanel();
	}

	public JPanel getQFuctionPanel() {
		return this.qToolUpPanel;
	}

	public JButton getFinishButton() {
		return this.finishButton;
	}

	public void createComp() {
		
		
		answerLabel = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			answerLabel[i] = new JLabel();
			answerLabel[i].setBorder(null);
		}
		answerLabel[0].setText("A.");
		answerLabel[1].setText("B.");
		answerLabel[2].setText("C.");
		answerLabel[3].setText("D.");
		
		xButtons = new JButton[4];
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 4; i++) {
					if (xButtons[i].isEnabled()) {
						xButtons[i].setBorder(new LineBorder(Color.red));
						xButtons[i].setForeground(Color.red);
						return;
					} else {
						xButtons[i].setBorder(null);
						return;
					}
				}
			}
		}
		ClickListener listener = new ClickListener();
		for (int i = 0; i < 4; i++) {
			xButtons[i] = new JButton("X");
			xButtons[i].setContentAreaFilled(false);
			xButtons[i].setPreferredSize(new Dimension(20, 20));
			xButtons[i].setBorder(null);
			xButtons[i].addActionListener(listener);
		}
		
		timeLabel = new JLabel();
		timeLabel.setText("Time:");
		// timeLabel update time
		questionArea = new JTextArea(300, 50);
		scrollPane = new JScrollPane(questionArea);
		// update questionArea from database
		
	}

	public void createQToolButton() {
		otherQButton = new JButton("#");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		otherQButton.addActionListener(listener);
	}
	
	public void createStartButton() {
		startButton = new JButton("|>");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		startButton.addActionListener(listener);
	}

	public void createStopButton() {
		stopButton = new JButton("||");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		stopButton.addActionListener(listener);
	}

	public void createBackButton() {
		backButton = new JButton("<--");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				remove(answerPanel);
				remove(qToolDownPanel);
				number--;
				
				createAnswerButtons();
				add(qToolDownPanel);
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createNextButton() {
		nextButton = new JButton("-->");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				remove(answerPanel);
				remove(qToolDownPanel);
				number++;
				
				createAnswerButtons();
				add(qToolDownPanel);
				
			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createFinishButton() {
		finishButton = new JButton("Done");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		finishButton.addActionListener(listener);
	}

	public void createMarkButton() {
		markButton = new JButton("?");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		markButton.addActionListener(listener);
	}
	
	public void createAnswerButtons() {
		try {
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "MG05";
		String url = server + database;
		String username = "MG05";
		String password = "9mMuzQ";
		Connection conn = DriverManager.getConnection(url, username, password);
		Statement statA = conn.createStatement();
		Statement statB = conn.createStatement();
		Statement statC = conn.createStatement();
		Statement statD = conn.createStatement();
		Statement statQ = conn.createStatement();
		
		String queryA =  "SELECT A FROM Society WHERE Number = "+number;
		statA.execute(queryA);
		ResultSet resultA = statA.getResultSet();
		resultA.next();
		
		String queryB =  "SELECT B FROM Society WHERE Number = "+number;
		statB.execute(queryB);
		ResultSet resultB = statB.getResultSet();
		resultB.next();
		
		String queryC =  "SELECT C FROM Society WHERE Number = "+number;
		statC.execute(queryC);
		ResultSet resultC = statC.getResultSet();
		resultC.next();
		
		String queryD =  "SELECT D FROM Society WHERE Number = "+number;
		statD.execute(queryD);
		ResultSet resultD = statD.getResultSet();
		resultD.next();

		
		answerButtons = new ArrayList<JButton>();
		aButton = new JButton(resultA.getString(1));
		bButton = new JButton(resultB.getString(1));
		cButton = new JButton(resultC.getString(1));
		dButton = new JButton(resultD.getString(1));
		
		answerButtons.add(aButton);
		answerButtons.add(bButton);
		answerButtons.add(cButton);
		answerButtons.add(dButton);
		
		answerPanel = new JPanel(new GridLayout(4, 3));
		for (int i = 0; i < 4; i++) {
			answerPanel.add(answerLabel[i]);
			answerPanel.add(answerButtons.get(i));
			answerPanel.add(xButtons[i]);
		}
		add(answerPanel);
		
		String Question =  "SELECT Question FROM Society WHERE Number = "+number;
		statQ.execute(Question);
		ResultSet resultQ = statQ.getResultSet();
		resultQ.next();
		
		questionArea.setText(resultQ.getString(1));
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for (JButton b : answerButtons) {
					if (b.isEnabled()) {
						b.setBorder(new LineBorder(Color.gray));
						return;
					} else {
						b.setBorder(null);
						return;
					}
				}
			}
		}
		ClickListener listener = new ClickListener();
		for (JButton b : answerButtons) {
			b.setContentAreaFilled(false);
			b.setPreferredSize(new Dimension(300, 50));
			b.setBorder(null);
			b.addActionListener(listener);
		}
		}
		catch(Exception e) {
			
		}
	}

	public void createPanel() {
		qToolUpPanel = new JPanel();
		qToolUpPanel.add(otherQButton);
		qToolUpPanel.add(startButton);
		qToolUpPanel.add(stopButton);
		qToolUpPanel.add(timeLabel);
		
		questionPanel = new JPanel();
		questionPanel.add(scrollPane);
		//questionPanel.add(questionArea);
		questionPanel.add(markButton);
		
		answerPanel = new JPanel(new GridLayout(4, 3));
		for (int i = 0; i < 4; i++) {
			answerPanel.add(answerLabel[i]);
			answerPanel.add(answerButtons.get(i));
			answerPanel.add(xButtons[i]);
		}
		
		qToolDownPanel = new JPanel();
		qToolDownPanel.add(backButton);
		qToolDownPanel.add(finishButton);
		qToolDownPanel.add(nextButton);
		
		setLayout(new GridLayout(4, 1));
		add(qToolUpPanel);
		add(questionPanel);
		add(answerPanel);
		add(qToolDownPanel);
	}
}
		
	
