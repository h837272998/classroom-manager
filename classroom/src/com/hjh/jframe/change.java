package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.hjh.db.DataBase;
import com.hjh.httpclient.IOUtils;
import com.hjh.jframe.classHttpClient.MyListener;

public class change extends JPanel{
	private MyListener ml;  //监听器对象
	private JPanel jp1,jp2,jp3;	  
	private JLabel jl1,jl2,jl3,jl4,secretCode;
	private JComboBox jcb1,jcb2;
	private MyButton mb1,mb2;
	private JTextArea jta;
	private JTextField jtf1;
	private static CloseableHttpClient client;
	private static String Cookie = null;  //COOKIE
	//private Timer time;
	private int judge = 0,judge1=1;
	private static String secretText = "",Sel_XNXQ = null,Sel_JXL = null,Sel_ROOM = null;
	private int weeknum;
	private static RequestConfig requestConfig = RequestConfig.custom()  
	        .setConnectTimeout(5000).setConnectionRequestTimeout(1000)  
	        .setSocketTimeout(5000).build();
	private JProgressBar progressBar;
	private int textLocation = 0;
	private String str = "";
	private Timer timer;
	private String[] bus1 = {"学期","2018-2019第一学期","2018-2019第二学期","2019-2020第一学期","2019-2020第二学期","2020-2021第一学期","2020-2021第二学期",};
	private String[] bus2 = {"选择楼","杨楼201-204 206-210","杨楼301-304 306-310","杨楼401-404 406-410","杨楼501-504 506-510","曾楼201-206 208-211","曾楼401-406 408-411"};
	
	public change() {
		ml = new MyListener();
		this.setLayout(new BorderLayout());
		theMain();
		timer = new javax.swing.Timer(10,createTextLoadAction() );
		this.add(jp1, "North");
		this.add(jp2, "Center");
		this.add(jp3, "South");
		this.setVisible(true);
	}
	
	public Action createTextLoadAction() {
		return new AbstractAction("text load action") {
		    public void actionPerformed (ActionEvent e) {
			if(progressBar.getValue() < progressBar.getMaximum()) {
			    progressBar.setValue(progressBar.getValue() + 1);
			    jta.append(str.substring(textLocation, textLocation+1));
			    textLocation++;
			} else {
				str="";
				mb2.setEnabled(true);
				timer.stop();
			}
		    }
		};
    }

