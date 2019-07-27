package com.hjh.jframe;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import com.hjh.db.DataBase;
import com.hjh.object.Operater;
import com.hjh.object.schoolYearM;
import com.hjh.object.user;
import com.hjh.object.weekAndWeek;
import com.sun.mail.handlers.message_rfc822;



public class MainApp extends JFrame implements ActionListener{
	//private JFrame jf = new JFrame();
	private JLabel info,showtime;  //背景 标题
	private JLabel cJLB_1,cJLB_2,cJLB_3,cJLB_4,cJLB_5,cJLB_6,swich1,swich2;
	private Operater operater;
	private MyListener ml;  //按钮点击监听内部类
	private schoolYearM sym;
	private weekAndWeek waw;
	private user ui;
	private JPanel center1,center2;
	private JPanel centercard;
	private JMenu jm1,jm2,jm3,jm4,jm5,jm6;
	private JMenuItem jm1_1,jm1_3,jm2_1,jm2_2,jm3_1,jm3_2,jm3_3,jm4_1;
	private JMenuBar jmb;
	private Image bg1;
	private CardLayout myCard;
	private JPanel imagePanel,jp1;  
	private JPanel centerleft,centerright,botton;
	private JSplitPane jspcenter;
	private classHttpClient chc;
	//private List<ImageIcon> list= new ArrayList<ImageIcon>();  //图片数组
	private Timer t;//可定时触发Action事件
	private Thread th;
	
	public MainApp() {
		
	}
	
	public MainApp(Operater op) {
		
		this.operater = op;
		MainUI();
	}

	
	
	private void MainUI() {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(3);//  错误关闭操作
		//this.setLocationRelativeTo(null);//相对位置  中间位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ml = new MyListener();
		
		this.setTitle("class");
		Menu();
		center();
		bottom();
		this.getContentPane().add(jspcenter,"Center");
		this.getContentPane().add(botton,"South");
		Tool1.sizeWindowOnScreen(this,0.8,0.8);
		setLocationRelativeTo(null);//相对位置  中间位置
		//this.setBackground(Color.WHITE);
		this.setVisible(true);
	}


