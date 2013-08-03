package test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import main.BasicQuery;

public class BasicQueryTest {
	
	@Test
	public void insertTest() throws SQLException {
		Connection con = BasicQuery.getConnection();
		BasicQuery.showAllTables();
	}
}