	private void theMain() {
		// TODO Auto-generated method stub
		jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jl1 = Tool1.setJLbView(jp1, "教务网获得教室课程：", "black", 16);
		
		jcb1 = new JComboBox(bus1);
		jcb2 = new JComboBox(bus2);
		
		mb1 = new MyButton("  获得验证码 ");
		mb1.addActionListener(ml);
		
		secretCode = new JLabel(new ImageIcon("imag/secretCode1.png"));
		secretCode.addMouseListener(ml);
		secretCode.setEnabled(false);

		jtf1 = Tool1.setJTFView(jp1, "验证码", 0, 16, 4);
		jtf1.addFocusListener(ml);
		
		mb2 = new MyButton("  爬取 ");
		mb2.addActionListener(ml);
		jp1.add(jl1);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(mb1);
		jp1.add(secretCode);
		jp1.add(jtf1);
		jp1.add(mb2);
		
		jp2 = new JPanel(new BorderLayout());
		jta = new JTextArea("获得信息如下："+"\r\n");
		jta.setLineWrap(true);
		jta.setEditable(false);
	
		jp2.add(new JScrollPane(jta),"Center");
		
		jp3 = new JPanel(new BorderLayout());
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,2) {
			 public Dimension getPreferredSize() {
					return new Dimension(270, super.getPreferredSize().height);
//				    	return new Dimension(super.getPreferredSize().width,300 );
				    }
				};
		progressBar.setStringPainted(true);
		jp3.add(progressBar,"Center");
	}
	
	public static void geturl() {
		client = HttpClients.createDefault();  //httpclient 
		HttpGet getU = new HttpGet(IOUtils.GetUrl);  //get 一个链接向教室查询网页 获得请求头
		CloseableHttpResponse getr;
		getU.setConfig(requestConfig);
		try {
			getr = client.execute(getU);
			Cookie = getr.getFirstHeader("Set-Cookie").getValue();  //从get链接获得cookie信息
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
			e.printStackTrace();
		}
		
	}
	public static void gainsecrt() {
		HttpGet secretCodeGet = new HttpGet(IOUtils.secretCodeUrl); //get 验证码链接
		secretCodeGet.setHeader("Accept-Language", "zh-CN");  //设置get的请求头
		secretCodeGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; Core/1.53.4882.400 QQBrowser/9.7.13059.400)");  
		secretCodeGet.setHeader("Cookie", Cookie);
	   	secretCodeGet.setHeader("Referer", IOUtils.GetUrl);
		try {
			HttpResponse responseSecret = client.execute(secretCodeGet);
			try {
				IOUtils.getSecret(responseSecret.getEntity().getContent(),  
				           "secretCode.png", "imag/");
			} catch (UnsupportedOperationException e) {
				JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"教务网 发生错误"); 
			e.printStackTrace();
		}
		
	}
	
	public class MyListener implements ActionListener,MouseListener, FocusListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==secretCode) {
				
				gainsecrt();
				secretCode.setIcon(new ImageIcon("imag/secretCode.png"));
				secretCode.setEnabled(true);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//secretCode.setIcon(new ImageIcon("imag/secretCode.png"));
			
			
			if(e.getSource()==mb1) {
				//if(judge==0)
				geturl();
				gainsecrt();
				
				secretCode.setIcon(new ImageIcon("imag/secretCode.png"));
				secretCode.setEnabled(false);
				
				judge = 1;
			}
			
			if(e.getSource()==mb2) {
				if(jtf1.getText().trim().length()!=4) {
					JOptionPane.showMessageDialog(null,"验证码错误"); 
					jtf1.setText("");
				}else if(jcb1.getSelectedIndex()==0||jcb2.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null,"未选择学期或教学楼！"); 
				}else {
					judge1 = 0;
					secretText = jtf1.getText().trim(); //设置验证码
					for(int i = 1;i<=6;i++)
						if(jcb1.getSelectedIndex()==i) {
							if(i%2==0)
								Sel_XNXQ = jcb1.getSelectedItem().toString().substring(0, 4)+"1";  //设置学期
							else
								Sel_XNXQ = jcb1.getSelectedItem().toString().substring(0, 4)+"0";
						}
					try {
						weeknum = Integer.parseInt(DataBase.getWeeknum(Sel_XNXQ));
	    		     } catch (NumberFormatException e1) {
	    		         e1.printStackTrace();
	    		     }
					 
					String[] c1 = {"01","02","03","04","06","07","08","09","10"};
					String[] c2 = {"01","02","03","04","05","06","08","09","10","11"};
					String[] c = null;
					for(int j=1;j<=6;j++) {
						if(jcb2.getSelectedIndex()==j) {
							if(j<=4) {
								c = new String[9];
								String t = "301"+jcb2.getSelectedItem().toString().substring(2,3);
								Sel_JXL = "301";
								for(int i=0;i<=8;i++) {
									c[i] = t+c1[i];
								}
							}else {
								c = new String[10];
								String t = "302"+jcb2.getSelectedItem().toString().substring(2,3);
								Sel_JXL = "302";
								for(int i=0;i<=9;i++) {
									c[i] = t+c2[i];
								}
							}
							jta.append("待获取教室：");
							for(int i=0;i<c.length;i++)
								jta.append(c[i]+" ");
							jta.append("\r\n");
							break;
						}
					}
					//教务网一个验证码可以获得10间教室 
					for(int i=0;i<c.length;i++) {
							Sel_ROOM = c[i];
							if(judge1==1)break;
						
							httpPost();
						}
					
				}
				try {
					secretCode.setIcon(new ImageIcon("imag/secretCode1.png"));
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				mb2.setEnabled(false);
				progressBar.setMaximum(str.length());
		                if (progressBar.getValue() == progressBar.getMaximum()) {
		                    progressBar.setValue(0);
		                    textLocation = 0;
		                   // jta.setText("");
		                }
				timer.start();
			}
		}

		private void add(JPanel jp31, String string) {
			// TODO Auto-generated method stub
			this.add(jp31, string);
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jtf1) {
				if(jtf1.getText().equals("验证码")) {
					jtf1.setText(null);
				}

			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
