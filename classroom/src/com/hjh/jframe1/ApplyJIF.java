package com.hjh.jframe1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hjh.db.DataBaseU;
import com.hjh.jframe.MyButton;
import com.hjh.jframe.Tool1;
import com.hjh.jframe1.ApplyinfoJIF.MyListener;
import com.hjh.object.apply;
import com.hjh.object.weekAndWeek;

public class ApplyJIF  extends JInternalFrame {
	private int width,height;
	private JPanel center,jp1;
	private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8,jl9,jl10,jl11,jl12;
	private JTextField jf1,jf2,jf3,jf6,jf7,jf8,jf9,jf10,jf11,jf12;
	private JComboBox jcb1,jcb2,jcb3;
	private CalendarPanel cdp1,cdp2;
	private MyButton mb1,mb2,mb3,mb4;
	String []str1 = {"一","二","三","四","五","六","日"};
	String []str2 = {"1","2","3","4","5"};
	private MyListener ml;
	private int judge = 0;
	private apply ay;
	private ArrayList<String> select= new ArrayList<>();
	ArrayList<JCheckBox> jcb = new ArrayList<>();
	
	public void setmodel1(int judge) {
		// TODO Auto-generated method stub
		jf1.setEnabled(true);
		jf1.addFocusListener(ml);
		jf9.setEnabled(true);
		jf9.setText("100/150/200");
		jcb2.setEnabled(true);
		String []t = {"100","150","200"};
		jcb3 = new JComboBox(t);
		this.add(jcb3);
		jcb3.setBounds(100, 240, 120, 30);
		jf9.setVisible(false);
		this.judge = judge;
		
		mb3 = new MyButton("自动选择");
		mb3.addActionListener(ml);
		this.add(mb3);
		mb3.setBounds(190, 390, 75, 20);
	}
	
