//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

/*Enemy is child of circle because enemies are also circles 
except they move and eat. */
public class Enemy extends Circle {
	//Declarations
	private int dx, dy; //speed for x and y
	int id;
	public Enemy (int minX, int maxX, int minY, int maxY) {
		super(minX, maxX, minY, maxY); 
		this.radius=(int)(Math.random()*10)+25; //the radius for enemies is random between 25 and 35
		dx=(int)(Math.random()*10)-5; //generate random value between -5 and 5 for speed 
        dy=(int)(Math.random()*10)-5;
		if (dx==0) //if the movement speed is 0 default it to 5
			dx=5; 
		if (dy==0)
			dy=5; 
	} 
	public Enemy () { //overloading constructor
		this(minXs, maxXs, minYs, maxYs); 
		//do the same thing as first constructor but use constants as input 
	} 

	public void drawEnemy (Graphics g) { //method for showing the enemy
		super.drawCircle(g); //draw enemy and circle the same way
		g.drawString(" " + id, x, y); //draw the id 
	}
	
	public void move() { //default enemy movement 
		if (x>=maxXs||x<=minXs){ //if enemy is at horizontal edge of screen 
			dx*=-1; //move in opposite x direction
			x+=dx; 
		}
		else //else keep moving 
			x+=dx; 
		if (y>=maxYs||y<=minYs) { //if enemy is at vertical edge of screen 
			dy*=-1; //move in opposite y direction 
			y+=dy; 
		}
		else //else keep moving 
			y+=dy; 
	}
	
//For circles #2 and #3 to chase each other
	public void follow(int xChase, int yChase){
		double speed =  Math.sqrt(dx*dx + dy*dy); //Calculate the speed that the circle was originally moving
		double a = xChase - x;
		double b = yChase - y ;
		double distance = Math.sqrt(a*a + b*b); //Find the distance between the two circles
		int c = (int)Math.round((speed * (a/distance)));
		int d = (int)Math.round((speed* (b/distance)));

		dx = c;
		dy = d; 
	}

}