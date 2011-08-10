package com.checkettsweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

public class TestH2Connection {

	@Test
	public void simpleConnectionTest() throws ClassNotFoundException, SQLException{
		
		Connection conn = WicketApplication.get().getDataSource().getConnection();
		
		PreparedStatement ps = conn.prepareStatement("select * from test");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		
		// add application code here
		conn.close();
		
	}
	
	@Test
	public void useMetaData(){
		String query = "select * from test";
		int first = 1;
		int count = 10;
		
		
		ArrayList resultList = new ArrayList();
		
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = WicketApplication.get().getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int totalColumns = meta.getColumnCount();
			
			int resultsFetched = 0;
			int rowsRead = 0;
			
			while(rs.next() && rowsRead <= first){
				for(int i=1; i<=totalColumns;i++){
					System.out.print(rs.getString(i));
					System.out.print(" - ");
				}
				System.out.println("\n");
			}
			
		} catch (SQLException sqlEx) {

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		
		
	}
	
}
