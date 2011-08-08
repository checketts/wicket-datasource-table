package com.checkettsweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.wicket.protocol.http.WebApplication;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.checkettsweb.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	private static Logger log = LoggerFactory.getLogger(WicketApplication.class);
	DataSource ds;
	
	/**
	 * Constructor
	 */
	public WicketApplication() {
	}

	@Override
	protected void init() {
		ds = getDataSource();
		super.init();
	}

	private JdbcDataSource getDataSource() {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:example-db");
		ds.setUser("sa");
		ds.setPassword("");
		
		Connection conn;
		
		try{
			conn = ds.getConnection();
			ResultSet rs = conn.prepareStatement("select * from example").executeQuery();
		} catch (SQLException sqlEx){
			if(sqlEx.getMessage().contains("Table \"EXAMPLE\" not found")){
				createExampleTable(ds);
			}
		}
		
		createExampleRowsIfNeeded(ds);
		
		
		return ds;
	}

	private void createExampleRowsIfNeeded(DataSource ds2) {
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("select count(*) from example");
			ResultSet rs = ps.executeQuery();
			
			int rowCount = 0;
			if(rs.next()){
				rowCount = rs.getInt(1);
			}
			
			if(rowCount == 0){
//				conn.prepareStatement("insert into ")
			}
			
		} catch (SQLException e) {
			log.error("Trouble creating example table.",e);
		}
	}

	private void createExampleTable(DataSource ds) {
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("create table example(id int PRIMARY KEY, name varchar(255))");
			ps.execute();
			
		} catch (SQLException e) {
			log.error("Trouble creating example table.",e);
		}
		
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

}
