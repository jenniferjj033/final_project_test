import javax.swing.*;
import javax.swing.border.LineBorder;

import style.*;

import java.awt.event.*;
import java.awt.*;

public class SubjectPanel extends JPanel {
	private JPanel subjectPanel;
	private JLabel titleLabel;
	private JButton homeButton, backButton;
	private JButton subjectButtonChi, subjectButtonEn, subjectButtonMa, subjectButtonSo, subjectButtonSc,
			subjectButtonBi, subjectButtonChe, subjectButtonPh, subjectButtonGe, subjectButtonHi, subjectButtonCi;
	private QTool qTool;

	public SubjectPanel() {
		createTitleLabel();
		createSubjectBtn();
		createBackButton();
		addSBtnListener();
		createBtnPanel();
		setLayout();
	}
	
	public QTool getQTool() {
		return this.qTool;
	}

	public void createTitleLabel() {
		ImageIcon noteIcon = new ImageIcon(
				new ImageIcon("images/note.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		titleLabel = new JLabel("Note", noteIcon, SwingConstants.LEFT);
		titleLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 40));
		titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		titleLabel.setForeground(new Color(139, 69, 19));
	}

	public void createSubjectBtn() {
		subjectButtonChi = new RoundButton("國文", 3, 3);
		subjectButtonEn = new RoundButton("英文", 3, 3);
		subjectButtonMa = new RoundButton("數學", 3, 3);
		subjectButtonSo = new RoundButton("社會", 3, 3);
		subjectButtonSc = new RoundButton("自然", 3, 3);
		subjectButtonHi = new RoundButton("歷史", 3, 3);
		subjectButtonGe = new RoundButton("地理", 3, 3);
		subjectButtonCi = new RoundButton("公民", 3, 3);
		subjectButtonPh = new RoundButton("物理", 3, 3);
		subjectButtonChe = new RoundButton("化學", 3, 3);
		subjectButtonBi = new RoundButton("生物", 3, 3);
		JButton[] subjectBtns = { subjectButtonChi, subjectButtonEn, subjectButtonMa, subjectButtonSo, subjectButtonSc,
				subjectButtonHi, subjectButtonGe, subjectButtonCi, subjectButtonPh, subjectButtonChe, subjectButtonBi };

		for (int i = 0; i < 11; i++) {
			subjectBtns[i].setFont(new Font("微軟正黑體", Font.PLAIN, 40));
			subjectBtns[i].setPreferredSize(new Dimension(120, 120));
		}
	}

	public void createBackButton() {
		ImageIcon backIcon = new ImageIcon(
				new ImageIcon("images/back1.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.setVisible(false);

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setVisible(false);
				removeAll();
				createBtnPanel();
				setLayout();
				validate();
				repaint();
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createBtnPanel() {
		subjectPanel = new JPanel(new GridBagLayout());
		subjectPanel.setBackground(Color.decode("#F8EFD4"));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonChi, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonMa, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonEn, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 0, 0);
		subjectPanel.add(subjectButtonSo, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 0, 0);
		subjectPanel.add(subjectButtonSc, gbc);
	}

	public void addSBtnListener() {
		class SocietyBtnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setVisible(true);
				removeAll();
				createSocietyPanel();
				setLayout();
				validate();
				repaint();
			}
		}
		SocietyBtnListener listenerSo = new SocietyBtnListener();
		subjectButtonSo.addActionListener(listenerSo);

		class ScienceBtnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setVisible(true);
				removeAll();
				createSciencePanel();
				setLayout();
				validate();
				repaint();
			}
		}
		ScienceBtnListener listenerSc = new ScienceBtnListener();
		subjectButtonSc.addActionListener(listenerSc);
	}

	public void createSocietyPanel() {
		subjectPanel = new JPanel(new GridBagLayout());
		subjectPanel.setBackground(Color.decode("#F8EFD4"));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 30, 30, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonGe, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonHi, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonCi, gbc);
	}

	public void createSciencePanel() {
		subjectPanel = new JPanel(new GridBagLayout());
		subjectPanel.setBackground(Color.decode("#F8EFD4"));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonPh, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonChe, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 30, 0, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonBi, gbc);
	}

	public void setLayout() {
		qTool = new QTool();
		homeButton = qTool.createHomeButton();

		JPanel down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(homeButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(new JLabel());
		down_toolPanel.add(Box.createRigidArea(new Dimension(50, 0)));

		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		// gbc.insets = new Insets(15, 0, 0, 0);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(titleLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 30, 0, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		add(subjectPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(down_toolPanel, gbc);
	}
	
	public void addButtonListener(JPanel panel, AnswerListPanel ansListPanel) {
		CardLayout card = (CardLayout) (panel.getLayout());

		class ChineseListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Chinese");
				card.show(panel, "noteQPanel");
			}
		}
		ChineseListener listenerChi = new ChineseListener();
		subjectButtonChi.addActionListener(listenerChi);
		
		class EnglishListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("English");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerEn = new EnglishListener();
		subjectButtonEn.addActionListener(listenerEn);
		
		class MathListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Math");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerMa = new MathListener();
		subjectButtonMa.addActionListener(listenerMa);
		
		class HistoryListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("History");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerHi = new HistoryListener();
		subjectButtonHi.addActionListener(listenerHi);
		
		class GeographyListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Geography");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerGe = new GeographyListener();
		subjectButtonGe.addActionListener(listenerGe);
		
		class CitizenListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Citizen");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerCi = new CitizenListener();
		subjectButtonCi.addActionListener(listenerCi);
		
		class PhysicListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Physic");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerPh = new PhysicListener();
		subjectButtonPh.addActionListener(listenerPh);
		
		class ChemistryListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Chemistry");
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerChe = new ChemistryListener();
		subjectButtonChe.addActionListener(listenerChe);
		
		class BiologyListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ansListPanel.updateTitle("Biology");
			}
		}
		ActionListener listenerBi = new BiologyListener();
		subjectButtonBi.addActionListener(listenerBi);
	}
}
