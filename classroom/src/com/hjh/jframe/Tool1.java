package com.hjh.jframe;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.jsoup.nodes.Element;
import org.omg.CORBA_2_3.portable.OutputStream;

import com.hjh.object.weekAndWeek;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Tool1 {
	private static Color gray = new Color(147,147,147);
	private static Color red = new Color(255,145,145);
	private static Color black = new Color(30,30,30);
	
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

//获得一个时间距离当前学期的第几周，星期几  前提 开学第一天为星期一
	public static weekAndWeek getDayWeek(String sd1,String sd2) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d2 = null;
        Date d1 = null;
		try {
			d1=sdf.parse(sd1);
			d2=sdf.parse(sd2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i= (int) ChronoUnit.DAYS.between(d1.toInstant(), d2.toInstant());
		weekAndWeek waw = new weekAndWeek();
		waw.setWeek(i/7+1);
		waw.setWeekday(i%7+1);
		return waw;
		
	}
	
	public static boolean checkday(String sd1,String sd2) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d2 = null;
        Date d1 = null;
		try {
			d1=sdf.parse(sd1);
			d2=sdf.parse(sd2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i= (int) ChronoUnit.DAYS.between(d1.toInstant(), d2.toInstant());
		if(i>0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public static String rollDayWeek(String sd1,int num) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String temp = "";
	
		  try {
	            Date date = sdf.parse(sd1);
	            Calendar cl = Calendar.getInstance();
	            cl.setTime(date);
	            // cl.set(Calendar.DATE, day);
	            cl.add(Calendar.DATE, num);
	            temp = sdf.format(cl.getTime());

	        } catch (ParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		  return temp;
	}
 
	public static JLabel setJLbView(Container panel, String str, String color, int size) {
		// TODO Auto-generated method stub
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

	public static JTextField setJTFView(Container jp1, String string, int judge, int size,int len) {
		// TODO Auto-generated method stub
		JTextField jtf = new JTextField(len);
		jtf.setText(string);
		Font fs = new Font("微软雅黑",Font.ITALIC,size);
		jtf.setFont(fs);		//字体
		jtf.setForeground(gray);  //字体颜色
		//jtf.setOpaque(false);  //设置透明
		if(judge==1) 
			jtf.setBorder(null);   //去掉边框线
		jp1.add(jtf);
		return jtf;
	}

	
	//正则表达获取星期
	public static int[] setweek(String text) {
		// TODO Auto-generated method stub
		int[] temp  = new int[25];
		String check = "\\d+-\\d+[,周]|\\d+[,周]|\\d+\\s双周";
        Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(text);
	    while(matcher.find()){
	    	 if(!matcher.group().equals("")) {
	    		 String str = matcher.group().trim();
	    		 String str1 = "";
	    		 String str2 = "";
	    		 int ju = 0;
	    		 for(int i=0;i<str.length();i++){
	    			 if(str.charAt(i)>=48 && str.charAt(i)<=57){
	    				 if(ju==0)
	    					 str1+=str.charAt(i);
	    				 else
	    					 str2+=str.charAt(i);
	    			 }else {
	    				 ju++;
	    				 continue;
	    			 }
	    		 }
	    		 
	    		 if(str2.equals("")) {
	    			 try {
	    		         int a = Integer.parseInt(str1);
	    		         temp[a-1] = 1;
	    		     } catch (NumberFormatException e) {
	    		         e.printStackTrace();
	    		     }
	    		 }else {
	    			 try {
	    		         int a = Integer.parseInt(str1);
	    		         int b = Integer.parseInt(str2);
	    		         for(int i= a;i<=b;i++) {
	    		        	 temp[i-1] = 1;
	    		         }
	    		     } catch (NumberFormatException e) {
	    		         e.printStackTrace();
	    		     }
	    		 }
	    	 }	 
		}
		return temp;
	}
	
	public static boolean Zhengze(String str, String check) {
		Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(str);
	    boolean flag = matcher.matches();
		
    	return flag;
	}
	
	public static void sizeWindowOnScreen(JFrame calculator, double widthRate, double heightRate)
	{
	   Dimension screenSize = Tool.WindowOnScreen();
	   calculator.setSize(new Dimension((int)(screenSize.width * widthRate),(int)(screenSize.height *heightRate)));
	}
	
	public static JMenu setJM(JMenuBar jmb,JFrame jf,String str,int size) {
		JMenu jm = new JMenu(str);
		Font f = new Font("微软雅黑",Font.ITALIC,size);
		jm.setFont(f);
		jmb.add(jm);
		return jm;
		
	}
	
	public static void openURLOnBrowser(String str) {
		if (Desktop.isDesktopSupported()){
            try {
                URI uri = new URI(str);
                Desktop desktop = Desktop.getDesktop(); //创建desktop对象
                //调用默认浏览器打开指定URL
                desktop.browse(uri);
 
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
 
            } catch (IOException e1) {
                //如果没有默认浏览器时，将引发下列异常
            	JOptionPane.showMessageDialog(null,"计算机没有默认浏览器"); 
                e1.printStackTrace();
            }
 
 
        }
	}
	
	 public static void exportTable(JTable table, File file) throws IOException {
		 try {  
	            FileOutputStream out = new FileOutputStream(file);  
	            TableModel model = table.getModel();  
	            WritableWorkbook wwb = Workbook.createWorkbook(out);  
	            // 创建字表，并写入数据  
	            WritableSheet ws = wwb.createSheet("中文", 0);  
	            // 添加标题  
	            for (int i = 0; i < model.getColumnCount(); i++) {  
	                jxl.write.Label labelN = new jxl.write.Label(i, 0, model.getColumnName(i));  
	                try {  
	                    ws.addCell(labelN);  
	                } catch (RowsExceededException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (WriteException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
	            // 添加列  
	            for (int i = 0; i < model.getColumnCount(); i++) {  
	                for (int j = 1; j <= model.getRowCount(); j++) {  
	                    jxl.write.Label labelN = new jxl.write.Label(i, j, model.getValueAt(j - 1, i).toString());  
	                    try {  
	                        ws.addCell(labelN);  
	                    } catch (RowsExceededException e) {  
	                        e.printStackTrace();  
	                    } catch (WriteException e) {  
	                        e.printStackTrace();  
	                    }  
	                }   
	            }  
	            wwb.write();  
	            try {  
	                wwb.close();  
	            } catch (WriteException e) {  
	                e.printStackTrace();  
	            }  
	        } catch (FileNotFoundException e) {  
	            JOptionPane.showMessageDialog(null, "导入数据前请关闭工作表");  
	        }  

	 }
}