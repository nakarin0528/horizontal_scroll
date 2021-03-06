import java.awt.Image;
import java.awt.Point;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


import javax.swing.ImageIcon;

public class Player implements ActionListener{

    private Timer timer;

    //キャラの大きさ
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    //スピード
    private static final int SPEED = 8;
    //ジャンプ力
    private static final int JUMP_SPEED = 19;

    //キャラ種類
    private static final int Red = 0;
    private static final int Blue = 1;
    private static final int Yellow = 2;

    //位置
    private double x;
    private double y;
    private double defaultX;
    private double defaultY;

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
    private boolean DPressed;
    private boolean APressed;

    // 遺伝子
    private String gene = "";
    private int index = 0;
    private int jumpCount = 0;

    // 遺伝子読み込み終えたかどうか
    private boolean isFinished = false;

    public Player(double x, double y, Map map, int p_num) {

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
        // 0.2秒ごとに動きますか
        timer = new Timer(200, this);
        timer.start();

        //アニメーション用
        AnimationThread thread = new AnimationThread();
        thread.start();
    }

    //停止
    public void stop() {
        vx = 0;
    }

    public void actionPerformed(ActionEvent e){
      if (index < gene.length()) {
        this.loadGene(gene.charAt(index));
        index++;
      } else {
        // 遺伝子消化した
        this.isFinished = true;
      }
    }

    //プレイヤー状態更新
    public void move() {
        //重力がかかる
        vy += Map.GRAVITY;

        if (vx > 0){
          vx -= 1;
        }
        if (vx < 0){
          vx += 1;
        }
        // ジャンプがある時は先に飛ばせて、乗移りやすくする
        if (spacePressed) {
            vy = -JUMP_SPEED;
            spacePressed = false;
        }
        /*x方向の当たり判定*/
        //移動先
        if  (DPressed) {
            vx = SPEED;
        }
        if  (APressed) {
            vx = -SPEED;
        }
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
                // 地面にいる時の位置
                this.defaultX = this.x;
                this.defaultY = this.y;
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

      spacePressed = false;
      DPressed = false;
      APressed = false;

    }
    // ゴールチェック
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

    public void loadGene(char gene) {
      switch (gene) {
        case '0':
          // 左
          APressed = true;
          break;
        case '1':
          // 右
          DPressed = true;
          break;
        case '2':
          // ジャンプ
          if (onGround) {
            // jumpした回数を数える
            this.jumpCount++;
            spacePressed = true;
          }
          break;
      }
    }
    // x座標を返す
    public int returnX() {
      return (int)this.defaultX;
    }
    // y座標を返す
    public int returnY() {
      return (int)this.defaultY;
    }

    public int returnJumpCount() {
      return this.jumpCount;
    }

    // 遺伝子をセット&初期化
    public void setGene(String gene) {
      this.index = 0;
      this.isFinished = false;
      this.gene = gene;
    }

    public boolean returnIsFinished() {
      return this.isFinished;
    }

    public void changeToFalse_isFinished() {
      this.isFinished = false;
    }

    // 以下キーボード操作
    public void KeyPressedAnalyze(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && this.onGround){
            spacePressed = true;
        }
        if (key == KeyEvent.VK_D){
            DPressed = true;
        }
        if (key == KeyEvent.VK_A){
            APressed = true;
        }
    }
    public void KeyReleasedAnalyze(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if (key == KeyEvent.VK_D){
            DPressed = false;
        }
        if (key == KeyEvent.VK_A){
            APressed = false;
        }
    }
    public void KeyTypedAnalyze(KeyEvent e){}
}
