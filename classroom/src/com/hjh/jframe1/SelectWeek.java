package com.hjh.jframe1;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;

import com.hjh.jframe.MyButton;

public class SelectWeek  extends JInternalFrame implements ActionListener {
	
	ArrayList<JCheckBox> jcb = new ArrayList<>();
	ArrayList<String> select = new ArrayList<>();
	int []s;
	private int width,height;
	private MyButton mb1;
	private ResultSet rs;
	
	public SelectWeek(ArrayList<String> select,int w,int h) {
		this.s = s;
		this.setTitle("选择可用周数");
		this.setClosable(true);
		this.setMaximizable(false);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setBackground(Color.WHITE);
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setLayout(new GridLayout(4,5));
		width = w;
		height = h;
		this.select = select;
		
		for(int i = 0;i<select.size();i++) {
			jcb.add(new JCheckBox(select.get(i),true));
			this.add(jcb.get(0));
		}
		

		
		
		Launch();

		mb1 = new MyButton("确定选择");
		this.add(mb1);
		mb1.addActionListener(this);
	}
	
	public JInternalFrame getJIF() {
		return this;
		
	}
	
	public void Launch() {
		Thread t = new Thread(new Runnable() {
			 
            @Override
            public void run() {
                for (int i = 40; i < height; i += 5) {
                    //this.setSize(200, i);
                	getJIF().setBounds(100,100, width, i);
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
	public ArrayList sts() {
		ArrayList<String> ss = new ArrayList<>();
		for(int i= 0 ;i<jcb.size() ;i++) {
			if(jcb.get(i).isSelected()) {
				ss.add(select.get(i));
			}
		}
		return ss;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mb1) {
			 getJIF().setVisible(false);
		}
	}

}
