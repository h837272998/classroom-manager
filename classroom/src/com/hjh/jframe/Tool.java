package com.hjh.jframe;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hjh.db.*;

public class Tool {
	private static final long time = 0;
	private static Color gray = new Color(147,147,147);
	private static Color red = new Color(255,145,145);
	private static Color black = new Color(10,10,10);
	private static List<ImageIcon> list= new ArrayList<ImageIcon>();
	
	public static JButton setJBtView(Container panel, ImageIcon imag) {//按钮设置
		// TODO Auto-generated method stub
		JButton btn = new JButton();
		btn.setBorderPainted(false);//设置边框不可见
		btn.setContentAreaFilled(false);//设置透明
		btn.setIcon(imag);//设置图片
		panel.add(btn);//添加按钮
		return btn;
	}
	
	public static JTextField setJTFView(Container panel,String str,int judge,int size) {  //文本框设置 ，judge判断是否去除边框线
		JTextField jtf = new JTextField(str);
		Font fs = new Font("微软雅黑",Font.ITALIC,size);
		jtf.setFont(fs);		//字体
		jtf.setForeground(gray);  //字体颜色
		jtf.setOpaque(false);  //设置透明
		if(judge==1) 
			jtf.setBorder(null);   //去掉边框线
		panel.add(jtf);
		return jtf;
	}
	
	public static JPasswordField setJPFView(Container panel,String str,int judge,int size) {  //密码框设置
		JPasswordField jpf = new JPasswordField (str);
		Font fs = new Font("Microsoft Uighur",Font.ITALIC,size);
		jpf.setFont(fs);		//字体
		jpf.setForeground(gray);  //字体颜色
		jpf.setOpaque(false);  //设置透明
		if(judge==1) 
			jpf.setBorder(null);   //去掉边框线
		panel.add(jpf);
		return jpf;
	}
	
	public static JLabel setJLbView(Container panel,String str,String color,int size) {   //标签设置
		JLabel jl = new JLabel(str);
		Font f = new Font("微软雅黑",Font.ITALIC,size);
		if(color.equals("red")) {
			jl.setForeground(red);
		}else if(color.equals("gray")){
			jl.setForeground(gray);
		}else {
			jl.setForeground(black);
		}
		jl.setFont(f);

		panel.add(jl);
		return jl;
	}
	
	public static JButton setclose(JFrame jf,int width) {
		JButton clo = new JButton();
		clo.setBorderPainted(false);
		clo.setContentAreaFilled(false);
		
		for(int i=1;i<5;i++){
			ImageIcon img = new ImageIcon("imag/sign-in/top"+i+".png");
			list.add(img);
		}
		
		clo.setIcon(list.get(2));
		clo.setRolloverIcon(list.get(3));
		jf.add(clo);
		clo.setBounds(width- list.get(2).getIconWidth(), 0, list.get(2).getIconWidth(), list.get(2).getIconHeight());
		return clo;
		
	}
	
	public static JButton setmin(JFrame jf,int width) {
		JButton mi = new JButton();
		mi.setBorderPainted(false);
		mi.setContentAreaFilled(false);
		
		for(int i=1;i<5;i++){
			ImageIcon img = new ImageIcon("imag/sign-in/top"+i+".png");
			list.add(img);
		}
		
		mi.setIcon(list.get(0));
		mi.setRolloverIcon(list.get(1));  //进入时
		jf.add(mi);
		mi.setBounds((width - list.get(3).getIconWidth()-list.get(0).getIconWidth()),0,list.get(0).getIconWidth(),list.get(0).getIconHeight());
		return mi;
	}
	
	
	//邮箱格式验证
	public static boolean checkEmail(String email) {
	    boolean flag = false;
	    try {
	        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(email);
	        flag = matcher.matches();
	    } catch (Exception e) {
	        flag = false;
	    }
	    return flag;
	}
	//手机格式验证
	public static boolean checkTel(String tel) {
	    boolean flag = false;
	    try {
	        String check = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(tel);
	        flag = matcher.matches();
	    } catch (Exception e) {
	        flag = false;
	    }
	    return flag;
	}
	
	public static String randomNum() {
		String sb1 = new String();
		Random random = new Random();
        // sb.append("  ");
         for(int i = 0;i<6;i++) {
         	//sb.append(random.nextInt(10));
         	sb1+=random.nextInt(10);
         }
         return sb1;
	}
    public static String sendMail(String to,String title,String t)throws Exception {
        if (to != null){
            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.163.com");
            props.put("mail.smtp.auth", "true");
            com.hjh.db.MailAuthenticator auth = new MailAuthenticator();
            MailAuthenticator.USERNAME = "hjh2614@163.com";
            MailAuthenticator.PASSWORD = "hjh134625";
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            StringBuffer sb = new StringBuffer();
            
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("hjh2614@163.com"));
                if (!to.trim().equals(""))
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(to.trim()));
                message.setSubject(title);//标题
                MimeBodyPart mbp1 = new MimeBodyPart(); // 正文
                //产生6个随机数
                
                
                mbp1.setContent(t, "text/html;charset=utf-8");
                Multipart mp = new MimeMultipart(); // 整个邮件：正文+附件
                mp.addBodyPart(mbp1);
                // mp.addBodyPart(mbp2);
                message.setContent(mp);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport trans = session.getTransport("smtp");
                trans.send(message);
               // System.out.println(message.toString());

            } catch (Exception e){
                e.printStackTrace();
                return "error1";
            }
            return t;
        }else{            
            return "error1";
        }
    }

    //菜单
	public static JMenuItem setJMI(JMenu jm1, String string, int size) {
		// TODO Auto-generated method stub
		JMenuItem jmi = new JMenuItem(string);
		Font f = new Font("微软雅黑",Font.ITALIC,size);
		jmi.setFont(f);
		jm1.add(jmi);
		return jmi;
	}
    
    //获得屏幕分辨率
	public static  Dimension WindowOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
		
	}
	
	
}