package com.Ling_eryao.scense;

import com.Ling_eryao.obj.gameObj.GameObj;
import com.Ling_eryao.obj.gameObj.TileObj;
import com.Ling_eryao.obj.levelObj.Level;
import com.Ling_eryao.obj.levelObj.buildMechanismButtonObj;
import com.Ling_eryao.wins.MainWin;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelScene {
    MainWin farme;

    int levelID; //第几关
    //关卡宽高
    int levelWidth;
    int levelHeight;
    public static List<GameObj> levelObjList = new ArrayList<>();
    public static List<GameObj> delObjList = new ArrayList<>();

    public Level getLevelData(int level){
        ObjectMapper objectMapper = new ObjectMapper();
        Level levelObj = null;
        try {
            levelObj = objectMapper.readValue(new File(String.format("data/levelData/level%d.json",level)), Level.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelObj;
    }

    public void setTile(int x,int y, char type,int level){
        levelObjList.add(new TileObj(x,y,farme,type,level));
    }
    public void setBuildButton(int x, int y,int mechID,int level,MainWin farme){levelObjList.add(new buildMechanismButtonObj(x,y,mechID,level,farme));}
    public void generateLevel(int levelID){
        //生成关卡
        Level levelObj = getLevelData(levelID);
//        System.out.println(levelObj.name);
//        System.out.println(levelObj.levelObj);
//        System.out.println(levelObj.author);
//        System.out.println(levelObj.availableList);
//        System.out.println(levelObj.width);
//        System.out.println(levelObj.height);
        System.out.println(levelObj.mapData.get(0).charAt(1));
        levelWidth = levelObj.width;
        levelHeight = levelObj.height;
        int blockX,blockY;//生成的tile的坐标
        int buttonY = 70;
        int buttonX = 680;
        blockX = 400 - (35*levelWidth/2);
        blockY = 315 - (35*levelHeight/2);
        //生成tile对象
        for (int i = 0; i < levelWidth; i++) {
            for (int j = 0; j < levelHeight; j++) {
                setTile(blockX,blockY,levelObj.mapData.get(i).charAt(j),levelID);
                blockX += 35;
            }
            blockY += 35;
            blockX = 400 - (35*levelWidth/2);
        }

        //生成Shallow
        blockX = 400 - (35*levelWidth/2);
        blockY = 315 - (35*levelHeight/2);
        for (int i = 0; i < levelWidth; i++) {
            for (int j = 0; j < levelHeight; j++) {
                setTile(blockX,blockY,'Y',levelID);
                blockX += 35;
            }
            blockY += 35;
            blockX = 400 - (35*levelWidth/2);
        }

        // 生成建造按钮
        for (int i = 0; i < levelObj.availableList.size(); i++) {
            setBuildButton(buttonX,buttonY,levelObj.availableList.get(i),levelID,farme);
            setBuildButton(buttonX,buttonY,0,levelID,farme);
            buttonY += 70;
        }

    }

    public LevelScene(int level, MainWin frame) {
        this.levelID = level;
        this.farme=frame;
        //generateLevel(level);
    }

    public void paintScene(Graphics g) {
        levelObjList.removeAll(delObjList);
        for (GameObj obj : levelObjList) {
            obj.paintSelf(g);
        }
    }
}
