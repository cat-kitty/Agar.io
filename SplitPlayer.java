//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

/*Splitplayer is child of player because it does the same things 
as the player except it must be created by the user. */
public class SplitPlayer extends Player {
	//Declarations
	int x, y, cnt; 
	static boolean visible; 
	
	public SplitPlayer(Player p) {
		this.x = (p.x + p.radius*2); 
		this.y = p.y; 
		this.colour = Color.WHITE; 
		visible = false;
		cnt = 0; 
	}

	//Draw the playerdouble onto the screen
	public void drawPlayer2(Graphics g) {
		//super.drawCircle(g); 
		g.setColor(colour);
		g.fillOval(x, y, radius *2, radius*2);
		g.setColor(Color.black);
		g.drawOval(x, y, radius *2, radius*2);
	}

	//Updates the position of the player double and the radius
	public void update(Player p) {
		this.x =(p.x + p.radius*2); 
		this.y = p.y; 
		if (cnt == 0) { //If it's the first time calling the method 
			this.colour=Color.BLACK;
			this.radius=p.radius; 
		} 
		cnt++; 
	}
	
	public void reJoin (Player p) {
		if (visible) { //if it exists 
			visible = false; 
			p.radius+=this.radius; 
			this.radius=0; 
			cnt = 0; 
		}
	}
}
