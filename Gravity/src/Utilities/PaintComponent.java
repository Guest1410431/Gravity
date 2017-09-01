package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Planets.PlanetCreation;

public class PaintComponent extends JPanel
{
	JFrame frame;
	
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	PlanetCreation pc = new PlanetCreation();
	
	public PaintComponent()
	{
		super();
		 
		setBackground(Color.BLACK);
			
		int width = 1200;
		int height = 600;
		
		frame = new JFrame();                    
	        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.add(this);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		
		
		frame.addKeyListener(new KeyListener() //key listener, WASD input
				{					
					public void keyPressed(KeyEvent e) 
					{
						int key = e.getKeyCode();
						
						if(key == KeyEvent.VK_ESCAPE) //key bind to close the application
						{
							System.exit(1);
						}
					}
					public void keyReleased(KeyEvent arg0) {}
					
					public void keyTyped(KeyEvent arg0) {}
				});
	 }
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawRect(0, 0, 600, 600);
		g.drawRect(600, 0, 600, 600);
		
		pc.render(g);
	}
	
	public void update()
	{
		pc.update();
		
		repaint();
	}
}