	public void setmodel(int j, ArrayList<String> s) {
		this.judge = j;
		this.select = s;
		jf3.setText("");
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for(int i = 0;i<select.size();i++) {
			jcb.add(new JCheckBox(select.get(i),false));
			System.out.print(select.get(i));
			jcb.get(i).addActionListener(ml);
			jp1.add(jcb.get(i));
		}
		jp1.setBounds(10, 350, select.size()*60, 30);
		this.add(jp1);
	}
	public ApplyJIF(apply ay,int w,int h, int judge) {
		this.setTitle("申请");
		this.setClosable(true);
		this.setMaximizable(false);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setBackground(Color.WHITE);
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ml = new MyListener();
		//this.setSize(width, height);
		this.width = w/3;
		this.height = h - h/3;
		this.judge = judge;
		this.ay = ay;
		this.setLayout(null);
		
		jl1 = Tool1.setJLbView(this, "开始时间 ","black", 12);
		jf1 = Tool1.setJTFView(this, ay.getBegintime(), 0, 12 , 8);
		jf1.setEnabled(false);
		cdp1 = new CalendarPanel(jf1,"yyyy-MM-dd");
		jl1.setBounds(10, 10,60, 30);
		jf1.setBounds(100, 10, 120,30);
		cdp1.initCalendarPanel();
		this.add(cdp1);
		
		jl2 = Tool1.setJLbView(this, "结束时间 ","black", 12);
		jl2.setBounds(250, 10, 60, 30);
		jf2 = Tool1.setJTFView(this, ay.getEndtime(), 0, 12 , 8);
		jf2.setEnabled(false);
		jf2.setBounds(350,10,120,30);
		cdp2 = new CalendarPanel(jf2,"yyyy-MM-dd");
		cdp2.initCalendarPanel();
		this.add(cdp2);
		//jf2.setBounds(x, y, w, h);
		
		jl3 = Tool1.setJLbView(this, "周次    ","black", 14);
		jf3 = Tool1.setJTFView(this, ay.getWeeknum(), 0, 14 , 8);
		jf3.setEnabled(false);
		jl3.setBounds(10, 60, 60, 30);
		jf3.setBounds(100, 60, 120, 30);
		
		jl4 = Tool1.setJLbView(this, "星期   ","black", 14);
		jcb1 = new JComboBox(str1);
		for(int i = 0;i<str1.length;i++) {
			if(str1[i].equals(ay.getWeeknum()))
				jcb1.setSelectedIndex(i);
		}
		jcb1.setEnabled(false);
		this.add(jcb1);
		jl4.setBounds(250, 60, 60, 30);
		jcb1.setBounds(350, 60, 120, 30);
		
		jl5 = Tool1.setJLbView(this, "节次  ","black", 14);
		jcb2 = new JComboBox(str2);
		for(int i = 0;i<str2.length;i++) {
			if(str2[i].equals(ay.getPartnum()))
				jcb2.setSelectedIndex(i);
		}
		jcb2.setEnabled(false);
		this.add(jcb2);
		jl5.setBounds(10, 120, 60, 30);
		jcb2.setBounds(100, 120, 120, 30);
		
		jl6 = Tool1.setJLbView(this, "活动名称","black", 14);
		jf6 = Tool1.setJTFView(this, "", 0, 14 , 8);
		jl6.setBounds(250, 120, 60, 30);
		jf6.setBounds(350, 120, 120, 30);
		
		jl7 = Tool1.setJLbView(this, "地址       ","black", 14);
		jf7 = Tool1.setJTFView(this, ay.getAddress(), 0, 14 , 8);
		jf7.setEnabled(false);
		jl7.setBounds(10, 180, 60, 30);
		jf7.setBounds(100, 180, 120, 30);
		
		jl8 = Tool1.setJLbView(this, "组织单位","black", 14);
		jf8 = Tool1.setJTFView(this, "", 0, 14 , 8);
		jl8.setBounds(250, 180, 60, 30);
		jf8.setBounds(350, 180, 120, 30);
		
		jl9 = Tool1.setJLbView(this, "人数上限 ","black", 14);
		jf9 = Tool1.setJTFView(this, ay.getPeolager(), 0, 14 , 8);
		jf9.setEnabled(false);
		jl9.setBounds(10, 240, 60, 30);
		jf9.setBounds(100, 240, 120, 30);
		
		jl10 = Tool1.setJLbView(this, "联系人姓名","black", 14);
		jf10 = Tool1.setJTFView(this, ay.getPeoname(), 0, 14 , 8);
		jf10.setEnabled(false);
		jl10.setBounds(250, 240, 75, 30);
		jf10.setBounds(350, 240, 120, 30);
		
		jl11 = Tool1.setJLbView(this, "联系人手机","black", 14);
		jf11 = Tool1.setJTFView(this, ay.getPeorel(), 0, 14 , 8);
		jf11.setEnabled(false);
		jl11.setBounds(10, 300, 75, 30);
		jf11.setBounds(100, 300, 120, 30);
		
		jl12 = Tool1.setJLbView(this, "申请时间 ","black", 14);
		jf12 = Tool1.setJTFView(this, ay.getApplytime(), 0, 14 , 8);
		jf12.setEnabled(false);
		jl12.setBounds(250, 300, 60, 30);
		jf12.setBounds(350, 300, 120, 30);
		
		
		
		mb1 = new MyButton("确认");
		mb2 = new MyButton("取消");
		this.add(mb1);
		this.add(mb2);
		mb1.setBounds(150, 450, 50, 20);
		mb2.setBounds(250, 450, 50, 20);
		mb1.addActionListener(ml);
		mb2.addActionListener(ml);
		Launch();
		
	}
	
	public JInternalFrame getJIF() {
		return this;
		
	}
	
