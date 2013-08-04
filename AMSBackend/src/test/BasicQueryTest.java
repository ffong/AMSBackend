package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import main.BasicQuery;

public class BasicQueryTest {
	
	private static Connection con;
	private static BasicQuery bq;
	private static TestQueries tq;
	
	@BeforeClass
	public static void setUp() {
		bq = new BasicQuery();
		con = bq.getConnection();
		tq = new TestQueries(con);
	}
	
	@Test
	public void insertTest1() {
		// make sure we're connected
		try {
			int rowCount = tq.getRowCount("TYPES");
			bq.insertTuple2("TYPES", BasicQuery.TYPES_SCHEMA, new String[] {"1", "Mike", "25-DEC-2005"});
			Assert.assertTrue(tq.getRowCount("TYPES") > rowCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertTest2() {
		int rowCount = tq.getRowCount("STRINGONLY");
		bq.insertTuple("STRINGONLY", new String[] {"50", "Nora", "Blah"});
		Assert.assertTrue(tq.getRowCount("STRINGONLY") > rowCount);
	}
	
	@Test
	public void deleteTest() {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("id", "07");
		bq.deleteTuple("TYPES", h, "OR");
	}
}
