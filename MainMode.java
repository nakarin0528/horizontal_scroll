import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.*;




public class MainMode implements GameMode{
    
    //マップ
    private Map MAp;
    //プレイヤー
    private Player player;
    private Enemy enemy[] = new Enemy[7];
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    
    public MainMode(int p_num){
        init(p_num);
    }
    
    public void init() {
        // TODO Auto-generated method stub
    }
    
    public void init(int p_num){
        //マップ生成
        MAp = new Map();
        //キャラクター生成
        player = new Player(192, 32, MAp, p_num);
        loadmap(MAp,player);
        
    }
    
    public void loadmap(Map mm,Player player){
        int k = 0;
        for(int i=0;i<100;i++){
            for(int j=0;j<14;j++){
                if(mm.map[j][i] == 7){
                    enemy[k] = new Enemy((int)mm.tilesToPixels(i),(int)mm.tilesToPixels(j),MAp,player);
                    k++;
                    }
            }
        }
    }
    

    
    public void Show(Graphics2D g2){
        //offsetを計算，マップ端ではスクロールしない
        int offsetX = MainMode.WIDTH/2 - (int)player.getX();
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainMode.WIDTH - Map.WIDTH);
        int offsetY = MainMode.HEIGHT/2 - (int)player.getY();
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainMode.HEIGHT - Map.HEIGHT);
        //背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //マップ
        MAp.show(g2, offsetX, offsetY);
        //プレイヤー
        player.show(g2, offsetX, offsetY);
        for(int i = 0;i<7;i++){
            enemy[i].show(g2,offsetX, offsetY);
        }
    }
    
    public boolean HitWithPlayer(){
        boolean hitornot = false;
        for(int en = 0;en<7;en++){
        double px = player.getX()+Player.WIDTH/2;
        double py = player.getY()+Player.HEIGHT/2;
        double ex = enemy[en].getX()+Enemy.WIDTH/2;
        double ey = enemy[en].getY()+Enemy.HEIGHT/2;
        double hit = Math.sqrt(Math.pow(ex-px,2)+Math.pow(ey-py,2));
        if(hit <= Player.WIDTH){hitornot = true;}
        }
        return hitornot;
    }
    
    public void run(GameManager gm){
        
        player.move();
        for(int i = 0;i<7;i++){
            enemy[i].move();
        }
        
        if(HitWithPlayer()){gm.ChangeMode(new ExitState());}
        if(player.HitCheck()){
           gm.ChangeMode(new ExitState());
       }
    }
    
    public void KeyPressed(KeyEvent arg0){
        player.KeyPressedAnalyze(arg0);
    }
    public void KeyReleased(KeyEvent arg0){
        player.KeyReleasedAnalyze(arg0);
    }
    public void KeyTyped(KeyEvent arg0){
        player.KeyTypedAnalyze(arg0);
    }
    
    
}
