import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import java.lang.System;
import java.lang.String;


public class Map {

    //�u���b�N�T�C�Y
    public static final int TILE_SIZE = 32;
    //�s����
    public static final int ROW = 15;
    public static final int COL = 100;
    //���E����
    public static final int WIDTH = TILE_SIZE*COL;
    public static final int HEIGHT = TILE_SIZE*ROW;
    //�d��
    public static final double GRAVITY = 1.5;

    //�}�b�v
    public int[][] map = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,4,4,4,4,4,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,5,1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,2},
    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,5,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,1,0,0,7,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,5,1,1,1,1,1,0,0,0,1,0,0,5,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,5,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,4,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,5,1,1,0,0,0,7,0,0,0,0,0,0,1,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,3,3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,5,1,0,0,0,0,0,0,1,0,1,1,1,0,0,0,0,0,0,0,5,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,1,1,1,0,0,0,1,1,1,1,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,1,1,1,1,1,1,1,0,0,0,1,1,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,3,3,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2}
  };

    //�f��
    private Image blockImage;   //1
    private Image hari_up;      //3
    private Image hari_down;    //4
    private Image hari_left;    //5
    private Image hari_right;   //6

    public Map(){
        loadImage();
    }

    public void show(Graphics2D g2, int offsetX, int offsetY){
        //offset�����ɕ`��͈͂��Z�o
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainMode.WIDTH) + 1;
        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainMode.HEIGHT) + 1;
        //�`��͈͂��}�b�v��菬�������Ƃ�
        lastTileX = Math.min(lastTileX, COL);
        lastTileY = Math.min(lastTileY, ROW);

        g2.setColor(Color.RED);
        for (int i = firstTileY; i<lastTileY; i++){
            for (int j = firstTileX; j<lastTileX; j++){
                //map�̒l�ɉ����ĕ`��
                switch (map[i][j]) {
                    case 1:
                        g2.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 2: // �u���b�N
                        g2.fillRect(tilesToPixels(j) + offsetX,
                                   tilesToPixels(i) + offsetY,
                                   TILE_SIZE, TILE_SIZE);
                        break;
                    case 3:
                        g2.drawImage(hari_up, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 4:
                        g2.drawImage(hari_down, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 5:
                        g2.drawImage(hari_left, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 6:
                        g2.drawImage(hari_right, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 7:
                        break;
                }
            }
        }
    }

    public Point getTileCollision(Player player, double newX,double newY){

        newX = Math.ceil(newX);
        newY = Math.ceil(newY);

        double fromX = Math.min(player.getX(), newX);
        double fromY = Math.min(player.getY(), newY);
        double toX = Math.max(player.getX(), newX);
        double toY = Math.max(player.getY(), newY);

        int fromTileX = pixelsToTiles(fromX);
        int fromTileY = pixelsToTiles(fromY);
        int toTileX = pixelsToTiles(toX + Player.WIDTH - 1);
        int toTileY = pixelsToTiles(toY + Player.HEIGHT - 1);

        //�Փ˔���
        for (int x = fromTileX; x<=toTileX; x++){
            for(int y = fromTileY; y<=toTileY;y++){
                //�}�b�v�O�͏Փ�
                if(y<0 || x>=COL){
                    return new Point(x,y);
                }
                if (y < 0 || y >= ROW) {
                    return new Point(x, y);
                }
                //�u���b�N�Փ�
                if(map[y][x] == 1){
                    return new Point(x,y);
                }
            }
        }
        return null;
    }


  public Point getNeedleCollision(Player player, double newX,double newY){

        newX = Math.ceil(newX);
        newY = Math.ceil(newY);

        double fromX = Math.min(player.getX(), newX);
        double fromY = Math.min(player.getY(), newY);
        double toX = Math.max(player.getX(), newX);
        double toY = Math.max(player.getY(), newY);

        int fromTileX = pixelsToTiles(fromX);
        int fromTileY = pixelsToTiles(fromY);
        int toTileX = pixelsToTiles(toX + Player.WIDTH - 1);
        int toTileY = pixelsToTiles(toY + Player.HEIGHT - 1);


        for (int x = fromTileX; x<=toTileX; x++){
            for(int y = fromTileY; y<=toTileY; y++){

                //�S�[���Ɛj�Փ�
                if(map[y][x] >= 2 && map[y][x] <= 6){
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    private void loadImage(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("image/block.gif"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("image/hari_up.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("image/hari_down.png"));
        ImageIcon icon4 = new ImageIcon(getClass().getResource("image/hari_left.png"));
        ImageIcon icon5 = new ImageIcon(getClass().getResource("image/hari_right.png"));
        blockImage = icon1.getImage();
        hari_up = icon2.getImage();
        hari_down = icon3.getImage();
        hari_left = icon4.getImage();
        hari_right = icon5.getImage();
    }

    //�s�N�Z���P�ʂ��^�C���P�ʂ֕ύX
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels/TILE_SIZE);
    }

    //�^�C���P�ʂ��s�N�Z���P�ʂ֕ύX
    public static int tilesToPixels(int tiles){
        return tiles*TILE_SIZE;
    }

}