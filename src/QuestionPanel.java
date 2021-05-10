import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.*;

public class QuestionPanel extends JPanel {
	static final long startTime = System.currentTimeMillis();
	private JPanel qToolUpPanel;
	private JPanel qToolDownPanel;
	private JPanel questionPanel;
	private JPanel answerPanel;
	private JPanel panel;
	private boolean change;
	private JButton moreButton;
	private JButton playButton;
	private JButton pauseButton;
	private JLabel timeLabel;
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

	public QuestionPanel(JPanel panel) {
		change = false;
		this.panel = panel;

		createComp();
		createMoreButton();
		createPlayButton();
		createPauseButton();
		createBackButton();
		createFinishButton();
		createNextButton();
		createQuestionArea();
		createMarkButton();
		createAnswerButtons();
		createPanel();
	}

	public JPanel getQToolUpPanel() {
		return this.qToolUpPanel;
	}

	public JButton getFinishButton() {
		return this.finishButton;
	}

	public boolean getChange() {
		return this.change;
	}

	public void createComp() {
		ImageIcon timerIcon = new ImageIcon(
				new ImageIcon("images/timer3.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		timeLabel = new JLabel();
		timeLabel.setIcon(timerIcon);
		timeLabel.setVerticalTextPosition(SwingConstants.CENTER);
		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		timeLabel.setText("000:00");
		// timeLabel update time

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

			}
		}
		ClickListener listener = new ClickListener();
		ImageIcon xIcon = new ImageIcon(
				new ImageIcon("images/x.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		for (int i = 0; i < 4; i++) {
			// xButtons[i] = new RoundButton("X", Color.decode("#F8EFD4"),
			// Color.decode("#D21F3C"), Color.decode("#FFB9BB"));
			xButtons[i] = new JButton(); // "x"
			xButtons[i].setIcon(xIcon);
			xButtons[i].setBorder(null);
			xButtons[i].setContentAreaFilled(false);
			xButtons[i].addActionListener(listener);
		}
	}

	public void createMoreButton() {
		ImageIcon moreIcon = new ImageIcon(
				new ImageIcon("images/more.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		moreButton = new JButton(); // "#"
		moreButton.setIcon(moreIcon);
		moreButton.setBorder(null);
		moreButton.setOpaque(false);
		moreButton.setContentAreaFilled(false);
		CardLayout cardLayout = (CardLayout) (panel.getLayout());
		class ClickListener implements ActionListener {
			int click = 0;

			public void actionPerformed(ActionEvent e) {
				click++;
				if (click % 2 == 0) {
					change = false;
					JOptionPane.showMessageDialog(null, "more");
					cardLayout.show(panel, "3");
				} else {
					change = true;
					cardLayout.show(panel, "4");
				}
			}
		}
		ClickListener listener = new ClickListener();
		moreButton.addActionListener(listener);
	}

	public void createPlayButton() {
		ImageIcon playIcon = new ImageIcon(
				new ImageIcon("images/play.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		playButton = new JButton(); // "|>"
		playButton.setIcon(playIcon);
		playButton.setBorder(null);
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		playButton.addActionListener(listener);
	}

	public void createPauseButton() {
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/pause.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		pauseButton = new JButton(); // "||"
		pauseButton.setIcon(pauseIcon);
		pauseButton.setBorder(null);
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		pauseButton.addActionListener(listener);
	}

	public void createBackButton() {
		ImageIcon backIcon = new ImageIcon(
				new ImageIcon("images/back.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
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

	public void createFinishButton() {
		ImageIcon finishIcon = new ImageIcon(
				new ImageIcon("images/finish.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishButton = new JButton(); // "Fin"
		finishButton.setIcon(finishIcon);
		finishButton.setBorder(null);
		finishButton.setOpaque(false);
		finishButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

			}
		}
		ClickListener listener = new ClickListener();
		finishButton.addActionListener(listener);
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

	public void createQuestionArea() {
		questionArea = new JTextArea();
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);
		// update questionArea from database

	}

	public void createMarkButton() {
		ImageIcon markIcon = new ImageIcon(
				new ImageIcon("images/mark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setIcon(markIcon);
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
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

			String queryA = "SELECT A FROM Society WHERE Number = " + number;
			statA.execute(queryA);
			ResultSet resultA = statA.getResultSet();
			resultA.next();

			String queryB = "SELECT B FROM Society WHERE Number = " + number;
			statB.execute(queryB);
			ResultSet resultB = statB.getResultSet();
			resultB.next();

			String queryC = "SELECT C FROM Society WHERE Number = " + number;
			statC.execute(queryC);
			ResultSet resultC = statC.getResultSet();
			resultC.next();

			String queryD = "SELECT D FROM Society WHERE Number = " + number;
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

			answerPanel = new JPanel(new GridBagLayout());
			answerPanel.setBackground(Color.decode("#F8EFD4"));
			GridBagConstraints gbc;
			for (int i = 0; i < 4; i++) {
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
				gbc.ipadx = 660;
				gbc.fill = GridBagConstraints.BOTH;
				answerPanel.add(answerButtons.get(i), gbc);

				gbc = new GridBagConstraints();
				gbc.gridx = 2;
				gbc.gridy = i;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				answerPanel.add(xButtons[i], gbc);
			}

			String Question = "SELECT Question FROM Society WHERE Number = " + number;
			statQ.execute(Question);
			ResultSet resultQ = statQ.getResultSet();
			resultQ.next();

			questionArea.setText(resultQ.getString(1));
			class ClickListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					
				}
			}
			ClickListener listener = new ClickListener();
			for (JButton b : answerButtons) {
				b.setContentAreaFilled(false);
				b.setPreferredSize(new Dimension(200, 100));
				b.setBorder(null);
				b.addActionListener(listener);
			}
		} catch (Exception e) {

		}
	}

	public void createPanel() {

		qToolUpPanel = new JPanel();
		qToolUpPanel.setBackground(Color.decode("#F8EFD4"));
		qToolUpPanel.setLayout(new BoxLayout(qToolUpPanel, BoxLayout.X_AXIS));
		qToolUpPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		qToolUpPanel.add(moreButton);
		qToolUpPanel.add(Box.createGlue());
		qToolUpPanel.add(playButton);
		qToolUpPanel.add(Box.createRigidArea(new Dimension(15, 0)));
		qToolUpPanel.add(pauseButton);
		qToolUpPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		qToolUpPanel.add(timeLabel);
		qToolUpPanel.add(Box.createRigidArea(new Dimension(30, 0)));

		JScrollPane scrollPane = new JScrollPane(questionArea);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#EC9706")));
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

		answerPanel = new JPanel(new GridBagLayout());
		answerPanel.setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc;
		for (int i = 0; i < 4; i++) {
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
			gbc.ipadx = 660;
			gbc.fill = GridBagConstraints.BOTH;
			answerPanel.add(answerButtons.get(i), gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 2;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			answerPanel.add(xButtons[i], gbc);
		}

		qToolDownPanel = new JPanel();
		qToolDownPanel.setBackground(Color.decode("#F8EFD4"));
		qToolDownPanel.setLayout(new BoxLayout(qToolDownPanel, BoxLayout.X_AXIS));
		qToolDownPanel.add(Box.createRigidArea(new Dimension(25, 0)));
		qToolDownPanel.add(backButton);
		qToolDownPanel.add(Box.createGlue());
		qToolDownPanel.add(finishButton);
		qToolDownPanel.add(Box.createGlue());
		qToolDownPanel.add(nextButton);
		qToolDownPanel.add(Box.createRigidArea(new Dimension(30, 0)));

		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(qToolUpPanel);
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(answerPanel);
		add(Box.createGlue());
		add(qToolDownPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));

	}
}
