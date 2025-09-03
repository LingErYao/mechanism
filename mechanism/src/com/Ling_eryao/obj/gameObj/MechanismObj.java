package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;

public class MechanismObj extends GameObj {
    public int xIndex,yIndex;
    int type = -1;
    MainWin frame;
    int level;
    public boolean del = false;
    public MechanismObj(int x, int y, int type, MainWin frame,int level,int _xIndex,int _yIndex) {
        super(x, y);
        this.xIndex = _xIndex;
        this.yIndex = _yIndex;
        this.type = type;
        this.frame = frame;
        this.level = level;
        setImg(GameUtils.getScaledImage(GameUtils.loadImg(String.format("assets/img/game/mechanism/%d.png", type)), 33, 33));

    }

    @Override
    public void paintSelf(Graphics g) {
        if(!del) {
            super.paintSelf(g);
            if (MainWin.currentLevel != this.level) {
                LevelScene.delObjList.add(this);
            }
        }else {
            System.out.println(1145);
        }
    }
}
