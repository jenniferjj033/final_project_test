import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import style.BubbleBorder;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class goforAnswerPanel extends JPanel {
	private JLabel CiLabel, Score, Time, Congratulations, ScoreImage, TimeImage;
	private JButton AnswerButton;
	private JPanel AnswerPanel;
	private Answer answer;
	private String time;
	private double score;
	
	public goforAnswerPanel(QuestionPanel questionPanel) {
		answer = questionPanel.getAnswer();
		time = questionPanel.getTimeLabel().getText();
		score = answer.getScore();
		createPanel();
	}

	public void addAnswerListener(JPanel panel) {
		class ClickListener implements ActionListener {
			CardLayout card = (CardLayout) (panel.getLayout());
			
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "3"); //?
			}
		}
		ActionListener listener = new ClickListener();
		AnswerButton.addActionListener(listener);
	}
	
	public void createPanel() {
		CiLabel = new JLabel("公民考科-108學年度指定考試科目");
		Dimension preferredSize1 = new Dimension(500, 100);
		Font font1 = new Font("微軟正黑體", Font.BOLD, 30);
		CiLabel.setFont(font1);
		CiLabel.setPreferredSize(preferredSize1);
		CiLabel.setBackground(new Color(255, 239, 213));

		AnswerPanel = new JPanel(new GridBagLayout());
		Congratulations = new JLabel("Congratulations !!");
		GridBagConstraints gbc1 = new GridBagConstraints();
		Dimension preferredSize = new Dimension(300, 100);
		Font font = new Font("微軟正黑體", Font.ITALIC, 20);
		Color color = new Color(139, 69, 19);
		Congratulations.setForeground(color);
		Congratulations.setFont(font);
		Congratulations.setPreferredSize(preferredSize);
		Congratulations.setBackground(new Color(255, 239, 213));
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.anchor = GridBagConstraints.PAGE_START;
		gbc1.insets = new Insets(0, 0, 0, 30);
		AnswerPanel.add(Congratulations, gbc1);

		Score = new JLabel(String.format("%.2f", score));
		gbc1 = new GridBagConstraints();
		Font font2 = new Font("微軟正黑體", Font.PLAIN, 40);
		Score.setForeground(color);
		Score.setFont(font2);
		Score.setPreferredSize(preferredSize);
		Score.setBackground(new Color(255, 239, 213));
		gbc1.insets = new Insets(0, 100, 0, 0);
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.anchor = GridBagConstraints.CENTER;
		AnswerPanel.add(Score, gbc1);

		Time = new JLabel(String.format("%s", time));
		gbc1 = new GridBagConstraints();
		Time.setForeground(color);
		Time.setFont(font2);
		Time.setPreferredSize(preferredSize);
		Time.setBackground(new Color(255, 239, 213));
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(0, 150, 60, 50);
		gbc1.anchor = GridBagConstraints.PAGE_END;
		AnswerPanel.add(Time, gbc1);

		ImageIcon score = new ImageIcon("images/score.png");
		ScoreImage = new JLabel(score);
		gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(0, 70, 0, 0);
		gbc1.anchor = GridBagConstraints.LINE_START;
		AnswerPanel.add(ScoreImage, gbc1);

		ImageIcon time = new ImageIcon("images/time.png");
		TimeImage = new JLabel(time);
		gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(50, 70, 50, 0);
		gbc1.anchor = GridBagConstraints.LAST_LINE_START;
		AnswerPanel.add(TimeImage, gbc1);
		AnswerPanel.setBackground(Color.decode("#F8EFD4"));
		AnswerPanel.setBorder(new BubbleBorder(Color.decode("#524632"), 2, 30, 0));
		
		//ImageIcon approve = new ImageIcon("approve2.png");
		//AnswerPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 7));
		
		AnswerButton = new JButton("Answer");
		AnswerButton.setFont(font1);
		AnswerButton.setPreferredSize(new Dimension(200, 50));
		AnswerButton.setContentAreaFilled(true);
		AnswerButton.setBorder(new BubbleBorder(Color.decode("#524632"), 2, 30, 0));
		AnswerButton.setBackground(Color.decode("#F8EFD4"));
		AnswerButton.setForeground(color);
		//AnswerButton.setFocusPainted(false);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(20, 55, 0, 0);
		gbc2.anchor = GridBagConstraints.PAGE_START;
		add(CiLabel, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 120, 0);
		gbc2.anchor = GridBagConstraints.CENTER;
		add(AnswerPanel, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 100, 0);
		gbc2.anchor = GridBagConstraints.PAGE_END;
		add(AnswerButton, gbc2);
		

		setBackground(Color.decode("#F8EFD4"));

		
	}
}
