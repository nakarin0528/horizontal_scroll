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
    //GA
    private GA ga;
    //世代
    private int gen=1;
    //プレイヤー
    private Player[] players = new Player[ga.POP_SIZE];
    //遺伝子
    private String[] genes;

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
        //ga
        ga = new GA();
        ga.initialize();

        for (int i=0; i<ga.POP_SIZE; i++) {
          players[i] = new Player(320, 380, MAp, 0);
        }
    }

    public void initGene() {
      //キャラクター生成
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i] = new Player(320, 380, MAp, 0);
      }

      int[][] newGenes = ga.returnGenes();
      this.genes = new String[ga.POP_SIZE];

      for (int i=0; i<ga.POP_SIZE; i++) {
        for (int j=0; j<ga.LEN_CHROM; j++) {
          // 文字列に変換して代入
          genes[i] = genes[i] + newGenes[i][j];
        }
      }
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].setGene(genes[i]);
      }
    }

    public void Show(Graphics2D g2){
        //背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //マップ
        MAp.show(g2, 0, 0);
        //プレイヤー
        for (int i=0; i<ga.POP_SIZE; i++) {
          players[i].show(g2, 0, 0);
        }
    }

    public void run(GameManager gm){
      // 遺伝子消化されたら、データを渡して、次の遺伝子をもらう。
      // 遺伝子の長さは一緒なのでどれか1つみればよい。
      if (players[0].returnIsFinished()) {
        int[] scores = new int[ga.POP_SIZE];
        System.out.printf("/* 遺伝子情報とスコア */\n");
        for (int i=0; i<ga.POP_SIZE; i++) {
          scores[i] = this.playerScore(players[i]);
          System.out.printf("[%d]: ", i);
          // 最初なんかnull
          System.out.printf(genes[i]);
          System.out.printf(" -> %d \n", scores[i]);
        }
        ga.setScores(scores, gen);
        gen++;
        // gm.ChangeMode(new MainMode(0));
        for (int i=0; i<ga.POP_SIZE; i++) {
          players[i].changeToFalse_isFinished();
        }
      }
      // 遺伝子が生成されたら、プレーヤーたちリセット
      if (ga.returnIsGenerated()) {
        ga.changeToFalse_isGenerated();
        this.initGene();
      }

      // System.out.printf("player1のスコア: %d\n", playerScore(players[0]));
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].move();
      }

      // if(player1.HitCheck()){
      //    gm.ChangeMode(new MainMode(0));
      //  }
    }

    public void KeyPressed(KeyEvent arg0){
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].KeyPressedAnalyze(arg0);
      }
    }
    public void KeyReleased(KeyEvent arg0){
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].KeyReleasedAnalyze(arg0);
      }
    }
    public void KeyTyped(KeyEvent arg0){
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].KeyTypedAnalyze(arg0);
      }
    }

    // ゴールまでの距離
    public int getDistance(double x, double y, double x2, double y2) {
      double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
      return (int) distance;
    }

    // プレーヤーのスコア
    public int playerScore(Player player) {
      // ゴール(35,70)までの距離
      int dis = getDistance(player.returnX(), player.returnY(), 35, 70);
      // 到達した高さ　- ゴールまでの距離。
      // 得点が高ければ高いほど良い。
      int score = (int)((-1*(player.returnY() - 380))*1.8) - dis - player.returnJumpCount();
      // int score = (int)((-1*(player.returnY() - 380))*2.5) - dis;
      return score;
    }

}
