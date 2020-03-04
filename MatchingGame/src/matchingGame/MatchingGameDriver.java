package matchingGame;

/**Class: Programming Fundamentals

 *Project #  : Group Project #1

 *Students:    Hector Solis, Jackson Martinez, Jessie Billingsly

 *Completion Date: 3/26/2014

 *Project Description: This program is a matching game, the user will be presented with a target icon and an array of buttons. The user will have to match the target icon with buttons with the same icons from the button array within
 *a specified time. The user will earn points 100 points for every correctly matched icon and lose 100 points for every miss-matched icons. If the button clicked is a match with the target, the button will highlight green. If the button clicked
 *is NOT a match with the target, the button will highlight red. After the game expires, the user may end the game or start a new game.
**/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
public class MatchingGameDriver {

public static void main(String[] args) {

			  JFrame frame = new JFrame ("Welcome to Match Game!");
			  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		      frame.setPreferredSize (new Dimension(dim.width,dim.height));
		      Welcome welcome = new Welcome();

		      welcome.setPreferredSize(new Dimension(dim.width, dim.height));
		      frame.getContentPane().add(welcome);
		      
		      frame.pack();
		      frame.setVisible(true);
}
}


