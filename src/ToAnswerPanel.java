import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import style.BubbleBorder;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ToAnswerPanel extends JPanel {
	private JLabel CiLabel, Score, Time, Congratulations, ScoreImage, TimeImage;
	private JButton AnswerButton;
	private JPanel AnswerPanel;
	
	public ToAnswerPanel() {
		CiLabel = new JLabel("公民考科 - 108學年度指定考試科目");
		Font font1 = new Font("微軟正黑體", Font.BOLD, 30);
		CiLabel.setFont(font1);
		CiLabel.setBackground(new Color(255, 239, 213));

		Congratulations = new JLabel("Congratulations !!");
		Dimension preferredSize = new Dimension(300, 100);
		Font font = new Font("微軟正黑體", Font.ITALIC, 20);
		Color color = new Color(139, 69, 19);
		Congratulations.setForeground(color);
		Congratulations.setFont(font);
		Congratulations.setPreferredSize(preferredSize);
		Congratulations.setBackground(new Color(255, 239, 213));
		
		AnswerButton = new JButton("Answer");
		AnswerButton.setFont(font1);
		AnswerButton.setPreferredSize(new Dimension(200, 40));
		AnswerButton.setContentAreaFilled(true);
		AnswerButton.setBorder(new BubbleBorder(color, 2, 30, 0));
		AnswerButton.setBackground(Color.decode("#F8EFD4"));
		AnswerButton.setForeground(color);
		
		ImageIcon score = new ImageIcon(
				new ImageIcon("images/score.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		ScoreImage = new JLabel(score);
		
		ImageIcon time = new ImageIcon(
				new ImageIcon("images/time.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		TimeImage = new JLabel(time);
		
	}

	public void addAnswerListener(JPanel panel) {
		class ClickListener implements ActionListener {
			CardLayout card = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				card.show(panel, "ansListPanel");
			}
		}
		ActionListener listener = new ClickListener();
		AnswerButton.addActionListener(listener);
	}
	
	public void updateScore(double score) {
		Score = new JLabel(String.format("%.2f", score));
		Score.setForeground(new Color(139, 69, 19));
		Score.setFont(new Font("微軟正黑體", Font.PLAIN, 40));
		Score.setHorizontalAlignment(SwingConstants.CENTER);
		Score.setPreferredSize(new Dimension(300, 100));
		Score.setBackground(new Color(255, 239, 213));
	}
	
	public void updateTime(String time) {
		Time = new JLabel(String.format("%s", time));
		Time.setForeground(new Color(139, 69, 19));
		Time.setFont(new Font("微軟正黑體", Font.PLAIN, 40));
		Time.setHorizontalAlignment(SwingConstants.CENTER);
		Time.setPreferredSize(new Dimension(300, 100));
		Time.setBackground(new Color(255, 239, 213));
	}

	public void createPanel() {
		AnswerPanel = new JPanel(new GridBagLayout());
		AnswerPanel.setBackground(Color.decode("#F8EFD4"));
		AnswerPanel.setBorder(new BubbleBorder(Color.decode("#8B4513"), 2, 30, 0));
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.anchor = GridBagConstraints.CENTER;
		gbc1.insets = new Insets(0, 10, 0, 0);
		AnswerPanel.add(Congratulations, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		//gbc1.insets = new Insets(0, 0, 0, 0);
		gbc1.anchor = GridBagConstraints.LINE_END;
		AnswerPanel.add(Score, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(50, 0, 0, 0);
		gbc1.anchor = GridBagConstraints.LINE_END;
		AnswerPanel.add(Time, gbc1);

		
		gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(0, 30, 0, 0);
		gbc1.anchor = GridBagConstraints.CENTER;
		AnswerPanel.add(ScoreImage, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(50, 30, 0, 0);
		gbc1.anchor = GridBagConstraints.CENTER;
		AnswerPanel.add(TimeImage, gbc1);
		

		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(50, 0, 0, 0);
		gbc2.anchor = GridBagConstraints.PAGE_START;
		add(CiLabel, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 50, 0);
		gbc2.anchor = GridBagConstraints.CENTER;
		add(AnswerPanel, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 50, 0);
		gbc2.anchor = GridBagConstraints.PAGE_END;
		add(AnswerButton, gbc2);

		
	}
}
