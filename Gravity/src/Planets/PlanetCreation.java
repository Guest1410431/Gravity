package Planets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Genetics.GeneticAlgorithm;

public class PlanetCreation 
{
	private int numPlanets = 1;
	
	public int score = 0;
	private int totalScore = 0;
	private int maxScore = 10000;
	private ArrayList<Integer>scores = new ArrayList<Integer>();
	
	private static float maxX = 300;
	private static float minX = 0;
	private static float maxY = 300;
	private static float minY = 0;
	private static float maxSize = 50;
	private static float minSize = 10;
	private static float maxXVel = 3;
	private static float minXVel = -3;
	private static float maxYVel = 3;
	private static float minYVel = -3;
	private static float minMass = 5;
	private static float maxMass = 15;
	
	private CopyOnWriteArrayList<Planet>planetList = new CopyOnWriteArrayList<Planet>();
	
	public static int index=0;//keeps track of which iteration the genetic algorithm is on
	public static int generation=1;
	
	public static Planet[]testPlanets = new Planet[10];//list of the 10 planets' starting attributes
	public static int[]scoreList = new int[10];//list of the scores of the planets
	
	GeneticAlgorithm ga = new GeneticAlgorithm();
	
	public PlanetCreation()
	{
		initPlanets();
	}
	
	public void initPlanets()
	{
		if(scores.isEmpty())
		{
			scores.add(0);
		}
		if(index > 9)
		{
			ga.generateGeneration(testPlanets, scoreList);
			index = 0;
			generation++;
			scores.add(totalScore);
			totalScore = 0;
		}
		
		for(int i=0; i<numPlanets; i++)
		{
			float xPos = (float)(Math.random()*(maxX - minX) + minX);
			float yPos = (float)(Math.random()*(maxY - minY) + minY);
			float size = (float)(Math.random()*(maxSize-minSize)+minSize);
			float xVel = (float)(Math.random()*(maxXVel - minXVel) + minXVel);
			float yVel = (float)(Math.random()*(maxYVel - minYVel) + minYVel);		
			float mass = (float)(Math.random()*(maxMass - minMass) + minMass);
			
			planetList.add(new Planet(xPos, yPos, size, xVel, yVel, mass, false));
			testPlanets[index] = new Planet(xPos, yPos, size, xVel, yVel, mass, false);
		}
		//planetList.add(new Planet(300, 300, 50, 0, 0, 100, true));
		planetList.add(new Planet(100, 300, 50, 0, 0, 50, true));
		planetList.add(new Planet(450, 200, 50, 0, 0, 50, true));
	}
	
	public void update()
	{
		interact();
		
		Planet planet = planetList.get(0);
		
		planet.update();
		
		for(int i=1; i<planetList.size(); i++)
		{
			if(planet.xPos >= 600 || planet.xPos <= 0 || planet.yPos >= 600 || planet.yPos <= 0 || score == maxScore || planet.intersect(planetList.get(i)))
			{
				scoreList[index] = score;
				score = 0;
				index++;
				
				planet.clearList();
				planetList.clear();
				initPlanets();
			}
			score++;
			totalScore++;
		}
	}
	
	public void interact()
	{
		for(int i=0; i<planetList.size(); i++)
		{
			for(int h=0; h<planetList.size(); h++)
			{
				if(i == h)
				{
					continue;
				}
				planetList.get(i).interact(planetList.get(h));
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.drawString("Generation: " + generation + "   Trial: " + (index+1) + "   Score: " + score, 50, 50);

		for(Planet planets: planetList)
		{
			planets.render(g);
		}
		g.setColor(Color.WHITE);
		g.drawRect((int)minX, (int)minY, (int)(maxX-minX), (int)(maxY-minY));
		
		//graphing the data
		g.fillRect(600, 270, 600, 300);
		
		g.setColor(Color.GRAY);
		for(int i=0; i<10; i++)
		{
			g.drawLine(600, 300+(30*i), 1200, 300+(30*i));
		}		
		g.setColor(Color.BLUE);
		for(int i=0; i<scores.size(); i++)
		{
			g.fillRect(579 + (i*21), 570-(scores.get(i)/(maxScore/30)), 20, scores.get(i)/(maxScore/30));
			g.fillRect(600 + ((scores.size()-1)*21), 570-(totalScore/(maxScore/30)), 20, totalScore/(maxScore/30));
		}
		ga.render(g);
	}
	
	public static void setMinX(float into)
	{
		minX = into;
	}
	public static void setMaxX(float into)
	{
		maxX = into;
	}
	public static void setMinY(float into)
	{
		minY = into;
	}
	public static void setMaxY(float into)
	{
		maxY = into;
	}
	public static void setMinXVel(float into)
	{
		minXVel = into;
	}
	public static void setMaxXVel(float into)
	{
		maxXVel = into;
	}
	public static void setMinYVel(float into)
	{
		minYVel = into;
	}
	public static void setMaxYVel(float into)
	{
		maxYVel = into;
	}
	public static void setMinMass(float into)
	{
		minMass = into;
	}
	public static void setMaxMass(float into)
	{
		maxMass = into;
	}
	public static void setMinSize(float into)
	{
		minSize = into;
	}
	public static void setMaxSize(float into)
	{
		maxSize = into;
	}
}
