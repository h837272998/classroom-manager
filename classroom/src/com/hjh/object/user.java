package com.hjh.object;

import java.sql.Date;

public class user {
	private String user;
	private String name;
	private String tel;
	private String email;
	private String lontime;

	public String getemail() {
		return email;
	}
	public void setemail(String e) {
		this.email = e;
	}
	
	public String gettime() {
		return lontime;
	}
	public void settime(String t) {
		this.lontime = t;
	}
	public String getuser() {
		return user;
	}
	public void setuser(String id) {
		this.user = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
