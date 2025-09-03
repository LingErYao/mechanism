package com.Ling_eryao.scense;

import com.Ling_eryao.obj.gameObj.*;
import com.Ling_eryao.obj.levelObj.Level;
import com.Ling_eryao.wins.MainWin;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelScene {
    MainWin frame;
    public static boolean building = false; //是否正在建造
    public static int buildType = 0;
    int levelID; //第几关
    public static int xIndex = 0;
    public static int yIndex = 0;
    // ↑以格为单位的玩家初始坐标
    //关卡宽高
    public static int levelWidth;
    public static int levelHeight;
    public static int playerOriginX,playerOriginY;
    public static List<GameObj> levelObjList = new ArrayList<>();
    public static List<MechanismObj> mechanismObjList = new ArrayList<>();
    public static List<GameObj> delObjList = new ArrayList<>();
    public static Level levelObj;


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

    public void setTile(int x,int y, char type,int level,int _xIndex,int _yIndex){
        levelObjList.add(new TileObj(x,y, frame,type,level,_xIndex,_yIndex));
    }
    public void setBuildButton(int x, int y,int mechID,int level,MainWin farme){levelObjList.add(new BuildMechanismButtonObj(x,y,mechID,level,farme));}
    public void generateLevel(int levelID){
        levelObj = getLevelData(levelID);

        //生成关卡
//        System.out.println(levelObj.name);
//        System.out.println(levelObj.levelObj);
//        System.out.println(levelObj.author);
//        System.out.println(levelObj.availableList);
//        System.out.println(levelObj.width);
//        System.out.println(levelObj.height);
        //System.out.println(levelObj.mapData.get(0).charAt(1));
        levelWidth = levelObj.width;
        levelHeight = levelObj.height;
        int blockX,blockY;//生成的tile的坐标
        int buttonY = 70;
        int buttonX = 680;
        blockX = 400 - (35*levelHeight/2);
        blockY = 315 - (35*levelWidth/2);
        //生成tile对象
        for (int i = 0; i < levelWidth; i++) {
            for (int j = 0; j < levelHeight; j++) {
                setTile(blockX,blockY,levelObj.mapData.get(i).charAt(j),levelID,j,i);
                if(levelObj.mapData.get(i).charAt(j) == 'S'){
                    playerOriginX = blockX + 8;
                    playerOriginY = blockY + 8;
                    xIndex = j;
                    yIndex = i;
                }
                blockX += 35;
            }
            blockY += 35;
            blockX = 400 - (35*levelWidth/2);
        }

        //生成Shallow
        blockX = 400 - (35*levelHeight/2);
        blockY = 315 - (35*levelWidth/2);
        for (int i = 0; i < levelWidth; i++) {
            for (int j = 0; j < levelHeight; j++) {
                setTile(blockX,blockY,'Y',levelID,0,0);
                blockX += 35;
            }
            blockY += 35;
            blockX = 400 - (35*levelWidth/2);
        }

        // 生成建造按钮
        for (int i = 0; i < levelObj.availableList.size(); i++) {
            setBuildButton(buttonX,buttonY,levelObj.availableList.get(i),levelID, frame);
            setBuildButton(buttonX,buttonY,0,levelID, frame);
            buttonY += 70;
        }
        setBuildButton(20,520,-1,levelID, frame);
        setBuildButton(20,520,0,levelID, frame);
        levelObjList.add(new PlayerObj(playerOriginX,playerOriginY, frame,levelID,xIndex,yIndex));

    }

    public LevelScene(int level, MainWin frame) {
        this.levelID = level;
        this.frame =frame;
    }

    public void paintScene(Graphics g) {
        levelObjList.removeAll(delObjList);
        mechanismObjList.remove(delObjList);
        for (GameObj obj : levelObjList) {
            if(obj.getClass() != PlayerObj.class)
                obj.paintSelf(g);
        }

        for (int i = 0; i < levelObjList.size(); i++) {
            if (levelObjList.get(i).getClass() == PlayerObj.class){
                levelObjList.get(i).paintSelf(g);
            }
        }
    }

    public static void buildMech(int x,int y,int type, MainWin farme, int level,int _xIndex, int _yIndex){
        MechanismObj tempObj = new MechanismObj(x, y, type,farme,level,_xIndex,_yIndex);
        levelObjList.add(tempObj);
        mechanismObjList.add(tempObj);
    }

    public static void delMech(int _xIndex, int _yIndex){
//        System.out.println("******");
//        System.out.println(_xIndex);
//        System.out.println(_yIndex);
//        System.out.println("******");
        for(MechanismObj obj : mechanismObjList){
//            System.out.println("------");
//            System.out.println(obj.xIndex);
//            System.out.println(obj.yIndex);
//            System.out.println("------");
            if(obj.xIndex == _xIndex && obj.yIndex == _yIndex){
                System.out.println("拆除成功");
                obj.del = true;
                delObjList.add(obj);
                return;
            }
        }
    }


}