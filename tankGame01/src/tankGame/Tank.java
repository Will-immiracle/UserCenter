package tankGame;

import java.util.Vector;

public class Tank {
    public boolean isLive = true;
    private int type;
    private int x;
    private int y;
    private int direction = 0;
    int speed = 3;

    public int getType() {
        return type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void moveUp(){
        y-=speed;
    }
    public void moveDown(){
        y+=speed;
    }
    public void moveLeft(){
        x-=speed;
    }
    public void moveRight(){
        x+=speed;
    }
}
class Hero extends Tank{
    public Vector<Shot> bullets = new Vector<>();

    int type = 0;

    public Hero(int x, int y) {
        super(x, y);
    }
    public int getType() {
        return type;
    }

    public Vector<Shot> getBullets() {
        return bullets;
    }

    public void shotEnemy(){
        switch(getDirection()){
            case 0:
                Shot shot0 = new Shot(getX() + 20, getY(), 0);
                bullets.add(shot0);
                new Thread(shot0).start();
                break;
            case 1:
                Shot shot1 = new Shot(getX() + 20, getY() + 50, 1);
                bullets.add(shot1);
                new Thread(shot1).start();
                break;
            case 2:
                Shot shot2 = new Shot(getX(), getY() + 20, 2);
                bullets.add(shot2);
                new Thread(shot2).start();
                break;
            case 3:
                Shot shot3 = new Shot(getX() + 50, getY() + 20, 3);
                bullets.add(shot3);
                new Thread(shot3).start();
                break;
        }
    }

}
class Enemy extends Tank implements Runnable{
    public Vector<Shot> bulletVector = new Vector<>();
    static int modCount = 0;

    int type = 1;
    public int times = 0;

    public Enemy(int x,int y) {
        super(x,y);
        modCount++;
        shot();
    }
    public int getType() {
        return type;
    }
    public void shot(){
        switch (getDirection()) {
            case 0:
                Shot shot0 = new Shot(getX() + 20, getY(), 0);
                bulletVector.add(shot0);
                new Thread(shot0).start();
                break;
            case 1:
                Shot shot1 = new Shot(getX() + 20, getY() + 50, 1);
                bulletVector.add(shot1);
                new Thread(shot1).start();
                break;
            case 2:
                Shot shot2 = new Shot(getX(), getY() + 20, 2);
                bulletVector.add(shot2);
                new Thread(shot2).start();
                break;
            case 3:
                Shot shot3 = new Shot(getX() + 50, getY() + 20, 3);
                bulletVector.add(shot3);
                new Thread(shot3).start();
                break;

        }
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!isLive){
                return;
            }
            switch(getDirection()){
                case 0:
                    if(getY() <10){
                        setDirection((int)(Math.random()*4));
                        break;
                    }
                    moveUp();
                    break;
                case 1:
                    if(getY() >740){
                        setDirection((int)(Math.random()*4));
                        break;
                    }
                    moveDown();
                    break;
                case 2:
                    if(getX() <10){
                        setDirection((int)(Math.random()*4));
                        break;
                    }
                    moveLeft();
                    break;
                case 3:
                    if(getX() >940){
                        setDirection((int)(Math.random()*4));
                        break;
                    }
                    moveRight();
                    break;
            }
            if(bulletVector.isEmpty()){
                shot();
            }
            times++;
        }
    }
}
