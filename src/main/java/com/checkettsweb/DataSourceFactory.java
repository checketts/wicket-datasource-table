package com.checkettsweb;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

public class DataSourceFactory {

	public static DataSource createDataSource(){
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:~/test");
		ds.setUser("sa");
		ds.setPassword("");
		
		
		return ds;
	}
	
}
