package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hjh.db.DataBase;
import com.hjh.object.applyModel;
import com.hjh.object.classCourseModel;

public class ApplyClass extends JPanel{
	private MyListener ml;
	private applyModel am;
	private JPanel jp1,jp1_0,jp1_1,jp1_2,jp1_3,jp1_4,jp1_5,jp2,jp3,jp3_1;	
	private JLabel jl1,jl2,jl3,jl4,jl5;
	private JTable jt;
	private JScrollPane jsp;
	private MyButton mb1,mb2,delete1,delete2;
	private JTextField jtf1,jtf2,jtf3;
	private JCheckBox  jcb1,jcb2;
	
	public ApplyClass(){
		ml = new MyListener();
		this.setLayout(new BorderLayout());
		
		center1();
		center2();
		center3();
		
		this.add(jp1,"West");
		this.add(jp2,"Center");
		this.add(jp3, "South");
		this.setVisible(true);
	}

	private void center1() {
		// TODO Auto-generated method stub
		jp1=new JPanel(new GridLayout(9, 1, 10, 30));
		jp1_0 = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jl1 = Tool1.setJLbView(jp1_0, "     检索:  ", "black", 16);
		jp1.add(jp1_0);
		jp1_1 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jcb1 = new JCheckBox("按时间",false);
		jcb1.addItemListener(ml);
		jcb2 = new JCheckBox("只显示未来",false);
		jcb2.addItemListener(ml);
		jp1_1.add(jcb1);
		jp1_1.add(jcb2);
		jp1.add(jp1_1);
		jp1_2 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jl2 = Tool1.setJLbView(jp1_2, "教室*: ", "black", 14);
		jtf1 = Tool1.setJTFView(jp1_2, "杨楼201", 0, 14,5);
		jtf1.addFocusListener(ml);
		jp1.add(jp1_2);
		jp1_3 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jl3 = Tool1.setJLbView(jp1_3, "周数  : ", "black", 14);
		jtf2 = Tool1.setJTFView(jp1_3, "1-25", 0, 14,5);
		jtf2.addFocusListener(ml);
		jtf2.setEnabled(false);
		jp1.add(jp1_3);
		jp1_4 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jl4 = Tool1.setJLbView(jp1_4, "星期  : ", "black", 14);
		jtf3 = Tool1.setJTFView(jp1_4, "1-7", 0, 14,5);
		jtf3.addFocusListener(ml);
		jtf3.setEnabled(false);
		jp1.add(jp1_4);
		jp1_5 = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		mb1 = new MyButton("  查询  ");
		mb1.addActionListener(ml);
		jp1_5.add(mb1);
		mb2 = new MyButton("  所有  ");
		mb2.addActionListener(ml);
		jp1_5.add(mb2);
		jp1.add(jp1_5);
	}

	private void center2() {
		// TODO Auto-generated method stub
		jp2=new JPanel(new BorderLayout());
		am = new applyModel();
		am.model();
		jt = new JTable(am);
		jt.getColumnModel().getColumn(0).setMinWidth(50);
		jsp = new JScrollPane(jt);
		jp2.add(jsp);
	}

	private void center3() {
		// TODO Auto-generated method stub
		jp3=new JPanel(new BorderLayout());
		int t = am.getRowCount();
		jl5 = Tool.setJLbView(jp3, "表中共："+t+"条申请记录", "black", 14);
		jp3_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT,30,10));
		delete1 = new MyButton("删除过去的申请");
		delete1.addActionListener(ml);
		jp3_1.add(delete1);
		delete2 = new MyButton("删除所有的申请");
		delete2.addActionListener(ml);
		jp3_1.add(delete2);
		jp3.add(jl5,"North");
		jp3.add(jp3_1,"South");
	}
	
	public class MyListener implements ActionListener,FocusListener, ItemListener{

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
			
			if(e.getSource()==jtf3) {
				if(!jtf3.getText().equals(""))
					jtf3.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==mb1) {
				if(jcb1.isSelected()) {
					if(!Tool1.Zhengze(jtf2.getText().trim(), "^\\d+$")) {
						JOptionPane.showMessageDialog(null,"周数填写错误！"); 
						return;
					
					}
				
					if(!Tool1.Zhengze(jtf3.getText().trim(), "[1-7]")) {
						JOptionPane.showMessageDialog(null,"星期时间填写错误！"); 
						return;
					}
				}
				String[] str = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
				
				am = new applyModel();
				if(jcb1.isSelected()==false&&jcb2.isSelected()==false) {
					am.model(0, 0, jtf1.getText().trim(), "", "");
					am.model();
				}else if(jcb1.isSelected()==true&&jcb2.isSelected()==true) {
					int i = Integer.parseInt(jtf3.getText().trim());
					am.model(1, 1, jtf1.getText().trim(), jtf2.getText().trim(), str[i-1]);
					am.model();
				}else if(jcb1.isSelected()==false&&jcb2.isSelected()==true) {
					am.model(0, 1, jtf1.getText().trim(), "", "");
					am.model();
				}else if(jcb1.isSelected()==true&&jcb2.isSelected()==false) {
					int i = Integer.parseInt(jtf3.getText().trim());
					am.model(1, 0, jtf1.getText().trim(), jtf2.getText().trim(), str[i-1]);
					am.model();
				}
				jt.setModel(am);
				jl5.setText("表中共："+am.getRowCount()+"条申请记录");
			}
			
			if(e.getSource()==mb2) {
				am = new applyModel();
				am.model();
				jt.setModel(am);
				jl5.setText("表中共："+am.getRowCount()+"条申请记录");
			}
			
			if(e.getSource()==delete1) {
				int n = JOptionPane.showConfirmDialog(null, "确认删除过去申请的表?谨慎操作", "清空确认", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					int t = DataBase.applyTableDelete("delete from applyclass  where endtime - GETDATE()>=0");
					JOptionPane.showMessageDialog(null,"共删除"+t+"条记录！"); 
				} 
			}
			
			
			if(e.getSource()==delete2) {
				int n = JOptionPane.showConfirmDialog(null, "确认删除所有申请的表?谨慎操作", "清空确认", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					int t = DataBase.applyTableDelete("truncate table applyclass");
					JOptionPane.showMessageDialog(null,"共删除"+t+"条记录！"); 
				} 
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jcb1) {
				if(jcb1.isSelected()) {
					jtf2.setEnabled(true);
					jtf3.setEnabled(true);
				}else {
					jtf2.setEnabled(false);
					jtf3.setEnabled(false);
				}
			}
		}
		
	}
}