	public void Launch() {
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = 40; i < height; i += 6) {
                    //this.setSize(200, i);
                	getJIF().setBounds(80,80,  width, i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
 
            }
        });
        t.start();//启动线程
	}
	
	public class MyListener implements ActionListener, FocusListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			

			if(e.getSource()==mb1) {
				if(judge==0) {//短期申请
					if(jf6.getText().equals("")==true&&jf8.getText().equals("")==true) {
						JOptionPane.showMessageDialog(null, "请填写完整的申请信息");
					}else {
						ay.setAname(jf6.getText());
						ay.setOrganize(jf8.getText());
						String classid = "";
						if(ay.getAddress().substring(0, 2).equals("杨楼")) {
							classid = "301"+ay.getAddress().substring(2, 5);
						}else {
							classid = "302"+ay.getAddress().substring(2, 5);
						}
						ay.setAddress(classid);
						String []p = {ay.getAddress()};
						ay.setApplyid(classid+DataBaseU.getApplyNum(p));
						String [] pa = {ay.getApplyid(),ay.getApplytime(),ay.getBegintime(),ay.getEndtime(),ay.getWeeknum(),ay.getSecnum(),ay.getPartnum()
								,ay.getAname(),ay.getAddress(),ay.getOrganize(),ay.getPeolager(),ay.getPeoname(),ay.getPeorel(),ay.getUserid()};
						if(DataBaseU.insertApply(pa)) {
							JOptionPane.showMessageDialog(null, "申请成功！");
							String []params = {"2",ay.getAddress(),ay.getWeeknum()};
							String []pa1 = {ay.getAddress()};
							DataBaseU.changeClassApply(pa1);
							getJIF().dispose();
						}
						//......
					}
				}else if(judge==1){//长期申请
					int t = 0;
					for(int i=0;i<select.size();i++) {
						if(jcb.get(i).isSelected()) {
							t++;
						}
					}
					if(t<2) {
						JOptionPane.showMessageDialog(null, "至少选择两个周次");
						return ;
					}
					ay.setAname(jf6.getText());
					ay.setOrganize(jf8.getText());
					String classid = "";
					if(ay.getAddress().substring(0, 2).equals("杨楼")) {
						classid = "301"+ay.getAddress().substring(2, 5);
					}else {
						classid = "302"+ay.getAddress().substring(2, 5);
					}
					ay.setAddress(classid);
					
				for(int i=0;i<select.size();i++) {
					if(jcb.get(i).isSelected()) {
						String []p = {ay.getAddress()};
						ay.setApplyid(classid+DataBaseU.getApplyNum(p));
						ay.setWeeknum(select.get(i));
						String [] pa = {ay.getApplyid(),ay.getApplytime(),ay.getBegintime(),ay.getEndtime(),ay.getWeeknum(),ay.getSecnum(),ay.getPartnum()
								,ay.getAname(),ay.getAddress(),ay.getOrganize(),ay.getPeolager(),ay.getPeoname(),ay.getPeorel(),ay.getUserid()};
						DataBaseU.insertApply(pa);
						String []params = {"2",ay.getAddress(),ay.getWeeknum()};
						String []pa1 = {ay.getAddress()};
						DataBaseU.changeClassApply(pa1);
					}
				}
				JOptionPane.showMessageDialog(null, "申请成功！");
				getJIF().dispose();
				}else {
					if(jf7.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "请先点击自动选择");
						return;
					}else {
						ay.setAname(jf6.getText());
						ay.setOrganize(jf8.getText());
						String []p = {ay.getAddress()};
						ay.setApplyid(ay.getAddress()+DataBaseU.getApplyNum(p));
						String [] pa = {ay.getApplyid(),ay.getApplytime(),ay.getBegintime(),ay.getEndtime(),ay.getWeeknum(),ay.getSecnum(),ay.getPartnum()
								,ay.getAname(),ay.getAddress(),ay.getOrganize(),ay.getPeolager(),ay.getPeoname(),ay.getPeorel(),ay.getUserid()};
						if(DataBaseU.insertApply(pa)) {
							JOptionPane.showMessageDialog(null, "申请成功！");
							String []params = {"2",ay.getAddress(),ay.getWeeknum()};
							String []pa1 = {ay.getAddress()};
							DataBaseU.changeClassApply(pa1);
							getJIF().dispose();
						}
					}
				}
			}
			
			if(e.getSource()==mb2) {
				getJIF().dispose();
			}
			
			if(e.getSource()==mb3) {
				if(jf3.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "请选择好日期");
					return;
				}
				String t1 = jcb1.getSelectedItem().toString()+jcb2.getSelectedItem().toString();
				String sql = "select class.classid from class,classresources where weeknum = ? and "+t1+" = 0 and peonumber = ? "
						+ "and class.classid = classresources.classid";
				String []p = {jf3.getText().trim(),jcb3.getSelectedItem().toString()};
				ResultSet rs = DataBaseU.executeQuery(sql, p);
				ArrayList<String> select = new ArrayList<>();
				try {
					while (rs.next()) {
						select.add(rs.getString("classid"));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				int i = (int) (Math.random()*select.size());
				ay.setBegintime(jf1.getText());
				ay.setEndtime(jf1.getText());
				ay.setWeeknum(jf3.getText());
				ay.setSecnum("星期"+jcb1.getSelectedItem().toString());
				ay.setPartnum(jcb2.getSelectedItem().toString());
				ay.setAddress(select.get(i));
				ay.setPeolager(jcb3.getSelectedItem().toString());
				
				if(select.get(i).substring(0, 3).equals("301")) {
					jf7.setText("杨楼"+select.get(i).substring(3, 6));
				}else {
					jf7.setText("曾楼"+select.get(i).substring(3, 6));
				}
			}
			
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jf1) {
				if(!jf1.getText().equals("")) {
					weekAndWeek waw = Tool1.getDayWeek(DataBaseU.getSchoolBegin(), jf1.getText().trim());
					if(waw.getWeek()>20) {
						JOptionPane.showMessageDialog(null, "选择周数溢出");
						return;
					}
					ay.setWeeknum(""+waw.getWeek());
					ay.setSecnum(""+waw.getWeekday());
					jf3.setText(ay.getWeeknum());
					jcb1.setSelectedIndex(waw.getWeekday());
				}
			}
		}
	}

	
}
