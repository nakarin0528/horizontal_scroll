import java.awt.Image;
import java.awt.Point;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.ImageIcon;

public class Enemy implements ActionListener{
    
    private Timer timer;
    private boolean isJump;
    
    //キャラの大きさ
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    //スピード
    private static final int SPEED = 3;
    //ジャンプ力
    private static final int JUMP_SPEED = 15;
    //位置
    private double x, y;
    //速度
    private double vx, vy;
    //着地しているか
    private boolean onGround;
    //アニメーション用カウンタ
    private int count;
    //キャラの向き
    private static final int RIGHT = 1;
    private static final int LEFT = 0;
    private int dir = LEFT;
    //プレイヤー画像
    private Image image;
    //マップへの参照
    private Map map;
    //天井についたかどうか
    private boolean onCeiling;
    private Player player;
    
    public Enemy(double x, double y, Map map,Player player) {   
        
        this.x = x; this.y = y;
        this.map = map;
        this.player = player;
        vx = -SPEED; vy = 0;
        onGround = false; onCeiling = false;
        count = 0;
        loadImage();
        AnimationThread thread = new AnimationThread();
        timer = new Timer(1000, this);
        timer.start();
        thread.start();
    }
    public void stop() {
        vy = 0;
    }
    
    public void actionPerformed(ActionEvent e){
        isJump = true;
    }

    //プレイヤー状態更新
    public void move() {
        
        //重力がかかる
        vy += Map.GRAVITY;
        
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
            }else if(vx < 0){
                x = Map.tilesToPixels(tile.x) + WIDTH;
            }
            vx = 0;
            vx = -vx;
            if (dir == LEFT){
                vx = SPEED;
                dir = RIGHT;
            }else{
                dir = LEFT;
                vx = -SPEED;
            }
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
        
        if (isJump) {
            vy = -JUMP_SPEED;
            isJump = false;
        }
    }

    private void loadImage() {  
        ImageIcon icon = new ImageIcon(getClass().getResource("image/zombi.png"));
        image = icon.getImage();
    }
    //プレイヤーを描画
    public void show(Graphics2D g2, int offsetX, int offsetY) { 
        g2.drawImage(image,
                     (int)x + offsetX, (int)y + offsetY,
                     (int)x + offsetX + WIDTH, (int)y + offsetY + HEIGHT,
                     count * WIDTH, dir * HEIGHT,
                     count * WIDTH + WIDTH, dir * HEIGHT + HEIGHT,
                     null);
    }
     public boolean HitWithPlayer(){
        double px = player.getX()+WIDTH/2;
        double py = player.getY()+HEIGHT/2;
        double ex = x+WIDTH/2;
        double ey = y+HEIGHT/2;
        double hit = Math.sqrt(Math.pow(x-px,2)+Math.pow(y-py,2));
        boolean hitornot=false;
        if(hit <= WIDTH){hitornot = true;}
        return hitornot;
    }
    
    //アニメーション処理
    private class AnimationThread extends Thread {  //OK
        public void run() {
            while (true) {
                if(count == 0){count=1;} else {count=0;}
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
}
