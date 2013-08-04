package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.BasicQuery;

public class BasicQueryTest {
	
	private static final String types_tablename = "JUNIT_TYPES";
	private static final String notypes_tablename = "JUNIT";
	private static Connection con;
	private static BasicQuery bq;
	private static TestQueries tq;
	
	// schema definitions
	// make sure the insertion order is the same as the column order
	// type must be one of NUMBER, STRING, or DATE
	public static LinkedHashMap<String,String> TYPES_SCHEMA = new LinkedHashMap<String, String>();
	static {
		TYPES_SCHEMA.put("TEST_ID", "NUMBER");
		TYPES_SCHEMA.put("TEST_NAME", "STRING");
		TYPES_SCHEMA.put("TEST_DATE", "DATE");
	}
	
	@BeforeClass
	public static void setUp() {
		bq = new BasicQuery();
		con = bq.getConnection();
		tq = new TestQueries(con);
		
		// create a testing table
		if (tq.getAllTableNames().contains(types_tablename)) {
			tq.executeSpecialQuery("DROP TABLE " + types_tablename);
		}
		
		if (tq.getAllTableNames().contains(notypes_tablename)) {
			tq.executeSpecialQuery("DROP TABLE " + notypes_tablename);
		}
		
		tq.executeSpecialQuery("CREATE table " + types_tablename + 
				"(test_id int not null primary key," + 
				"test_name varchar2(15)," + 
				"test_date date)");

		tq.executeSpecialQuery("CREATE table " + notypes_tablename + 
				"(test_id varchar2(10) not null primary key," + 
				"test_name varchar2(15))");
	}
	
	@AfterClass
	public static void tearDown() {
		if (tq.getAllTableNames().contains(types_tablename)) {
			tq.executeSpecialQuery("DROP TABLE " + types_tablename);
		}

		if (tq.getAllTableNames().contains(notypes_tablename)) {
			tq.executeSpecialQuery("DROP TABLE " + notypes_tablename);
		}
		try {
			con.close();
		} catch (SQLException e) {
			tq.rollback();
			e.printStackTrace();
		}
	}
	
	
	// TODO: multiple inserts/deletes
	
	@Test
	public void insertTest1() {
		try {
			int rowCount = tq.getRowCount(types_tablename);
			bq.insertTuple2(types_tablename, TYPES_SCHEMA, new String[] {"1", "Mike", "25-DEC-2005"});
			Assert.assertTrue(tq.getRowCount(types_tablename) > rowCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertTest2() {
		// test single insert
		int rowCount = tq.getRowCount(notypes_tablename);
		bq.insertTuple(notypes_tablename, new String[] {"50", "Nora"});
		Assert.assertTrue(tq.getRowCount(notypes_tablename) == rowCount + 1);
		
		// test 2 inserts
		rowCount = rowCount + 1;
		bq.insertTuple(notypes_tablename, new String[] {"10", "Fran"});
		bq.insertTuple(notypes_tablename, new String[] {"30", "Joe"});
		Assert.assertEquals(rowCount + 2, tq.getRowCount(notypes_tablename));
	
		
	}
	
	@Test
	public void deleteTest() throws Exception {
		// insert a tuple
		bq.insertTuple2(types_tablename, TYPES_SCHEMA, new String[] {"10", "Mike", "01-DEC-2005"});
		int rowCount = tq.getRowCount(types_tablename);
		// delete tuple
		bq.deleteTuple(types_tablename, new String[] {"test_id=10"}, "OR");
		Assert.assertTrue(tq.getRowCount(types_tablename) == rowCount-1);
		
		// insert tuples
		bq.insertTuple(notypes_tablename, new String[] {"5", "Alice"});
		bq.insertTuple(notypes_tablename, new String[] {"6", "Mark"});
		bq.insertTuple(notypes_tablename, new String[] {"7", "Jon"});
		int rowCount2 = tq.getRowCount(notypes_tablename);
		// delete tuple with AND
		bq.deleteTuple(notypes_tablename, new String[] {"test_id>5", "test_name='Jon'"}, "AND");
		Assert.assertEquals(rowCount2-1, tq.getRowCount(notypes_tablename));
		// delete tuple with OR
		bq.deleteTuple(notypes_tablename, new String[] {"test_id=5", "test_name='Mark'"}, "OR");
		Assert.assertEquals(rowCount2-3, tq.getRowCount(notypes_tablename));
	}
}
