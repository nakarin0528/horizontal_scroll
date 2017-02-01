import java.awt.Image;
import java.awt.Point;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Player {
    
    //キャラの大きさ
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    //スピード
    private static final int SPEED = 4;
    //ジャンプ力
    private static final int JUMP_SPEED = 14;
    
    //キャラ種類
    private static final int Red = 0;
    private static final int Blue = 1;
    private static final int Yellow = 2;
    
    //位置
    private double x;
    private double y;
    
    //速度
    private double vx;
    private double vy;
    
    //着地しているか
    private boolean onGround;
    
    //使用するキャラ
    private int p_color;
    
    //アニメーション用カウンタ
    private int count;
    
    //プレイヤー画像
    private Image image;
    
    //マップへの参照
    private Map map;
    
    //天井についたかどうか
    private boolean onCeiling;
    
    //当たり判定用
    private boolean hitCheck_x, hitCheck_y;
    
    //キーの入力状態
    private boolean spacePressed;
    
    public Player(double x, double y, Map map, int p_num) {   //OK
        
        p_color = p_num;
        
        this.x = x;
        this.y = y;
        this.map = map;
        vx = 0;
        vy = 0;
        onGround = false;
        onCeiling = false;
        count = 0;
        
        loadImage();
        
        //アニメーション用
        AnimationThread thread = new AnimationThread();
        thread.start();
    }
    
    //Select用
    
    public Player(double x,double y, int p_num){
    
        p_color = p_num;
        
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
        onGround = false;
        onCeiling = false;
        count = 0;
        
        loadImage();
        
        AnimationThread thread = new AnimationThread();
        thread.start();
        
    }
    
    //停止
    public void stop() {
        vx = 0;
    }
    
    
    //プレイヤー状態更新
    public void move() {
        //重力がかかる
        vy += Map.GRAVITY;
        //右に加速
        vx = SPEED;
        
        /*x方向の当たり判定*/
        //移動先
        double newX = x + vx;
        //移動先のタイルの有無
        Point tile = map.getTileCollision(this, newX, y);
        if (tile==null) {   //タイルなし
            x = newX;
        } else {    //タイルあり
            if (vx > 0) {
                x = Map.tilesToPixels(tile.x) - WIDTH;  //位置調整
            }
            vx = 0;
        }
        
        /*y方向の当たり判定*/
        //移動先
        double newY = y + vy;
        //移動先のタイルの有無
        tile = map.getTileCollision(this, x, newY);
        if (tile == null){  //タイルなし
            y = newY;
            onGround = false;
            onCeiling = false;
        } else {    //タイルあり
            if(vy>0) {  //下に移動中の時，下のブロックと衝突
                y = Map.tilesToPixels(tile.y) - HEIGHT;
                vy = 0;
                onGround = true;
            } else if (vy<0) {
                y = Map.tilesToPixels(tile.y + 1);
                vy = 0;
                onCeiling = true;
            }
        }
        
        //x方向の当たり判定
    	//移動先の針の有無
       Point needle = map.getNeedleCollision(this, newX, y);
    	if (needle==null) {   //針なし
            hitCheck_x = false;
        } else {   //針あり
            
            vx = 0;
            hitCheck_x = true;
        }
        //y方向の当たり判定
    	//移動先のタイルの有無
    	needle = map.getNeedleCollision(this, x, newY);
        if (needle == null){  //タイルなし
            hitCheck_y = false;
            
        } else {    //タイルあり
                vy = 0;
            hitCheck_y = true;
        }
        
        if (spacePressed) {
            vy = -JUMP_SPEED;
            spacePressed = false;
        }
    }
    
    public boolean HitCheck() {
        boolean rtn = false;
        rtn = hitCheck_x || hitCheck_y;
        return rtn;
    }
    
    private void loadImage() {  //OK
        ImageIcon icon = new ImageIcon(getClass().getResource("image/players.png"));
        image = icon.getImage();
    }
    
    //プレイヤーを描画
    public void show(Graphics2D g2, int offsetX, int offsetY) {  //OK
        g2.drawImage(image,
                     (int)x + offsetX, (int)y + offsetY,
                     (int)x + offsetX + WIDTH, (int)y + offsetY + HEIGHT,
                     count * WIDTH, p_color * HEIGHT,
                     count * WIDTH + WIDTH, p_color * HEIGHT + HEIGHT,
                     null);
    }
    
    //Select用
    public void show(Graphics2D g2){
        g2.drawImage(image,
                     (int)x , (int)y ,
                     (int)x + WIDTH, (int)y + HEIGHT,
                     count * WIDTH, p_color * HEIGHT,
                     count * WIDTH + WIDTH, p_color * HEIGHT + HEIGHT,
                     null);
    }
    
    //アニメーション処理
    private class AnimationThread extends Thread {  //OK
        public void run() {
            while (true) {
                if(count == 0){
                    count=1;
                } else {
                    count=0;
                }
                
                //絵の切り替え
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void KeyPressedAnalyze(KeyEvent e){
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
    }
    public void KeyReleasedAnalyze(KeyEvent e){
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
    public void KeyTypedAnalyze(KeyEvent e){}
}
