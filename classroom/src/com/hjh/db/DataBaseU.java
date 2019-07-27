package com.hjh.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hjh.object.user;


public class DataBaseU {
	 static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	 static String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=ClassroomResources";
	 static String dbUser = "HJH";
	 static String dbPwd = "134600";
	 static Connection conn = null;
	 static PreparedStatement ps=null;
	//ResultSet rs=null;
	
	public DataBaseU() {
		try {
				//Class.forName(dbClassName);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public static void close() {
		try {
			if(ps!=null)
			ps.close();
			if(conn!=null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn = null;
		}
	}
	/**
	 * sql语句的实现
	 * @param sql
	 * @return
	 */
	//[]params，通过?赋值方式可以防止漏洞注入方式，保证安全性
	public static ResultSet executeQuery(String sql,String []params) {
		ResultSet rs = null;
		if(conn==null)
			new DataBaseU();
		try {
			ps = conn.prepareStatement(sql);
			//对sql的参数赋值
			for(int i=0;i<params.length;i++)
			{
				ps.setString(i+1, params[i]);
			}
			//执行查询
			rs=ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//返回结果集
		return rs;
	}
	
	public static int executeUpdate(String sql) {
		if(conn==null)
			new DataBaseU();
		int sum = 0;
		try {
			ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				sum=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//返回结果集
		return sum;
	}
	
	public static boolean updateExecete(String sql,String []params){
		boolean b=true;
		if(conn==null)
			new DataBaseU();
		try {
			ps=conn.prepareStatement(sql);
			//对sql的参数赋值
			for(int i=0;i<params.length;i++)
			{
				ps.setString(i+1, params[i]);
			}
			//执行查询
			if(ps.executeUpdate()!=1)
			{
				b=false;
			}
		} catch (Exception e) {
			b=false;
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			close();
		}
		
		//返回结果集
		return b;
		
	}
	
	public  static user getUserInf(String str) {
		user ui = new user();
		String[]params={str};
		String sql= "select *  from userinfo where userid = ?";
		ResultSet rs = executeQuery(sql, params);
		try {
			while (rs.next()) {
				ui.setuser(rs.getString("userid"));
				ui.setemail(rs.getString("uemail"));
				ui.setName(rs.getString("uname"));
				ui.setTel(rs.getString("utel"));
				ui.settime(rs.getString("ulogtime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		close();
		return ui;
	}
	
	
	public static boolean updateClassResoures(String []params) {
		String sql = "update classresources set ? = 0 where classid = ?";
		boolean t = updateExecete(sql,params);
		return t;
	}
	
	public static boolean deleteApply(String []params) {
		String sql = "delete from applyclass where applyid = ?";
		boolean t = updateExecete(sql,params);
		return t;
	}

	public static String getSchoolBegin() {
		// TODO Auto-generated method stub
		String sql = "select top 1*  from schoolyear where 1 = ?";
		String []p = {"1"};
		ResultSet rs = executeQuery(sql,p);
		String names ="";
		try {
			while (rs.next()) {
				names = rs.getString("begint");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public static String getApplyNum(String []p) {
		// TODO Auto-generated method stub
		String sql = "select applynum from clasapply where classid = ?";
		ResultSet rs = executeQuery(sql,p);
		String names ="";
		try {
			while (rs.next()) {
				names = rs.getString("applynum");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public static boolean insertApply(String []params) {
		String sql = "insert into applyclass values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean t = updateExecete(sql,params);
		return t;
	}
	
	public static boolean changeClassR(String []params,String s) {
		String sql = "update classresources set "+s+"= ? where classid = ? and weeknum = ?";
		boolean t = updateExecete(sql,params);
		return t;
	}
	
	public static boolean changeClassApply(String []params) {
		String sql = "update clasapply set applynum = applynum+1 where classid = ? ";
		boolean t = updateExecete(sql,params);
		return t;
	}
}
