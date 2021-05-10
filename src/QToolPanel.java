import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;

import style.*;

public class QToolPanel extends JPanel {
	private JButton[] buttons;
	private JPanel buttonPanel;
	private JPanel qToolUpPanel;
	private JButton finishButton;
	private boolean change;

	public QToolPanel(QuestionPanel questionPanel) {
		this.qToolUpPanel = questionPanel.getQToolUpPanel();
		this.finishButton = questionPanel.getFinishButton();
		this.change = questionPanel.getChange();
		try {
			createComp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createComp() throws SQLException {
		int size = 0;
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "MG05";
		String url = server + database;
		String username = "MG05";
		String password = "9mMuzQ";
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Society";
			try {
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet();
					result.last();
					size = result.getInt(3);
					result.beforeFirst();
					result.close();
				}
			} finally {
				conn.close();
			}
			buttons = new RoundButton[size];
			GridLayout gridLayout = new GridLayout(5, 10);
			gridLayout.setHgap(20);
			gridLayout.setVgap(20);
			buttonPanel = new JPanel(gridLayout);
			buttonPanel.setBackground(Color.decode("#F8EFD4"));
			for (int i = 0; i < size; i++) {
				buttons[i] = new RoundButton(String.format("%d", i + 1));
				buttons[i].setPreferredSize(new Dimension(15, 15));
				buttonPanel.add(buttons[i]);
			}

			setLayout(new GridBagLayout());
			setBackground(Color.decode("#F8EFD4"));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.insets = new Insets(10, 0, 20, 0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.NORTH;
			add(qToolUpPanel, gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.ipadx = 320;
			gbc.ipady = 160;
			gbc.anchor = GridBagConstraints.CENTER;
			add(buttonPanel, gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.insets = new Insets(20, 0, 10, 0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.SOUTH;
			add(finishButton, gbc);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
