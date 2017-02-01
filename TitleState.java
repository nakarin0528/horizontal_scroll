import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class TitleState implements GameMode{
    
    private final static int SELECT	= 0;
    private final static int END		= 1;
    private int _cursorPos = SELECT;
    
    private final static int TITLEPOSX	= 200;
    private final static int TITLEPOSY	= 150;
    
    private final static int MENUPOSX		= 300;
    private final static int MENUPOSY		= 350;
    private final static int MENUINTVL	= 50;
    private final static int CURSOR		= 250;
    
    private boolean m_bKeyUp;
    public void KeyUp(boolean on){m_bKeyUp = on;}
    private boolean m_bKeyDown;
    public void KeyDown(boolean on){m_bKeyDown = on;}
    private boolean m_bKeySpace;
    public void KeySpace(boolean on){m_bKeySpace = on;}
    
    
    public TitleState()
    {
        init();
    }
    
    @Override
    public void init() {
    }
    
    public void run(GameManager gm)
    {

    	if(m_bKeyUp)
        {
            if(_cursorPos != SELECT)
                _cursorPos--;
        }
        else if(m_bKeyDown)
        {
            if(_cursorPos != END)
                _cursorPos++;
        }
        
        if(m_bKeySpace)
        {
	    switch(_cursorPos)
            {
                case SELECT:
			gm.ChangeMode(new SelectState());
                    break;
                case END:
                    gm.ChangeMode(new ExitState());
                    break;
            }
        }
    }
    
    public void Show(Graphics2D g2) {
         g2.setFont(new Font("Arial", Font.BOLD, 28));
    
        g2.setPaint(Color.white);
        g2.drawString("横スクロールゲーム",TITLEPOSX,TITLEPOSY);
        
        if(_cursorPos == SELECT)
            g2.setPaint(Color.green);
        else
            g2.setPaint(Color.white);
        
        g2.drawString("スタート",MENUPOSX,MENUPOSY);
        
        if(_cursorPos == END)
            g2.setPaint(Color.green);
        else
            g2.setPaint(Color.white);
        
        g2.drawString("おわり",MENUPOSX,MENUPOSY + MENUINTVL);
        
        g2.setPaint(Color.white);
        switch(_cursorPos)
        {
            case SELECT:
                g2.drawString("→",CURSOR,MENUPOSY);
                break;
            case END:
                g2.drawString("→",CURSOR,MENUPOSY + MENUINTVL);
                break;
        }
    }
    
    @Override
    public void KeyPressed(KeyEvent arg0) {
        switch(arg0.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
                KeySpace(true);
                break;
            case KeyEvent.VK_UP:
                KeyUp(true);
                break;
            case KeyEvent.VK_DOWN:
                KeyDown(true);
                break;
        }
    }
    
    @Override
    public void KeyReleased(KeyEvent arg0) {
        switch(arg0.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
                KeySpace(false);
                break;
            case KeyEvent.VK_UP:
                KeyUp(false);
                break;
            case KeyEvent.VK_DOWN:
                KeyDown(false);
                break;
        }
    }
    
    @Override
    public void KeyTyped(KeyEvent arg0) {
    }
    
}
