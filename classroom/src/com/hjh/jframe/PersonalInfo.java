package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hjh.db.DataBase;
import com.hjh.jframe.SchoolYear.MyListener;
import com.hjh.object.user;

public class PersonalInfo  extends JFrame{
	private MyButton mb1;
	private JPanel jp1,jp2,jp3,jp4,jp5,centercard;
	private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	private MyListener ml;  //按钮点击监听内部类
	private CardLayout myCard;
	private JTextField jtf1,jtf2;
	private user ui;
	private JTabbedPane tabbedPane;
	
	public PersonalInfo(user u) {
		
		this.setResizable(false);
		this.setSize(300, 350);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);//相对位置  中间位置dddddddddd
		ml = new MyListener();
		ui = u;
		//myCard=new CardLayout();
		//centercard = new JPanel(myCard);
		
		mb1 = new MyButton(" 确定修改 ",1);
		mb1.addActionListener(ml);
		
		
		tabbedPane = new JTabbedPane();
		
		jp1 = new JPanel(new GridLayout(5, 1, 10, 30));
		jl1 = Tool1.setJLbView(jp1, "账号："+ui.getuser(), "black", 14);
		jl2 = Tool1.setJLbView(jp1, "邮箱："+ui.getemail(), "black", 14);
		jl3 = Tool1.setJLbView(jp1, "姓名："+ui.getName(), "black", 14);
		jl4 = Tool1.setJLbView(jp1, "手机："+ui.getTel(), "black", 14);
		jl5 = Tool1.setJLbView(jp1, "注册时间："+ui.gettime(), "black", 14);
		tabbedPane.addTab("个人信息", jp1);
		
		jp2 = new JPanel(new GridLayout(3, 1, 10, 30));
		jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jp4 = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
		jp5 = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		//jp2.setLayout();
		jl6 = Tool1.setJLbView(jp3, "姓名：",	"black", 16);
		jtf1 = Tool1.setJTFView(jp3, ui.getName(), 0, 16, 10);
		jl7 = Tool1.setJLbView(jp4, "手机：", "black", 16);
		jtf2 = Tool1.setJTFView(jp4, ui.getTel(), 0, 16, 10);
		jp5.add(mb1);
		jp2.add(jp3);
		jp2.add(jp4);
		jp2.add(jp5);
		/*jl6.setBounds(5, 5, 50, 30);
		jtf1.setBounds(60, 5, 120, 30);
		jl7.setBounds(5, 50, 50, 30);
		jtf2.setBounds(60, 50, 120, 30);
		mb1.setBounds(80, 150, 80, 30);*/
		tabbedPane.addTab("修改信息", jp2);
		tabbedPane.addChangeListener(ml);
		this.add(tabbedPane);
		this.setVisible(true);
	}
	
	public void di() {
		this.dispose();
	}
	
	public class MyListener implements ActionListener, FocusListener, ChangeListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==mb1) {
				if(jtf1.getText().equals("")||jtf2.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"输入错误，检测输入！"); 
				}else {
					if(Tool.checkTel(jtf2.getText().trim())) {
						JOptionPane.showMessageDialog(null,"手机格式错误！"); 
					}else {
						if(DataBase.updateInfo(ui.getuser(),jtf1.getText().trim(),jtf2.getText().trim())>=0) {
							JOptionPane.showMessageDialog(null,"修改成功！"); 
							di();
						}
					}
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
