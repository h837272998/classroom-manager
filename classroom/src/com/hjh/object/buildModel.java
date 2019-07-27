package com.hjh.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.hjh.db.DataBase;

public class buildModel extends AbstractTableModel{
	public Vector<String> colums;
	public Vector<Vector>rows;
	private ResultSet rs;
	
	public void model() {
		String[] cname = {"教学楼编号","楼名","开放教室数","未知列","未知列","未知列","未知列","未知列","未知列"};
		colums=new Vector<String>();
		rows=new Vector<Vector>();
		rs = DataBase.buildTable();

		try {
			ResultSetMetaData rsmd=rs.getMetaData();
			for(int i=0;i<rsmd.getColumnCount();i++){
				this.colums.add(cname[i]);	
			}
				
		
			while(rs.next()){
				Vector<String> temp=new Vector<String>();
				for(int i=0;i<rsmd.getColumnCount();i++){
					temp.add(rs.getString(i+1));
				}
				rows.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataBase.close();
	}
	
	@Override
	public String getColumnName(int arg0) {
		// TODO 自动生成的方法存根
		return this.colums.get(arg0).toString();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.colums.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rows.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return ((Vector)rows.get(arg0)).get(arg1);
	}
}
