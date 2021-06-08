import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import style.BubbleBorder;

public class InstructionPanel extends JPanel {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;

	private JButton startButton, replaceButton;
	private JLabel yearANDtype, subject, notice,time,count,first,second,way;
	private JPanel titlePanel, noticePanel;

	public InstructionPanel() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		creaLabelComp();
		creaButton();
		creaTotalPanel();

	}

	public void creaLabelComp() {
		yearANDtype = new JLabel("公民考科 - 108學年度指定考試科目");
		subject = new JLabel("公民考科");
		
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.decode("#F8EFD4"));
		titlePanel.add(yearANDtype);
		titlePanel.add(subject);

		notice=new JLabel("<html><body><br></br><br>-作答注意事項-</br></body></html>",SwingConstants.CENTER);
		notice.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		time=new JLabel("<html><body><br>考試時間： 80分鐘</br></body></html>",SwingConstants.CENTER);
		time.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		count=new JLabel("題型題數：",SwingConstants.CENTER);
		count.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		first=new JLabel("-第壹部分： 單選題共38題",SwingConstants.CENTER);
		first.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		second=new JLabel("-第貳部分:  複選題共12題",SwingConstants.CENTER);
		second.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		way=new JLabel("作答方式:  略",SwingConstants.CENTER);
		way.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Font font=new Font("微軟正黑體",Font.BOLD,30);
		yearANDtype.setFont(font);
		Color color=new Color(139,69,19);
		yearANDtype.setForeground(color);
		Border empty = BorderFactory.createEmptyBorder();
		yearANDtype.setBorder(empty);
		yearANDtype.setBackground(Color.decode("#F8EFD4"));
		
		subject.setFont(font);
		subject.setBackground(new Color(255,239,213));
		subject.setForeground(color);
		subject.setBackground(Color.decode("#F8EFD4"));
		
		Font font1=new Font("微軟正黑體",Font.BOLD,25);
		notice.setFont(font1);
		notice.setBackground(new Color(255,239,213));
		notice.setForeground(color);
		notice.setBackground(Color.decode("#F8EFD4"));
		
		time.setFont(font1);
		time.setBackground(new Color(255,239,213));
		time.setForeground(color);
		time.setBackground(Color.decode("#F8EFD4"));
		
		count.setFont(font1);
		count.setBackground(new Color(255,239,213));
		count.setForeground(color);
		count.setBackground(Color.decode("#F8EFD4"));
		
		first.setFont(font1);
		first.setBackground(new Color(255,239,213));
		first.setForeground(color);
		first.setBackground(Color.decode("#F8EFD4"));
		
		second.setFont(font1);
		second.setBackground(new Color(255,239,213));
		second.setForeground(color);
		second.setBackground(Color.decode("#F8EFD4"));
		
		way.setFont(font1);
		way.setBackground(new Color(255,239,213));
		way.setForeground(color);
		way.setBackground(Color.decode("#F8EFD4"));
		
		noticePanel = new JPanel();
		noticePanel.setLayout(new BoxLayout(noticePanel, BoxLayout.Y_AXIS));
		noticePanel.add(Box.createGlue());
		noticePanel.add(notice);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		noticePanel.add(time);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		noticePanel.add(count);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		noticePanel.add(first);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		noticePanel.add(second);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		noticePanel.add(way);
		noticePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		noticePanel.setBackground(Color.decode("#F8EFD4"));
		noticePanel.setPreferredSize(new Dimension(500, 500));
	}

	public void creaButton() {
		startButton = new JButton("Start");
		Font font=new Font("微軟正黑體",Font.BOLD,20);
		startButton.setFont(font);
		startButton.setContentAreaFilled(false);
		startButton.setForeground(new Color(139, 69, 19));
		startButton.setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 30, 0));
		startButton.setPreferredSize(new Dimension(120, 30));
		
		replaceButton = new JButton("Back");
		replaceButton.setFont(font);
		replaceButton.setContentAreaFilled(false);
		replaceButton.setForeground(new Color(139, 69, 19));
		replaceButton.setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 30, 0));
		replaceButton.setPreferredSize(new Dimension(120, 30));

	}

	public void addButtonListener(JPanel panel, QuestionPanel question) {
		class ReplaceListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout) (panel.getLayout());
				card.show(panel, "rangePanel");
			}
		}
		ActionListener listenerR = new ReplaceListener();
		replaceButton.addActionListener(listenerR);
		
		class StartListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout) (panel.getLayout());
				question.getQTool().setTime(question.getAnswer().getNumbers().size());
				card.show(panel, "questionPanel");
			}
		}
		ActionListener listenerS = new StartListener();
		startButton.addActionListener(listenerS);
	}
	

	public void creaTotalPanel() {
		JPanel down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		down_toolPanel.add(replaceButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(startButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(70, 0)));

		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(50, 0, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(titlePanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		add(noticePanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(down_toolPanel, gbc);
		
	}
}
