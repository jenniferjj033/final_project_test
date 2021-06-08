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
	private ArrayList<Integer> numbers;
	private ArrayList<String> ifMarked;
	private Connection conn;
	private double score;

	public Answer() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database;
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.printf("constructor %s\n", e.getMessage());
		}

		userAnswers = new ArrayList<String>();
		ifCorrect = new ArrayList<Boolean>();
		correctAnswers = new ArrayList<String>();
		numbers = new ArrayList<Integer>();
		ifMarked = new ArrayList<String>();
		score = 0;
	}

	public void setNumbers(String test) {
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Number FROM " + test;
			ResultSet result = stat.executeQuery(query);

			while (result.next()) {
				numbers.add(result.getInt(1));
				userAnswers.add(null);
				ifMarked.add("FALSE");
			}
		} catch (Exception e) {
			System.out.println("setNumbers from <Answer>: " + e.getMessage());
		}
	}
	
	public ArrayList<Integer> getNumbers() {
		return this.numbers;
	}

	public void setUserAnswers(int i, String ans) {
		userAnswers.set(i, ans);
	}

	public ArrayList<String> getUserAnswers() {
		return this.userAnswers;
	}

	public void insertUserAnswers(String test) {
		try {
			Statement stat = conn.createStatement();
			for (int i = 0; i < userAnswers.size(); i++) {
				String query = "UPDATE " + test + " SET UserAnswer = '" + userAnswers.get(i) + "' WHERE Number = "
						+ numbers.get(i);
				stat.execute(query);
			}

		} catch (Exception e) {
			System.out.println("insertUserAnswers: " + e.getMessage());
		}
	}

	public ArrayList<String> getUserAnswers(String test) {
		try {
			userAnswers = new ArrayList<String>();
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT UserAnswer FROM " + test;
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					userAnswers.add(result.getString(1));
				}
			}
			return userAnswers;
		} catch (Exception e) {
			System.out.println("getUserAnswers from <test>: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<Boolean> getIfCorrect(ArrayList<String> correctAnswers, ArrayList<String> userAnswers) {
		ifCorrect = new ArrayList<Boolean>();
		for (int i = 0; i < userAnswers.size(); i++) {
			if (userAnswers.get(i) != null) {
				if (userAnswers.get(i).equals(correctAnswers.get(i))) {
					ifCorrect.add(true);
				} else {
					ifCorrect.add(false);
				}
			} else {
				ifCorrect.add(false);
			}
		}
		return this.ifCorrect;
	}

	public ArrayList<String> getCorrectAnswers(String test) {
		try {
			correctAnswers = new ArrayList<String>();
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT Answer FROM " + test ;
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					correctAnswers.add(result.getString(1));
				}
			}
		} catch (Exception e) {
			System.out.println("getCorrectAnswers from <Answer>: " + e.getMessage());
		}
		return this.correctAnswers;
	}
	
	public void setIfMarked(int i, String s) {
		ifMarked.set(i , s);
	}
	
	public void insertIfMarked(String test) {
		try {
			Statement stat = conn.createStatement();

			for (int i = 0; i < ifMarked.size(); i++) {
				String query = "UPDATE " + test + " SET UserAnswer = '" + ifMarked.get(i) + "' WHERE Number = "
						+ numbers.get(i);
				stat.execute(query);
			}

		} catch (Exception e) {
			System.out.println("insertIfMarked: " + e.getMessage());
		}
	}
	
	public ArrayList<String> getIfMarked(){
		return this.ifMarked;
	}

	public double getScore() {
		return this.score;
	}

	public double getScore(String test) {
		int correctNum = 0;
		int qNum = 0;
		score = 0;
		userAnswers = getUserAnswers(test);
		correctAnswers = getCorrectAnswers(test);
		ifCorrect = getIfCorrect(userAnswers, correctAnswers);
		
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT `Number` FROM "+ test + " WHERE `MCQ` = 'TRUE'";
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
