package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;


// We need to import the java.sql package to use JDBC

public class BasicQuery 
{		
	private Connection con = null;
	private static final String url = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	private static final String username = "ora_m0h7";
	private static final String password = "a70054093";
	
	// schema definitions
	// make sure the insertion order is the same as the column order
	// type must be one of NUMBER, STRING, or TYPE
	public static LinkedHashMap<String,String> TYPES_SCHEMA = new LinkedHashMap<String, String>();
	static {
		TYPES_SCHEMA.put("ID", "NUMBER");
		TYPES_SCHEMA.put("NAME", "STRING");
		TYPES_SCHEMA.put("TYPE_DATE", "DATE");
	}

	/**
	 * If no connection has been made to database, will start a new one
	 * @return Connection
	 */
	public Connection getConnection()
	{
		try {

			if (con == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url, username, password);
				con.setAutoCommit(false);
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
	
	// TODO: CloseConnection

	/**
	 * This method will not work with Dates
	 * @param tableName
	 * @param tuple
	 */
	public void insertTuple(String tableName, String[] tuple) {
		try {
			Statement s = con.createStatement();
			String q = "INSERT INTO " + tableName + " values (";
			
			for (int i=0; i<tuple.length; i++) {
				q += "\'" + tuple[i] + "\', ";
			}
			q = q.substring(0, q.lastIndexOf(",")) + ")";
			
			System.out.println(q);
			s.executeQuery(q);
			con.commit();
			
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
		
	}

	/**
	 * Insert tuple into specified table
	 * <p>
	 * ex. insertTuple (branch, {20, Richmond, 23 No.3 Road, Richmond, 5552331})
	 * 
	 * @param tableName Name of table
	 * @param values Array of values in the tuple
	 * @throws Exception 
	 */
	public void insertTuple2(String tableName, LinkedHashMap<String, String> schema, String[] tuple) throws Exception {
		PreparedStatement ps;
		int paramIndex = 1;
		
		// check that there are enough values for this table
		if (tuple.length != schema.size()) {
			throw new Exception("Not enough values for table " + tableName + 
					". Please check your values." );
		}
		
			// construct the prepared statement with the right number of ?'s
			String q = "INSERT INTO " + tableName + " values (";
			for (int i=1; i<tuple.length; i++) {
				q += "?,";
			}
			q += "?)";
			
			try {
				// create the statement
				ps = con.prepareStatement(q);
				
				// set the value for each ? according to data type of the schema
				for (String col : schema.keySet()) {
					String type = schema.get(col).toUpperCase();		// get the data type 
					String val = tuple[paramIndex-1];						// get the value
					
					if (type.equals("NUMBER")) {
						ps.setInt(paramIndex, Integer.parseInt(val));
					} else if (type.equals("STRING")) {
						ps.setString(paramIndex, val);
					} else if (type.equals("DATE")) {
						// parse the date into a SQL Date object
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
						Date d = formatter.parse(val);						
						ps.setDate(paramIndex, new java.sql.Date(d.getTime()));
					} else {
						throw new Exception("Unrecognized type in insertTuple: " + type);
					}			
					
					paramIndex++;	
				}
				
				ps.executeUpdate();
				con.commit();
				
			} catch (SQLException e) {
				rollback();
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void deleteTuple(String tableName, HashMap<String, String> conditions, String op) {
		
		try {
			String q = "DELETE FROM " + tableName + " WHERE ";
			for (String col : conditions.keySet()) {
				q += col + "=\'" + conditions.get(col) + "\'" + op; 
			}
			q = q.substring(0, q.lastIndexOf(op));
			
			System.out.println(q);
			Statement s = con.createStatement();
			s.execute(q);
			con.commit();
			
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
