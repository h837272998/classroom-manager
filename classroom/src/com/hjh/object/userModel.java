package com.hjh.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.hjh.db.DataBase;

public class userModel extends AbstractTableModel{
	public Vector<String> colums;
	public Vector<Vector>rows;
	private int judge=0;//判断是检索  还是 显示全部
	private String str = null;//检索文字
	private ResultSet rs;
	
	public void model(int j,String s) {
		judge = j;  //判断是检索  还是 显示全部
		str = s;  //检索文字
	}
	
	public void model() {
		String[] cname = {"账号","密码","email","权限","姓名","手机号","最后一次登录时间","注册时间","未知列","未知列"};
		colums=new Vector<String>();
		rows=new Vector<Vector>();
		if(judge==0)
			rs = DataBase.userTable();
		else
			rs = DataBase.userTablecheck(str);
		
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
//				temp.add(rs.getString(1));
//				temp.add(rs.getString(2));
//				temp.add(rs.getString(3));
//				temp.add(rs.getString(4));	
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
