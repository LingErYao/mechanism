package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;

public class TitleObj extends GameObj {
    double tick = 0;
    private int baseY = y;
    public TitleObj(int x, int y, MainWin frame) {
        super(x, y, frame);
        this.img = GameUtils.titleImg;
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
        y = (int) (baseY + 12 * Math.sin(tick));
        tick += 0.05;
    }
}
