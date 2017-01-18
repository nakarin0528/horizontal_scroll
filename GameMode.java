import java.awt.*;
import java.awt.event.*;

public interface GameMode{
	
	public void Show(Graphics2D g2);
	public void run(GameManager gm);
	public void init();
	
	public void KeyPressed(KeyEvent e);
	public void KeyReleased(KeyEvent e);
	public void KeyTyped(KeyEvent e);
}
