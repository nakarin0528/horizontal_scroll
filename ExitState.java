import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class ExitState implements GameMode{
    
    private boolean m_bKeySpace;
    public void KeySpace(boolean on){m_bKeySpace = on;}
    
    public void init() {
    }
    
    @Override
    public void Show(Graphics2D g2) {
        g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
        g2.setPaint(Color.white);
        g2.drawString("ゲーム終了です．スペースキーでリスタート",10, 200);
    }
    
    @Override
    public void run(GameManager gm) {
        if(m_bKeySpace){
            gm.ChangeMode(new TitleState());
            KeySpace(false);
        }
    }
    
    @Override
    public void KeyPressed(KeyEvent arg0) {
        switch(arg0.getKeyCode()){
            case KeyEvent.VK_SPACE:
                KeySpace(true);
                break;
        }
    }
    
    @Override
    public void KeyReleased(KeyEvent arg0) {
    }
    
    @Override
    public void KeyTyped(KeyEvent arg0) {
    }
    
    
}
