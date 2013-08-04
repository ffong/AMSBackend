package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.BasicQuery;

public class TestQueries {
	/* methods for testing and debugging */
	private Connection con;
	
	public TestQueries(Connection acon) {
		con = acon;
	}
	
	public void executeSpecialQuery(String str) {
		try {
			Statement s = con.createStatement();
			
			
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	/* for testing */
	public int getRowCount(String tableName) {
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM " + tableName);
			
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
		return 0;
	}
	
	/* for debugging */
	public void printTable(String tableName) {
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM " + tableName);
			int colCount = rs.getMetaData().getColumnCount();
			
			for (int i=1; i<=colCount; i++) {		
				System.out.format("%12s", rs.getMetaData().getColumnName(i)  + " ");
			}
			
			System.out.println("");
			
			while (rs.next()) {
				for (int i=1; i<=colCount; i++) {
					System.out.format("%12s", rs.getObject(i) + " ");
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
		
	}

	/* for debugging */
	public void showAllTables(){
		Statement s;
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT table_name FROM user_tables");
			con.commit();

			System.out.println("All Tables:");
			while (rs.next()) {
				System.out.print(rs.getString(1) + " ");
			}
			System.out.println("\n");
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	private void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println("Failed to rollback the last transaction.");
			e.printStackTrace();
		}
	}
}
