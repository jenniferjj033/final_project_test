import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class Answer {
	private ArrayList<String> userAnswers;
	private ArrayList<Boolean> ifCorrect;
	private ArrayList<String> correctAnswers;
	private Connection conn;
	private String url, username, password;

	public Answer() {
		userAnswers = new ArrayList<String>();
		ifCorrect = new ArrayList<Boolean>();
		correctAnswers = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			userAnswers.add(null);
			ifCorrect.add(false);
		}

		url = "jdbc:mysql://140.119.19.73:9306/" + "MG05";
		username = "MG05";
		password = "9mMuzQ";

	}

	public void updateUserAnswers(int i, String ans) {
		userAnswers.set(i - 1, ans);
	}

	public void insertUserAnswers(String userID, int year, String subject) {
		try {
			String column = "`1`";
			String value = "?";
			for (int i = 2; i <= 50; i++) {
				column += ", `" + i + "`";
				value += ", ?";
			}
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement();
			String query1 = "SELECT COUNT(*) FROM Answer WHERE UserID = '" + userID + "' AND Year = '" + year
					+ "' AND Subject = '" + subject + "' ";
			stat.execute(query1);

			ResultSet result = stat.getResultSet();
			result.next();
			int count = Integer.parseInt(result.getString(1));
			if (count == 0) {
				String query2 = "INSERT INTO Answer (UserID, Year, Subject, " + column + ") VALUES (?, ?, ?, " + value
						+ ")";
				PreparedStatement preStat = conn.prepareStatement(query2);
				preStat.setString(1, userID);
				preStat.setInt(2, year);
				preStat.setString(3, subject);
				for (int i = 0; i < 50; i++) {
					preStat.setString(i + 4, userAnswers.get(i));
				}
				preStat.executeUpdate();
			} else {
				System.out.printf("%s, %d %s: Data already upload.", userID, year, subject);
			}

		} catch (Exception e) {
			System.out.println("insertUserAnswers: " + e.getMessage());
		}
	}

	public ArrayList<String> getUserAnswers(String userID, int year, String subject) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Answer WHERE UserID = '" + userID + "' AND Year = '" + year
					+ "' AND Subject = '" + subject + "' ";
			ArrayList<String> list = new ArrayList<String>();
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				ResultSetMetaData metaData = result.getMetaData();
				int columnCount = metaData.getColumnCount();
				while (result.next()) {
					for (int i = 4; i <= columnCount; i++) {
						list.add(result.getString(i));
					}
				}
			}
			return list;
		} catch (Exception e) {
			System.out.println("getUserAnswers: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<Boolean> getIfCorrect() {
		for (int i = 0; i < 50; i++) {
			if (userAnswers.get(i) != null) {
				if (userAnswers.get(i).equals(correctAnswers.get(i))) {
					ifCorrect.set(i, true);
				}
			} else {
				ifCorrect.set(i, false);
			}
		}
		return this.ifCorrect;
	}

	public void updateIfCorrect(int i, boolean b) {
		ifCorrect.set(i, b);
	}

	public ArrayList<String> getCorrectAnswers() {
		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT Answer FROM Society WHERE Number <> 0";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					correctAnswers.add(result.getString(1));
				}
			}
		} catch (Exception e) {
			System.out.println("getCorrectAnswers: " + e.getMessage());
		}
		return this.correctAnswers;
	}

	public double checkAnswer(ArrayList<Boolean> ifCorrect, ArrayList<String> correctAnswers,
			ArrayList<String> userAnswers) {
		double score = 0;
		int correctNum = 0;
		int qNum = 0;
		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement();
			String query = "SELECT `Number` FROM `Society` WHERE `MCQ` = 'TRUE'";
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				result.next();
				qNum = result.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("qNum: " + e.getMessage());
		}

		for (int i = 0; i < qNum - 1; i++) {
			if (ifCorrect.get(i).equals(true)) {
				score += 2;
			}
		}
		for (int i = qNum - 1; i < 50; i++) {
			if (ifCorrect.get(i).equals(true)) {
				score += 2;
			} else if (ifCorrect.get(i).equals(false) && userAnswers.get(i) != null) {
				for (int m = 0; m < correctAnswers.get(i).length(); m++) {
					for (int n = 0; n < userAnswers.get(i).length(); n++) {
						// 檢測答案選項是否符合考生的選項
						if (correctAnswers.get(i).charAt(m) == userAnswers.get(i).charAt(n)) {
							correctNum += 1; // 答對一個選項
						}
					}

				}
				if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) > 2) {
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 2) {
					score += 0.4;
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 1) {
					score += 1.2;
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 0) {
					score += 2;
					correctNum = 0;
				}
			}
			// 所有選項均答對者，得2分；答錯1個選項者，得 1.2分；答錯2個選項者，得0.4分；答錯多於2個選項或所有選項均未作答者，該題以零分計算。
		}
		return score;
	}
}
