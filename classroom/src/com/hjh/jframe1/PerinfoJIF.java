package com.hjh.jframe1;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hjh.db.DataBase;
import com.hjh.jframe.MyButton;
import com.hjh.jframe.Tool;
import com.hjh.jframe.Tool1;
import com.hjh.jframe.PersonalInfo.MyListener;
import com.hjh.object.user;

public class PerinfoJIF extends JInternalFrame{
	private MyButton mb1;
	private JPanel jp1,jp2,jp3,jp4,jp5,centercard;
	private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	private MyListener ml;  //按钮点击监听内部类
	private JTextField jtf1,jtf2;
	private user ui;
	private JTabbedPane tabbedPane;
	private int width,height,windowCount;
	
	
	public PerinfoJIF(user u,int width, int height,int windowCount) {
		this.setTitle("个人信息");
		this.setClosable(true);
		this.setMaximizable(false);
		this.setIconifiable(true);
		this.setResizable(true);
		//this.setBounds(20*(windowCount%10), 50*(windowCount%10), width, height);
		
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.width = width;
		this.height = height;
		this.windowCount = windowCount;
		ml = new MyListener();
		ui = u;
		
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
		
		tabbedPane.addTab("修改信息", jp2);
		tabbedPane.addChangeListener(ml);
		this.add(tabbedPane);
		
		Launch();
	}
	
	public void Launch() {
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = 40; i < height; i += 2) {
                    //this.setSize(200, i);
                	getJIF().setBounds(20*(windowCount%10), 50*(windowCount%10), width, i);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
 
            }
        });
        t.start();//启动线程
	}
	
	public JInternalFrame getJIF() {
		return this;
		
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
