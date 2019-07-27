package com.hjh.jframe;

import java.awt.Frame;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.hjh.db.DataBase;
import com.hjh.jframe1.MainApp_1;
import com.hjh.localized.Encript;
import com.hjh.object.Operater;


public class LoginJFrame extends JFrame{
	
	private JButton close,min;  //最小化，关闭
	//private JFrame jf = new JFrame();
	private JLabel bg,title;  //背景 标题
	private JButton remind,forget,register,signIn;//记住密码，忘记密码，注册，登录
	private JTextField userName;   //账号框
	private JPasswordField passWord;  //密码框
	private List<ImageIcon> list= new ArrayList<ImageIcon>();  //图片数组
	private MyListener ml;  //按钮点击监听内部类
	private static Operater user;
	private int judge = 0;  //判断是否记住密码
	//获得本机名
	private InetAddress inetAddr = InetAddress.getLocalHost();
	private String computerName = inetAddr.getHostName();
	private javax.swing.Timer timer = new javax.swing.Timer(18, ml);
	private LoadingJFrame ej;
	private MainApp ma;
	
//--------------------------------------------------------------------------------
	public LoginJFrame() throws UnknownHostException {
		getImg();
		showLoginUI();  //注册主界面
		new SetDragable(this);  //可拖动界面
	}
	
	//--------------------------------------------------------------------------------	
	private void setRemind() throws UnknownHostException {
		// TODO Auto-generated method stub
			
			user = DataBase.comparewithname(computerName);
			if(user.getUser()!=null) {
				remind = Tool.setJBtView(this,list.get(2));
				remind.setBounds(140,215,list.get(2).getIconWidth(),list.get(2).getIconHeight());
				userName.setText(user.getUser());
				passWord.setText(Encript.convertMD5(user.getPassword()));
				
			}else {
				remind = Tool.setJBtView(this,list.get(0));
				remind.setBounds(140,215,list.get(0).getIconWidth(),list.get(0).getIconHeight());
			}
			remind.addMouseListener(ml);
			remind.addActionListener(ml);
	}
	//--------------------------------------------------------------------------------
	private void getImg() {
		// TODO Auto-generated method stub
		for(int i =1;i<11;i++){
			ImageIcon imag = new ImageIcon("imag/sign-in/center"+i+".png");
			list.add(imag);
		}
	}
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	public void showLoginUI() throws UnknownHostException {
		
		this.setSize(540, 420);
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setLocationRelativeTo(null);//相对位置  中间位置
		this.setLayout(null);//布局
		this.setUndecorated(true); //装饰  去除窗体u
		//this.setAlwaysOnTop(true); //顶部窗口
		
		
		ml = new MyListener();
		//顶部初始
		close = Tool.setclose(this, 540);
		close.addMouseListener(ml);
		close.addActionListener(ml);
		min = Tool.setmin(this, 540);
		min.addMouseListener(ml);
		min.addActionListener(ml);
		
		//账号
		userName = Tool.setJTFView(this, "输入账号",1,18);
		userName.setBounds(200,128,180,32);
		userName.addMouseListener(ml); 
		
		//密码  
		passWord = Tool.setJPFView(this,"输入密码",1,18);
		passWord.setBounds(200,175,180,35);
		passWord.addMouseListener(ml);
		/*passWord.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn.setIcon(list.get(9));
					signIn.doClick(100);
					signIn.setIcon(list.get(8));
				}
			}
		});*/
		
		//忘记密码
		forget = Tool.setJBtView(this,list.get(4));
		forget.setBounds(300,215,list.get(5).getIconWidth(),list.get(5).getIconHeight());
		forget.setRolloverIcon(list.get(5));
		forget.addMouseListener(ml);
		forget.addActionListener(ml);
		
		//注册
		register = Tool.setJBtView(this,list.get(6));
		register.setBounds(18,380,list.get(6).getIconWidth(),list.get(6).getIconHeight());
		register.setRolloverIcon(list.get(7));
		register.addMouseListener(ml);
		register.addActionListener(ml);
		
		//登录按钮
		signIn = Tool.setJBtView(this, list.get(8));
		signIn.setBounds(145,245,list.get(8).getIconWidth(),list.get(8).getIconHeight());
		signIn.setRolloverIcon(list.get(9));
		signIn.addActionListener(ml);
		
		//记住密码
		setRemind();
	
		setBackground();  //设置背景
		
		
		this.setVisible(true);//是否可见
		
		
	}
	
//--------------------------------------------------------------------------------
	private void setBackground() {
		bg = new JLabel();
		bg.setIcon(new ImageIcon("imag/sign-in/background/bg.png"));
		bg.setBounds(0, 0, 540, 420);
		this.add(bg);
	}
//--------------------------------------------------------------------------------
	public JFrame getJFrame () {
		return this;
	}
//--------------------------------------------------------------------------------
	public static void main(String[] args) throws UnknownHostException {
		new LoginJFrame();
		
	}
//--------------------------------------------------------------------------------
	/*public void dispose() {
		// TODO Auto-generated method stub
		this.dispose();
	}*/
//--------------------------------------------------------------------------------
	public String getUserText() {
		return userName.getText();
	}
//--------------------------------------------------------------------------------
	public String getPassword() {
			return passWord.getText();
	}
	
