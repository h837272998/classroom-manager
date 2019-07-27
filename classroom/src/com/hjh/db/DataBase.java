package com.hjh.db;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 存储数据库各种工作
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hjh.object.*;


public class DataBase {
	private static String dbClassName = "com.sqlserver.jdbc.Driver";
	private static String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=ClassroomResources";
	private static String dbUser = "HJH";
	private static String dbPwd ="134600";
	private static Connection conn = null;
	
	private DataBase() {
		try {
			if (conn == null) {
				//Class.forName(dbClassName);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			}
			else
				return;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public static void close() {
		try {
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
	private static ResultSet executeQuery(String sql) {
		try {
			if(conn==null)
			new DataBase();
			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}
	
	private static int executeUpdate(String sql) {
		
		try {
			if(conn==null)
				new DataBase();
			return conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());	
			return -1;
		} finally {
		}
	}
	/*
	 * 登录方法
	 */
	public static Operater check(String user, String password) {
		int i = 0;
		Operater operater=new Operater();
		String sql = "select *  from users where userid='" + user
				+ "' and upassword='" + password+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
			while (rs.next()) {
				String names = rs.getString(1);
				operater.setUser(rs.getString("userid"));
				operater.setGrade(rs.getString("ugrade"));
				operater.setPassword(rs.getString("upassword"));
				if (names != null) {
					i = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==1) {
			String sql1 = "update userinfo set ulogtime = getdate() where userid = '"+operater.getUser()+"'";
			DataBase.executeUpdate(sql1);
		}
		DataBase.close();
		return operater;
		
	}
	/**
	 * 记住密码实现
	 * 通过读取电脑名，当点击记住密码 ，成功登陆后将电脑名，账号，密码，存入数据库。下次打开界面判断电脑名是否存在数据库，有则将文字录入界面。当取消记住密码则将数据库电脑名删除
	 * @throws UnknownHostException 
	 */
	//写入数据库 电脑名等
	public static int writeRemind(String computername,String us,String passw) throws UnknownHostException {
		String sql = "insert into Cookie values('"+computername+"','"+us+"','"+passw+"',getdate())";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	//删除数据库所存电脑
	public static int removeRemind(String comname) {
		String sql = "delete from Cookie where computerid = '"+comname+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	//
	public static Operater comparewithname(String comname) {
		Operater operater=new Operater();
		String sql = "select cpassword,userid from Cookie where computerid = '"+comname+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
			while (rs.next()) {
				String names = rs.getString(1);
				operater.setUser(rs.getString("userid"));
				operater.setPassword(rs.getString("cpassword"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataBase.close();
		return operater;
		
	}
	//注册时判断用户是否存在
	public static boolean checkuser(String us){
		// TODO Auto-generated method stub
		String sql = "select userid from users where userid = '"+us+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
		if(rs.next()) {
			return true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		DataBase.close();
		return false;
	}
	//注册

	public static int insertUser(String us,String pas,String em) {
		// TODO Auto-generated method stub
		String sql = "insert into users values('"+us+"','"+pas+"','"+em+"','1',getdate())";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	//忘记密码
	public static int  updatePass(String us,String pas) {
		String sql = "update users set upassword = '"+pas+"' where userid = '"+us+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	
	public static boolean checkEmailisTrue(String us,String ema) {
		String sql = "select userid from users where userid = '"+us+"' and uemail = '"+ema+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
			if(rs.next()) {
				return true;
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			DataBase.close();
			return false;
			
	}
	//用户管理
	public static ResultSet userTable() {
		String sql = "select userinfo.userid,userinfo.upassword,userinfo.uemail,userinfo.ugrade,uname,utel,ulogtime,ucretime from userinfo,users where userinfo.userid=users.userid";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}

	public static ResultSet userTablecheck(String str) {
		// TODO Auto-generated method stub
		String sql = "select userinfo.userid,userinfo.upassword,userinfo.uemail,userinfo.ugrade,uname,utel,ulogtime,ucretime from userinfo,users where userinfo.userid=users.userid "
				+ "and users.userid = '"+str+"' or uname = '"+str+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}
	

	public static int improveG(userModel um, int selectedRow) {//提升权限
		// TODO Auto-generated method stub
		String user = um.getValueAt(selectedRow, 0).toString();
		String sql =  "update users set ugrade = 2 where userid = '"+user+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}

	public static int DownG(userModel um, int selectedRow) {  //降低权限
		// TODO Auto-generated method stub
		String user = um.getValueAt(selectedRow, 0).toString();
		String sql =  "update users set ugrade = 1 where userid = '"+user+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}


	public static int newEmail(userModel um, int selectedRow, String text) {  //修改email
		// TODO Auto-generated method stub
		String user = um.getValueAt(selectedRow, 0).toString();
		String sql =  "update users set uemail = '"+text+"' where userid = '"+user+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}

	public static int deleteuser(userModel um, int selectedRow) {  //删除账号
		// TODO Auto-generated method stub
		String user = um.getValueAt(selectedRow, 0).toString();
		String sql =  "delete from users where userid = '"+user+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	
	public static int writeClass(String classid, int week,int[] a) {
		String sql = "insert into classresources values('"+classid+"','"+week+"','"+a[0]+"','"+a[1]+"','"+a[2]+"','"+a[3]+"','"+a[4]+"','"+a[5]+"','"+a[6]+"','"+a[7]+"','"+a[8]+"'"
				+ ",'"+a[9]+"','"+a[10]+"','"+a[11]+"','"+a[12]+"','"+a[13]+"','"+a[14]+"','"+a[15]+"','"+a[16]+"'"
				+ ",'"+a[17]+"','"+a[18]+"','"+a[19]+"','"+a[20]+"','"+a[21]+"','"+a[22]+"','"+a[23]+"','"+a[24]+"'"
				+ ",'"+a[25]+"','"+a[26]+"','"+a[27]+"','"+a[28]+"','"+a[29]+"','"+a[30]+"','"+a[31]+"','"+a[32]+"'"
				+ ",'"+a[33]+"','"+a[34]+"')";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	
	}
	//获得学期 周末数
	public static String getWeeknum(String yer) {
		Operater operater=new Operater();
		String sql = "select weeknum from schoolyear where syear = '"+yer+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		String names = "";
		try {
			while (rs.next()) {
				names = rs.getString("weeknum");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataBase.close();
		return names;
		
	}

	public static ResultSet buildTable() {
		// TODO Auto-generated method stub
		String sql = "select buildid,buildname,classnumber from building";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}
	
	public static ResultSet classTable() {
		// TODO Auto-generated method stub
		String sql = "select classid,classname,peonumber,buildid from class";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}

	public static ResultSet classTablecheck(String str) {
		// TODO Auto-generated method stub
		String sql = "if('"+str+"'='杨' or '"+str+"'='杨楼') select classid,classname,peonumber,buildid from class where buildid = '301' " + 
				"else if('"+str+"'='曾' or '"+str+"'='曾楼') select classid,classname,peonumber,buildid from class where buildid = '302' " + 
				"else select classid,classname,peonumber,buildid from class where classname = '"+str+"' or classid = '"+str+"' or classid = '301'+'"+str+"' or classid = '302'+'"+str+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}
	
	public static ResultSet classCourseTable() {
		String sql = "select * from classresources";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}
	
	public static ResultSet classCourseTablecheck(String s1,String s2) {
		String sql = "declare @s varchar(3) " + 
				"if(SUBSTRING('"+s1+"',0,2)='杨') set @s = '301' " + 
				"else if(SUBSTRING('"+s1+"',0,2)='曾') set @s = '302' " + 
				"if(SUBSTRING('"+s1+"',3,4) = '' and '"+s2+"' = '') select * from classresources where classid like @s+SUBSTRING('"+s1+"',2,3)+'%' " + 
				"else if('"+s2+"'='') select * from classresources where classid = @s+SUBSTRING('"+s1+"',2,6) " + 
				"else if(SUBSTRING('"+s1+"',3,4)='') select * from classresources where classid like @s+SUBSTRING('"+s1+"',2,3)+'%' and weeknum like '"+s2+"' " + 
				"else select * from classresources where classid like @s+SUBSTRING('"+s1+"',2,6) and weeknum like '"+s2+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}

	public static schoolYearM getYear() {
		// TODO Auto-generated method stub
		int i = 0;
		schoolYearM sym=new schoolYearM();
		String sql = "select top 1*  from schoolyear";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
			while (rs.next()) {
				String names = rs.getString(1);
				sym.setYear(rs.getString("syear"));
				sym.setWeek(rs.getString("weeknum"));
				sym.setBegin(rs.getString("begint"));
				sym.setEnd(rs.getString("endt"));
				if (names != null) {
					i = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataBase.close();
		return sym;
	}

	
	public static int deletetable(String s) {  //删除账号
		// TODO Auto-generated method stub
		String sql =  "truncate table "+s+"";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}
	
	public static boolean insertSchoolY(String t, String trim, String trim2, String trim3) {
		// TODO Auto-generated method stub
		//int rs = DataBase.deletetable("schoolyear");
		String sql = "truncate table schoolyear  insert into schoolyear values('"+t+"','"+trim+"','"+trim2+"','"+trim3+"')";
		int rs1 = DataBase.executeUpdate(sql);
		DataBase.close();
		if(rs1>=2) {
			return true;
		}else {
			return false;
		}
	}

	public static user getUserInf(String user) {
		// TODO Auto-generated method stub
		int i = 0;
		user ui=new user();
		String sql = "select *  from userinfo where userid = '"+user+"'";
		ResultSet rs = DataBase.executeQuery(sql);
		try {
			while (rs.next()) {
				String names = rs.getString(1);
				ui.setuser(rs.getString("userid"));
				ui.setemail(rs.getString("uemail"));
				ui.setName(rs.getString("uname"));
				ui.setTel(rs.getString("utel"));
				ui.settime(rs.getString("ulogtime"));
				if (names != null) {
					i = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataBase.close();
		return ui;
	}
	
	public static int updateInfo(String id,String name,String tel) {
		String sql =  "update userinfo set uname = '"+name+"',utel = '"+tel+"' where userid = '"+id+"'";
		int rs = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs;
	}

	public static ResultSet applyTable() {
		// TODO Auto-generated method stub
		String sql = "select * from applyclass";
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}

	public static ResultSet applyTablecheck(String sql) {
		// TODO Auto-generated method stub
		ResultSet rs = DataBase.executeQuery(sql);
		return rs;
	}
	
	public static int applyTableDelete(String sql) {
		int rs1 = DataBase.executeUpdate(sql);
		DataBase.close();
		return rs1;
	}
}
