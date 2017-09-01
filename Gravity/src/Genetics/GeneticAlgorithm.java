package Genetics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Planets.Planet;
import Planets.PlanetCreation;

public class GeneticAlgorithm 
{
	/* This is my first genetic algorithm
	 * My plan for it will be generating one planet with random attributes 
	 * within a set area, a score will be kept which represents its lifespan.
	 * If the planet wanders of screen, its life ends, the starting values as 
	 * well as the score are recorded and the process is repeated nine more 
	 * times. The two highest scores are taken from those ten tests and the
	 * attributes are averaged and stored. Then the ten trials start again
	 * with a more specified region based on the averaged values. This will
	 * continue until a stable orbit is found. A stable orbit is personally 
	 * defined as one minute without exceeding the bounds of the screen.
	 */
	private ArrayList<Integer>totalScore = new ArrayList<Integer>();
	
	public int[]scoreList = new int[10];
	public Planet[]planetList = new Planet[10];
	
	private float locationRange = 100 / PlanetCreation.generation;
	private float massRange = 3 / PlanetCreation.generation;
	private float velocityRange = 2 / PlanetCreation.generation;
	private float sizeRange = 5 / PlanetCreation.generation;
	
	private Planet average = new Planet(0, 0, 0, 0, 0, 0, false);
	
	public GeneticAlgorithm()
	{
		
	}
	
	public void generateGeneration(Planet[]planets, int[]score)
	{
		planetList = planets;
		
		scoreList = score;
		
		findHighestScoring();
	}
	//takes in the score array and finds the two highest indexes from score, 
	//directs the corresponding planets into the averaging method
	public void findHighestScoring()
	{
		float first = 0;
        float second = 0;
        int firstIndex = 0;
		int secondIndex = 0;
        
		int compound = 0;
		
		for(int i=0; i<scoreList.length; i++)
		{
			compound += scoreList[i];
		}
		totalScore.add(compound);
		
        for(int i=0; i<scoreList.length; i++)
        {
            if(first < scoreList[i])
            {
            	second = first;
            	first = scoreList[i];
            	secondIndex = firstIndex;
            	firstIndex = i;
            	
            } 
            else if(second < scoreList[i])
            {
            	second = scoreList[i];
            	secondIndex = i;
            }
        }        
        averagePlanetAttributes(planetList[firstIndex], planetList[secondIndex]);
	}
	//Takes in two planets and averages all the attributes
	public void averagePlanetAttributes(Planet a, Planet b)
	{
		average = new Planet((a.xPos+b.xPos)/2, (a.yPos+b.yPos)/2, (a.size+b.size)/2, (a.xVel+b.xVel)/2, (a.yVel+b.yVel)/2, (a.mass+b.mass)/2, false);
		setNewBounds();
	}
	//sets new bounds for the PlanetCreation class; xPos, yPos, xVel, yVel, mass (min/max)
	public void setNewBounds()
	{
		locationRange = 100 / PlanetCreation.generation;
		massRange = 3 / PlanetCreation.generation;
		velocityRange = 2 / PlanetCreation.generation;
		sizeRange = 5 / PlanetCreation.generation;

		PlanetCreation.setMinX(average.xPos-locationRange);
		PlanetCreation.setMaxX(average.xPos+locationRange);
		PlanetCreation.setMinY(average.yPos-locationRange);
		PlanetCreation.setMaxY(average.yPos+locationRange);
		PlanetCreation.setMinXVel(average.xVel-velocityRange);
		PlanetCreation.setMaxXVel(average.xVel+velocityRange);
		PlanetCreation.setMinYVel(average.yVel-velocityRange);
		PlanetCreation.setMaxYVel(average.yVel+velocityRange);
		PlanetCreation.setMinMass(average.mass+massRange);
		PlanetCreation.setMaxMass(average.mass+massRange);
		PlanetCreation.setMinSize(average.size-sizeRange);
		PlanetCreation.setMaxSize(average.size+sizeRange);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawOval((int)(1100-(average.size/2)), (int)(200-(average.size/2)),(int)average.size, (int)average.size);
		g.setColor(Color.BLUE);
		g.drawLine(1100, 200, (int)(1100+(average.xVel*10)), 200);
		g.setColor(Color.YELLOW);
		g.drawLine(1100, 200, 1100, (int)(200+(average.xVel*10)));
		
		for(int i=0; i<totalScore.size(); i++)
		{
			if(i<=12)
			{
				g.drawString("Generation " + (i+1) + ": " + totalScore.get(i), 625, (20*i)+25);
			}
			else if(i<=25)
			{
				g.drawString("Generation " + (i+1) + ": " + totalScore.get(i), 750, (20*(i-13))+25);
			}
			else if(i<=38)
			{
				g.drawString("Generation " + (i+1) + ": " + totalScore.get(i), 900, (20*(i-26))+25);
			}		
		}
	}
}
