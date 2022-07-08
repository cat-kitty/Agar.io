//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes 
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

//this is the class for the pieces of food
public class Circle{ 
	//variables 
	Color colour; 
	protected int radius, x, y; 
	static int minXs = 40; 
	static int maxXs = 910; 
	static int minYs = 40; 
	static int maxYs = 500; 

	public Circle (int minX, int maxX, int minY, int maxY){
		colour = makeColour();
		radius = 5;
		x = (int)(Math.random()*(maxX-minX)) + minX;
		y = (int)(Math.random()*(maxY-minY)) + minY;
		//generate x and y values within range
	}
	
	public Circle() { //overloading constructor 
	//do the same thing as first constructor and use constants for the values
		this(minXs, maxXs, minYs, maxYs); 
	}
	
	public void drawCircle(Graphics g){
		//method for drawing the circle 
		g.setColor(colour);
		g.fillOval(x, y, radius *2, radius*2);
		g.setColor(Color.black);
		g.drawOval(x, y, radius *2, radius*2);
	}
	
	public Color makeColour() { //To generate random colour 
		int n = (int)(Math.random()*12); 
		switch(n) {
			case 0: return Color.yellow; 
			
			case 1: return Color.blue; 

			case 2: return Color.cyan; 

			case 3: return Color.darkGray; 

			case 4: return Color.gray;

			case 5: return Color.green; 

			case 6: return Color.lightGray; 

			case 7: return Color.magenta; 

			case 8: return Color.orange; 

			case 9: return Color.pink; 
			
			case 10: return Color.red;  
			
			case 11: return Color.white; 
			
			default: return Color.red; 
		}
	}
}