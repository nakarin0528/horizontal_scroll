import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

// 終了時のモード
// 終了しないでタイトル戻ってもいい
public class ExitState implements GameMode{
    
    @Override
    public void init() {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void Show(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
        g2.setPaint(Color.yellow);
        g2.drawString("終わりです。",10, 100);
        g2.drawString("このままアプレットウィンドウを閉じてください。", 10,120);
    }
    
    @Override
    public void run(GameManager gm) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void KeyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void KeyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void KeyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    
}
