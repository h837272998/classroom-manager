package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hjh.db.DataBase;
import com.hjh.object.userModel;

public class UserManage extends JPanel{
	private userModel um;
	private JTable jt;
	private JScrollPane jsp;
	private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10,bottonCard;
	private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,info;
	private JTextField jtf,jtf1,jtf2;
	private MyButton mb1,mb2,mb3,mb4,mb5,mb6,mb7,mb8,mb9,mb10,mb11;
	private MyListener ml;  //按钮点击监听内部类
	
	private JPopupMenu jppm;
	private JMenuItem jmi0,jmi1,jmi2,jmi3,jmi4;
	
	private CardLayout myCard;
	
	public UserManage() {
		//this.setSize(810, 510);
		ml = new MyListener();
		this.setLayout(new BorderLayout());
		
		center1();
		center2();
		center3();
		
		this.add(jp1, "North");
		this.add(jsp,"Center");
		this.add(jp2, "South");
		this.setVisible(true);
	}	
	
	public void center1() {
		jp1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jl1 = Tool1.setJLbView(jp1, "检索:  ", "black", 16);
		jtf = Tool1.setJTFView(jp1, "输入账号或姓名", 0, 16,18);
		mb1 = new MyButton("  查 询   ",1);
		mb1.addActionListener(ml);
		//mb1.addActionListener(ml);
		jtf.addFocusListener(ml);
		jp1.add(mb1);
	}
	
	public void center2() {
		um = new userModel();
		um.model();
		jt = new JTable(um);
		jt.addMouseListener(ml);
		
		//jt.setBackground(new Color(232,248,247));
		jsp=new JScrollPane(jt);
		
		jppm = new JPopupMenu();
		jmi1 = new JMenuItem("提高权限");
		jmi1.addActionListener(ml);
		jmi2 = new JMenuItem("降低权限");
		jmi2.addActionListener(ml);
		jmi3 = new JMenuItem("修改Email");
		jmi3.addActionListener(ml);
		jmi4 = new JMenuItem("删除");
		jmi4.addActionListener(ml);
		jppm.add(jmi1);
		jppm.add(jmi2);
		jppm.add(jmi3);
		jppm.add(jmi4);
	}
	
	public void center3() {
		jp2 = new JPanel(new BorderLayout());
		
		jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		int t = um.getRowCount();
		jl2 = Tool.setJLbView(jp3, "共："+t+" 条", "black", 14);
		
		myCard=new CardLayout();
		bottonCard = new JPanel(myCard);
		
		jp4 = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		mb2 = new MyButton("  删除选择行");
		mb2.addActionListener(ml);
		jp4.add(mb2);		
		
		jp5 = new  JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		jl3 = Tool1.setJLbView(jp5, "新邮箱：", "black", 16);
		jtf1 = Tool1.setJTFView(jp5, "", 0, 16, 15);
		mb3 = new MyButton("  确定 ");
		mb3.addActionListener(ml);
		mb4 = new MyButton("  返回 ");
		mb4.addActionListener(ml);
		jp5.add(mb3);
		jp5.add(mb4);
		
			
		bottonCard.add(jp4,"0");
		bottonCard.add(jp5,"1");
		
		jp2.add(jp3,"West");
		jp2.add(bottonCard,"East");
	}
	
	
	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon("imag/main/background/Maincenter2.png").getImage();
		super.paintComponent(g);
		g.drawImage(img, 0,0, this.getWidth(), this.getHeight(),this);
	}
	
	public class MyListener implements ActionListener,MouseListener, FocusListener{

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
				if(jtf.getText().trim().equals("")) {
					um = new userModel();
					um.model(0,"");
					um.model();
					jt.setModel(um);
					jl2.setText("共"+um.getRowCount()+"条");
				}else {
					um = new userModel();
					um.model(1,jtf.getText().trim().toString());
					um.model();
					jt.setModel(um);
					jl2.setText("共："+um.getRowCount()+"条");
				}
			}
			
			if(e.getSource()==jmi1) {
				int t = DataBase.improveG(um,jt.getSelectedRow());
				if(t!=0)
					JOptionPane.showMessageDialog(null,"权限提升成功"); 
				else
					JOptionPane.showMessageDialog(null,"错误"); 
			}
			
			if(e.getSource()==jmi2) {
				int t = DataBase.DownG(um,jt.getSelectedRow());
				if(t!=0)
					JOptionPane.showMessageDialog(null,"权限降低成功"); 
				else
					JOptionPane.showMessageDialog(null,"错误"); 
			}
			
			if(e.getSource()==jmi3) {
				myCard.show(bottonCard, "1");
			}	
			
			if(e.getSource()==mb3) {//确定修改邮箱
				if(!Tool.checkEmail(jtf1.getText())||jtf1.getText().equals("")) {
					//错误的邮箱格式
					JOptionPane.showMessageDialog(null,"错误的邮箱"); 
				}else {
					int t = DataBase.newEmail(um,jt.getSelectedRow(),jtf1.getText());
					if(t!=0) {
						JOptionPane.showMessageDialog(null,"修改邮箱"+jtf1.getText()+"成功"); 
					}
					myCard.show(bottonCard, "0");
				}
			}
			
			if(e.getSource()==mb4) {
				myCard.show(bottonCard, "0");
			}
			
			
			if(e.getSource()==jmi4) {
				//String inpi = JOptionPane.showInternalInputDialog(this, null, "删除原因！", JOptionPane.WARNING_MESSAGE);
				String inputValue = JOptionPane.showInputDialog(null,"删除原因！"); 
				if(inputValue == null) {
					return;
				}
				if(inputValue.equals("")) {
					JOptionPane.showMessageDialog(null,"输入删除原因");
					return;
				}
				int t = DataBase.deleteuser(um,jt.getSelectedRow());
				String em = um.getValueAt(jt.getSelectedRow(), 2).toString();
				if(t!=0) {
					try {
						if(!Tool.sendMail(em, "HJH账号被删除！", inputValue).equals("error1"))
							JOptionPane.showMessageDialog(null,"删除成功已发送原因");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			if(e.getSource()==mb2) {  //删除选中
				int[] i = jt.getSelectedRows();
				String inputValue = JOptionPane.showInputDialog(null,"删除原因！"); 
				if(inputValue == null) {
					return;
				}
				if(inputValue.equals("")) {
					JOptionPane.showMessageDialog(null,"输入删除原因");
					return;
				}
				for(int j = 0;j<i.length;j++) {
					
					int t = DataBase.deleteuser(um,i[j]);
					String em = um.getValueAt(i[j], 2).toString();
					if(t!=0) {
						try {
							if(Tool.sendMail(em, "HJH账号被删除！", inputValue).equals("error1"))
								JOptionPane.showMessageDialog(null,"删除"+em+"出错");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null,"删除成功已发送原因");
				}
			}
			
			
			
			/*if (e.getSource() == jmi0) // 拷贝到剪贴板。
	        {
				int i = jt.getSelectedColumn();
				int j = jt.getSelectedRow();
	            String temp = jt.getValueAt(j, i).toString(); // 拖动鼠标选取文本。
	            StringSelection text = new StringSelection(temp);
	            getToolkit().getSystemClipboard().setContents(text, null);
	        }*/
			
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jtf) {
			
				if(jtf.getText().equals("输入账号或姓名")) {
					jtf.setText(null);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}