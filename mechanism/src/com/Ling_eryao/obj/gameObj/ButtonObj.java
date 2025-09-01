package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.wins.MainWin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonObj extends GameObj {
    public Rectangle rect = new Rectangle();
    public String name;
    int mouseX, mouseY;

    public ButtonObj(Image img1, Image img2, int width, int height, int x, int y, String name, MainWin frame) {
        super(img1, width, height, x, y, frame);
        rect.x = x;
        rect.y = y;
        rect.height = height;
        rect.width = width;
        this.name = name;
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                if (rect.contains(mouseX, mouseY)) {
                    ButtonObj.super.img = img2;
                } else {
                    ButtonObj.super.img = img1;
                }

            }
        });

        this.frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("鼠标被点击了");
//                if (rect.contains(mouseX, mouseY)) {
//                    System.out.println("按钮被点击了");
//                }
                if(name=="StartGameButton" && rect.contains(mouseX,mouseY)){
                    MainWin.gameState = 3;
                }
            }
        });
    }

}
