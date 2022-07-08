//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

//The MenuPanel is a panel that extends the ChoicePanel
public class MenuPanel extends ChoicePanel implements ActionListener {
	
	//The menupanel
	public MenuPanel() {
		super("Play Game", "Instructions", "Exit"); 
		b1.addActionListener(this); 
		b2.addActionListener(this); 
		b3.addActionListener(this); 
		setBackground(Color.CYAN);
	}
	
	//To click the buttons
	public void actionPerformed (ActionEvent e) {
		//Game Button
		if (e.getSource()== b1) {
			MyJava.thing.show(MyJava.c, MyJava.gp); 
			MyJava.game.restart(); 
		}

		//Instructions Button
		if (e.getSource()== b2){
			MyJava.thing.show(MyJava.c, MyJava.ip);  
		}
		
		//Exit Button
		if (e.getSource()== b3){
			System.exit(0);
		}
	}
	
	//To write text on the panel
	public void paintComponent (Graphics g) {
		super.paintComponent(g); 
		g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 25)); 
		g.drawString("Welcome to Agar.io", 370, 70); 
		g.drawString("By Sameer Fettes and Kitty Cai", 300, 100); 
	}



} 