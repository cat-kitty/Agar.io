//Fettes Sameer, Cai Kitty
//June 13th, 2019
//Final Project: Agar.io Game 
//ISC3U7
//Import java classes
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;

//Class for panels with buttons for going to other panels 
public class ChoicePanel extends JPanel {
	JButton b1, b2, b3; 
	static int bWidth = 115; 
	static int bHeight = 30; 
	static Dimension d = new Dimension(bWidth, bHeight); //default button size 
	
	//constructor 
	public ChoicePanel (String b1Name, String b2Name, String b3Name) {
		//make each of the buttons 
		b1 = new JButton(b1Name); 
		b1.setSize(d); 
		add(b1); 
		b2 = new JButton(b2Name); 
		b2.setSize(d); 
		add(b2); 
		b3 = new JButton(b3Name); 
		b3.setSize(d); 
		add(b3); 
		setLayout(new FlowLayout()); 
	}
	/*The constructor for this panel should generate all the buttons for the panel. 
	It takes the names of each button as input. The actionListeners are processed in 
	the individual panels themselves. */
}