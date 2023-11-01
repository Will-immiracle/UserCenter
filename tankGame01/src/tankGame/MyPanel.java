package tankGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    public Vector<Bomb> bombs = new Vector<>();
    Image image1;
    Image image2;
    Image image3;
    private Hero hero = null;
    private Vector<Enemy> enemyVector = null;
    private int enemySize;
    private int enenmyCount = 0;
    private  Tank imageTank = new Enemy(1020,100);

    public MyPanel() throws IOException {
        hero = new Hero(100, 100);
        image1 = ImageIO.read(new FileInputStream("/Users/zhangzan/java/java/src/bg1.jpeg"));
        image2 = ImageIO.read(new FileInputStream("/Users/zhangzan/java/java/src/bg2.jpeg"));
        image3 = ImageIO.read(new FileInputStream("/Users/zhangzan/java/java/src/bg3.jpeg"));
        enemySize = 3;
        enemyVector = new Vector<Enemy>();
        for (int i = 0; i < enemySize; i++) {
            Enemy enemy = new Enemy(200 + Enemy.modCount * 100, 100);
            enemyVector.add(enemy);
            Thread thread = new Thread(enemy);
            thread.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //设置击杀面板
        g.setColor(Color.white);
        g.fillRect(1000, 0, 300, 800);
        //填充击杀面板
        g.setFont(new Font("宋体",Font.BOLD,30));
        g.setColor(Color.black);
        g.drawString("击毁坦克数量 :",1020,30);
        g.drawString(enenmyCount+"",1150,150);
        //画坦克移动区域
        g.fillRect(0, 0, 1000, 800);
        drawTank(imageTank,g);
        if (hero.isLive) {
            drawTank(hero, g);
        }
        if (!hero.bullets.isEmpty()) {
            for (Shot o : hero.bullets) {
                if (o.isLive) {
                    g.setColor(Color.cyan);
                    g.fill3DRect(o.x, o.y, 2, 2, false);
                } else {
                    hero.bullets.remove(o);
                }
            }
        }

        for (Enemy o : enemyVector) {
            if (o.isLive) {
                g.setColor(Color.yellow);
                drawTank(o, g);
                for (Shot shot : o.bulletVector) {
                    if (shot.isLive) {
                        g.fill3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        o.bulletVector.remove(shot);
                    }
                }
            } else {
                enemyVector.remove(o);
            }
        }
        if (bombs != null) {
            for (Bomb bomb : bombs) {
                if (!bomb.isLive) {
                    bombs.remove(bomb);
                    break;
                }
                if (bomb.life > 6) {
                    g.drawImage(image1, bomb.x, bomb.y, 50, 50, this);
                } else if (bomb.life > 3) {
                    g.drawImage(image2, bomb.x + 10, bomb.y + 10, 30, 30, this);
                } else if (bomb.life > 0) {
                    g.drawImage(image3, bomb.x + 20, bomb.y + 20, 10, 10, this);
                }
                bomb.lifeDown();
            }

        }
    }

    public void drawTank(Tank tank, Graphics g) {
        int x = tank.getX();
        int y = tank.getY();
        switch (tank.getType()) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
            default:
                System.out.println("暂未设定...");
                break;
        }
        switch (tank.getDirection()) {
            case 0:
                g.fill3DRect(x, y, 10, 50, false);
                g.fill3DRect(x + 10, y + 10, 20, 30, false);
                g.fill3DRect(x + 30, y, 10, 50, false);
                g.fill3DRect(x + 19, y, 2, 15, false);
                g.fillOval(x + 10, y + 15, 20, 20);
                break;
            case 1:
                g.fill3DRect(x, y, 10, 50, false);
                g.fill3DRect(x + 10, y + 10, 20, 30, false);
                g.fill3DRect(x + 30, y, 10, 50, false);
                g.fill3DRect(x + 19, y + 35, 2, 15, false);
                g.fillOval(x + 10, y + 15, 20, 20);
                break;
            case 2:
                g.fill3DRect(x, y, 50, 10, false);
                g.fill3DRect(x + 10, y + 10, 30, 20, false);
                g.fill3DRect(x, y + 30, 50, 10, false);
                g.fill3DRect(x, y + 19, 15, 2, false);
                g.fillOval(x + 15, y + 10, 20, 20);
                break;
            case 3:
                g.fill3DRect(x, y, 50, 10, false);
                g.fill3DRect(x + 10, y + 10, 30, 20, false);
                g.fill3DRect(x, y + 30, 50, 10, false);
                g.fill3DRect(x + 35, y + 19, 15, 2, false);
                g.fillOval(x + 15, y + 10, 20, 20);
                break;
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (!enemyVector.isEmpty()) {
                lable_Up:
                for (Enemy enemy : enemyVector) {
                    switch (enemy.getDirection()) {
                        case 0:
                        case 1:
                            if(!(hero.getX() > enemy.getX()-50 && hero.getX() <enemy.getX()+50)){
                                continue lable_Up;
                            }
                            if (hero.getY() < enemy.getY() + 60) {
                                return;
                            }
                            break;
                        case 2:
                        case 3:
                            if(!(hero.getX() > enemy.getX()-50 && hero.getX() <enemy.getX()+60)){
                                continue lable_Up;
                            }
                            if (hero.getY() < enemy.getY() + 50) {
                                return;
                            }
                            break;
                    }
                }
            }
            if (hero.getY() < 10) {
                return;
            }
            hero.setDirection(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if(!enemyVector.isEmpty()){
                label_Down:
                for (Enemy enemy : enemyVector) {
                    switch (enemy.getDirection()){
                        case 0:
                        case 1:
                            if(!(hero.getX() > enemy.getX()-50 && hero.getX() <enemy.getX()+50)){
                                continue label_Down;
                            }
                            break;
                        case 2:
                        case 3:
                            if(!(hero.getX() > enemy.getX()-50 && hero.getX() <enemy.getX()+60)){
                                continue label_Down;
                            }
                            break;
                    }
                    if (hero.getY() + 50 > enemy.getY() - 10) {
                        return;
                    }
                }
            }
            if (hero.getY() > 740) {
                return;
            }
            hero.setDirection(1);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (!enemyVector.isEmpty()) {
                lable_Left:
                for (Enemy enemy : enemyVector) {
                    switch (enemy.getDirection()) {
                        case 0:
                        case 1:
                            if(!(hero.getY() > enemy.getY()-50 && hero.getY() <enemy.getY()+60)){
                                continue lable_Left;
                            }
                            if (hero.getX() < enemy.getY() + 50) {
                                return;
                            }
                            break;
                        case 2:
                        case 3:
                            if(!(hero.getY() > enemy.getY()-50 && hero.getY() <enemy.getY()+50)){
                                continue lable_Left;
                            }
                            if (hero.getY() < enemy.getY() + 60) {
                                return;
                            }
                            break;
                    }
                }
            }
            if (hero.getX() < 10) {
                return;
            }
            hero.setDirection(2);
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if(!enemyVector.isEmpty()){
                lable_Right:
                for (Enemy enemy : enemyVector) {
                    switch(enemy.getDirection()){
                        case 0:
                        case 1:
                            if(!(hero.getY() > enemy.getY()-50 && hero.getY() <enemy.getY()+60)){
                                continue lable_Right;
                            }
                            break;
                        case 2:
                        case 3:
                            if(!(hero.getY() > enemy.getY()-50 && hero.getY() <enemy.getY()+50)){
                                continue lable_Right;
                            }
                            break;
                    }
                    if (hero.getX() + 60 > enemy.getX() - 10) {
                        return;
                    }
                }
            }
            if (hero.getX() > 940) {
                return;
            }
            hero.setDirection(3);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (hero.bullets.size() >= 3) {
                return;
            }
            hero.shotEnemy();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void save() throws IOException {
        BufferedWriter bufferedWriter;
        String filePath = "/Users/zhangzan/Github/Git01/savedfile.txt";
        bufferedWriter = new BufferedWriter(new FileWriter(filePath,true));
        bufferedWriter.write(enenmyCount);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!hero.bullets.isEmpty() && !enemyVector.isEmpty()) {
                for (Shot shot : hero.bullets) {
                    for (Enemy enemy : enemyVector) {
                        collision(shot, enemy);
                    }
                }
            }
            if (!enemyVector.isEmpty() && hero.isLive) {
                for (Enemy enemy : enemyVector) {
                    if (!enemy.bulletVector.isEmpty()) {
                        for (Shot shot : enemy.bulletVector) {
                            collision(shot, hero);
                        }
                    }
                }

            }
            repaint();
        }
    }

    public void collision(Shot shot, Tank tank) {
        if (tank.isLive) {
            switch (tank.getDirection()) {
                case 0:
                case 1:
                    if (shot.isLive && shot.x > tank.getX() && shot.x < tank.getX() + 40 &&
                            shot.y > tank.getY() && shot.y < tank.getY() + 50) {
                        shot.isLive = false;
                        tank.isLive = false;
                        bombs.add(new Bomb(tank.getX(), tank.getY()));
                        enenmyCount ++;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    if (shot.isLive && shot.x > tank.getX() && shot.x < tank.getX() + 50 &&
                            shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                        shot.isLive = false;
                        tank.isLive = false;
                        bombs.add(new Bomb(tank.getX(), tank.getY()));
                        enenmyCount ++;
                        break;
                    }
                    break;
            }
        }
    }
}
