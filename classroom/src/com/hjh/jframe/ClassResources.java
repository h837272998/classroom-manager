package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hjh.jframe.UserManage.MyListener;
import com.hjh.object.buildModel;
import com.hjh.object.classModel;
import com.hjh.object.userModel;

public class ClassResources  extends JPanel{
	private buildModel bm;
	private classModel cm;
	private JTable jt1,jt2;
	private JScrollPane jsp1,jsp2;
	private JPanel jp1,jp2,jp3;
	private JLabel jl1,jl2,jl3,jl4,jl5;
	private JTextField jtf1,jtf2;
	private MyButton mb1,mb2;
	private MyListener ml;  //按钮点击监听内部类
	
	public ClassResources() {
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

	private void center3() {
		// TODO Auto-generated method stub
		jp3=new JPanel(new BorderLayout());
		int t = cm.getRowCount();
		jl2 = Tool.setJLbView(jp3, "表中共："+t+"间教室", "black", 14);
	}

	private void center2() {
		// TODO Auto-generated method stub
		
		jp2=new JPanel(new BorderLayout());
		bm = new buildModel();
		bm.model();
		jt1 = new JTable(bm);
		jsp1 = new JScrollPane(jt1);
		
		cm = new classModel();
		cm.model();
		jt2 = new JTable(cm);
		jsp2 = new JScrollPane(jt2);
		
		jp2.add(jsp1,"West");
		jp2.add(jsp2,"Center");
	}

	private void center1() {
		// TODO Auto-generated method stub
		jp1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jl1 = Tool1.setJLbView(jp1, "检索:  ", "black", 16);
		jtf1 = Tool1.setJTFView(jp1, "输入教学楼或教室名", 0, 16,18);
		mb1 = new MyButton("  查 询   ",1);
		mb1.addActionListener(ml);
		//mb1.addActionListener(ml);
		jtf1.addFocusListener(ml);
		jp1.add(mb1);
	}
	
	public class MyListener implements ActionListener,FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jtf1) {
				if(jtf1.getText().equals("输入教学楼或教室名")) {
					jtf1.setText(null);
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
				if(jtf1.getText().trim().equals("")) {
					cm = new classModel();
					cm.model(0,"");
					cm.model();
					jt2.setModel(cm);
					jl2.setText("表中共："+cm.getRowCount()+"间教室");
				}else {
					cm = new classModel();
					cm.model(1,jtf1.getText().trim().toString());
					cm.model();
					jt2.setModel(cm);
					jl2.setText("表中共："+cm.getRowCount()+"间教室");
				}
			}
		}
	}
	
}
