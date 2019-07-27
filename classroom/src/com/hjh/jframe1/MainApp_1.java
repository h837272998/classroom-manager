package com.hjh.jframe1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import com.hjh.db.Cookie;
import com.hjh.db.DataBase;
import com.hjh.db.DataBaseU;
import com.hjh.jframe.Tool;
import com.hjh.jframe.Tool1;
import com.hjh.object.Operater;
import com.hjh.object.apply;
import com.hjh.object.schoolYearM;
import com.hjh.object.user;
import com.hjh.object.weekAndWeek;

public class MainApp_1 extends JFrame implements ActionListener{
	private static final String NULL = null;
	private Operater operater;
	private MyListener ml;  //按钮点击监听内部类
	private JTabbedPane tabbedPane;
	private JPanel jp1,jp2,jp3,bottom;
	private JDesktopPane jdtp;
	private JLabel info,showtime,jl1,jl2,jl3,jl4,jl5;
	private schoolYearM sym;
	private weekAndWeek waw;
	private Timer t;//可定时触发Action事件
	private String[] wee = {"","一","二","三","四","五","六","日"};
	private PerinfoJIF pjif;
	private ApplyinfoJIF ajif;
	private ClassCourseJIF ccjif;
	private user ui;
	private int windowCount = 0;
	private JInternalFrame palette;
	
	public MainApp_1(Operater op) throws IOException {
		this.operater = op;
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ml = new MyListener();
		this.setTitle("class");
		Tool1.sizeWindowOnScreen(this,0.8,0.8);
		setLocationRelativeTo(null);//相对位置  中间位置
		
		tabbedPane = new JTabbedPane();
		Image image1=ImageIO.read(new File("imag/bg.png"));
		jdtp = new JDesktopPane() {
			public void paintChildren(Graphics g) {
				g.drawImage(image1, 0, 0, getWidth(),getHeight(),this);
				super.paintChildren(g);
			}
		};
		

		JPanel jp = new JPanel();
		
		//jdtp.add(comp)
		tabbedPane.add(jdtp,"  主    页  ");
		
		jp1 = new JPanel();
		tabbedPane.add(jp1,"  关       于 ");
		
		bottom = new JPanel(new BorderLayout());
		jp2 = new JPanel();
		info = Tool.setJLbView(this, "欢迎你："+operater.getUser(), "gray",12);
		t = new Timer(3000, this);//每隔一秒触发ActonEvent
		sym = DataBase.getYear();
		String ti = Calendar.getInstance().getTime().toLocaleString();
		waw = Tool1.getDayWeek(sym.getBegin(), ti.substring(0, 9));
		showtime = Tool1.setJLbView(this, "当前时间："+ti.substring(0, 15)+
				"  第"+waw.getWeek()+"周   星期"+wee[waw.getWeekday()]+"   ", "gray", 12);
		showtime.addMouseListener(ml);
		showtime.setEnabled(false);
		t.start();
		bottom.add(showtime,"East");
		bottom.add(info,"West");
		
		jp3 = new JPanel();
		createInternalFramePalette();
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		this.getContentPane().add(bottom, BorderLayout.SOUTH);
		this.ui = DataBase.getUserInf(operater.getUser());
		
		i();
		
		this.setVisible(true);
	}
	
	public JFrame getJFrame() {
		return this;
	}
	
