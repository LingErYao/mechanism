package com.Ling_eryao.scense;

import com.Ling_eryao.obj.gameObj.ButtonObj;
import com.Ling_eryao.obj.gameObj.GameObj;
import com.Ling_eryao.obj.gameObj.TitleObj;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMenu {
    MainWin frame;
    public static List<GameObj> menuObjList = new ArrayList<>();

    public GameMenu(MainWin frame) {
        this.frame = frame;

        //添加元素
        TitleObj titleObj = new TitleObj(239, 90, frame);
        menuObjList.add(titleObj);
        ButtonObj startButton = new ButtonObj(GameUtils.startButtonImg_1, GameUtils.startButtonImg_2, (int) (164 * 0.85), (int) (88 * 0.85), 330, 320, "StartGameButton", frame);
        menuObjList.add(startButton);

    }

    public void paintScene(Graphics g) {
        for (GameObj obj : menuObjList) {
            obj.paintSelf(g);
        }
    }


}
