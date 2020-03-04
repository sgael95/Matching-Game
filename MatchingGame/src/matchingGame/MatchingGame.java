package matchingGame;

/**Class: Programming Fundamentals

 *Project #  : Group Project #1

 *Students:    Hector Solis, Jackson Martinez, Jessie Billingsly

 *Completion Date: 3/26/2014

 *Project Description: This program is a matching game, the user will be presented with a target icon and an array of buttons. The user will have to match the target icon with buttons with the same icons from the button array within
 *a specified time. The user will earn points 100 points for every correctly matched icon and lose 100 points for every miss-matched icons. If the button clicked is a match with the target, the button will highlight green. If the button clicked
 *is NOT a match with the target, the button will highlight red. After the game expires, the user may end the game or start a new game.
**/

import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
public class MatchingGame<T> extends JPanel{
//timer
private Timer timer, t;

//panels
private JPanel main;
private JPanel grid;
private JPanel north;
private JPanel south;


//panel components
private JButton newGame, endGame;
private JLabel timerLabel,scoreLabel,scoreText, possibleCorrect, amountCorrect, startImage,gameOverDialog;
private JComboBox difficultySelect;

//Buttons
private Button c1,c2;

//instance variables
private int numClickedCorrect;
private int timerCount=0;
private int timerCount2=0;
private int score;
private int gameCount=0;
private int gameSize =0;
private int pairs= 0;

//arrays
String [] difficulty = {"Please select difficulty","easy(4X4)","medium(6X6)","hard(8X8)"};
ArrayList<Button> buttons = new ArrayList<Button>();
int [] buttonCheck = new int[64];
String[] imageLocation = {"images/1.png","images/2.png","images/3.png","images/4.png","images/5.png","images/6.png"};
int[] scoreSet = new int[11];

//constructor
public MatchingGame(){
	difficultySelect = new JComboBox(difficulty);
	difficultySelect.setPreferredSize(new Dimension(300,50));
	
	main = new JPanel();
	main.setLayout(new BorderLayout());
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	main.setPreferredSize (new Dimension(dim.width-100, dim.height-100));

	grid = new JPanel();
	north = new JPanel();
	north.setLayout(new GridLayout(1,3));
	north.setOpaque(true);
	
	south = new JPanel();
	south.setLayout(new GridLayout(1,3));
	
	difficultySelect.addActionListener(new ComboBoxListener());

 timerLabel = new JLabel();
 timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
 timerLabel.setFont(new Font("verdana",Font.PLAIN,25));
 scoreLabel = new JLabel(Integer.toString(score));
 scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
 scoreLabel.setFont(new Font("verdana",Font.PLAIN,25));
 scoreText = new JLabel("Score: ");
 scoreText.setHorizontalAlignment(SwingConstants.RIGHT);
 scoreText.setFont(new Font("verdana",Font.PLAIN,25));
 possibleCorrect = new JLabel(Integer.toString(numClickedCorrect));
 possibleCorrect.setFont(new Font("verdana",Font.PLAIN,25));
 possibleCorrect.setHorizontalAlignment(SwingConstants.LEFT);
 amountCorrect = new JLabel("Amount Correct: ");
 amountCorrect.setFont(new Font("verdana",Font.PLAIN,20));
 amountCorrect.setHorizontalAlignment(SwingConstants.RIGHT);
	newGame = new JButton("New Game");
	newGame.addActionListener(new ButtonListener());
	endGame = new JButton("End Game");
	ImageIcon startImageIcon = new ImageIcon("images/Intro.png");
	startImage = new JLabel(startImageIcon);
	grid.add(startImage);
	
	//add 4 panels
	add(main);
	add(north);
	add(grid);
	add(south);
	
	//add 3 panels to main panel using BorderLayout
	main.add(grid, BorderLayout.CENTER);
	main.add(north, BorderLayout.NORTH);
	main.add(south, BorderLayout.SOUTH);
	
	//add components to panels
	north.add(difficultySelect);
	north.add(timerLabel);
	south.add(scoreText);
	south.add(scoreLabel);	
	south.add(amountCorrect);
	south.add(possibleCorrect);
	south.add(newGame);
	south.add(endGame);
	newGame.setEnabled(false);
	endGame.setEnabled(false);
	endGame.addActionListener(new EndGameButtonListener());
}
//ComboBoxListener used to determine the game displayed
private class ComboBoxListener implements ActionListener{
	public void actionPerformed(ActionEvent event){
		grid.remove(startImage);
		newGame.setEnabled(true);
		endGame.setEnabled(true);
		if(event.getSource()==difficultySelect){
		String selected = difficultySelect.getSelectedItem().toString();
			// 5 X 5 game selected
			if(selected=="easy(4X4)"){
				gameSize=4;
				pairs = 8;
				began(gameSize, 3, pairs);
			// 6 X 6 game selected
			}else if(selected=="medium(6X6)"){
				gameSize=6;
				pairs = 18;
				began(gameSize, 1, pairs);
			// 7 X 7 game selected
			}else if(selected=="hard(8X8)"){
				gameSize=8;
				pairs = 32;
				began(gameSize, 2, pairs);
			}
		}
	}

}

private class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent event){
		//Conditional statements to determine the action based on user selection
		if(event.getSource()==newGame){
			grid.removeAll();
			grid.revalidate();
			grid.repaint(); 
			buttons.removeAll(buttons);
			timer.stop();
			timerLabel.setText("0");
			difficultySelect.setEnabled(true);
			difficultySelect.setSelectedIndex(0);
			//disables the new game button after 10 games have been played
			if (gameCount >10){
				newGame.setEnabled(false);
			}
		}
		int index = buttons.indexOf(event.getSource());
		if(event.getSource() == buttons.get(index)){
			int index2 = buttons.indexOf(event.getSource());
			doTurn(buttons.get(index2));
		}
	}
}
//Timer Listener to put time restriction on game
private class TimerListener implements ActionListener{
	public void actionPerformed	(ActionEvent e){
		if(timerCount>15){
			timerLabel.setText("Time: "+Integer.toString(timerCount2));
			timerCount2--;
			timerCount--;
		}else if(timerCount==15){
			for(Button b : buttons){
				b.setIcon(new ImageIcon("images/card_back.png"));
			}
			timerLabel.setText("Time: " +Integer.toString(timerCount));
			timerCount--;
		}else if(timerCount<15 && timerCount>=0){
			timerLabel.setText("Time: " +Integer.toString(timerCount));
			timerCount--;
		}
		else if(timerCount == -1){
			timer.stop();
			grid.removeAll();
			grid.revalidate();
			grid.repaint();
			scoreSet[gameCount] = score;
			timerCount=-2;
			if(timerCount==-2){
			gameOverDialog = new JLabel("Game Over! Please click \"New Game\" or \"End Game\".");
			JOptionPane.showMessageDialog (null, gameOverDialog,"Game Over",JOptionPane.PLAIN_MESSAGE);
			timerCount--;
			}
		}
	}
}
//End Game button to close MatchGame and display score results
private class EndGameButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent event){
		int highScore = 0;
		int bestGame = 0;
		String scoreOut = "";
		scoreOut = ("\n");
		for (int i=1; i<scoreSet.length; i++){
			if (scoreSet[i] > highScore){
				highScore = scoreSet[i];
				bestGame = i;
				}
				scoreOut = scoreOut +("\tGame: " + i + "\t\tScore: " + scoreSet[i]+ "\n");
			}
			scoreOut = scoreOut +("\t--------------------------------------------------\n");
			scoreOut = scoreOut +("\tBest Game: " + bestGame + "\t\tHighscore:" + highScore);
			ScoreCard endScore = new ScoreCard(scoreOut);	
		    Window w = SwingUtilities.getWindowAncestor(MatchingGame.this);
			w.setVisible(false);
	}
}
//Creates board and list with buttons of images.
public void began(int size, int sel, int pairs){
	numClickedCorrect=0;
	score=0;
	scoreLabel.setText(Integer.toString(score));
	difficultySelect.setEnabled(false);
	timerCount= 20;
	timerCount2=5;
	gameCount++;
	
	ArrayList<Button> cardList = new ArrayList<Button>();
	ArrayList<Integer> cardVals = new ArrayList<Integer>();

	main.add(grid, BorderLayout.CENTER);
	grid.setLayout(new GridLayout(size,size));
	
	for(int i=0; i < pairs; i++){
		cardVals.add(i);
		cardVals.add(i);
	}
	Collections.shuffle(cardVals);
	
	for(int val: cardVals){
		if(val > 5){
			buttonCheck[val] = (val % 5)+1;
			ImageIcon icon = new ImageIcon(imageLocation[buttonCheck[val]]);
			Button b = new Button(buttonCheck[val], icon);
			b.addActionListener(new ButtonListener());
			cardList.add(b);
		}else{
			buttonCheck[val] = val;
			ImageIcon icon = new ImageIcon(imageLocation[buttonCheck[val]]);
			Button b = new Button(buttonCheck[val], icon);
			b.addActionListener(new ButtonListener());
			cardList.add(b);
		}
	}
	this.buttons = cardList;
	for(Button c: buttons){
		grid.add(c);
	}
	t = new Timer(750, new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			checkCards();
		}
	});
	t.setRepeats(false);
	
	possibleCorrect.setText(Integer.toString(numClickedCorrect));
	grid.setOpaque(true);
	timer = new Timer(1000,new TimerListener());
	timer.start();
}
//Flips card chosen, leaves flipped if its the first card, flips second as well.
public void doTurn(Button selectedButton){
	if(c1 == null && c2 == null){
		c1 = selectedButton;
		c1.setIcon(new ImageIcon(imageLocation[c1.getValue()]));
	}
	
	if(c1 != null && c1 != selectedButton && c2 == null){
		c2 = selectedButton;
		c2.setIcon(new ImageIcon(imageLocation[c2.getValue()]));
		t.start();
	}
}
//Checks if both cards chosen are equal, disables them or flips them back 
//over if incorrect.
public void checkCards(){
	if((c1.getValue() == c2.getValue()) && c2 != c1){
		c1.setEnabled(false);
		c2.setEnabled(false);
		c1.setMatched(true);
		c2.setMatched(true);
		score += 500;
	}
	else{
		c1.setIcon(new ImageIcon("images/card_back.png"));
		c2.setIcon(new ImageIcon("images/card_back.png"));
		score -= 200;
	}
	c1 = null;
	c2 = null;
	scoreLabel.setText(Integer.toString(score));
}
}
