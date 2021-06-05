import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Viewer {

	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame();
		CardLayout cardLayout0 = new CardLayout();
		JPanel allCard = new JPanel(cardLayout0);
		CardLayout cardLayout1 = new CardLayout();
		JPanel loginCard = new JPanel(cardLayout1);
		CardLayout cardLayout2 = new CardLayout();
		JPanel questionCard = new JPanel(cardLayout2);
		CardLayout cardLayout3 = new CardLayout();
		JPanel answerCard = new JPanel(cardLayout3);
		CardLayout cardLayout4 = new CardLayout();
		JPanel noteCard = new JPanel(cardLayout4);

		SignUpPanel signUpPanel = new SignUpPanel();
		signUpPanel.addLoginListener(loginCard);
		signUpPanel.addSignUpListener(loginCard);
		LoginPanel loginPanel = new LoginPanel();
		loginPanel.addLoginListener(loginCard);
		loginPanel.addSignUpListener(loginCard);
		HomePanel homePanel = new HomePanel();
		homePanel.addTestListener(allCard);

		RangePanel rangePanel = new RangePanel();
		InstructionPanel instructionPanel = new InstructionPanel();
		String test = "TestM"; // «Ý­×
		QuestionPanel questionPanel = new QuestionPanel(test);
		questionPanel.getQTool().addMoreButtonListener(questionCard, questionPanel);
		questionPanel.getQTool().addFinishButtonListener(questionCard, questionPanel, test);
		QToolPanel qToolPanel = new QToolPanel(test);
		qToolPanel.getQTool().addMoreButtonListener(questionCard, qToolPanel);
		qToolPanel.getQTool().addFinishButtonListener(questionCard, questionPanel, test);
		qToolPanel.addQNumListener(questionCard, questionPanel);
		goforAnswerPanel toAnsPanel = new goforAnswerPanel(questionPanel);
		toAnsPanel.addAnswerListener(allCard);

		AnswerKeyPanel answerKeyPanel = new AnswerKeyPanel(test);
		answerKeyPanel.getQTool().addMoreButtonListener(answerCard);
		QListPanel qListPanel = new QListPanel(test);
		qListPanel.addQuestionButtonListener(answerCard, answerKeyPanel);
		
		

		cardLayout1.show(loginCard, "1");
		loginCard.add(loginPanel, "1");
		loginCard.add(signUpPanel, "2");
		loginCard.add(homePanel, "3");

		cardLayout2.show(questionCard, "1");
		questionCard.add(questionPanel, "1");
		questionCard.add(qToolPanel, "2");
		questionCard.add(toAnsPanel, "3");

		cardLayout3.show(answerCard, "1");
		answerCard.add(qListPanel, "1");
		answerCard.add(answerKeyPanel, "2");

		/*cardLayout0.show(allCard, "1");
		allCard.add(loginCard, "1");
		allCard.add(questionCard, "2");
		allCard.add(answerCard, "3");
		allCard.add(noteCard, "4");*/

		frame.add(questionCard);
		frame.setTitle("College Entrance Examination");
		frame.setSize(800, 500);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