	private void center() {
		// TODO Auto-generated method stub
		centerleft = new JPanel(new BorderLayout());
		center1 = new JPanel();
		center1.setLayout(new GridLayout(6,1));
		
		cJLB_1= Tool.setJLbView(center1, "    用户管理", "gray", 20);
		cJLB_1.setEnabled(false);
		cJLB_1.addMouseListener(ml);
		center1.add(cJLB_1);
		
		cJLB_2= Tool.setJLbView(center1, "    教室资源", "gray", 20);
		cJLB_2.setEnabled(false);
		cJLB_2.addMouseListener(ml);
		center1.add(cJLB_2);
		
		cJLB_3= Tool.setJLbView(center1, "    教室课表", "gray", 20);
		cJLB_3.setEnabled(false);
		cJLB_3.addMouseListener(ml);
		center1.add(cJLB_3);
		
		cJLB_4= Tool.setJLbView(center1, "    教室爬取", "gray", 20);
		cJLB_4.setEnabled(false);
		cJLB_4.addMouseListener(ml);
		center1.add(cJLB_4);
		
		cJLB_5= Tool.setJLbView(center1, "    申请管理", "gray", 20);
		cJLB_5.setEnabled(false);
		cJLB_5.addMouseListener(ml);
		center1.add(cJLB_5);
		
		cJLB_6= Tool.setJLbView(center1, "    退出", "gray", 20);
		cJLB_6.setEnabled(false);
		cJLB_6.addMouseListener(ml);
		center1.add(cJLB_6);
		
		//centerleft.add(jp1,"East");
		centerleft.add(center1,"Center");
		//创建卡片布局  不同功能实现不同界面  互相切换
		myCard=new CardLayout();
		centerright = new JPanel(new BorderLayout());
		centercard = new JPanel(myCard);
		
		center2 = new JPanel();
		centercard.add(center2,"0");
		
		UserManage um = new UserManage();
		centercard.add(um,"1");   //用户管理 
		
		ClassResources cr = new ClassResources();
		centercard.add(cr,"2");   //教室资源 
		
		ClasscCourse cc = new ClasscCourse();
		centercard.add(cc,"3");   //教室资源 
		
		chc = new classHttpClient();//教室爬取
		centercard.add(chc,"4");
		
		
		ApplyClass ac = new ApplyClass();
		centercard.add(ac,"5");
		
		
		centerright.add(centercard,"Center");
		
		jspcenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,centerleft,centerright);
		jspcenter.setDividerLocation(150);
		jspcenter.setDividerSize(0);
	}

	private void Menu() {
		// TODO Auto-generated method stub
		jmb=new JMenuBar();
		
		jm1=Tool1.setJM(jmb, this, "系统管理", 14);
		jm1_3 = Tool1.setJMI(jm1, "校历管理", 14);
		jm1_3.addMouseListener(ml);

		jm1_1 = Tool1.setJMI(jm1,"退出",14);
		jm1_1.addMouseListener(ml);
		
		jm2 = Tool1.setJM(jmb, this, "用户管理", 14);
		jm2_2 = Tool1.setJMI(jm2, "个人信息", 14);
		jm2_2.addMouseListener(ml);
		jm2_1 = Tool1.setJMI(jm2, "用户管理", 14);
		jm2_1.addMouseListener(ml);
				
		
		jm3 = Tool1.setJM(jmb, this, "教室管理", 14);
		jm3_1 = Tool1.setJMI(jm3, "教室资源", 14);
		jm3_1.addMouseListener(ml);
		jm3_2 = Tool1.setJMI(jm3, "教室课表", 14);
		jm3_2.addMouseListener(ml);
		jm3_3 = Tool1.setJMI(jm3, "教室爬取", 14);
		jm3_3.addMouseListener(ml);
		
		jm4 = Tool1.setJM(jmb, this, "帮助", 14);
		jm4_1 = Tool1.setJMI(jm4, "HJH", 14);
		
		jmb.setUI(null);
		jmb.setBounds(5,30, 400, 30);
		
		this.setJMenuBar(jmb);
	}

	public void bottom() {
		botton =  new JPanel(new BorderLayout());
		String str= "管理员：";
		info = Tool.setJLbView(this, str+operater.getUser(), "gray",12);
		
		t = new Timer(3000, this);//每隔一秒触发ActonEvent
		sym = DataBase.getYear();
		String ti = Calendar.getInstance().getTime().toLocaleString();
		waw = Tool1.getDayWeek(sym.getBegin(), ti.substring(0, 9));
		
		showtime = Tool1.setJLbView(this, "当前时间："+ti.substring(0, 15)+
				"  第"+waw.getWeek()+"周   星期"+waw.getWeekday()+"○   ", "gray", 12);
		showtime.addMouseListener(ml);
		showtime.setEnabled(false);
		t.start();
		botton.add(showtime,"East");
		botton.add(info,"West");
	}
	
	private class MyListener implements ActionListener,MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==cJLB_1) {
				myCard.show(centercard, "1");
			}
			
			if(e.getSource()==cJLB_2) {
				myCard.show(centercard, "2");
			}
			
			if(e.getSource()==cJLB_3) {
				myCard.show(centercard, "3");
			}
			
			if(e.getSource()==cJLB_4) {
				myCard.show(centercard, "4");
			}
			
			if(e.getSource()==cJLB_5) {
				myCard.show(centercard, "5");
			}
			
			if(e.getSource()==showtime) {
				Tool1.openURLOnBrowser("http://jw.zhku.edu.cn/_data/index_lookxl.aspx");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==cJLB_1) {
				cJLB_1.setForeground( new Color(0,0,0));
				cJLB_1.setEnabled(true);
			}
			
			if(e.getSource()==cJLB_2) {
				cJLB_2.setForeground( new Color(0,0,0));
				cJLB_2.setEnabled(true);
			}
			
			if(e.getSource()==cJLB_3) {
				cJLB_3.setForeground( new Color(0,0,0));
				cJLB_3.setEnabled(true);
			}
			
			if(e.getSource()==cJLB_4) {
				cJLB_4.setForeground( new Color(0,0,0));
				cJLB_4.setEnabled(true);
			}
			
			if(e.getSource()==cJLB_5) {
				cJLB_5.setForeground( new Color(0,0,0));
				cJLB_5.setEnabled(true);
			}
			
			if(e.getSource()==cJLB_6) {
				cJLB_6.setForeground( new Color(0,0,0));
				cJLB_6.setEnabled(true);
			}
			
			if(e.getSource()==showtime) {
				showtime.setForeground( new Color(0,0,0));
				showtime.setEnabled(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==cJLB_1) {
				cJLB_1.setForeground( new Color(147,147,147));
				cJLB_1.setEnabled(false);
			}
			
			if(e.getSource()==cJLB_2) {
				cJLB_2.setForeground( new Color(147,147,147));
				cJLB_2.setEnabled(false);
			}
			
			if(e.getSource()==cJLB_3) {
				cJLB_3.setForeground( new Color(147,147,147));
				cJLB_3.setEnabled(false);
			}
			
			if(e.getSource()==cJLB_4) {
				cJLB_4.setForeground( new Color(147,147,147));
				cJLB_4.setEnabled(false);
			}
			
			if(e.getSource()==cJLB_5) {
				cJLB_5.setForeground( new Color(147,147,147));
				cJLB_5.setEnabled(false);
			}
			
			if(e.getSource()==cJLB_6) {
				cJLB_6.setForeground( new Color(147,147,147));
				cJLB_6.setEnabled(false);
			}
			
			if(e.getSource()==showtime) {
				showtime.setForeground( new Color(147,147,147));
				showtime.setEnabled(false);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jm1_1) {
				System.exit(0);
				// setExtendedState(JFrame.MAXIMIZED_BOTH);
				
			}
			
			
			if(e.getSource()==jm2_1) {
				myCard.show(centercard, "1");
			}
			
			if(e.getSource()==jm2_2) {
				ui = DataBase.getUserInf(operater.getUser());
				new PersonalInfo(ui);
			}
			
			if(e.getSource()==jm3_1) {
				myCard.show(centercard, "2");
			}
			
			if(e.getSource()==jm3_2) {
				myCard.show(centercard, "3");
			}
			
			if(e.getSource()==jm3_3) {
				myCard.show(centercard, "4");
			}
			
			if(e.getSource()==jm1_3) {
				new SchoolYear();
			}
			
			
			
			if(e.getSource()==cJLB_6) {
				System.exit(0);
				chc.httpclient();
			}
			
			
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		/*sym = DataBase.getYear();
		String ti = Calendar.getInstance().getTime().toLocaleString();
		waw = Tool1.getDayWeek(sym.getBegin(), ti.substring(0, 9));*/
		String wuliao = "●";
		if(Calendar.getInstance().getTime().getSeconds()%2==0) {
			showtime.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString().substring(0, 15)+
				"  第"+waw.getWeek()+"周   星期"+waw.getWeekday()+"●      ");
		}else {
			showtime.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString().substring(0, 15)+
					"  第"+waw.getWeek()+"周   星期"+waw.getWeekday()+"○      ");
		}
	}
	
}
