package com.hjh.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.hjh.db.DataBaseU;


public class applyModel_1 extends AbstractTableModel{
	public Vector<String> colums;
	public Vector<Vector>rows;
	private String str = null;
	private ResultSet rs;
	
	public void model(String id) {
		String[] cname = {"申请号","申请时间","开始时间","结束时间","周次","星期","节次","活动名称","地点","组织单位","人数上限","联系人姓名","联系人手机","申请账号","未知列","未知列"};
		colums=new Vector<String>();
		rows=new Vector<Vector>();
		String sql = "select * from applyclass where userid = ?";
		String []params = {id};
		rs = DataBaseU.executeQuery(sql,params);

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
		DataBaseU.close();
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
