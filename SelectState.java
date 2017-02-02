import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class SelectState implements GameMode{
    
    private int CharaSelect = 0;
    private final static int CHARA1 = 0;
    private final static int CHARA2 = 1;
    private final static int CHARA3 = 2;
    private int _charaselec = CHARA1;
    
    private final static int TITLEPOSX	= 100;
    private final static int TITLEPOSY	= 150;
    
    private final static int MENUPOSX		= 120;
    private final static int MENUPOSY		= 250;
    private final static int MENUINTVL	= 50;
    private final static int CURSOR		= 70;
    
    private boolean m_bKeyUp;
    public void KeyUp(boolean on){m_bKeyUp = on;}
    private boolean m_bKeyDown;
    public void KeyDown(boolean on){m_bKeyDown = on;}
    private boolean m_bKeySpace;
    public void KeySpace(boolean on){m_bKeySpace = on;}
    
    
    //ここから下はプレイヤー表示に関係する
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private Player player1;
    private Player player2;
    private Player player3;
    
    public SelectState()
    {
        init();
    }
    
    @Override
    public void init() {
        player1 = new Player(400,250,CHARA1);
        player2 = new Player(400,250,CHARA2);
        player3 = new Player(400,250,CHARA3);
    }
    
    public void run(GameManager gm)
    {
        if(m_bKeyUp)
        {
            if(_charaselec != CHARA1)
            {
		_charaselec--;
	     }
	     else
	     {
	     	_charaselec++;_charaselec++;
	     }
            KeyUp(false);
        }
        else if(m_bKeyDown)
        {
            if(_charaselec != CHARA3)
            {
		_charaselec++;
	     }
	     else
	     {
	     	_charaselec--;_charaselec--;
	     }
            KeyDown(false);
        }
        
        if(m_bKeySpace)
        {
            switch(_charaselec)
            {
                case CHARA1:
                    gm.ChangeMode(new MainMode(0));
                    break;
                case CHARA2:
                    gm.ChangeMode(new MainMode(1));
                    break;
                case CHARA3:
                    gm.ChangeMode(new MainMode(2));
                    break;
            }
            KeySpace(false);
        }
    }
    
    public void Show(Graphics2D g2) {
         g2.setFont(new Font("Arial", Font.BOLD, 28));

            g2.setPaint(Color.white);
            g2.drawString("キャラクタぁを選んでください",TITLEPOSX,TITLEPOSY);
            
            if(_charaselec == CHARA1)
                g2.setPaint(Color.green);
            else
                g2.setPaint(Color.white);
            
            g2.drawString("CHARA1",MENUPOSX,MENUPOSY);
            
            if(_charaselec == CHARA2)
                g2.setPaint(Color.green);
            else
                g2.setPaint(Color.white);
            
            g2.drawString("CHARA2",MENUPOSX,MENUPOSY + MENUINTVL);
            
            if(_charaselec == CHARA3)
                g2.setPaint(Color.green);
            else
                g2.setPaint(Color.white);
            
            g2.drawString("CHARA3",MENUPOSX,MENUPOSY+MENUINTVL+MENUINTVL);
            
            switch(_charaselec)
        {
            case CHARA1:
                g2.drawString("→",CURSOR,MENUPOSY);
                player1.show(g2);
                break;
            case CHARA2:
                g2.drawString("→",CURSOR,MENUPOSY + MENUINTVL);
                player2.show(g2);
                break;
            case CHARA3:
                g2.drawString("→",CURSOR,MENUPOSY + MENUINTVL + MENUINTVL);
                player3.show(g2);
                break;

        }
        
        //四角形
        g2.setPaint(Color.white);
        g2.drawRect(60,200,200,180);
    }
    
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
    
    public void KeyReleased(KeyEvent arg0) {
    }
    
    public void KeyTyped(KeyEvent arg0) {
    }
    
}
