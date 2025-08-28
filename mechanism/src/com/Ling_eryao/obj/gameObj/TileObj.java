package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TileObj extends GameObj{
    char type;
    int levelID;
    Rectangle rect = new Rectangle();
    int outX = 9999;
    int outY = 9999;
    int inX,inY;
    //type:
    // S：起点
    //  E：空方格
    //  A：无方格
    //  D：目的地
    //  B：障碍
    // Y:阴影遮罩
    public TileObj(int x, int y, MainWin frame, char type, int levelID) {
        super(x, y, frame);
        inX = x;
        inY = y;
        this.type = type;
        this.levelID  = levelID;
        rect.x = x;
        rect.y = y;
        rect.height = 35;
        rect.width = 35;
        switch (type){
            case 'S':
                this.img = GameUtils.originTileImg;
                break;

            case 'E':
                this.img = GameUtils.commonTileImg;
                break;

            case 'A':
                this.img = null;
                break;
            case 'D':
                this.img = GameUtils.destTileImg;
                break;
            case 'B':
                this.img = GameUtils.barrierTileImg;
                break;
            case 'Y':
                this.img = GameUtils.darkShallowImg;
                break;
        }

        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (type=='Y'){
                    if(rect.contains(e.getX(),e.getY())) {
                        setX(outX);
                        setY(outY);
                    }else {
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
        if (MainWin.currentLevel!=this.levelID){
            LevelScene.delObjList.add(this);
        }
    }
}
