//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

//The FinalPanel is a panel that extends the ChoicePanel
class FinalPanel extends ChoicePanel implements ActionListener {

	public FinalPanel() {
		super("Play Again", "Main Menu", "Exit"); 
		b1.addActionListener(this); 
		b2.addActionListener(this); 
		b3.addActionListener(this); 
		setBackground(Color.MAGENTA); 
	}
	
	//To click the buttons
	public void actionPerformed (ActionEvent e) {
		//Game Button
		if(e.getSource()== b1) { //if first button is clicked 	
			MyJava.game.restart(); 
			MyJava.thing.show(MyJava.c, MyJava.gp); 
			//go to the game 
		}
		//Menu Button
		if (e.getSource()== b2) { //if second button is clicked 
			MyJava.thing.show(MyJava.c, MyJava.mp); 	
			//go to the menu panel 
		}
		//Exit Button
		if (e.getSource()== b3) { //if third button is clicked
			System.exit(0); 
			//exit the program
		}	
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g); 
		g.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 25));
		g.drawString("Thanks For Playing!", 370, 70);	
	}
}