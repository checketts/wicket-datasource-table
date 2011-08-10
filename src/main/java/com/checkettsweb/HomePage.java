package com.checkettsweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private String query;
	private String ddlQuery;
	
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {
		query = "select * from example";
		
		

		ResultSetDataProvider prov = new ResultSetDataProvider();
		prov.setQuery(query);
		
		List<IColumn<?>> cols = new ArrayList<IColumn<?>>();
		System.err.println(prov.getColumnCount());
		
		for(int i =0; i< prov.getColumnCount();i++){
			cols.add(new MetaDataColumn(prov,i));
		}

		DefaultDataTable table = new DefaultDataTable("rsTable", cols, prov, 10);
		add(table);

		
		Form<Void> form = new Form<Void>("form");
		add(form);
		
		form.add(new TextField<String>("queryField",new PropertyModel<String>(this,"query")));
		
		
		
		Form<Void> ddlForm = new Form<Void>("ddlForm"){
			@Override
			protected void onSubmit() {
				Connection conn = null;
				try {
					conn = WicketApplication.get().getDataSource().getConnection();
					
					PreparedStatement ps = conn.prepareStatement(ddlQuery);
					ps.execute();
				
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(conn != null){
							conn.close();	
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				super.onSubmit();
			}
		};
		add(ddlForm);
		
		ddlForm.add(new TextField<String>("ddlField",new PropertyModel<String>(this,"ddlQuery")));
		
	}
}
