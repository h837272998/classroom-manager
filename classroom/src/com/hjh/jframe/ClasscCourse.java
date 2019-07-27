package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hjh.db.DataBase;
import com.hjh.jframe.ClassResources.MyListener;
import com.hjh.object.classCourseModel;
import com.hjh.object.classModel;

public class ClasscCourse  extends JPanel{
	private classCourseModel ccm;
	private JTable jt;
	private JScrollPane jsp;
	private JPanel jp1,jp2,jp3;
	private JLabel jl1,jl2,jl3,jl4;
	private JTextField jtf1,jtf2;
	private MyButton mb1,delete;
	private JRadioButton  jrb;
	private MyListener ml;  //按钮点击监听内部类
	
	public ClasscCourse() {
		ml = new MyListener();
		this.setLayout(new BorderLayout());
		
		center1();
		center2();
		center3();
		
		this.add(jp1,"North");
		this.add(jp2,"Center");
		this.add(jp3, "South");
		this.setVisible(true);
	}

	private void center1() {
		// TODO Auto-generated method stub
		jp1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jl1 = Tool1.setJLbView(jp1, "检索:  ", "black", 16);
		jl2 = Tool1.setJLbView(jp1, "按教室名:  ", "black", 14);
		jtf1 = Tool1.setJTFView(jp1, "杨2", 0, 14,5);
		jtf1.addFocusListener(ml);
		jl3 = Tool1.setJLbView(jp1, "按周数:  ", "black", 14);
		jtf2 = Tool1.setJTFView(jp1, "1", 0, 14,3);
		jtf2.addFocusListener(ml);
		mb1 = new MyButton("  查 询   ",1);
		mb1.addActionListener(ml);
		delete = new MyButton("  清空表格   ",3);
		delete.addActionListener(ml);
		//mb1.addActionListener(ml);
		jp1.add(mb1);
		jp1.add(delete);
	}

	private void center2() {
		// TODO Auto-generated method stub
		jp2=new JPanel(new BorderLayout());
		ccm = new classCourseModel();
		ccm.model();
		jt = new JTable(ccm);
		jt.getColumnModel().getColumn(0).setMinWidth(50);
		jsp = new JScrollPane(jt);
		jp2.add(jsp);
	}

	private void center3() {
		// TODO Auto-generated method stub
		jp3=new JPanel(new BorderLayout());
		int t = ccm.getRowCount();
		jl4 = Tool.setJLbView(jp3, "表中共："+t+"条教室课表", "black", 14);
	}
	public class MyListener implements ActionListener,FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jtf1) {
				if(!jtf1.getText().equals("")) {
					jtf1.setText(null);
				}
			}
			
			if(e.getSource()==jtf2) {
				if(!jtf2.getText().equals("")) {
					jtf2.setText(null);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==mb1) {
				if(jtf1.getText().trim().equals("")&&jtf2.getText().trim().equals("")){
					ccm = new classCourseModel();
					ccm.model(0,jtf1.getText().trim(),jtf2.getText().trim());
					ccm.model();
					jt.setModel(ccm);
					jt.getColumnModel().getColumn(0).setMinWidth(50);
					jl4.setText("表中共："+ccm.getRowCount()+"条教室课表");
				}else {
					ccm = new classCourseModel();
					ccm.model(1,jtf1.getText().trim().toString(),jtf2.getText().trim());
					ccm.model();
					jt.setModel(ccm);
					jt.getColumnModel().getColumn(0).setMinWidth(50);
					jl4.setText("表中共："+ccm.getRowCount()+"条教室课表");
				}
			}
			
			if(e.getSource()==delete) {
				int n = JOptionPane.showConfirmDialog(null, "确认清空表吗?谨慎操作", "清空确认", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					int t = DataBase.deletetable("classresources");
					JOptionPane.showMessageDialog(null,"共删除"+t+"条教室课表记录！"); 
				} 
		}
		}
	}
}
