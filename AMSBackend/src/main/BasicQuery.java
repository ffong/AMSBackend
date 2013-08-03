package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


// We need to import the java.sql package to use JDBC

public class BasicQuery 
{		
	private static Connection con = null;
	private static final String url = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	private static final String username = "ora_m0h7";
	private static final String password = "a70054093";

	public static Connection getConnection()
	{
		try {

			if (con == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url, username, password);
				System.out.println("Connected.");
			} else {
				System.out.println("Already connected.");
			}


		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return con;
	}



	/* for debugging */
	public static void printTable(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int colNum = md.getColumnCount();
		for (int i=0; i< colNum; i++) {
			System.out.println(md.getColumnType(i));
		}
	}

	/* for debugging */
	public static void showAllTables() throws SQLException{
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("SELECT table_name FROM user_tables");

		System.out.println("All Tables:");
		while (rs.next()) {
			System.out.print(rs.getString(1) + " ");
		}
	}

}
