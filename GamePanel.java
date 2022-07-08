//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes 
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;
import java.awt.image.*; 
import javax.imageio.*; 
import java.io.*;
import java.awt.event.InputEvent; 
import javax.sound.sampled.*;

//This is the GamePanel
public class GamePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
	//Declarations
	private ImageIcon img;
	private int numFood = 100;  //how many food pieces
	private int numEnemies = 10; //how many enemies 
	private JLabel statusBar;
	private Circle food []; 
	private Enemy enemies[]; 
	private Timer t, moveT; 
	private int width, height;  
	private  Player p; 
	private JButton sample;
	private int mouseXc, mouseYc;   
	private int cnt;  
	private  SplitPlayer p2; 
	private boolean doubleExist;  
	private boolean cont;  
	private int splits; 
	
	//Constructor idk
	public GamePanel() {
		splits = 0; 
		cont = true; 
		img =  new ImageIcon("Grid.png");
		doubleExist = false; 
		sample = new JButton("Click to use spacebar to enable splitting"); 
		sample.addKeyListener(this); 
		food = new Circle[numFood];  
		enemies = new Enemy[numEnemies]; 
		makeFood(); 
		makeEnemies(); 
		t = new Timer(60, this);
		t.start();
		width = getWidth();
		height = getHeight(); 
		p = new Player();
		p2= new SplitPlayer(p);
		
		statusBar = new JLabel();
		statusBar.setText("Score is: " + p.radius); 
		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.SOUTH);
		add(sample, BorderLayout.NORTH);
				
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this); 
		//idk = MouseInfo.getPointerInfo(); 
		//bro = idk.getLocation(); 
		moveT = new Timer(10, this); 
		moveT.start(); 
		cnt = 0; 
		music();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawGrid(g);
		drawFood(g); 
		drawEnemies(g); 
		drawP(g); 
		if (p2.visible) //only show splitplayer if they exist 
			drawP2(g); 
    }
	
	//Method to be called when the game is played multiple times
	public void restart() {
		p = new Player(); //Player's size and other variables reset
		makeFood(); //new food 
		makeEnemies(); //new set of enemies
		cont = true; 
		//p2.visible=false; 
		p2=new SplitPlayer(p); //SplitPlayer resets
	}
   
	//Declaring food 
	public void makeFood () {
		for (int i = 0; i < numFood; i++) {
			food[i] = new Circle(); 
		}
	}
	
	//Declaring enemies 
	public void makeEnemies () {
		for (int i = 0; i < numEnemies; i++) {
			enemies[i] = new Enemy(); 
			enemies[i].id=i+1; //making the ids 
		}
	} 

	//To import the background image 
	public void drawGrid (Graphics g) {
		g.drawImage(img.getImage(), 0,0, null); 
	}

	//Draw the food
	public void drawFood (Graphics g) {
		for (int i = 0; i < numFood; i++) {
			food[i].drawCircle(g); 
		}
	}
	
	//Draw the enemies
	public void drawEnemies (Graphics g) {
		for (int i = 0; i < numEnemies; i++) {
			enemies[i].drawEnemy(g); 
		}
	}
	
	//Make each enemy move
	public void enemiesMove() {
		for (int i = 0; i < numEnemies; i++) {
			enemies[i].move(); 
		}
	}
	
	//calling methods for timers 
	public void actionPerformed(ActionEvent e){ 
		if (e.getSource()== t)
			timerAction();
		else if (e.getSource()== moveT)
			moveTAction(); 
	}
	
	public void timerAction(){ 
		if (cont){ //if the game is still going 
			for(int i = 0; i < numEnemies; i++) {
				enemies[i].move(); //move all the enemies
			}
			p.follow(mouseXc, mouseYc); //make the player track the mouse
			if (p2.visible){ //if splitplayer exists
				p2.update(p); //update the location of splitplayer 
			}	
			repaint();  
		}
    }
	
	//second timer method for the faster timer 
	public void moveTAction() {
		if (cont) { //if the game should keep going 
			cnt++; 
			if (cnt%3==0) { 
			/*It should only call certain methods every third time 
			the timer goes off. This makes sure player movement isn't too fast
			*/
				p.follow(mouseXc,mouseYc);
				//follow called in both timer methods to increase the speed of movement 
				foodEaten(); //check which pieces of food have been eaten 
				enemiesEaten(); //check which enemies have been eaten 
				enemiesEatEnemies(); //check if enemies should eat each other
				if (p2.visible) { //if there is a splitplayer 
					p2.update(p); //update its location 
					foodEatenDouble(); //make it eat
				}
			}
			if (cnt%1 == 0){
				AICatch();
			}
			
			if (cont) //if game is still going 
				winner(playerWon(), enemiesWon()); 
				//check if anyone has won yet 
			
			repaint(); 
		}
	}
	
	//method to call the player's draw method
	public void drawP (Graphics g) {
		p.drawPlayer(g); 
	}
	
	//calling splitplayer's draw method 
	public void drawP2 (Graphics g) {
		if (p2.visible) //if player has been split 
			p2.drawPlayer2(g); //draw the splitplayer
	}
	
	//Unused mouse movement 
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	//Tracking mouse for player movement
	public void mouseMoved(MouseEvent e){
		mouseXc = (int)e.getX(); //get mouse x coordinate 
		mouseYc = (int)e.getY(); //get mouse y coordinate 
		if (p2.visible) { //if splitplayer exists
			p2.update(p); //update its location  
		}
		repaint(); 
	}

	//Keylistener methods 
	
	public void keyPressed (KeyEvent e) { 
		if (e.getKeyCode() == KeyEvent.VK_SPACE && p.radius > 50&&!p2.visible) {
			//if they pressed spacebar and player is within desired size and splitplayer doesn't exist
			p.radius = p.radius/2; //decrease player size to half 
			if (splits == 0) { //If they haven't split yet
				p2=new SplitPlayer(p); //make the splitplayer 
				p2.visible=true; 
				p2.update(p); //update splitplayer location 
			}
			showScore(); //update score shown 			
			repaint();		
		}
		
		//Use the space bar while holding shift to combine 
		else if (e.getKeyCode()==KeyEvent.VK_SPACE&&e.getModifiers()==InputEvent.SHIFT_MASK&&p2.visible) {
		//if they press space while holding shift and there is a splitplayer. 
			p2.reJoin(p); //rejoin them 
			p.radius += p2.radius; //return player radius back to normal 
			splits = 0; 
		}
		
	}
	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}
	
	public void foodEaten() {
		//Declaration
		double midX, midY, midX1, midY1, distance;	//For the for loop, to check if food is being eaten
		double mX, mY, mPX, mPY, distance1; //For the player, to check if food is being eaten
		double size = 0; //Checking the size of the food being eaten 
		int[] sizeE = new int[]{0,0,0,0,0,0,0,0,0,0}; //To see how many pieces of food were eaten by each enemy so that they can only add a certain number to the size so that the growing looks smooth
		
		for (int i =0; i<numFood; i++) { //Look in the food to see which one is being eaten
			for (int j =0; j<numEnemies; j++) {	//Look for which enemy is eating the food
				midX = food[i].x + food[i].radius;
				midY = food[i].y + food[i].radius;
				midX1 = enemies[j].x + enemies[j].radius;
				midY1 = enemies[j].y + enemies[j].radius;
				distance = Math.sqrt((midX1-midX)*(midX1-midX) + (midY1-midY)*(midY1-midY)); //Check the distance between the food and the enemy and see if they're touching
				
				if (distance < food[i].radius + enemies[j].radius) { //If they are touching
					food[i] = new Circle(); //Redraw the food that was eaten
					
					if (enemies[j].radius < 20) //To only add a certain amount to the radius
						enemies[j].radius += 1;
					else if (enemies[j].radius > 19 && enemies[j].radius < 30){
						sizeE[j] += 0.5;
						if (sizeE[j] == 1){
							enemies[j].radius += 1;
							sizeE[j] = 0;
						}
					}
					else if (enemies[j].radius > 29 && enemies[j].radius < 40){
						sizeE[j] += 0.2;
						if (sizeE[j] == 1){
							enemies[j].radius += 1;
							sizeE[j] = 0;
						}
					}
					else if (enemies[j].radius > 39){
						sizeE[j] += 0.1;
						if (sizeE[j] == 1){
							enemies[j].radius += 1;
							sizeE[j] = 0;
						}
					}
				}
			}
		}
		
		//To check if the player is eating food, same concept as enemy eating the food
		for (int i = 0; i < numFood; i++) {
			mX = food[i].x + food[i].radius;
			mY = food[i].y + food[i].radius;
			mPX = p.x + p.radius;
			mPY = p.y + p.radius;
			distance1 =  Math.sqrt((mPX-mX)*(mPX-mX) + (mPY-mY)*(mPY-mY));
			if (distance1 < food[i].radius + p.radius) {
				food[i] = new Circle();
				
				if (p.radius < 20) {
					p.radius += 1;
				    showScore();
				}
				else if (p.radius >= 20 && p.radius < 30){
					size += 0.5;
					if (size == 1){ // For every two pieces of food eaten, the circle gets bigger
						p.radius += 1;
						showScore();
						size = 0;
					}
				}
				else if (p.radius >= 30){
					size += 0.20;
					if (size == 1){ // For every four pieces of food eaten, the circle gets bigger 
						p.radius += 1;
						showScore();
						size = 0;
					}
				}
			}
		}
	}
	
	//Same concept as foodEaten()
	public void enemiesEaten() {
		//Declaration
		double mEX, mEY, mPX, mPY, distance2; //For the for loop, to check if enemies are being eaten
		
		for (int i = 0; i < numEnemies; i++) {
			mEX = enemies[i].x + enemies[i].radius;
			mEY = enemies[i].y + enemies[i].radius;
			mPX = p.x + p.radius;
			mPY = p.y + p.radius;
			distance2 =  Math.sqrt((mPX-mEX)*(mPX-mEX) + (mPY-mEY)*(mPY-mEY));
			if (distance2 < enemies[i].radius + p.radius) {
				if (p.radius > enemies[i].radius){
					enemies[i].x = (int)(Math.random()*(Circle.maxXs-Circle.minXs)) + Circle.minXs;
					enemies[i].y =(int)(Math.random()*(Circle.maxYs-Circle.minYs)) + Circle.minYs;
					enemies[i].colour = enemies[i].makeColour(); 
					enemies[i].radius = (int)(Math.random()*10)+25;

					if (p.radius < 20)
						p.radius += (enemies[i].radius/2);
					else if (p.radius > 19 && p.radius < 30)
						p.radius += (enemies[i].radius/3);
					else if (p.radius > 29 && p.radius < 40)
						p.radius += (enemies[i].radius/4);
					else
						p.radius += (enemies[i].radius/5);
					
					showScore();
				}
				else{
					if (p.radius != 10)
						enemies[i].radius += p.radius/2;
					p.radius = 10;
					showScore();
				}
			}
		}
	}
	
	//If you do not want to try playing the game uncomment the comments
	public void enemiesEatEnemies() {
		//Declaration
		double mEX1, mEY1, mEX2, mEY2, distance3; //For the for loop, to check if enemy is being eaten by another enemy
		for (int i = 0; i < numEnemies; i++) {
			for (int j = 0; j < numEnemies; j++) {
				if (i != j && enemies[i].radius != enemies[j].radius) {
					mEX1 = enemies[i].x + enemies[i].radius;
					mEY1 = enemies[i].y + enemies[i].radius;
					mEX2 = enemies[j].x + enemies[j].radius;
					mEY2 = enemies[j].y + enemies[j].radius;
					distance3 =  Math.sqrt((mEX2-mEX1)*(mEX2-mEX1) + (mEY2-mEY1)*(mEY2-mEY1));
					if (distance3 < enemies[i].radius + enemies[j].radius) {
						if (enemies[i].radius > enemies[j].radius){
							enemies[j].x = (int)(Math.random()*(Circle.maxXs-Circle.minXs)) + Circle.minXs;
							enemies[j].y = (int)(Math.random()*(Circle.maxYs-Circle.minYs)) + Circle.minYs;
							enemies[j].radius = (int)(Math.random()*20)+15;
							enemies[j].colour = enemies[j].makeColour(); 
						
							if (enemies[i].radius < 15)
								enemies[i].radius += (enemies[j].radius/2);
							else if (enemies[i].radius > 14 && enemies[i].radius < 25)
								enemies[i].radius += (enemies[j].radius/3);
							else if (enemies[i].radius > 24 && enemies[i].radius < 35)
								enemies[i].radius += (enemies[j].radius/4);
							else // if (enemies[i].radius > 34 && enemies[i].radius < 45)
								enemies[i].radius += (enemies[j].radius/5);
							/*else if (enemies[i].radius > 44 && enemies[i].radius < 55)
								enemies[i].radius += (enemies[j].radius/10);
							else if (enemies[i].radius > 54 && enemies[i].radius < 65)
								enemies[i].radius += (enemies[j].radius/20);
							else
								enemies[i].radius += (enemies[j].radius/100);*/
						}
						
						else if (enemies[j].radius > enemies[i].radius){
							enemies[i].x = (int)(Math.random()*(Circle.maxXs-Circle.minXs)) + Circle.minXs;
							enemies[i].y = (int)(Math.random()*(Circle.maxYs-Circle.minYs)) + Circle.minYs;
							enemies[i].radius = (int)(Math.random()*20)+15;
							enemies[i].colour = enemies[i].makeColour(); 

							if (enemies[j].radius < 15)
								enemies[j].radius += (enemies[i].radius/2);
							else if (enemies[j].radius > 14 && enemies[j].radius < 25)
								enemies[j].radius += (enemies[i].radius/3);
							else if (enemies[j].radius > 24 && enemies[j].radius < 35)
								enemies[j].radius += (enemies[i].radius/4);
							else //if (enemies[j].radius > 34 && enemies[j].radius < 45)
								enemies[j].radius += (enemies[i].radius/5);
							/*else if (enemies[j].radius > 44 && enemies[j].radius < 55)
								enemies[j].radius += (enemies[i].radius/10);
							else if (enemies[j].radius > 54 && enemies[j].radius < 65)
								enemies[j].radius += (enemies[i].radius/20);
							else
							/enemies[j].radius += (enemies[i].radius/100);*/
						}
					}
				}
			}
		}
	}
	
	public void foodEatenDouble() {
		//Declaration
		double midX, midY, distance4;	//For the for loop, to check if food is being eaten
		double mXD, mYD; //For the player double, to check if food is being eaten
		double sizeD = 0; //Checking the size of the food being eaten 
		double mEXD, mEYD, distance5; //For the for loop, to check if enemy is being eaten

		for (int i = 0; i < numFood; i++) {
			midX = food[i].x + food[i].radius;
			midY = food[i].y + food[i].radius;
			mXD = p2.x + p2.radius;
			mYD = p2.y + p2.radius;
			distance4 =  Math.sqrt((mXD-midX)*(mXD-midX) + (mYD-midY)*(mYD-midY));
			if (distance4 < food[i].radius + p2.radius) {
				food[i]=new Circle(); 
				
				
				if (p2.radius < 20) {
					p2.radius += 1;
					showScore();
				}
				else if (p2.radius >= 20 && p2.radius < 30){
					sizeD += 0.5;
					if (sizeD == 1){ // For every two pieces of food eaten, the circle gets bigger
						p2.radius += 1;
						showScore();
						sizeD = 0;
					}
				}
				else if (p2.radius >= 30){
					sizeD += 0.20;
					if (sizeD == 1){ // For every four pieces of food eaten, the circle gets bigger
						p2.radius += 1;
						showScore();
						sizeD = 0;
					}
				}
			}
		}
		
		for (int i = 0; i < numEnemies; i++) {
			mEXD = enemies[i].x + enemies[i].radius;
			mEYD = enemies[i].y + enemies[i].radius;
			mXD = p2.x + p2.radius;
			mYD = p2.y + p2.radius;
			distance5 =  Math.sqrt((mXD-mEXD)*(mXD-mEXD) + (mYD-mEYD)*(mYD-mEYD));
			if (distance5 < enemies[i].radius + p2.radius) {
				if (p2.radius > enemies[i].radius){
					enemies[i].x = (int)(Math.random()*(Circle.maxXs-Circle.minXs)) + Circle.minXs;
					enemies[i].y = (int)(Math.random()*(Circle.maxYs-Circle.minYs)) + Circle.minYs;
					enemies[i].colour=enemies[i].makeColour(); 
					enemies[i].radius=(int)(Math.random()*10)+25;
					if (p2.radius < 20)
						p2.radius += (enemies[i].radius/2);
					else if (p2.radius > 19 && p2.radius < 30)
						p2.radius += (enemies[i].radius/3);
					else if (p2.radius > 29 && p2.radius < 40)
						p2.radius += (enemies[i].radius/4);
					else
						p2.radius += (enemies[i].radius/5);
					showScore();
				}
				else{
					if (p2.radius != 10)
						enemies[i].radius += p2.radius/2;
					p2.radius = 10;
					showScore();
				}
			}
		}
		
	}
	
	//method to check if game should end 
	public void winner(boolean pWon, boolean eWon) {
		if (pWon) { //if player won
			cont=false; //stop game refreshing 
			JOptionPane.showMessageDialog(this, "You Win!"); //display win message
			MyJava.thing.show(MyJava.c, MyJava.fp); //show final screen 
			
		}
		if (eWon) { //if enemy won 
			cont=false; //stop refreshing 
			JOptionPane.showMessageDialog(this, "You lose!"); //display lose message 
			MyJava.thing.show(MyJava.c, MyJava.fp); //show final screen 
		}
	} 
	
	public boolean enemiesWon() {
		int a =0; //number of enemies greater than the specifed size 
		for (int i =0; i<numEnemies; i++) { //check all enemies 
			if (enemies[i].radius>=250) { //if enemy size is greater than win size
				a++; //increment number of enemies 
				return true; //enemies won the game 
			}
		}
		
		if (a == 0) //if there are no enemies greater than win size 
			return false; //enemies did not win the game
		else
			return true; //default return statement 
	}
	
	public boolean playerWon() { //check if the player won 
	//if combined player and splitplayer size is greater than win size return true; 
		return (p.radius + p2.radius) >= 250; 
	}
	
	public void showScore() { //update the statusbar
		if (p2.visible) //if there is a splitplayer 
			statusBar.setText("Score is: " + (p.radius+p2.radius)); 
			//show player and splitplayer's score together
		else
			statusBar.setText("Score is: " + p.radius); 
			//show only player's score 
	}
	
	public void AICatch() {
		//If circle 2 is bigger than circle 3, circle 2 will chase circle 3
		if (enemies[1].radius > enemies[2].radius)
			enemies[1].follow( enemies[2].x, enemies[2].y);
		//If circle 3 is bigger than circle 2, circle 3 will chase circle 2
		else if (enemies[2].radius > enemies[1].radius)
			enemies[2].follow( enemies[1].x, enemies[1].y);
		
		if (enemies[3].radius > enemies[4].radius)
			enemies[3].follow( enemies[4].x, enemies[4].y);
		else if (enemies[4].radius > enemies[3].radius)
			enemies[4].follow( enemies[3].x, enemies[3].y);
		
		if (enemies[5].radius > enemies[6].radius)
			enemies[5].follow( enemies[6].x, enemies[6].y);
		else if (enemies[6].radius > enemies[5].radius)
			enemies[6].follow( enemies[5].x, enemies[5].y);
		
		if (enemies[7].radius > enemies[8].radius)
			enemies[7].follow( enemies[8].x, enemies[8].y);
		else if (enemies[8].radius > enemies[7].radius)
			enemies[8].follow( enemies[7].x, enemies[7].y);
		
		if (enemies[9].radius > enemies[0].radius)
			enemies[9].follow( enemies[0].x, enemies[0].y);
		else if (enemies[0].radius > enemies[9].radius)
			enemies[0].follow( enemies[9].x, enemies[9].y);
		
	}

	public static void music() {
		Clip music = null;
		AudioInputStream inputStream = null;
		try {
			music = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(new File("GamingMusic.wav"));
			music.open(inputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		music.loop(Clip.LOOP_CONTINUOUSLY);
		music.start();
	}

	
}