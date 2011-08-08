package com.checkettsweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private String query;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {
		query = "select * from test";

		ResultSetDataProvider prov = new ResultSetDataProvider();
		prov.setQuery(query);
		
		List<IColumn<?>> cols = new ArrayList<IColumn<?>>();
		System.err.println(prov.getColumnCount());
		
		for(int i =0; i< prov.getColumnCount();i++){
			cols.add(new MetaDataColumn(prov,i));
		}

		DefaultDataTable table = new DefaultDataTable("rsTable", cols, prov, 2);
		add(table);

	}
}
