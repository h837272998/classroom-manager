package com.hjh.jframe;

//可拖动类
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class SetDragable extends MouseAdapter {
	private Boolean isDragged = false,judge = true;
	private Point loc,tmp;
	
	
	public SetDragable(final JFrame jf) {
		jf.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isDragged = false;
				jf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());//获取窗体位置
				if(e.getY()<30) {
					isDragged = true;
					jf.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}else {
					jf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					isDragged = false;
				}
				
				
			}
			public void mouseEntered(MouseEvent e) {
				//if(e.getX()>=jf.getWidth()-3&&e.getX())
				if(e.getX()>jf.getWidth()) {
					jf.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					System.out.println(e.getX());
				}
			}
			
			public void mouseExited(MouseEvent e) {
				if(e.getX()<jf.getWidth()) {
					jf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					
				}
			}
		});
		jf.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDragged) {
					loc = new Point(jf.getLocation().x + e.getX()
							- tmp.x, jf.getLocation().y + e.getY()
							- tmp.y);
					jf.setLocation(loc);
				}
			}
		});
	}

}
