package com.hjh.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.hjh.db.DataBase;
import com.hjh.db.DataBaseU;

public class classCourseModel_1  extends AbstractTableModel{
	public Vector<String> colums;
	public Vector<Vector>rows;
	private ResultSet rs;
	
	
	
	public void model(int judge,String []p) {
		String[] cname = {"教室名","周数","人数上限","未知列","未知列","未知列","未知列","未知列","未知列","未知列"};
		colums=new Vector<String>();
		rows=new Vector<Vector>();
		String sql = null;
		String []params = p;
		String ss = "%";
		if(judge==0) {
			 sql = "if(SUBSTRING('"+p[0]+"',5,6)= '') select classname,weeknum,peonumber,"+p[2]+1+","+p[2]+2+","+p[2]+3+","+p[2]+4+","+p[2]+5+" from classresources,class where classresources.classid like '"+p[0]+"'+'%' and "
					+ "classresources.classid = class.classid  else select classname,weeknum,peonumber,"+p[2]+1+","+p[2]+2+","+p[2]+3+","+p[2]+4+","+p[2]+5+" from classresources,class where classresources.classid = '"+p[0]+"' and "
					+ "classresources.classid = class.classid" ;
		}else if(judge==1) {
			 sql = "if(SUBSTRING('"+p[0]+"',5,6)= '') select classname,weeknum,peonumber,"+p[2]+" from classresources,class where classresources.classid like '"+p[0]+"'+'%' and "
					+ "classresources.classid = class.classid and weeknum = '"+p[1]+"' else select classname,weeknum,peonumber,"+p[2]+" from classresources,class where classresources.classid = '"+p[0]+"' and "
					+ "classresources.classid = class.classid and weeknum = '"+p[1]+"'";
			 System.out.println(sql);
		}else {
			sql = "select classname,weeknum,peonumber,一1,一2,一3,一4,一5,二1,二2,二3,二4,二5,三1,三2,三3,三4,三5,"
					+ "四1,四2,四3,四4,四5,五1,五2,五3,五4,五5,六1,六2,六3,六4,六5,日1,日2,日3,日4,日5  from classresources,class where classresources.classid = class.classid ";
		}
		String []s = {};
		rs = DataBaseU.executeQuery(sql, s);
		try {
			ResultSetMetaData rsmd=rs.getMetaData();
			for(int i=0;i<rsmd.getColumnCount();i++){
				if(i<=2){	
					this.colums.add(cname[i]);	
				}else {
					this.colums.add(rsmd.getColumnName(i+1));	
				}
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
