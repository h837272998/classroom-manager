package com.hjh.jframe1;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hjh.jframe.Tool1;

public class TipJIF extends JInternalFrame{
	String str;
	private JPanel jp;
	private JLabel jl;
	
	public TipJIF(String s,int width,int heigth,int bigw,int bigh) {
		this.setTitle("提示！");
		this.setClosable(true);
		this.setMaximizable(false);
		this.setIconifiable(false);
		this.setResizable(false);
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		str = s;
		jp = new JPanel(new BorderLayout());
		jl = Tool1.setJLbView(jp, str, "red", 16);
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = bigh; i > bigh-heigth-100; i -= 2) {
                    //this.setSize(200, i);
                	getJIF().setBounds(bigw-width-10, i, width, heigth);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
 
            }
        });
        t.start();//启动线程
		this.add(jp);
		
	}
	
	public JInternalFrame getJIF() {
		return this;
		
	}
}
