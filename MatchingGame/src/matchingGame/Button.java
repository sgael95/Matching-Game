package matchingGame;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{
	private boolean matched = false;
	private int controValue;
	
	public Button(int controlValue, ImageIcon icon){
		this.setIcon(icon);
		controValue = controlValue;
	}
	public void setValue(int id){controValue = id;}
	public int getValue(){return controValue;}
	
	public void setMatched(boolean matched){this.matched = matched;}
	public boolean getMatched(){return this.matched;}
	
	public boolean isEqual(Button card){
		if(this.getValue() == card.getValue()){
			return true;
		}else
			return false;
	}
}
