import java.awt.Image;
import java.awt.Point;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


import javax.swing.ImageIcon;

public class Player implements ActionListener{

    private Timer timer;

    //�L�����̑傫��
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    //�X�s�[�h
    private static final int SPEED = 8;
    //�W�����v��
    private static final int JUMP_SPEED = 19;

    //�L�������
    private static final int Red = 0;
    private static final int Blue = 1;
    private static final int Yellow = 2;

    //�ʒu
    private double x;
    private double y;
    private double defaultX;
    private double defaultY;

    //���x
    private double vx;
    private double vy;

    //���n���Ă��邩
    private boolean onGround;

    //�g�p����L����
    private int p_color;

    //�A�j���[�V�����p�J�E���^
    private int count;

    //�v���C���[�摜
    private Image image;

    //�}�b�v�ւ̎Q��
    private Map map;

    //�V��ɂ������ǂ���
    private boolean onCeiling;

    //�����蔻��p
    private boolean hitCheck_x, hitCheck_y;

    //�L�[�̓��͏��
    private boolean spacePressed;
    private boolean DPressed;
    private boolean APressed;

    // ��`�q
    private String gene = "";
    private int index = 0;
    private int jumpCount = 0;

    // ��`�q�ǂݍ��ݏI�������ǂ���
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
        // 0.2�b���Ƃɓ����܂���
        timer = new Timer(200, this);
        timer.start();

        //�A�j���[�V�����p
        AnimationThread thread = new AnimationThread();
        thread.start();
    }

    //��~
    public void stop() {
        vx = 0;
    }

    public void actionPerformed(ActionEvent e){
      if (index < gene.length()) {
        this.loadGene(gene.charAt(index));
        index++;
      } else {
        // ��`�q��������
        this.isFinished = true;
      }
    }

    //�v���C���[��ԍX�V
    public void move() {
        //�d�͂�������
        vy += Map.GRAVITY;

        if (vx > 0){
          vx -= 1;
        }
        if (vx < 0){
          vx += 1;
        }
        // �W�����v�����鎞�͐�ɔ�΂��āA��ڂ�₷������
        if (spacePressed) {
            vy = -JUMP_SPEED;
            spacePressed = false;
        }
        /*x�����̓����蔻��*/
        //�ړ���
        if  (DPressed) {
            vx = SPEED;
        }
        if  (APressed) {
            vx = -SPEED;
        }
        double newX = x + vx;
        //�ړ���̃^�C���̗L��
        Point tile = map.getTileCollision(this, newX, y);
        if (tile==null) {   //�^�C���Ȃ�
            x = newX;
        } else {    //�^�C������
            if (vx > 0) {
                x = Map.tilesToPixels(tile.x) - WIDTH;  //�ʒu����
            }
            vx = 0;
        }

        /*y�����̓����蔻��*/
        //�ړ���
        double newY = y + vy;
        //�ړ���̃^�C���̗L��
        tile = map.getTileCollision(this, x, newY);
        if (tile == null){  //�^�C���Ȃ�
            y = newY;
            onGround = false;
            onCeiling = false;
        } else {    //�^�C������
            if(vy>0) {  //���Ɉړ����̎��C���̃u���b�N�ƏՓ�
                y = Map.tilesToPixels(tile.y) - HEIGHT;
                vy = 0;
                onGround = true;
                // �n�ʂɂ��鎞�̈ʒu
                this.defaultX = this.x;
                this.defaultY = this.y;
            } else if (vy<0) {
                y = Map.tilesToPixels(tile.y + 1);
                vy = 0;
                onCeiling = true;
            }
        }

        //x�����̓����蔻��
    	//�ړ���̐j�̗L��
      Point needle = map.getNeedleCollision(this, newX, y);
    	if (needle==null) {   //�j�Ȃ�
            hitCheck_x = false;
        } else {   //�j����
            vx = 0;
            hitCheck_x = true;
        }
        //y�����̓����蔻��
    	//�ړ���̃^�C���̗L��
    	needle = map.getNeedleCollision(this, x, newY);
      if (needle == null){  //�^�C���Ȃ�
          hitCheck_y = false;

      } else {    //�^�C������
              vy = 0;
          hitCheck_y = true;
      }

      spacePressed = false;
      DPressed = false;
      APressed = false;

    }
    // �S�[���`�F�b�N
    public boolean HitCheck() {
        boolean rtn = false;
        rtn = hitCheck_x || hitCheck_y;
        return rtn;
    }

    private void loadImage() {  //OK
        ImageIcon icon = new ImageIcon(getClass().getResource("image/players.png"));
        image = icon.getImage();
    }

    //�v���C���[��`��
    public void show(Graphics2D g2, int offsetX, int offsetY) {  //OK
        g2.drawImage(image,
                     (int)x + offsetX, (int)y + offsetY,
                     (int)x + offsetX + WIDTH, (int)y + offsetY + HEIGHT,
                     count * WIDTH, p_color * HEIGHT,
                     count * WIDTH + WIDTH, p_color * HEIGHT + HEIGHT,
                     null);
    }

    //Select�p
    public void show(Graphics2D g2){
        g2.drawImage(image,
                     (int)x , (int)y ,
                     (int)x + WIDTH, (int)y + HEIGHT,
                     count * WIDTH, p_color * HEIGHT,
                     count * WIDTH + WIDTH, p_color * HEIGHT + HEIGHT,
                     null);
    }

    //�A�j���[�V��������
    private class AnimationThread extends Thread {  //OK
        public void run() {
            while (true) {
                if(count == 0){
                    count=1;
                } else {
                    count=0;
                }
                //�G�̐؂�ւ�
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
          // ��
          APressed = true;
          break;
        case '1':
          // �E
          DPressed = true;
          break;
        case '2':
          // �W�����v
          if (onGround) {
            // jump�����񐔂𐔂���
            this.jumpCount++;
            spacePressed = true;
          }
          break;
      }
    }
    // x���W��Ԃ�
    public int returnX() {
      return (int)this.defaultX;
    }
    // y���W��Ԃ�
    public int returnY() {
      return (int)this.defaultY;
    }

    public int returnJumpCount() {
      return this.jumpCount;
    }

    // ��`�q���Z�b�g&������
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

    // �ȉ��L�[�{�[�h����
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