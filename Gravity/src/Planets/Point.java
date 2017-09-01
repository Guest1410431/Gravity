package Planets;

import java.awt.Graphics;

public class Point 
{
	public float xPos;
	public float yPos;
	
	public Point(float x, float y)
	{
		xPos = x;
		yPos = y;
	}
	public void render(Graphics g)
	{
		g.drawRect((int)xPos, (int)yPos, 1, 1);
	}
}
