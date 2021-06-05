import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.SystemColor.*;
import java.awt.event.*;
import java.awt.*;

public class AllSubjectPanel extends JPanel {
	private int FIELD_WIDTH = 10;
	private JPanel panel;
	private JPanel FunctionPanel, subjectFirstPanel;
	private JLabel titleLabel;
	private JButton backbutton, homeButton, subjectButtonCh, subjectButtonEn,
			subjectButtonMa, subjectButtonSo, subjectButtonSc;

	public AllSubjectPanel(JPanel panel) {
		this.panel = panel;
		createChineseButton();
		createMathButton();
		createEnglishButton();
		createSocietyButton();
		createScienceButton();
		createBackButton();
		createSubjectPanel();
		getsubjectButtonSo();
		getsubjectButtonSc();
	}

	public void createChineseButton() {
		subjectButtonCh = new JButton("  國文  ");
		// subjectButtonEn.setText();
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "4");
			}
		}
		ActionListener listener = new ClickListener();
		subjectButtonCh.addActionListener(listener);
	}

	public void createEnglishButton() {
		subjectButtonEn = new JButton();
		subjectButtonEn.setText("  英文  ");
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "5");
			}
		}
		ActionListener listener = new ClickListener();
		subjectButtonEn.addActionListener(listener);
	}

	public void createMathButton() {
		subjectButtonMa = new JButton();
		subjectButtonMa.setText("  數學  ");
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "6");
			}
		}
		ActionListener listener = new ClickListener();
		subjectButtonMa.addActionListener(listener);
	}

	public void createSocietyButton() {
		subjectButtonSo = new JButton();
		subjectButtonSo.setText("  社會  ");
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "2");
			}
		}
		ActionListener listener = new ClickListener();
		subjectButtonSo.addActionListener(listener);
	}

	public void createScienceButton() {
		subjectButtonSc = new JButton();
		subjectButtonSc.setText("  自然  ");
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "3");
			}
		}
		ActionListener listener = new ClickListener();
		subjectButtonSc.addActionListener(listener);
	}

	public void createBackButton() {
		backbutton = new JButton();
		ImageIcon back = new ImageIcon("back.png");
		backbutton.setIcon(back);
		CardLayout card = (CardLayout) panel.getLayout();
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				card.show(panel, "1");
			}
		}
		ActionListener listener = new ClickListener();
		backbutton.addActionListener(listener);
	}

	public void createSubjectPanel() {

		subjectFirstPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weighty = 1.0;
		Dimension preferredSize = new Dimension(300, 100);
		Font font = new Font("微軟正黑體", Font.PLAIN, 55);
		subjectButtonCh.setFont(font);
		subjectButtonCh.setPreferredSize(preferredSize);
		subjectButtonCh.setBackground(new Color(255, 239, 213));
		// subjectButtonCh.setContentAreaFilled(true);
		subjectButtonCh.setBorder(new LineBorder(Color.ORANGE, 5));
		gbc1.insets = new Insets(10, 20, 50, 40);
		// subjectButtonCh.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		// setFillForegroundColor((short) 13);// 設背景色
		// setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// subjectButtonCh.Clicked("#F5DEB3");
		// .setBounds(20,20,150,100);
		// subjectButtonCh.setSize(1000, 1000);
		subjectFirstPanel.add(subjectButtonCh, gbc1);

		// gbc1.setBackground(Color.decode("#F8EFD4"));
		// gbc1.setContentAreaFilled(true);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 2;
		gbc1.gridy = 0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(10, 40, 50, 50);
		subjectButtonMa.setFont(font);
		subjectButtonMa.setPreferredSize(preferredSize);
		subjectButtonMa.setBackground(new Color(255, 239, 213));
		subjectButtonMa.setContentAreaFilled(true);
		subjectButtonMa.setBorder(new LineBorder(Color.ORANGE, 5));
		subjectFirstPanel.add(subjectButtonMa, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 4;
		gbc1.gridy = 0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(10, 30, 50, 110);
		subjectButtonEn.setFont(font);
		subjectButtonEn.setPreferredSize(preferredSize);
		subjectButtonEn.setBackground(new Color(255, 239, 213));
		subjectButtonEn.setContentAreaFilled(true);
		subjectButtonEn.setBorder(new LineBorder(Color.ORANGE, 5));
		subjectFirstPanel.add(subjectButtonEn, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(80, 50, 30, 90);
		// gbc1.insets = new Insets(0, 0, 20, 50);
		subjectButtonSo.setFont(font);
		subjectButtonSo.setPreferredSize(preferredSize);
		subjectButtonSo.setBackground(new Color(255, 239, 213));
		subjectButtonSo.setContentAreaFilled(true);
		subjectButtonSo.setBorder(new LineBorder(Color.ORANGE, 5));
		subjectFirstPanel.add(subjectButtonSo, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 3;
		gbc1.gridy = 1;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(80, 50, 30, 90);
		subjectButtonSc.setFont(font);
		subjectButtonSc.setPreferredSize(preferredSize);
		subjectButtonSc.setBackground(new Color(255, 239, 213));
		subjectButtonSc.setContentAreaFilled(true);
		subjectButtonSc.setBorder(new LineBorder(Color.ORANGE, 5));
		subjectFirstPanel.add(subjectButtonSc, gbc1);
		subjectFirstPanel.setBackground(Color.decode("#F8EFD4"));

		setLayout(new GridBagLayout());
		ImageIcon image = new ImageIcon("note.png");
		titleLabel = new JLabel("     Note     ", SwingConstants.LEFT);

		// noteLabel.setBounds嚗�140,20,80,25嚗�;
		// noteLabel=new JLabel(image, SwingConstants.LEFT);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 1;
		// gbc2.weightx=1.0;
		// gbc2.weighty=1.0;
		gbc2.insets = new Insets(0, 0, 0, -150);
		Dimension preferredsize = new Dimension(1000, 80);
		Font fontfont = new Font("PERMANENT MARKER", Font.ITALIC, 50);
		titleLabel.setFont(fontfont);
		Color color = new Color(139, 69, 19);
		titleLabel.setForeground(color);
		titleLabel.setPreferredSize(preferredsize);
		titleLabel.setBorder(BorderFactory.createLineBorder(Color.yellow, 7));
		gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
		add(titleLabel, gbc2);

		image = new ImageIcon("house.png");
		homeButton = new JButton(image);
		gbc2 = new GridBagConstraints();
		gbc2.gridx = 2;
		gbc2.gridy = 2;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 0, 110);
		gbc2.anchor = GridBagConstraints.PAGE_END;
		homeButton.setContentAreaFilled(false);
		homeButton.setFocusPainted(false);
		Border empty = BorderFactory.createEmptyBorder();
		homeButton.setBorder(empty);
		add(homeButton, gbc2);

		backbutton = new JButton();
		ImageIcon back = new ImageIcon("back.png");
		backbutton.setIcon(back);
		gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		gbc2.gridwidth = 1 / 2;
		gbc2.gridheight = 1 / 2;
		gbc2.anchor = GridBagConstraints.LAST_LINE_START;
		backbutton.setContentAreaFilled(false);
		backbutton.setFocusPainted(false);
		backbutton.setBorder(empty);
		add(backbutton, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 2;
		gbc2.gridy = 2;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.anchor = GridBagConstraints.CENTER;
		// gbc2.fill=GridBagConstraints.HORIZONTAL;
		add(subjectFirstPanel, gbc2);

		setBackground(Color.decode("#F8EFD4"));
		// setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

	}

	public JButton getsubjectButtonSo() {
		return subjectButtonSo;
	}

	public JButton getsubjectButtonSc() {
		return subjectButtonSc;
	}

	public void addActionListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

}
