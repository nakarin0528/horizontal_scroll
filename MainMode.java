import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.*;

// �S�[���̍��W�i410, 90�j


public class MainMode implements GameMode{

    //�}�b�v
    private Map MAp;
    //GA
    private GA ga;
    //����
    private int gen=1;
    //�v���C���[
    private Player[] players = new Player[ga.POP_SIZE];
    //��`�q
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
        //�}�b�v����
        MAp = new Map();
        //ga
        ga = new GA();
        ga.initialize();

        for (int i=0; i<ga.POP_SIZE; i++) {
          players[i] = new Player(320, 380, MAp, 0);
        }
    }

    public void initGene() {
      //�L�����N�^�[����
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i] = new Player(320, 380, MAp, 0);
      }

      int[][] newGenes = ga.returnGenes();
      this.genes = new String[ga.POP_SIZE];

      for (int i=0; i<ga.POP_SIZE; i++) {
        for (int j=0; j<ga.LEN_CHROM; j++) {
          // ������ɕϊ����đ��
          genes[i] = genes[i] + newGenes[i][j];
        }
      }
      for (int i=0; i<ga.POP_SIZE; i++) {
        players[i].setGene(genes[i]);
      }
    }

    public void Show(Graphics2D g2){
        //�w�i
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //�}�b�v
        MAp.show(g2, 0, 0);
        //�v���C���[
        for (int i=0; i<ga.POP_SIZE; i++) {
          players[i].show(g2, 0, 0);
        }
    }

    public void run(GameManager gm){
      // ��`�q�������ꂽ��A�f�[�^��n���āA���̈�`�q�����炤�B
      // ��`�q�̒����͈ꏏ�Ȃ̂łǂꂩ1�݂�΂悢�B
      if (players[0].returnIsFinished()) {
        int[] scores = new int[ga.POP_SIZE];
        System.out.printf("/* ��`�q���ƃX�R�A */\n");
        for (int i=0; i<ga.POP_SIZE; i++) {
          scores[i] = this.playerScore(players[i]);
          System.out.printf("[%d]: ", i);
          // �ŏ��Ȃ�null
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
      // ��`�q���������ꂽ��A�v���[���[�������Z�b�g
      if (ga.returnIsGenerated()) {
        ga.changeToFalse_isGenerated();
        this.initGene();
      }

      // System.out.printf("player1�̃X�R�A: %d\n", playerScore(players[0]));
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

    // �S�[���܂ł̋���
    public int getDistance(double x, double y, double x2, double y2) {
      double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
      return (int) distance;
    }

    // �v���[���[�̃X�R�A
    public int playerScore(Player player) {
      // �S�[��(35,70)�܂ł̋���
      int dis = getDistance(player.returnX(), player.returnY(), 35, 70);
      // ���B���������@- �S�[���܂ł̋����B
      // ���_��������΍����قǗǂ��B
      int score = (int)((-1*(player.returnY() - 380))*3) - (int)(dis*1.5) - player.returnJumpCount();
      // int score = (int)((-1*(player.returnY() - 380))*2.5) - dis;
      return score;
    }

}