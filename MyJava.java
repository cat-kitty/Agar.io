//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7

//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;


class MyJava extends JFrame {//implements KeyListener {
	//Declarations
	static CardLayout thing; 
	static Container c; 
	static String gp, mp, ip, fp;
	static GamePanel game; 
	static InstructionsPanel instructions; 
	static FinalPanel finalP; 
	static MenuPanel menu;
	
	//
	public MyJava(String n) {
		super(n); 
		c = getContentPane(); 
		thing = new CardLayout(); 
		c.setLayout(thing); 
		menu = new MenuPanel(); 
		game = new GamePanel(); 
		instructions = new InstructionsPanel(); 
		finalP = new FinalPanel(); 
		mp = "menup"; 
		gp = "gamep"; 
		ip = "instructionsp"; 
		fp = "finalp"; 
		c.add(menu, mp); 
		c.add(game, gp); 
		c.add(instructions, ip); 
		c.add(finalP, fp); 
		this.setFocusable(false); 
		game.setFocusable(true); 
	}
	
	//	
	public static void main (String [] args) {
		MyJava test = new MyJava("Agar.io game"); 
		//Horizontal length and vertical length in pixels 
		test.setSize(1000,600); 
		//Dimensions of Strelkovska screen are 1366 by 768
		test.setVisible(true); 
		test.setResizable(false); 
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Circle a = new Circle(); 
		Enemy c = new Enemy(); 
		Player b = new Player(); 
		SplitPlayer idk = new SplitPlayer(b); 
		
	}
	
	//Import mouse controls
	//public void keyPressed (KeyEvent e) {}
	//public void keyTyped (KeyEvent e) {}
	//public void keyReleased (KeyEvent e) {}
}