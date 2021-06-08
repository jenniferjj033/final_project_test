import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Viewer {
	
	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		CardLayout cardLayout = new CardLayout();
		JPanel card = new JPanel(cardLayout);
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.addButtonListener(card);
		SignUpPanel signUpPanel = new SignUpPanel();
		signUpPanel.addLoginListener(card);
		signUpPanel.addSignUpListener(card);
		LoginPanel loginPanel = new LoginPanel();
		loginPanel.addLoginListener(card);
		loginPanel.addSignUpListener(card);
		HomePanel homePanel = new HomePanel();
		homePanel.addUserListener(card);
		homePanel.addTestListener(card);
		homePanel.addNoteListener(card);
		// addButtonListener

		RangePanel rangePanel = new RangePanel();
		InstructionPanel instructionPanel = new InstructionPanel();
		String test = "TestM"; // «Ý­×
		
		ToAnswerPanel toAnsPanel = new ToAnswerPanel();
		QuestionPanel questionPanel = new QuestionPanel(test);
		
		AnswerKeyPanel answerKeyPanel = new AnswerKeyPanel(test);
		AnswerListPanel ansListPanel = new AnswerListPanel(test);
		
		AnswerKeyPanel noteKeyPanel = new AnswerKeyPanel(test);
		AnswerListPanel noteQPanel = new AnswerListPanel(test);
		SubjectPanel subjectPanel = new SubjectPanel();
		
		rangePanel.createStartBtn(card);
		rangePanel.addBackListener(card);
		instructionPanel.addButtonListener(card, questionPanel);
		questionPanel.getQTool().addMoreButtonListener(questionPanel);
		questionPanel.getQTool().addFinishButtonListener(card, questionPanel, toAnsPanel);
		questionPanel.addQNumListener(card, questionPanel);
		toAnsPanel.addAnswerListener(card);
		
		answerKeyPanel.getQTool().addMoreButtonListener(card);
		ansListPanel.addQuestionButtonListener(card, answerKeyPanel);
		ansListPanel.getQTool().addHomeButtonListener(card);
		
		noteKeyPanel.getQTool().addMoreButtonListener(card);
		noteQPanel.addQuestionButtonListener(card, noteKeyPanel);
		noteQPanel.addBackListener(card);
		noteQPanel.getQTool().addHomeButtonListener(card);
		subjectPanel.addButtonListener(card, noteQPanel);
		subjectPanel.getQTool().addHomeButtonListener(card);

		card.add(titlePanel, "titlePanel");
		card.add(loginPanel, "loginPanel");
		card.add(signUpPanel, "signUpPanel");
		card.add(homePanel, "homePanel");

		card.add(rangePanel, "rangePanel");
		card.add(instructionPanel, "instructionPanel");
		card.add(questionPanel, "questionPanel");
		card.add(toAnsPanel, "toAnsPanel");

		card.add(ansListPanel, "ansListPanel");
		card.add(answerKeyPanel, "answerKeyPanel");
		
		card.add(subjectPanel, "subjectPanel");
		card.add(noteQPanel, "noteQPanel");
		card.add(noteKeyPanel, "noteKeyPanel");

		cardLayout.show(card, "titlePanel");
		frame.add(card);
		frame.setTitle("College Entrance Examination");
		//frame.setSize(1060, 650);
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
}
