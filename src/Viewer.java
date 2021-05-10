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
		/*
		 * JFrame frame = new JFrame(); //HomePanel homePanel = new HomePanel();
		 * QuestionPanel questionPanel = new QuestionPanel(); //QToolPanel qToolPanel =
		 * new QToolPanel(questionPanel); frame.add(questionPanel);
		 * frame.setTitle("Test"); frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 * frame.setVisible(true); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */

		// jolin
		/*
		 * JFrame frame = new JFrame("Tester"); CardLayout card = new CardLayout();
		 * JPanel panel = new JPanel(card);
		 * 
		 * RangePanel r = new RangePanel(); InstructionPanel i = new InstructionPanel();
		 * panel.add(r, "Range"); panel.add(i, "Instruction"); frame.add(panel);
		 * 
		 * r.creaStartBtn(panel);
		 * 
		 * frame.setSize(400, 400); frame.setVisible(true);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */

		// meg
		JFrame frame = new JFrame();
		CardLayout cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);

		SignUpPanel signUp = new SignUpPanel();
		LoginPanel login = new LoginPanel();
		HomePanel homePanel = new HomePanel();
		QuestionPanel questionPanel = new QuestionPanel(panel);
		// QToolPanel qToolPanel = new QToolPanel(questionPanel);

		panel.add(login, "1");
		panel.add(signUp, "2");
		panel.add(homePanel, "3");
		// panel.add(qToolPanel, "4");

		cardLayout.show(panel, "1");
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem Login = new JMenuItem("Login");
		Login.setActionCommand("Login");
		JMenuItem Signup = new JMenuItem("Sign up");
		Signup.setActionCommand("Sign up");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(panel, "1");
			}
		}

		class MenuItemListener2 implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(panel, "2");
			}
		}
		MenuItemListener menuItemListener = new MenuItemListener();
		MenuItemListener2 menuItemListener2 = new MenuItemListener2();
		Login.addActionListener(menuItemListener);
		Signup.addActionListener(menuItemListener2);
		menu.add(Login);
		menu.add(Signup);
		mb.add(menu);

		frame.add(questionPanel);
		frame.setJMenuBar(mb);
		frame.setTitle("Test");
		frame.setSize(800, 500);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// angel
		/*
		 * JFrame frame = new JFrame(); CardLayout cardLayout = new CardLayout(); JPanel
		 * panel = new JPanel(cardLayout);
		 * 
		 * SignUpPanel signUp = new SignUpPanel(); LoginPanel login = new LoginPanel();
		 * HomePanel homePanel = new HomePanel(); //QuestionPanel questionPanel = new
		 * QuestionPanel(panel); //QToolPanel qToolPanel = new
		 * QToolPanel(questionPanel);
		 * 
		 * panel.add(login, "1"); panel.add(signUp, "2"); panel.add(homePanel, "3");
		 * //panel.add(qToolPanel, "4");
		 * 
		 * cardLayout.show(panel, "1"); JMenuBar mb = new JMenuBar(); JMenu menu = new
		 * JMenu("Menu"); JMenuItem Login = new JMenuItem("Login");
		 * Login.setActionCommand("Login"); JMenuItem Signup = new JMenuItem("Sign up");
		 * Signup.setActionCommand("Sign up"); class MenuItemListener implements
		 * ActionListener { public void actionPerformed(ActionEvent event) {
		 * cardLayout.show(panel, "1"); } }
		 * 
		 * class MenuItemListener2 implements ActionListener { public void
		 * actionPerformed(ActionEvent event) { cardLayout.show(panel, "2"); } }
		 * MenuItemListener menuItemListener = new MenuItemListener(); MenuItemListener2
		 * menuItemListener2 = new MenuItemListener2();
		 * Login.addActionListener(menuItemListener);
		 * Signup.addActionListener(menuItemListener2); menu.add(Login);
		 * menu.add(Signup); mb.add(menu);
		 * 
		 * frame.add(login); frame.setJMenuBar(mb); frame.setTitle("Test");
		 * frame.setSize(800, 500); frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 * frame.setVisible(true); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */

	}

}
