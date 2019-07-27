package com.hjh.jframe1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.hjh.db.DataBaseU;
import com.hjh.jframe.MyButton;
import com.hjh.jframe.Tool;
import com.hjh.jframe.Tool1;
import com.hjh.jframe1.ApplyinfoJIF.MyListener;
import com.hjh.object.apply;
import com.hjh.object.classCourseModel_1;
import com.hjh.object.schoolYearM;
import com.hjh.object.user;

public class ClassCourseJIF  extends JInternalFrame {
	private JRadioButton jrb1,jrb2;
	private JLabel jl1,jl2,jl3,jl4,jl5;
	private JTextField jtf1,jtf2;
	private JComboBox jcb1,jcb2,jcb3;
	private MyButton mb1,mb2;
	private int width,height,windowCount;
	private JPanel jp1,jp2,jp3,jp4,jp5,jp6,sou,cen,nor;
	private ButtonGroup group;
	private MyListener ml;
	private JTable jt;
	private JScrollPane jsp;
	private classCourseModel_1 ccm;
	private JPopupMenu jppm;
	private JMenuItem jmi1,jmi2;
	private int focusedRowIndex;
	String []s = {"杨楼","曾楼"};
	String []s1 = {"一","二","三","四","五","六","日"};
	String []s2 = {"1","2","3","4","5"};
	private JDesktopPane jdp;
	private user ui;
	
	public ClassCourseJIF(user u,JDesktopPane jdp,int width, int height,int windowCount) {
		this.setTitle("空教室查询");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setResizable(true);
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui = u;
		this.width = width;
		this.height = height;
		this.windowCount = windowCount;
		this.jdp = jdp ;
		ml = new MyListener();
		
		sou = new JPanel(new GridLayout(6,1));
		jrb1 = new JRadioButton("模糊搜索",true);
		jrb2 = new JRadioButton("精确搜索");
		group =new ButtonGroup();
		group.add(jrb1);
		group.add(jrb2);
		jrb1.addActionListener(ml);	
		jrb2.addActionListener(ml);	
		jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1.add(jrb1);
		jp1.add(jrb2);
		
		jl1 = Tool1.setJLbView(jp2, "教室： ", "black", 14);
		jp2.add(jl1);
		jcb1 = new JComboBox(s);
		jp2.add(jcb1);
		jtf1 = Tool1.setJTFView(jp2, "2/201", 0	, 16, 3);
		jtf1.addFocusListener(ml);
		
		jl2 = Tool1.setJLbView(jp3, "周次： ", "black", 14);
		jtf2 = Tool1.setJTFView(jp3, "1-20", 0, 16,6);
		jtf2.addFocusListener(ml);
		jtf2.setEnabled(false);
		
		jl3 = Tool1.setJLbView(jp4, "星期： ", "black", 14);
		jcb2 = new JComboBox(s1);
		jp4.add(jcb2);
		
		jl4 = Tool1.setJLbView(jp5, "节次：", "black", 14);
		jcb3 = new JComboBox(s2);
		jcb3.setEnabled(false);
		jp5.add(jcb3);
		
		mb1 = new MyButton("查询");
		mb1.addActionListener(ml);
		mb2 = new MyButton("所有");
		mb2.addActionListener(ml);
		jp6.add(mb1);
		jp6.add(mb2);
		
		sou.add(jp1);
		sou.add(jp2);
		sou.add(jp3);
		sou.add(jp4);
		sou.add(jp5);
		sou.add(jp6);
		
		this.add(sou,BorderLayout.WEST);
		
		cen = new JPanel(new BorderLayout());
		ccm = new classCourseModel_1();
		String []p = {};
		ccm.model(3, p);
		jt = new JTable(ccm);
		jt.getColumnModel().getColumn(0).setMinWidth(50);
		jt.addMouseListener(ml);
		jt.setSelectionBackground(Color.GRAY);
		jsp = new JScrollPane(jt);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cen.add(jsp);
		this.add(cen, BorderLayout.CENTER);
		
		nor = new JPanel(new BorderLayout());
		jl5 = Tool.setJLbView(nor, "表中共："+ccm.getRowCount()+"条记录", "black", 14);
		this.add(nor, BorderLayout.SOUTH);
		jppm = new JPopupMenu();
		jmi1 = new JMenuItem("短期申请");
		jmi1.addActionListener(ml);
		jppm.add(jmi1);
		jmi2 = new JMenuItem("长期申请");
		jmi2.addActionListener(ml);
		jppm.add(jmi2);
		
		Launch();
	}
	
