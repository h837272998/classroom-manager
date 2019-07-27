package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hjh.db.DataBase;
import com.hjh.jframe.UserManage.MyListener;
import com.hjh.object.schoolYearM;

public class SchoolYear extends JFrame{
	private MyButton mb1,mb2,mb3,mb4;
	private JPanel jp1,jp2,jp3,centercard;
	private JLabel jl1,jl2;
	private JComboBox jcb;
	private JTextField jtf1,jtf2,jtf3;
	private MyListener ml;  //按钮点击监听内部类
	private CardLayout myCard;
	private schoolYearM sy;
	String[] bus1 = {"学期","2018-2019第一学期","2018-2019第二学期","2019-2020第一学期","2019-2020第二学期","2020-2021第一学期","2020-2021第二学期",};
	String[] str = {"0","20180","20181","20190","20191","20200","20201"};
	
	public SchoolYear() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);//相对位置  中间位置
		
		Tool1.sizeWindowOnScreen(this, 0.4,0.2);
		ml = new MyListener();
		
		jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10)); 
		mb1 = new MyButton(" 查看校历 ",1);
		mb1.addActionListener(ml);
		mb2 = new MyButton(" 当前学期 ",1);
		mb2.addActionListener(ml);
		mb3 = new MyButton(" 修改学期 ",1);
		mb3.addActionListener(ml);
		jp1.add(mb1);
		jp1.add(mb2);
		jp1.add(mb3);
		


		myCard=new CardLayout();
		centercard = new JPanel(myCard);
		
		
		
		
		jl1 = new JLabel("        614");
		jl1.setFont(new Font("微软雅黑",Font.ITALIC,16));
		
		jp2 = new JPanel(new BorderLayout());
		
		jcb = new JComboBox(bus1);
		
		jp2.add(jcb,"North");
		jp3 = new JPanel(new BorderLayout());
		jtf1 = Tool1.setJTFView(jp3, "2010-09-01", 0, 16, 10);
		jtf1.addFocusListener(ml);
		//jl2 = Tool1.setJLbView(jp3, "至", "black", 16);
		jtf2 = Tool1.setJTFView(jp3, "2011-01-01", 0, 16, 10);
		jtf2.addFocusListener(ml);
		jtf3 = Tool1.setJTFView(jp2, "周数", 0, 16, 10);
		jtf3.addFocusListener(ml);
		jp3.add(jtf1,"North");
		jp3.add(jtf2,"Center");
		jp3.add(jtf3,"South");
		mb4 = new MyButton(" 确定 ",1);
		mb4.addActionListener(ml);
		jp2.add(jp3,"Center");
		jp2.add(mb4,"South");
		centercard.add(jl1,"0");
		centercard.add(jp2,"1");
		
		this.add(jp1,"North");
		this.add(centercard, "Center");
		this.setVisible(true);
	}
	
	public class MyListener implements ActionListener, FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource()==jtf1) {
				if(jtf1.getText().equals("2010-09-01")) {
					jtf1.setText("");
				}
			}
			
			if(e.getSource()==jtf2) {
				if(jtf2.getText().equals("2011-01-01")) {
					jtf2.setText("");
				}
			}
			
			if(e.getSource()==jtf3) {
				if(jtf3.getText().equals("周数")) {
					jtf3.setText("");
				}
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
				Tool1.openURLOnBrowser("http://jw.zhku.edu.cn/_data/index_lookxl.aspx");
			}
			
			if(e.getSource()==mb2) {
				myCard.show(centercard, "0");
				sy = DataBase.getYear();
				
				int temp = -1;
				for(int i=0;i<str.length;i++) {
					if(sy.getYear().equals(str[i]))
						temp = i;
				}
				jl1.setText("当前学期："+bus1[temp]+"\r\n"+"  时间："+sy.getBegin()+" 至 "+sy.getEnd()+"  共："+sy.getWeek()+"周");
			}
			
			if(e.getSource()==mb3) {
				myCard.show(centercard, "1");
			}
			
			if(e.getSource()==mb4) {
				String t = str[jcb.getSelectedIndex()];
				if(jcb.getSelectedIndex()==0||DataBase.insertSchoolY(t,jtf3.getText().trim(),jtf1.getText().trim(),jtf2.getText().trim()))
					JOptionPane.showMessageDialog(null,"修改错误检测输入"); 
				else
					JOptionPane.showMessageDialog(null,"修改成功"); 
						
			}
		}
		
	}
}
