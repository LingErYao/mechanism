package com.Ling_eryao.obj.levelObj;

import com.Ling_eryao.obj.gameObj.GameObj;
import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;

public class MechanismObj extends GameObj {
    int type = -1;
    MainWin frame;
    int level;
    public MechanismObj(int x, int y, int type, MainWin frame, int level) {
        super(x, y);
        this.type = type;
        this.frame = frame;
        this.level = level;
        setImg(GameUtils.getScaledImage(GameUtils.loadImg(String.format("assets/img/game/mechanism/%d.png", type)), 33, 33));

    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        if (MainWin.currentLevel != this.level) {
            LevelScene.delObjList.add(this);
        }
    }
}
