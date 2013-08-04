package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;


// We need to import the java.sql package to use JDBC

public class BasicQuery 
{		
	private Connection con = null;
	private static final String url = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	private static final String username = "";
	private static final String password = "";

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

	/**
	 * Insert a tuple into a table. This method will not work with Dates
	 * 
	 * @param tableName Name of the table to insert values into
	 * @param tuple Array of Strings with values to insert (must be in order of table columns)
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
	 * Insert a tuple into specified table. This method can support Dates
	 * 
	 * @param tableName Name of table to insert tuple into
	 * @param schema HashMap of a table schema (column name, data type)
	 * @param tuple Array of String values (must be in order of table columns)
	 * @throws Exception if schema type is unrecognized
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
			} 
	}
	
	/**
	 * Delete tuple from table
	 * @param tableName Name of table to delete from
	 * @param conditions String array of clauses
	 * @param op link between conditions ("AND" or "OR")
	 */
	public void deleteTuple(String tableName, String[] conditions, String op) {
		
		try {
			String q = "DELETE FROM " + tableName + " WHERE ";
			for (int i=0; i<conditions.length; i++) {
				q += conditions[i] + " " + op + " "; 
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
