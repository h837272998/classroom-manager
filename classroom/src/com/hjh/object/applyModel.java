package com.hjh.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.hjh.db.DataBase;

public class applyModel extends AbstractTableModel{
	public Vector<String> colums;
	public Vector<Vector>rows;
	private int judge=0,judge1 = 0;
	private String str = null;
	private String str1="",str2="",str3 = "";
	private ResultSet rs;
	
	public void model(int j,int j1,String s1,String s2,String s3) {
		judge = j;  //判断是检索  还是 显示全部
		judge1 = j1;

		str1 = s1;
		str2 = s2;
		str3 = s3;
	}
	
	public void model() {
		String[] cname = {"申请号","申请时间","开始时间","结束时间","周次","星期","节次","活动名称","地点","组织单位","人数上限","联系人姓名","联系人手机","申请账号","未知列","未知列"};
		colums=new Vector<String>();
		rows=new Vector<Vector>();
		
		if(judge==0&&judge1 == 1 ) {
			str = "select * from applyclass where (endtime - GETDATE())>=0";
		}else if(judge==0&&judge1==0){
			str = "select applyid,applyidtime,begintime,endtime,weeknum,secnum,partnum,aname,aaddress,organize,peolager,peoname,peotel,userid from applyclass,class where applyclass.aaddress = class.classid and classname = '"+str1+"'";
		}else if(judge==1&&judge1==0) {
			str = "select applyid,applyidtime,begintime,endtime,weeknum,secnum,partnum,aname,aaddress,organize,peolager,peoname,peotel,userid from applyclass,class where applyclass.aaddress = class.classid and classname = '"+str1+"' and  weeknum = '"+str2+"' and secnum = '"+str3+"'";
		}else if(judge==1&&judge1==1){
			str = "select applyid,applyidtime,begintime,endtime,weeknum,secnum,partnum,aname,aaddress,organize,peolager,peoname,peotel,userid from applyclass,class where applyclass.aaddress = class.classid and classname = '"+str1+"' and weeknum = '"+str2+"' and secnum = '"+str3+"' and endtime - GETDATE()>=0";
		}
		
		if(judge==0&&judge1==0&&str1.equals(""))
			rs = DataBase.applyTable();
		else
			rs = DataBase.applyTablecheck(str);
		
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
