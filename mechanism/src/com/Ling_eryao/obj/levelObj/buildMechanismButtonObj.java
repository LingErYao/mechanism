package com.Ling_eryao.obj.levelObj;

import com.Ling_eryao.obj.gameObj.GameObj;
import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class buildMechanismButtonObj extends GameObj {
    int mechID;
    int levelID;
    Rectangle rect = new Rectangle();
    int outX = 9999;
    int outY = 9999;
    int inX, inY;

    public buildMechanismButtonObj(int x, int y, int mechID, int level, MainWin frame) {
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
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mechID == 0) {
                    if (rect.contains(e.getX(), e.getY())) {
                        setX(outX);
                        setY(outY);
                        //setX(inX);
                        //setX(inY);
                    } else {
                        setX(inX);
                        setY(inY);
                    }
                }
            }
        });
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        if (MainWin.currentLevel != this.levelID) {
            LevelScene.delObjList.add(this);
        }
    }

}
