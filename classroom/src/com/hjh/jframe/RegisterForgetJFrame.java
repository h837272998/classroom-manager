package com.hjh.jframe;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hjh.db.Cookie;
import com.hjh.db.DataBase;
import com.hjh.localized.Encript;




public class RegisterForgetJFrame extends JFrame{
	
	private JButton close,min;
	//private JFrame jf1 = new JFrame();
	private JButton register,verification,forget;   //注册，获得验证,忘记密码
	private JTextField userName,email,verificationText;  //账号，邮箱，验证码
	private JLabel bg;//背景
	private JLabel info5,info6,info7,info8;   //错误提示
	private boolean judgeVerification = false;   //判断是否点击获得验证码
	private JPasswordField password1;   //密码框
	private MyListener ml;   //监听
	private List<ImageIcon> list= new ArrayList<ImageIcon>();  //图片数组
	private Long time1 = (long) 0;//记录发生验证码时间
	private String str;//记录验证码
	private int[] judgeTrue = {0,0,0,0}; // 判断每一步是否正确
	private int weight; //1为注册。0为忘记
	private Cookie cook;
	File file;
	
	public RegisterForgetJFrame(int w) {
		this.setSize(452, 552);
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setLocationRelativeTo(null);//相对位置  中间位置
		this.setLayout(null);//布局
		this.setUndecorated(true); //装饰  去除窗体u
		//this.setAlwaysOnTop(true); //顶部窗口
		this.weight = w;
		file = new File("Cookie/sec.cookie");
		if(file.exists()) {
			try {
				FileInputStream fi = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fi);
				cook =  (Cookie) ois.readObject();
				time1 = cook.getTime();
			} catch (FileNotFoundException e) {
				
			}catch (IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
		}else {
			FileOutputStream fo = null;
			cook = new Cookie();
			try {
				fo = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				ObjectOutputStream oos = new ObjectOutputStream(fo);
				oos.writeObject(cook);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		getImg();
		showRegisterUI();
		new SetDragable(this);
	}
	
	private void getImg() {
		// TODO Auto-generated method stub
		for(int i =1;i<9;i++){
			ImageIcon imag = new ImageIcon("imag/sign-in/register"+i+".png");
			list.add(imag);
		}
	}

	private void showRegisterUI() {
		// TODO Auto-generated method stub
		
		ml = new MyListener();
		
		//顶部初始
		close = new JButton();
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setIcon(list.get(6));
		close.setRolloverIcon(list.get(7));
		close.setBounds(400, 40, list.get(6).getIconWidth(), list.get(6).getIconHeight());
		close.addActionListener(ml);
		this.add(close);
		
		//账号部分
		userName = Tool.setJTFView(this, "请输入账号", 1,16);//账号框
		userName.setBounds(128,193, 250, 30);
		info5 = Tool.setJLbView(this, null,"red",12);//提示文字
		info5.setBounds(85, 226, 200, 20);
		userName.addFocusListener(ml);
		
		//密码部分
		password1 = Tool.setJPFView(this, "输入密码",1,16);
		if(weight==1)
			password1.setBounds(128, 263, 250, 30);
		else
			password1.setBounds(143, 263, 250, 30);
		info6 = Tool.setJLbView(this, null,"red",12);//提示文字
		info6.setBounds(85, 296, 200, 20);
		password1.addFocusListener(ml);

		
		//邮箱部分
		email = Tool.setJTFView(this, "请输入邮箱", 1,16);
		email.setBounds(128, 122, 250, 30);
		info7 = Tool.setJLbView(this, null,"red",12);
		info7.setBounds(85, 155, 200, 20);
		email.addFocusListener(ml);

		//验证码部分
		verificationText = Tool.setJTFView(this, "输入验证码", 1,16);
		verificationText.setBounds(143, 330	, 100, 30);
		verificationText.addFocusListener(ml);
		verification = Tool.setJBtView(this,list.get(2));
		verification.setBounds(255, 335, list.get(2).getIconWidth(), list.get(2).getIconHeight());
		verification.setRolloverIcon(list.get(3));
		verification.addActionListener(ml);
		info8 = Tool.setJLbView(this, null,"red",12);
		info8.setBounds(85, 363, 200, 20);
		
		if(weight==1) {
			register = Tool.setJBtView(this, list.get(0));
			register.setBounds(135, 420,list.get(0).getIconWidth(), list.get(0).getIconHeight());
			register.setRolloverIcon(list.get(1));
			register.addActionListener(ml);
		}else {
			forget = Tool.setJBtView(this, list.get(4));
			forget.setBounds(135, 420, list.get(4).getIconWidth(), list.get(4).getIconHeight());
			forget.setRolloverIcon(list.get(5));
			forget.addActionListener(ml);
		}
		
		setBackground();
		
		this.setVisible(true);
	}

	public JFrame getJFrame () {
		return this;
	}
	
	private void setBackground() {
		bg = new JLabel();
		if(weight==1) {
			bg.setIcon(new ImageIcon("imag/sign-in/background/Registerbg1.png"));
			bg.setBounds(0, 0, this.getWidth(), this.getHeight());
		}else {
			bg.setIcon(new ImageIcon("imag/sign-in/background/Registerbg2.png"));
			bg.setBounds(0, 0, this.getWidth(), this.getHeight());
		}
		this.add(bg);
	}
	
	private class MyListener implements FocusListener,ActionListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==userName) {
				if(userName.getText().equals("请输入账号")) {
					userName.setText(null);
				}
			}
			
			if(e.getSource() == password1) {
				if(password1.getText().equals("输入密码")) {
				password1.setText(null);
				}
			}
			
			if(e.getSource() == email) {
				if(email.getText().equals("请输入邮箱")) {
					email.setText(null);
				}
			}
			
			if(e.getSource() == verificationText) {
				if(verificationText.getText().equals("输入验证码")) {
					verificationText.setText(null);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==userName) {
				if (userName.getText().equals("")){
					info5.setText("账号不能为空！");
				}else if(userName.getText().length()<6||userName.getText().length()>13){
					info5.setText("账号长度大于6小于13");
				}else if(DataBase.checkuser(userName.getText())&&weight==1){
					info5.setText("用户已存在！");
				}else {
					info5.setText("正确！");
					judgeTrue[0] = 1;
				}
				
			}
			
			if(e.getSource() == password1) {
				if(userName.getText().equals("")) {
					info6.setText("密码不能为空！");
				}else if(password1.getText().length()<6||password1.getText().length()>16){
					info6.setText("密码长度大于6小于16");
				}else {
					info6.setText("正确！");
					judgeTrue[1] = 1;
				}
			}
			
			if(e.getSource() == email) {
				if(email.getText().equals("")) {
					info7.setText("邮箱不能为空!");
				}else if(!Tool.checkEmail(email.getText())) {
					info7.setText("输入正确的邮箱格式!");
				}else {
					if(weight!=1) {
						if(judgeTrue[0]!=1) {
							info7.setText("请先输入账号！");
						}else if(!DataBase.checkEmailisTrue(userName.getText(),email.getText())) {
							info7.setText("邮箱不是注册时的邮箱!");
						}else {
							info7.setText("正确！");
							judgeTrue[2] = 1;
						}
					}else {
						info7.setText("正确！");
						judgeTrue[2] = 1;
					}
				}
			}
			
			if(e.getSource() == verificationText) {
				if(!judgeVerification) {
					info8.setText("请先获得验证码");
				}else if(verificationText.getText().equals("")) {
					info8.setText("验证码不能为空!");
				}else if(verificationText.getText().length()!=6) {
					info8.setText("验证码长度为6");
				}else {
					if(str.equals(verificationText.getText())) {  //判断验证码是否正确
						info8.setText("正确！");
						judgeTrue[3] = 1;
					}else {
						info8.setText("验证码错误！");
					}
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource()==close) {	// 点击关闭
				//jf1.dispose();
				//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				getJFrame().dispose();
			}
			
			if(e.getSource()==min) {	//最小化
				getJFrame().setExtendedState(JFrame.ICONIFIED);  //设置窗口状态  最小化
			}
			
			if(e.getSource()==verification) {
				

				if(judgeTrue[2]!=1) {
					info8.setText("先输入正确邮箱！");
				}else if((System.currentTimeMillis()-cook.getTime())/1000<=120) {
					info8.setText("请"+(120-(System.currentTimeMillis()-cook.getTime())/1000)+"秒后再获得验证码！");
				}else {
					
					cook.setTime(System.currentTimeMillis());
					judgeVerification = true;
					try {
						str = Tool.sendMail(email.getText(),"HJH的验证！！",Tool.randomNum());
						if(str.equals("error1")) {
							info8.setText("获得失败！");
						}else {
							info8.setText("获得成功！邮箱查收！");
							FileOutputStream fo = null;
							try {
								fo = new FileOutputStream(file);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							try {
								ObjectOutputStream oos = new ObjectOutputStream(fo);
								oos.writeObject(cook);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						info8.setText("获得失败！");
					}
					if(file.exists()) {
						
					}
				}
			}
			
			if(e.getSource()==register) {
				if(judgeTrue[0] == 1&&judgeTrue[1] == 1&&judgeTrue[2] == 1&&judgeTrue[3] == 1) {
						DataBase.insertUser(userName.getText(), Encript.md5(password1.getText()), email.getText());
						JOptionPane.showMessageDialog(null,"注册成功"); 
						close.doClick();
				}else {
						if(judgeTrue[0]==0) {
							userName.requestFocus();
						}
						if(judgeTrue[1]==0) {
							password1.requestFocus();
						}
						if(judgeTrue[2]==0) {
							email.requestFocus();
						}
						if(judgeTrue[3]==0) {
								verificationText.requestFocus();
						}
				}
			}
			
			if(e.getSource()==forget) {
				if(judgeTrue[0] == 1&&judgeTrue[1] == 1&&judgeTrue[2] == 1&&judgeTrue[3] == 1) {
					DataBase.updatePass(userName.getText(), Encript.md5(password1.getText()));
					//LoadingJFrame ej = new LoadingJFrame("新密码"+password1.getText());	
					JOptionPane.showMessageDialog(null,"新密码:"+password1.getText()); 
					close.doClick();
				}else {
					if(judgeTrue[0]==0) {
						userName.requestFocus();
					}
					if(judgeTrue[1]==0) {
						password1.requestFocus();
					}
					if(judgeTrue[2]==0) {
						email.requestFocus();
					}
					if(judgeTrue[3]==0) {
							verificationText.requestFocus();
					}
				}
			}
		}
	}
}