	public void i() {
		System.out.println(ui.getName());
		
		if(ui.getName()==NULL||ui.getTel()==NULL){
			int w = this.getWidth();
			int h = this.getHeight();
			TipJIF tjif = new TipJIF("请完善个人信息，避免某些功能不可用。",w/5,h/5,w,h);
			jdtp.add(tjif,new Integer(2));
			tjif.show();
		}
	}
	
	
	public JInternalFrame createInternalFramePalette() {   //工具栏
		palette = new JInternalFrame("工具栏");
		palette.putClientProperty("Tool", Boolean.TRUE);
		palette.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		palette.setResizable(true);
		palette.setIconifiable(true);
		
		palette.setBounds(10, 10, this.getWidth()-100, 60);
		palette.setBackground(Color.WHITE);
		jdtp.add(palette, new Integer(3));
		palette.setOpaque(true);
		
		jl1 = Tool1.setJLbView(palette, "个人信息       ", "gray", 16);
		jl2 = Tool1.setJLbView(palette, "个人申请        ", "gray"	, 16);
		jl3 = Tool1.setJLbView(palette, "空教室查询手动申请教室  ", "gray", 16);
		jl4 = Tool1.setJLbView(palette, "自动申请短期教室       ", "gray"	, 16);
		jl5 = Tool1.setJLbView(palette, "设置背景 ", "gray"	, 16);
		jl1.addMouseListener(ml);
		jl2.addMouseListener(ml);
		jl3.addMouseListener(ml);
		jl4.addMouseListener(ml);
		jl5.addMouseListener(ml);
		palette.show();

		return palette;
	}
	
	
	public class MyListener implements ActionListener, FocusListener, MouseListener, ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jl1) {
				//DataBaseU = da = new DataBaseU();
				ui = DataBaseU.getUserInf(operater.getUser());
				pjif = new PerinfoJIF(ui,getJFrame().getWidth()/5,getJFrame().getHeight()/3,windowCount+1);
				windowCount++;
				jdtp.add(pjif,new Integer(2));
				pjif.show();
			}
			
			if(e.getSource()==jl2) {
				ui = DataBaseU.getUserInf(operater.getUser());
				ajif = new ApplyinfoJIF(ui,getJFrame().getWidth()/2+100,getJFrame().getHeight()/3,windowCount+1);
				windowCount++;
				jdtp.add(ajif,new Integer(2));
				ajif.show();
			}
			
			if(e.getSource()==jl3) {
				ccjif = new ClassCourseJIF(ui,jdtp,getJFrame().getWidth()/2+100,getJFrame().getHeight()/3,windowCount+1);
				windowCount++;
				jdtp.add(ccjif,new Integer(2)); 
				ccjif.show();
			}
			
			if(e.getSource()==jl4) {
				apply ay = new apply();
				ay.setApplytime(Calendar.getInstance().getTime().toLocaleString());
				ay.setUserid(ui.getuser());
				ay.setPeoname(ui.getName());
				ay.setPeorel(ui.getTel());

				ApplyJIF ajif1 = new ApplyJIF(ay,jdtp.getWidth(),jdtp.getHeight(),0);
				ajif1.setmodel1(2);
				jdtp.add(ajif1,new Integer(2));
				ajif1.show();
				
				TipJIF tjif = new TipJIF("选择开始时间，填好空白，不满意可刷新",getJFrame().getWidth()/5,getJFrame().getHeight()/5,getJFrame().getWidth(),getJFrame().getHeight());
				jdtp.add(tjif,new Integer(2));
				tjif.show();
			}
			
			if(e.getSource()==jl5) {
				
				JFileChooser fc = new JFileChooser("");
				File swingFile = new File("");
				if(swingFile.exists()) {
					fc.setCurrentDirectory(swingFile);
					fc.setSelectedFile(swingFile);
				}
				FileFilter  jpgFilter = new FileFilter() {
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						if(f.getName().endsWith(".png")||f.getName().endsWith(".jpg"))
							return true;
						else
							return false;
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return null;
					} //jpg过滤器  
				
				};
				fc.addChoosableFileFilter(jpgFilter);
				fc.setFileFilter(jpgFilter);
				int result = fc.showOpenDialog(jdtp);
				File filex = null;
				if (result==fc.APPROVE_OPTION) {
					filex =fc.getSelectedFile();
					RenderedImage i = null;
					try {
						i = ImageIO.read(filex);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						ImageIO.write(i, "png", new File("imag", "bg.png"));
						JOptionPane.showMessageDialog(null,"修改成功，重启软件生效");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					
				}
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jl1) {
				jl1.setForeground( new Color(0,0,0));
				jl1.setEnabled(true);
			}
			
			if(e.getSource()==jl2) {
				jl2.setForeground( new Color(0,0,0));
				jl2.setEnabled(true);
			}
			
			if(e.getSource()==jl3) {
				jl3.setForeground( new Color(0,0,0));
				jl3.setEnabled(true);
			}
			
			if(e.getSource()==jl4) {
				jl4.setForeground( new Color(0,0,0));
				jl4.setEnabled(true);
			}
			
			if(e.getSource()==jl5) {
				jl5.setForeground( new Color(0,0,0));
				jl5.setEnabled(true);
			}
			
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jl1) {
				jl1.setForeground( new Color(147,147,147));
				jl1.setEnabled(false);
			}
			
			if(e.getSource()==jl2) {
				jl2.setForeground( new Color(147,147,147));
				jl2.setEnabled(false);
			}
			
			if(e.getSource()==jl3) {
				jl3.setForeground( new Color(147,147,147));
				jl3.setEnabled(false);
			}
			
			if(e.getSource()==jl4) {
				jl4.setForeground( new Color(147,147,147));
				jl4.setEnabled(false);
			}
			
			if(e.getSource()==jl5) {
				jl5.setForeground( new Color(147,147,147));
				jl5.setEnabled(false);
			}
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
			showtime.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString().substring(0, 15)+
				"  第"+waw.getWeek()+"周   星期"+wee[waw.getWeekday()]+"   ");
	}

	

	

}
	

