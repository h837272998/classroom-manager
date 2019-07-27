package com.hjh.jframe;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingJFrame extends JFrame implements ActionListener{
	
	
	public LoadingJFrame(String str){  //构造方法
		this.setDefaultCloseOperation(3);//  错误关闭操作
		this.setLocationRelativeTo(null);//相对位置  中间位置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(str);
		Tool1.sizeWindowOnScreen(this,0.25,0.2);
		new SetDragable(this);  //可拖动
		this.setLayout(new BorderLayout());
		JProgressBar pbIndeterminate = new JProgressBar()
		{
		    public Dimension getPreferredSize() {
//			    	return new Dimension(15,300 );
		    		return new Dimension(200, 15);
			    }
		}
		;
		JPanel jp = new JPanel();
		pbIndeterminate.setIndeterminate(true);
		jp.add(pbIndeterminate);
//		pbIndeterminate.setOrientation(JProgressBar.VERTICAL);//* add by Jack Jiang for test
		this.add(jp,"Center");
		this.setVisible(true);
	}

	
	
	public void closeJF(){
		this.dispose();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}