	public static Operater getUser() {
		return user;
	}
	public static void setUser(Operater user) {
		LoginJFrame.user = user;
	}

//--------------------------------------------------------------------------------
	private class MyListener implements ActionListener,MouseListener{

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==remind) { //进入记住密码 实现状态改变
			if(remind.getIcon().toString().equals("imag/sign-in/center1.png")) {
				remind.setIcon(list.get(1));
			}else {
				remind.setIcon(list.get(3));
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==userName) {	//账号框
			if(userName.getText().equals("输入账号"))
			userName.setText(null);
		}
		
		if(e.getSource()==passWord) {	//密码框
			passWord.setText(null);
		}
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {//离开记住密码 实现状态改变
		// TODO Auto-generated method stub
		if(e.getSource()==remind) {
			if(remind.getIcon().toString().equals("imag/sign-in/center1.png")||remind.getIcon().toString().equals("imag/sign-in/center2.png")) {
				remind.setIcon(list.get(0));
			}else {
				remind.setIcon(list.get(2));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==close) {	// 点击关闭
			System.exit(0);
		}
		
		if(e.getSource()==min) {	//最小化
			getJFrame().setExtendedState(JFrame.ICONIFIED);
		}
		
		if(e.getSource()==forget) {
			//new forget();
			RegisterForgetJFrame ru = new RegisterForgetJFrame(0);
		}
		
		if(e.getSource()==remind) {
			//方法实现
			//......
				if(remind.getIcon().toString().equals("imag/sign-in/center2.png")) {//状态改变
					remind.setIcon(list.get(3));
					judge = 1;
				}else {
					remind.setIcon(list.get(0));
					DataBase.removeRemind(computerName);
				}
		}
		
		if(e.getSource()==register) {
			RegisterForgetJFrame ru = new RegisterForgetJFrame(1);
			JFrame te = ru.getJFrame();
			te.addWindowListener(new WindowAdapter(){  //实现弹出窗口后 旧窗口不可操作
				public void windowOpened(WindowEvent e) {  
					getJFrame().setEnabled(false);
				}
				public void windowClosed(WindowEvent e) {
					getJFrame().setEnabled(true);
				}
			});
		}
		
	
		if(e.getSource()==signIn) {
			signIn.setIcon(list.get(9));
			user = DataBase.check(getUserText(),Encript.md5(getPassword()));
			System.out.println(user.getGrade()+user.getUser()+"ffffff");
			//jf.setVisible(false);
			if (user.getUser() != null) {
				try {
					//ej.closeJF();
					
					try
				    {
						UIManager.put("TabbedPane.tabAreaInsets"
								, new javax.swing.plaf.InsetsUIResource(3,0,2,0));
						BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
						UIManager.put("RootPane.setupButtonVisible", false);
						BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
						org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
						if(user.getGrade().equals("2")) {
							new MainApp(user);;	
						}else {
							new MainApp_1(user);
						}
				    }
				    catch(Exception e1)
				    {
				    	e1.printStackTrace();
				        //TODO exception
				    }
					if(judge == 1) {
						try {
							DataBase.writeRemind(computerName, getUserText(), Encript.convertMD5(getPassword()));
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				dispose();
			} else {
				
				JOptionPane.showMessageDialog(null,"登录失败"); 
			}
			
		}

	}
	}
}
