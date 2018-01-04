import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.*;

// ゴールの座標（410, 90）


public class MainMode implements GameMode{

    //マップ
    private Map MAp;
    //プレイヤー
    private Player[] players = new Player[10];
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;
    private Player player7;
    private Player player8;
    private Player player9;
    private Player player10;

    private String gene = "1000";

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public MainMode(int p_num){
        init(p_num);
    }

    public void init(int p_num){
        //マップ生成
        MAp = new Map();
        //キャラクター生成
        for (int i=0; i<10; i++) {
          players[i] = new Player(90, 380, MAp, p_num);
        }
        this.init();
    }

    public void init() {
        // TODO Auto-generated method stub

        for (int i=0; i<10; i++) {
          players[i].setGene("2122111212121102020");
        }
    }

    public void Show(Graphics2D g2){
        //offsetを計算，マップ端ではスクロールしない
        // int offsetX = MainMode.WIDTH/2 - (int)player.getX();
        // offsetX = Math.min(offsetX, 0);
        // offsetX = Math.max(offsetX, MainMode.WIDTH - Map.WIDTH);
        // int offsetY = MainMode.HEIGHT/2 - (int)player.getY();
        // offsetY = Math.min(offsetY, 0);
        // offsetY = Math.max(offsetY, MainMode.HEIGHT - Map.HEIGHT);
        //背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //マップ
        MAp.show(g2, 0, 0);
        //プレイヤー
        for (int i=0; i<10; i++) {
          players[i].show(g2, 0, 0);
        }
    }

    public void run(GameManager gm){

      // 遺伝子を読ませていって、読ませ終わったら、ストップ
      // timerをつかって0.2秒ごとに遺伝子情報を読み込むようにする。
      // player1.loadGene("2");

      System.out.printf("player1のスコア: %d\n", playerScore(players[0]));
      for (int i=0; i<10; i++) {
        players[i].move();
      }
      // if(player1.HitCheck()){
      //    gm.ChangeMode(new MainMode(0));
      //  }
    }

    public void KeyPressed(KeyEvent arg0){
      for (int i=0; i<10; i++) {
        players[i].KeyPressedAnalyze(arg0);
      }
    }
    public void KeyReleased(KeyEvent arg0){
      for (int i=0; i<10; i++) {
        players[i].KeyReleasedAnalyze(arg0);
      }
    }
    public void KeyTyped(KeyEvent arg0){
      for (int i=0; i<10; i++) {
        players[i].KeyTypedAnalyze(arg0);
      }
    }

    public int getDistance(double x, double y, double x2, double y2) {
      double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
      return (int) distance;
    }

    public int playerScore(Player player) {
      // ゴールまでの距離
      int dis1 = getDistance(player.returnX(), player.returnY(), 410, 90);
      // ゴールまでの距離 - 高さでスコアが低ければ低いほど良い。
      int score = dis1 + (player.returnY() - 380);
      return score;
    }

}
