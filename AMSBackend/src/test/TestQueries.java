package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestQueries {
	/* methods for testing and debugging */
	private Connection con;
	
	public TestQueries(Connection acon) {
		con = acon;
	}
	
	public void executeSpecialQuery(String str) {
		try {
			Statement s = con.createStatement();
			s.execute(str);
			con.commit();
			
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
			con.commit();
			
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
			e.printStackTrace();
		}
		
	}

	/* for debugging */
	public ArrayList<String> getAllTableNames(){
		ArrayList<String> tables = new ArrayList<String>();
		Statement s;
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT table_name FROM user_tables");

			while (rs.next()) {
				tables.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tables;
	}
	
	protected void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println("Failed to rollback the last transaction.");
			e.printStackTrace();
		}
	}
}
