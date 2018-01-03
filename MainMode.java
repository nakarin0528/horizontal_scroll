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

    public void init() {
        // TODO Auto-generated method stub
    }

    public void init(int p_num){
        //マップ生成
        MAp = new Map();
        //キャラクター生成
        player1 = new Player(90, 380, MAp, p_num);
        player2 = new Player(90, 380, MAp, p_num);
        player3 = new Player(90, 380, MAp, p_num);
        player4 = new Player(90, 380, MAp, p_num);
        player5 = new Player(90, 380, MAp, p_num);
        player6 = new Player(90, 380, MAp, p_num);
        player7 = new Player(90, 380, MAp, p_num);
        player8 = new Player(90, 380, MAp, p_num);
        player9 = new Player(90, 380, MAp, p_num);
        player10 = new Player(90, 380, MAp, p_num);

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
        player1.show(g2, 0, 0);
        player2.show(g2, 0, 0);
        player3.show(g2, 0, 0);
        player4.show(g2, 0, 0);
        player5.show(g2, 0, 0);
        player6.show(g2, 0, 0);
        player7.show(g2, 0, 0);
        player8.show(g2, 0, 0);
        player9.show(g2, 0, 0);
        player10.show(g2, 0, 0);

    }

    public void run(GameManager gm){

      // 遺伝子を読ませていって、読ませ終わったら、ストップ
      // timerをつかって0.2秒ごとに遺伝子情報を読み込むようにする。
      player1.loadGene("01");

      player1.move();
      player2.move();
      player3.move();
      player4.move();
      player5.move();
      player6.move();
      player7.move();
      player8.move();
      player9.move();
      player10.move();
      if(player1.HitCheck()){
         gm.ChangeMode(new MainMode(0));
       }
    }

    public void KeyPressed(KeyEvent arg0){
        player1.KeyPressedAnalyze(arg0);
        player2.KeyPressedAnalyze(arg0);
        player3.KeyPressedAnalyze(arg0);
        player4.KeyPressedAnalyze(arg0);
        player5.KeyPressedAnalyze(arg0);
        player6.KeyPressedAnalyze(arg0);
        player7.KeyPressedAnalyze(arg0);
        player8.KeyPressedAnalyze(arg0);
        player9.KeyPressedAnalyze(arg0);
        player10.KeyPressedAnalyze(arg0);
    }
    public void KeyReleased(KeyEvent arg0){
        player1.KeyReleasedAnalyze(arg0);
        player2.KeyReleasedAnalyze(arg0);
        player3.KeyReleasedAnalyze(arg0);
        player4.KeyReleasedAnalyze(arg0);
        player5.KeyReleasedAnalyze(arg0);
        player6.KeyReleasedAnalyze(arg0);
        player7.KeyReleasedAnalyze(arg0);
        player8.KeyReleasedAnalyze(arg0);
        player9.KeyReleasedAnalyze(arg0);
        player10.KeyReleasedAnalyze(arg0);
    }
    public void KeyTyped(KeyEvent arg0){
        player1.KeyTypedAnalyze(arg0);
        player2.KeyTypedAnalyze(arg0);
        player3.KeyTypedAnalyze(arg0);
        player4.KeyTypedAnalyze(arg0);
        player5.KeyTypedAnalyze(arg0);
        player6.KeyTypedAnalyze(arg0);
        player7.KeyTypedAnalyze(arg0);
        player8.KeyTypedAnalyze(arg0);
        player9.KeyTypedAnalyze(arg0);
        player10.KeyTypedAnalyze(arg0);
    }


}
