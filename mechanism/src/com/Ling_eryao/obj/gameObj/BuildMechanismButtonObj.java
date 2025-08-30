package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildMechanismButtonObj extends GameObj {
    int mechID;
    int levelID;
    Rectangle rect = new Rectangle();
    int outX = 9999;
    int outY = 9999;
    int inX, inY;
    int MouseX,MouseY;
    boolean del = false;
    public BuildMechanismButtonObj(int x, int y, int mechID, int level, MainWin frame) {
        super(x, y);
        super.setImg(GameUtils.getScaledImage(GameUtils.loadImg(String.format("assets/img/game/button/%d.png", mechID)), 65, 65));
        this.mechID = mechID;
        this.levelID = level;
        inX = x;
        inY = y;
        rect.x = x;
        rect.y = y;
        rect.height = 65;
        rect.width = 65;
        System.out.println(mechID);
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MouseX = e.getX();
                MouseY = e.getY();
                if (mechID == 0) {
                    if (rect.contains(MouseX,MouseY)) {
                        setX(outX);
                        setY(outY);
                    } else {
                        setX(inX);
                        setY(inY);
                    }
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(rect.contains(MouseX,MouseY)) {
                    if (LevelScene.building == false && mechID != 0 && !del) {
                        LevelScene.building = true;
                        LevelScene.buildType = mechID;
                       // System.out.println("建造");
                        //System.out.println(mechID);
                    }
                }
            }
        });
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        if (MainWin.currentLevel != this.levelID) {
            del = true;
            LevelScene.delObjList.add(this);
        }
    }

}
