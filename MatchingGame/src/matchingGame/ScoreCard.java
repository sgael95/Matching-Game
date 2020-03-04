package matchingGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class ScoreCard{
	//constructor
	public ScoreCard(String s){
		JFrame frame = new JFrame("Scores");
		JTextArea textarea = new JTextArea();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setPreferredSize (new Dimension(560, 290));
		frame.setLocation((int)(dim.getWidth()/3.5),(int)(dim.getHeight()/3));
		textarea.setBackground(Color.LIGHT_GRAY);
		Font font = new Font("Verdana", Font.BOLD, 14);
		textarea.setFont(font);

		textarea.append(s);
				
		frame.add(textarea);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
	}
}