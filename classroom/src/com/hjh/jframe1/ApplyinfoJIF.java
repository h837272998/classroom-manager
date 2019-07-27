package com.hjh.jframe1;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hjh.db.DataBaseU;
import com.hjh.jframe.MyButton;
import com.hjh.jframe.Tool;
import com.hjh.jframe.Tool1;
import com.hjh.jframe1.PerinfoJIF.MyListener;
import com.hjh.object.applyModel_1;
import com.hjh.object.user;

public class ApplyinfoJIF extends JInternalFrame{
	private MyButton mb1,mb2,mb3;
	private JPanel bottom,jp1,jp2;
	private JTable jt;
	private JScrollPane jsp;
	private user ui;
	private MyListener ml;  //按钮点击监听内部类
	private applyModel_1 am;
	private int width,height,windowCount;
	private JPopupMenu jppm;
	private JMenuItem jmi;
	private JLabel jl;
	private String []w = {"星期一","星期二","星期三","星期四","星期五","星期六","星期七"};
	private String []jie = {"1","2","3","4","5"};
	
	public ApplyinfoJIF(user u,int width, int height,int windowCount) {
		this.setTitle("个人申请");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setIconifiable(true);
		this.setResizable(true);
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
		
		am = new applyModel_1();
		am.model(ui.getuser());
		jt = new JTable(am);
		jt.addMouseListener(ml);
		jsp=new JScrollPane(jt);
		this.add(jsp,BorderLayout.CENTER);
		
		jppm = new JPopupMenu();
		jmi = new JMenuItem("撤销申请");
		jmi.addActionListener(ml);
		jppm.add(jmi);
		
		bottom = new JPanel(new BorderLayout());
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		int t = am.getRowCount();
		jl = Tool.setJLbView(jp1, "共："+t+" 条", "black", 14);
		jp1.add(jl);
		jp2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		mb1 = new MyButton("刷新");
		mb1.addActionListener(ml);
		mb2 = new MyButton("撤销申请所选");
		mb2.addActionListener(ml);
		mb3 = new MyButton("导出到Excel ");
		mb3.addActionListener(ml);
		jp2.add(mb1);
		jp2.add(mb2);
		jp2.add(mb3);
		bottom.add(jp1,"West");
		bottom.add(jp2,"East");
		this.add(bottom,BorderLayout.SOUTH);
		Launch();
	}
	
	public JInternalFrame getJIF() {
		return this;
		
	}
	
	public void Launch() {
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = 40; i < height; i += 2) {
                    //this.setSize(200, i);
                	getJIF().setBounds(20*(windowCount%10), 50*(windowCount%10), width, i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
 
            }
        });
        t.start();//启动线程
	}
	public class MyListener implements ActionListener,MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jt) {
				if(e.getButton()==java.awt.event.MouseEvent.BUTTON3) {
					int focusedRowIndex = jt.rowAtPoint(e.getPoint());
					if(focusedRowIndex == -1) return;
					jt.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
					jppm.show(jt, e.getX(), e.getY());
				}
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
			if(e.getSource()==mb1) {
				am = new applyModel_1();
				am.model(ui.getuser());
				jt.setModel(am);
				jl.setText("共："+am.getRowCount()+"条");
			}
			
			if(e.getSource()==mb2) {
				int[] i = jt.getSelectedRows();
				int n = JOptionPane.showConfirmDialog(null, "确认撤销吗?谨慎操作", "撤销确认", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					for(int j=0;j<i.length;j++) {
						String x = am.getValueAt(i[j], 5).toString();
						String y = am.getValueAt(i[j], 6).toString();
						x = x.substring(2,3)+y;
						String k = am.getValueAt(jt.getSelectedRow(), 8).toString();
						String []params = {x,k};
						boolean b1 = DataBaseU.updateClassResoures(params);//将教室设为空  再删除申请表
						String []params1 = {am.getValueAt(i[j], 0).toString()};
						boolean b2 = DataBaseU.deleteApply(params1);
					}
				}
						JOptionPane.showMessageDialog(null, "撤回成功!");
			}
			
			if(e.getSource()==mb3) {
				JFrame jf = new JFrame();
				FileDialog fd = new FileDialog(jf, "保存记录", FileDialog.SAVE);
				 fd.setLocation(400, 250);
			     fd.setVisible(true);  
			     String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
		         try {
		        	// OrderDAO oDao = new OrderDAO();
		        	 Tool1.exportTable(jt, new File(stringfile));
		         } catch (IOException ex) {
		             ex.printStackTrace();
		         }
			}
			
			if(e.getSource()==jmi) {
				int n = JOptionPane.showConfirmDialog(null, "确认撤销吗?谨慎操作", "撤销确认", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String i = am.getValueAt(jt.getSelectedRow(), 5).toString();
					String j = am.getValueAt(jt.getSelectedRow(), 6).toString();
					i = i.substring(2,3)+j;
					String k = am.getValueAt(jt.getSelectedRow(), 8).toString();
					String l = am.getValueAt(jt.getSelectedRow(), 4).toString();
					String []params = {"0",k,l};
					boolean b1 = DataBaseU.changeClassR(params,i);//将教室设为空  再删除申请表
					String []params1 = {am.getValueAt(jt.getSelectedRow(), 0).toString()};
					boolean b2 = DataBaseU.deleteApply(params1);
				
					if(b1==true&&b2==true) {
						JOptionPane.showMessageDialog(null, "撤回成功!");
					}
				}
			}
		}
	}

}
