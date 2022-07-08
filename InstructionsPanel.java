//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;
import java.util.*;
import java.io.*;  

//The InstructionsPanel is a panel that extends the ChoicePanel
class InstructionsPanel extends ChoicePanel implements ActionListener {
	//Declarations
	static int InstrX = 200; 
	static int InstrY = 100; 
	static int InstrSpaceY = 25; 
	int n = 9; 
	static String fileName = "Instructions.txt";
	String a = "";	
	JLabel l;
	String lines[];

	//The instruction panel
	public InstructionsPanel() {
		super("Play Game", "Main menu", "Exit"); 
		this.b1.addActionListener(this);
		this. b2.addActionListener(this); 
		b3.addActionListener(this); 
		this.setBackground(Color.GREEN); 
		setLayout(new FlowLayout()); 
		getInstructions();
	}

	//To click the buttons
	public void actionPerformed(ActionEvent e) {
		//Game Button
		if (e.getSource()==b1) {
			MyJava.thing.show(MyJava.c, MyJava.gp); 
			MyJava.game.restart(); 
		}
		
		//Instructions Button
		if (e.getSource()==b2) {
			MyJava.thing.show(MyJava.c, MyJava.mp); 
		}
		
		//Exit Button
		if (e.getSource()==b3) {
			System.exit(0); 
		}
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g); 
		g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 25)); 
		g.drawString("How To Play ", 410, 70); 
		for (int i = 0; i < n; i++) {
			if (i == 0)
				g.drawString(lines[i], InstrX+30, InstrY+i*InstrSpaceY); 
			else
				g.drawString(lines[i], InstrX, InstrY+i*InstrSpaceY); 
		}
	}
	
	public void getInstructions() {
		lines=new String[n]; 
		lines[0]="The goal of the game is to eat smaller circles,"; 
		lines[1]="and to avoid being eaten by larger circles. You can "; 
		lines[2]="move your circle using the mouse. You can also "; 
		lines[3]="split your circle into halves by pressing the "; 
		lines[4]="spacebar. You may not split again while your circle"; 
		lines[5]="is still split. To rejoin your circles, press"; 
		lines[6]="shift+spacebar. Once your score reaches 250, "; 
		lines[7]="you win the game. However, if any of the enemies "; 
		lines[8]="reach a score of 250, you lose. Enjoy!"; 
	}
}