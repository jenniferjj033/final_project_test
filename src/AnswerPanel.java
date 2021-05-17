import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnswerPanel extends JPanel {
	private JLabel titleLabel;
	private ArrayList<JButton> questionButtons;
	private JButton homeButton;
	
	public AnswerPanel() {
		createTitleLabel();
		createQuestionButtons();
		createHomeButton();
		setLayout();
	}
	
	public void createTitleLabel() {
		// image
		titleLabel = new JLabel("Answer");
	}
	
	public void createQuestionButtons() {
		questionButtons = new ArrayList<JButton>();
		for (int i = 0; i < 50; i++) {
			questionButtons.add(new JButton());
			// set text
		}
	}
	
	public void createHomeButton() {
		// create in qTool class?
	}
	
	public void setLayout() {
		
	}
}
