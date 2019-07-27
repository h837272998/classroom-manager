package com.hjh.db;

import java.io.Serializable;
import java.sql.Date;

public class Cookie  implements Serializable{
	private Long  time;
	
	public Cookie() {
		time = (long)0;
	}
	
	public Long  getTime() {
		return time;
	}
	
	public void setTime(Long str) {
		this.time = str;
	}
	
}
