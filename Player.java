//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

/*Player is a child of enemy because it does the same 
thing as enemies except it is controlled by the mouse 
movement and it can split */
public class Player extends Enemy  {
	String name; 
	public Player (int minX, int maxX, int minY, int maxY) {
		super(minX, maxX, minY, maxY); 
		this.colour = Color.black; 
		this.x = 300;
		this.y = 300; 
		this.radius = 30;
		this.name="P1"; 
	} 

	public Player () {
		this(minXs, maxXs, minYs, maxYs);
	} 

	//Draw the player onto the screen
	public void drawPlayer (Graphics g) {
		super.drawCircle(g); 
		g.drawString(this.name, x, y); //show the name 
	}
	
	//For the player to follow the mouse, add to the coordinates to bring the circles closer to the mouse
	public void follow(int mouseX, int mouseY){
		//To change the X-Coordinates
		if (mouseX > x) //if the mouse is to the right of the player
			x+=2; //move the player to the right 
		else if (mouseX < x) //if the mouse is to the left of the player
			x-=2; //move the player to the left 
		else if (mouseX == x) //if the mouse is on the same x location 
			x+=0; //do not change the x coordinate 
		//To change the Y-Coordinates
		if (mouseY > y) // if the mouse is below the player 
			y+=2; //move the player down 
		else if (mouseY < y ) //if the mouse is above the player
			y-=2; //move the player up
		else if (mouseY == y) //if the mouse is on the same y location 
			y+=0; //do not change the y value
	}
	
} 