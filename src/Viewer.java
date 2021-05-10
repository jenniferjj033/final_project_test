import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Viewer {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		//HomePanel homePanel = new HomePanel();
		QuestionPanel questionPanel = new QuestionPanel();
		QToolPanel qToolPanel = new QToolPanel(questionPanel); 
		frame.add(questionPanel);
		frame.setTitle("Test");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//哈！
	}
}
