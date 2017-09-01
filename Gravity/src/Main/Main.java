package Main;

import Utilities.Loop;
import Utilities.PaintComponent;

public class Main 
{
	public static void main(String[] args) 
	{	
		PaintComponent window = new PaintComponent();
		
		Loop loop = new Loop(window);
		
		loop.run();
	}
}
