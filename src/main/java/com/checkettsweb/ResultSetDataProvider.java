package com.checkettsweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class ResultSetDataProvider extends SortableDataProvider<List<? extends String>> {

	private static final long serialVersionUID = 1L;
	private String query;
	private IModel<List<List<? extends String>>> dataModel;
	private int size = 0;
	private List<String> colNames;
	
	public List<String> getColNames() {
		if(colNames == null){
			dataModel.getObject();
		}
		return colNames;
	}


	public ResultSetDataProvider() {
		dataModel = new LoadableDetachableModel<List<List<? extends String>>>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<List<? extends String>> load() {
				ArrayList<List<? extends String>> resultList = new ArrayList<List<? extends String>>();
				
				Connection conn = null;
				ResultSet rs = null;
				try {
					conn = DataSourceFactory.createDataSource().getConnection();
					System.out.println("Q:"+query);
					PreparedStatement ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					
					ResultSetMetaData meta = rs.getMetaData();
					int totalColumns = meta.getColumnCount();
					
					colNames = new ArrayList<String>();
					for(int i =1;i<=totalColumns;i++){
						colNames.add(meta.getColumnName(i));
					}
					
					int rowsRead = 0;
					
					while(rs.next()){
						List<String> row = new ArrayList<String>();
						
						rowsRead++;
						for(int i=1; i<=totalColumns;i++){
							System.out.print(rs.getString(i));
							row.add(rs.getString(i));
							System.out.print(" - ");
						}
						resultList.add(row);
						System.out.print("\n");
					}
					
					System.out.println("Size:"+rowsRead);
					size = rowsRead;
					
				} catch (SQLException sqlEx) {
					size =0;
					sqlEx.printStackTrace();

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
				
				return resultList;
			}
		};
	}
	
	
	public void setQuery(String q){
		query = q;
	}
	
	public String getQuery(){
		return query;
	}
	
	public Iterator<List<? extends String>> iterator(int first, int count) {

		int boundsSafeCount = count;
		
		if(first + count > size){
			boundsSafeCount = first - size;
		} else {
			boundsSafeCount = count;
		}
		
		System.out.println("size:"+size+" - boundSafe:"+boundsSafeCount);
		
		return dataModel.getObject().subList(first, first+boundsSafeCount).iterator();
	}

	public int size() {
		return size;
	}

	public IModel<List<? extends String>> model(List<? extends String> object) {
		return Model.<String>ofList(object);
	}
	
	@Override
	public void detach() {
		dataModel.detach();
		super.detach();
	}


	public int getColumnCount() {
		return getColNames().size();
	}

}
