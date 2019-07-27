package com.hjh.jframe;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Logining extends JFrame{
	LoginJPanel jp ;
	Logining(){
		super("text");
		setSize(300,300);
		jp = new LoginJPanel();
		this.add(jp);
		jp.repaint();
		setVisible(true);
		jp.launchJPanel();
	}
	class LoginJPanel extends JPanel{
		private List<ImageIcon> list= new ArrayList<ImageIcon>();
		int x = 20,y=20;
		
		void launchJPanel(){
			new Thread (new Runnable() {
				public void run() {
					while (true) {
                        repaint();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
				}
			}).start();
			for(int i =1;i<5;i++){
				ImageIcon imag = new ImageIcon("imag/sign-in/"+i+".png");
				list.add(imag);
			}
		}
		
		public void paint(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 500);

            g.drawImage(list.get(0).getImage(), x, y, this);
            g.drawImage(list.get(0).getImage(), x+1, y, this);
           /* g.drawImage(list.get(2).getImage(), x, y, this);
            g.drawImage(list.get(3).getImage(), x, y, this);*/
            moveImage();
        }

		private void moveImage() {
			// TODO Auto-generated method stub
			
	            if (x < 180)
	                x++;
	            else
	                x = 20;
	        }
	    
		}
		
	public static void main(String[] args) {
		 Logining f = new Logining();
	        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	}