	public JInternalFrame getJIF() {
		return this;
		
	}
	
	public void Launch() {
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = 40; i < height; i += 5) {
                    //this.setSize(200, i);
                	getJIF().setBounds(20*(windowCount%10), 50*(windowCount%10), width, i);
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
	
	public class MyListener implements ActionListener, FocusListener, MouseListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jtf1) {
				if(!jtf1.getText().equals(""))
					jtf1.setText("");
			}
			
			if(e.getSource()==jtf2) {
				if(!jtf2.getText().equals(""))
					jtf2.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jmi1) {  //菜单短期申请
				if(jt.getValueAt(focusedRowIndex, jt.getSelectedColumn()).equals("0")) {
					if(ui.getuser().equals("")||ui.getTel().equals("")) {
						JOptionPane.showMessageDialog(null,"请完善个人信息！");
						return;
					}
					String s1 = jt.getColumnName(jt.getSelectedColumn());// 时间
					String s2 = jt.getValueAt(focusedRowIndex, 1).toString();  //第几周
					int num = (Integer.parseInt(s2)-1)*7-1;
					int sec = 0;
					String s[] = {"","一","二","三","四","五","六","日"};
					String sxq[] = {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
					for(int i = 1;i<s.length;i++) {
						if(s1.substring(0, 1).equals(s[i])) {
							num = num + i;
							sec = i;
							break;
						}
					}
					String beginschool = DataBaseU.getSchoolBegin();
					String ss =	Tool1.rollDayWeek(beginschool, num);  //所选申请的时间
					if(!Tool1.checkday(Calendar.getInstance().getTime().toLocaleString().substring(0, 8),ss)) {
						JOptionPane.showMessageDialog(null,"当前时间已经过去请选择未来@");
						return;
					}
					
					
					apply ay = new apply();
					ay.setApplytime(Calendar.getInstance().getTime().toLocaleString());
					ay.setBegintime(ss);
					ay.setEndtime(ss);
					ay.setWeeknum(s2);
					ay.setSecnum(sxq[sec]);
					ay.setPartnum(s1.substring(1,2));
					ay.setAddress(jt.getValueAt(focusedRowIndex, 0).toString());
					ay.setPeolager(jt.getValueAt(focusedRowIndex, 2).toString());
					ay.setUserid(ui.getuser());
					ay.setPeoname(ui.getName());
					ay.setPeorel(ui.getTel());
					
					
					ApplyJIF ajif1 = new ApplyJIF(ay,jdp.getWidth(),jdp.getHeight(),0);
					jdp.add(ajif1,new Integer(2));
					ajif1.show();
					getJIF().setEnabled(false);
				}else {
					JOptionPane.showMessageDialog(null,"选择一个空闲的教室");
				}
				
			}
			
			
			if(e.getSource()==jmi2) {//菜单长期申请
				if(jt.getValueAt(focusedRowIndex, jt.getSelectedColumn()).equals("0")) {
					if(ui.getuser().equals("")||ui.getTel().equals("")) {
						JOptionPane.showMessageDialog(null,"请完善个人信息！");
						return;
					}
					
					String s1 = jt.getColumnName(jt.getSelectedColumn());// 时间
					String s2 = jt.getValueAt(focusedRowIndex, 1).toString();  //第几周
					int num = (Integer.parseInt(s2)-1)*7-1;
					int sec = 0;
					String s[] = {"","一","二","三","四","五","六","日"};
					String sxq[] = {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
					for(int i = 1;i<s.length;i++) {
						if(s1.substring(0, 1).equals(s[i])) {
							num = num + i;
							sec = i;
							break;
						}
					}
					String beginschool = DataBaseU.getSchoolBegin();
					String ss =	Tool1.rollDayWeek(beginschool, num);  //所选申请的时间
					if(!Tool1.checkday(Calendar.getInstance().getTime().toLocaleString().substring(0, 8),ss)) {
						JOptionPane.showMessageDialog(null,"当前时间已经过去请选择未来@");
						return;
					}
					String sql = "select weeknum from classresources where classid = ? and "+s1+"=0 and weeknum >= "+s2+"";
					String class1 = jt.getValueAt(focusedRowIndex, 0).toString();
					if(class1.substring(0, 2).equals("杨楼")) {
						class1 = "301"+class1.substring(2, 5);
					}else {
						class1 = "302"+class1.substring(2, 5);
					}
					String []params = {class1};
					ResultSet rs = DataBaseU.executeQuery(sql, params);
					ArrayList<String> select = new ArrayList<>();
					try {
						while (rs.next()) {
							select.add(rs.getString("weeknum"));
							//System.out.println(rs.getString("weeknum"));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
					apply ay = new apply();
					ay.setApplytime(Calendar.getInstance().getTime().toLocaleString());
					ay.setBegintime(ss);
					ay.setEndtime(ss);
					ay.setWeeknum(s2);
					ay.setSecnum(sxq[sec]);
					ay.setPartnum(s1.substring(1,2));
					ay.setAddress(jt.getValueAt(focusedRowIndex, 0).toString());
					ay.setPeolager(jt.getValueAt(focusedRowIndex, 2).toString());
					ay.setUserid(ui.getuser());
					ay.setPeoname(ui.getName());
					ay.setPeorel(ui.getTel());
					
					ApplyJIF ajif1 = new ApplyJIF(ay,jdp.getWidth(),jdp.getHeight(),0);
					ajif1.setmodel(1, select);
					jdp.add(ajif1,new Integer(2));
					ajif1.show();
					getJIF().setEnabled(false);
					
					//System.out.println(select.get(2)+" "+select.size());
					
					
				}else {
					JOptionPane.showMessageDialog(null,"选择一个空闲的教室");
				}
			}
			
			if(e.getSource()==jrb1) {
				jcb3.setEnabled(false);
				jtf2.setEnabled(false);
			}
			
			if(e.getSource()==mb1) {
				if(jrb2.isSelected()&&!Tool1.Zhengze(jtf2.getText(), "^[1]?[0-9]$")) {
					JOptionPane.showMessageDialog(null,"周数范围：0-20");
					return;
				}
				if(jrb1.isSelected()) {
					ccm = new classCourseModel_1();
					String p1 = null;
					if(jcb1.getSelectedIndex()==0) {
						p1 = "301"+jtf1.getText().trim().toString();
					}else {
						p1 = "302"+jtf1.getText().trim().toString();
					}
					String []p = {p1,"",jcb2.getSelectedItem().toString()};
					ccm.model(0, p);
					jt.setModel(ccm);
					jl5.setText("表中共："+ccm.getRowCount()+"条记录");
				}else if(jrb2.isSelected()) {
					ccm = new classCourseModel_1();
					String p1 = null;
					if(jcb1.getSelectedIndex()==0) {
						p1 = "301"+jtf1.getText().trim().toString();
					}else {
						p1 = "302"+jtf1.getText().trim().toString();
					}
					String []p = {p1,jtf2.getText().trim().toString(),jcb2.getSelectedItem().toString()+jcb3.getSelectedItem().toString()};
					ccm.model(1, p);
					jt.setModel(ccm);
					jl5.setText("表中共："+ccm.getRowCount()+"条记录");
				}
			}
			
			if(e.getSource()==jrb2) {
				jcb3.setEnabled(true);
				jtf2.setEnabled(true);
			}
			
			if(e.getSource()==mb2) {
				ccm = new classCourseModel_1();
				String []p = {};
				ccm.model(3, p);
				jt.setModel(ccm);
				jl5.setText("表中共："+ccm.getRowCount()+"条记录");
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jt) {
				if(e.getButton()==java.awt.event.MouseEvent.BUTTON3) {
					if(jt.getSelectedColumn()>=3) {
					focusedRowIndex = jt.rowAtPoint(e.getPoint());
					if(focusedRowIndex == -1) return;
					jt.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
					jppm.show(jt, e.getX(), e.getY());
				
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
