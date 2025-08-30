package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.wins.MainWin;

import java.awt.*;

public class GameObj {
    Image img;
    int width;
    int height;
    double x;
    double y;
    MainWin frame;

    public GameObj(int x, int y, MainWin frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
    }

    public GameObj(int width, int height, int x, int y, MainWin frame) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.frame = frame;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MainWin getFrame() {
        return frame;
    }

    public void setFrame(MainWin frame) {
        this.frame = frame;
    }

    public GameObj() {
    }

    public GameObj(Image img, int width, int height, int x, int y, MainWin frame) {
        this.img = img;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.frame = frame;
    }

    public void paintSelf(Graphics g) {
        g.drawImage(img, (int) x, (int) y, null);
    }

    public GameObj(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
