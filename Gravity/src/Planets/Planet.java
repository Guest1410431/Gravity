package Planets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planet 
{	
	public float xPos;
	public float yPos;
	public float size;
	public float xVel;
	public float yVel;
	public float xAcc;
	public float yAcc;
	public float mass;
	
	public boolean still;
	
	private int ratio = 1;

	private CopyOnWriteArrayList<Point>points = new CopyOnWriteArrayList<Point>();
	
	public Planet(float xP, float yP, float s, float xV, float yV, float weight, boolean stationary)
	{
		 xPos = xP;
		 yPos = yP;
		 size = s;
		 xVel = xV;
		 yVel = yV;
		 mass = weight;
		 still = stationary;
	}
	
	public void update()
	{
		xPos += xVel;
		yPos += yVel;
		
		points.add(new Point(xPos, yPos));
	}
	
	public static double calculateDistance(double dist_x, double dist_y)
	{
	    return Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2));
	}
	//sun collision
	public boolean intersect(Planet sun)
	{
		double DX = xPos - sun.xPos;
		double DY = yPos - sun.yPos;
		double distance = Math.sqrt((DX*DX)+(DY*DY));
		
		return distance < (size/2)+(sun.size/2);
	}
	//gravity
	public void interact(Planet other) 
	{
	    float dx = other.getXPos() - xPos;
	    float dy = other.getYPos() - yPos;
	    double r  = calculateDistance(dx, dy);
	    double inv_r3 = 1.0 / (r * r * r);
	    //Precalculate force component (1/r^2) times direction (dx/r) = dx / r^3 
	    dx *= inv_r3;
	    dy *= inv_r3;
	    //calculate accelerations for both bodies
	    if(!still)
	    {
	    	xVel += other.getMass() * dx;
	   	    yVel += other.getMass() * dy;
	    }
	    if(!other.still)
	    {
	    	other.xVel += (mass*-dx);
		    other.yVel += (mass*-dy);	
	    }    
	}
	
	public void clearList()
	{
		points.clear();
	}
	
	public void render(Graphics g)
	{
		g.drawOval((int)(xPos-(size/2))*ratio, (int)(yPos-(size/2))*ratio, (int)size*ratio, (int)size*ratio);
		
		if(still)
		{
			g.setColor(Color.YELLOW);
			g.fillOval((int)(xPos-(size/2))*ratio, (int)(yPos-(size/2))*ratio, (int)size*ratio, (int)size*ratio);
		}
		for(Point point: points)
		{
			point.render(g);
		}
	}
	
	public float getXPos()
	{
		return xPos;
	}
	public float getYPos()
	{
		return yPos;
	}
	public float getSize()
	{
		return size;
	}
	public float getXVel()
	{
		return xVel;
	}
	public float getYVel()
	{
		return yVel;
	}
	public float getXAcc()
	{
		return xAcc;
	}
	public float getYAcc()
	{
		return yAcc;
	}
	public float getMass()
	{
		return mass;
	}
}
