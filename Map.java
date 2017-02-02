import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import java.lang.System;
import java.lang.String;


public class Map {
    
    //ブロックサイズ
    public static final int TILE_SIZE = 32;
    //行数列数
    public static final int ROW = 15;
    public static final int COL = 100;
    //幅・高さ
    public static final int WIDTH = TILE_SIZE*COL;
    public static final int HEIGHT = TILE_SIZE*ROW;
    //重力
    public static final double GRAVITY = 1.5;
    
    //マップ
    public int[][] map = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,4,4,4,4,4,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,1,4,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,5,1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,5,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
    {1,6,0,0,0,0,0,1,1,1,1,1,0,0,1,7,0,0,0,0,0,0,0,0,1,0,0,7,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,5,1,1,1,1,1,0,0,0,1,0,0,5,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,3,3,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,5,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,4,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,5,1,1,0,0,0,7,0,0,0,0,0,0,1,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,3,3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,5,1,0,0,0,0,0,0,1,0,1,1,1,0,0,0,0,0,0,0,5,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
    {1,6,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,1,1,1,1,1,1,1,0,0,0,1,1,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,3,3,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,1,2},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2}
    };
    
    //素材
    private Image blockImage;   //1
    private Image hari_up;      //3
    private Image hari_down;    //4
    private Image hari_left;    //5
    private Image hari_right;   //6
    
    public Map(){
        loadImage();
    }    
        
    public void show(Graphics2D g2, int offsetX, int offsetY){
        //offsetを元に描画範囲を算出
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainMode.WIDTH) + 1;
        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainMode.HEIGHT) + 1;
        //描画範囲をマップより小さくしとく
        lastTileX = Math.min(lastTileX, COL);
        lastTileY = Math.min(lastTileY, ROW);
        
        g2.setColor(Color.RED);
        for (int i = firstTileY; i<lastTileY; i++){
            for (int j = firstTileX; j<lastTileX; j++){
                //mapの値に応じて描画
                switch (map[i][j]) {
                    case 1:
                        g2.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
                        break;
                    case 2: // ブロック
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
        
        //衝突判定
        for (int x = fromTileX; x<=toTileX; x++){
            for(int y = fromTileY; y<=toTileY;y++){
                //マップ外は衝突
                if(y<0 || x>=COL){
                    return new Point(x,y);
                }
                if (y < 0 || y >= ROW) {
                    return new Point(x, y);
                }
                //ブロック衝突
                if(map[y][x] == 1){
                    return new Point(x,y);
                }
            }
        }
        return null;
    }
    
        public Point getTileCollision(Enemy enemy, double newX,double newY){
        
        newX = Math.ceil(newX);
        newY = Math.ceil(newY);
        
        double fromX = Math.min(enemy.getX(), newX);
        double fromY = Math.min(enemy.getY(), newY);
        double toX = Math.max(enemy.getX(), newX);
        double toY = Math.max(enemy.getY(), newY);
        
        int fromTileX = pixelsToTiles(fromX);
        int fromTileY = pixelsToTiles(fromY);
        int toTileX = pixelsToTiles(toX + enemy.WIDTH - 1);
        int toTileY = pixelsToTiles(toY + enemy.HEIGHT - 1);
        
        //衝突判定
        for (int x = fromTileX; x<=toTileX; x++){
            for(int y = fromTileY; y<=toTileY;y++){
                //マップ外は衝突
                if(y<0 || x>=COL){
                    return new Point(x,y);
                }
                //ブロック，ゴール，針衝突
                if(map[y][x] >= 1 && map[y][x] <= 6){
                    return new Point(x, y);
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
                
                //ゴールと針衝突
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
    
    //ピクセル単位をタイル単位へ変更
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels/TILE_SIZE);
    }
    
    //タイル単位をピクセル単位へ変更
    public static int tilesToPixels(int tiles){
        return tiles*TILE_SIZE;
    }
    
}
