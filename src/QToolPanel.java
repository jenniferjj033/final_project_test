import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

//import Style.*;

public class QToolPanel extends JPanel {
	private JButton[] buttons;
	private JPanel buttonPanel;
	private JPanel qFunctionPanel;
	private QuestionPanel questionPanel;
	private JButton finishButton;
	
	public QToolPanel(QuestionPanel questionPanel) {
		this.qFunctionPanel = questionPanel.getQFuctionPanel();
		this.questionPanel = questionPanel;
		this.finishButton = questionPanel.getFinishButton();
		createComp();
	}
	
	public void createComp() {
		//num = counts of database
		buttons = new RoundButton[50]; //50 change num
		GridLayout gridLayout = new GridLayout(5, 10);
		gridLayout.setHgap(10);
		gridLayout.setVgap(10);
		buttonPanel = new JPanel(gridLayout);
		for(int i = 0; i < 50; i++) { //50 change to num
			buttons[i] = new RoundButton(String.format("Q%d", i+1));
			buttons[i].setPreferredSize(new Dimension(15, 15));
			buttonPanel.add(buttons[i]);
		}
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.CENTER);
		add(qFunctionPanel, BorderLayout.NORTH);
		add(finishButton, BorderLayout.SOUTH);
	}
	
}
