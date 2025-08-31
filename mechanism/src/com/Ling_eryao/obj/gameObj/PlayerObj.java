package com.Ling_eryao.obj.gameObj;


import com.Ling_eryao.obj.gameObj.motionAnimationObj.MotionAnimation;
import com.Ling_eryao.operaData.OperaData;
import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class PlayerObj extends GameObj {
    int level;
    boolean del = false;
    boolean doingAnimation = false; // 正在执行动画？
    int animationType = 0; // 1:移动动画 2:缩放动画
    int animationDir = 0; // 1：上 2：下 3：左 4：右
    int animationDis = 0; // 动画距离（单位:格）
    int animationTotalFrames = 0; // 动画总帧数
    int animationDoneFrames = 0;  // 动画已经完成的帧数
    int animationCount = 0;//坐标修正用 进行了几次动画
    int xIndex, yIndex;
    double imgW, imgH;
    double deltaScale;
    int beforScaleX, beforScaleY;
    Image oriImg;
    int delay = 0;
    boolean toOrigin = false;

    // xIndex/y:以方格为单位的坐标
    public PlayerObj(int x, int y, MainWin frame, int level, int _xIndex, int _yIndex) {
        super(x, y, frame);
        this.xIndex = _xIndex;
        this.yIndex = _yIndex;
        this.level = level;
        img = GameUtils.playerImg;
        oriImg = img;
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!doingAnimation && !del) {
                    if (e.getKeyChar() == 'w') {
                        loadAnimation("up");
                    }

                    if (e.getKeyChar() == 's') {
                        loadAnimation("down");
                    }

                    if (e.getKeyChar() == 'a') {
                        loadAnimation("left");
                    }

                    if (e.getKeyChar() == 'd') {
                        loadAnimation("right");
                    }
                }
            }
        });
    }

    public MotionAnimation loadAnimationObj(String name) {
        ObjectMapper objectMapper = new ObjectMapper();
        com.Ling_eryao.obj.gameObj.motionAnimationObj.MotionAnimation motionAnimation = null;
        try {
            motionAnimation = objectMapper.readValue(new File(String.format("data/animationData/%s.json", name)), MotionAnimation.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return motionAnimation;
    }

    public void loadAnimation(String name) {
        animationCount++;
        MotionAnimation motionAnimation = loadAnimationObj(name);
        this.animationDir = motionAnimation.animationDir;
        this.animationDis = motionAnimation.animationDis;
        this.animationTotalFrames = motionAnimation.animationTotalFrames;
        this.animationDoneFrames = 0;
        this.doingAnimation = true;
        this.animationType = 1;
    }

    public void addScalAnimation(double beforeScale, double afterScale, int totalFrames, int type) {
        //动画type:
        //1：移动动画
        //2：玩家重生时的放大动画
        //3：玩家掉出地图的缩小动画
        //4：普通缩放动画（不带坐标归正）
        this.doingAnimation = true;
        this.animationType = type;
        this.animationTotalFrames = totalFrames;
        this.animationDoneFrames = 0;
        imgH = (int) beforeScale;
        imgW = (int) beforeScale;
        beforScaleX = (int) x;
        beforScaleY = (int) y;
        deltaScale = (afterScale - beforeScale) / (totalFrames + 3);


    }

    public void afterMove() {
        if (xIndex > LevelScene.levelWidth - 1 || xIndex < 0 || yIndex > LevelScene.levelHeight - 1 || yIndex < 0) {
            addScalAnimation(20, 0, 30, 3);
            return;
        }
        if (LevelScene.levelObj.mapData.get(yIndex).charAt(xIndex) == 'A') {
            //判断是不是在虚空格子
            addScalAnimation(20, 0, 30, 3);
            return;
        }
        if (LevelScene.levelObj.mapData.get(yIndex).charAt(xIndex) == 'B') {
            //判断是不是障碍
            addScalAnimation(20, 0, 30, 3); // 障碍还没有做单独动画
            return;
        }
        if (LevelScene.levelObj.mapData.get(yIndex).charAt(xIndex) == 'D') {
            MainWin.currentLevel++; // 进入下一关  目前无动画
            return;
        }

        for (GameObj gameObj : LevelScene.levelObjList) {
            if (gameObj.getClass() == TileObj.class) {
                if (((TileObj) gameObj).yIndex == this.yIndex && ((TileObj) gameObj).xIndex == this.xIndex) {
                    if (((TileObj) gameObj).mechType != 0) {
                        handleMechanismAnimations(((TileObj) gameObj).mechType);
                    } else {
                        System.out.println("这里没有机关");
                    }

                }
            }
        }


    }

    public void handleMechanismAnimations(int mechType) {
        ObjectMapper objectMapper = new ObjectMapper();
        OperaData operaData = null;
        try {
            operaData = objectMapper.readValue(new File(String.format("data/mechanismData/operaData/%d.json", mechType)), OperaData.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        doingAnimation = true;
        animationType = 1;
        animationDir = operaData.dir;
        animationDis = operaData.dis;
        animationDoneFrames = 0;
        animationTotalFrames = operaData.totalFrames;

    }

    @Override
    public void paintSelf(Graphics g) {
        if (!del) {
            if (delay == 0) {
                if (MainWin.currentLevel != this.level) {
                    LevelScene.delObjList.add(this);
                }
                int blockSize; // 坐标修正后的格子长
                if (animationCount % 2 == 0) {
                    blockSize = 34;
                } else {
                    blockSize = 33;
                }
                super.paintSelf(g);
                if (doingAnimation) {
                    if (animationDoneFrames <= animationTotalFrames) {
                        if (animationType == 1) {
                            if (animationDir == 1) {
                                this.y -= (double) (blockSize * animationDis) / animationTotalFrames;
                            } else if (animationDir == 2) {
                                this.y += (double) (blockSize * animationDis) / animationTotalFrames;
                            } else if (animationDir == 3) {
                                this.x -= (double) (blockSize * animationDis) / animationTotalFrames;
                            } else if (animationDir == 4) {
                                this.x += (double) (blockSize * animationDis) / animationTotalFrames;
                            }
                        }
                        if (animationType == 2 || animationType == 3 || animationType == 4) {
                            //System.out.println(imgH);
                            setImg(GameUtils.getScaledImage(GameUtils.playerImg, (int) imgW, (int) imgH));
                            super.paintSelf(g);
                            imgH += deltaScale;
                            imgW += deltaScale;
                            x = beforScaleX + (int) ((20 - imgH) * 0.5);
                            y = beforScaleY + (int) ((20 - imgW) * 0.5);
                            //System.out.println(imgH);
                        }
                        animationDoneFrames++;
                    } else {
                        // 动画结束后的判定↓
                        if (animationType == 1) {
                            if (animationDir == 1) {
                                yIndex -= animationDis;
                            } else if (animationDir == 2) {
                                yIndex += animationDis;
                            } else if (animationDir == 3) {
                                xIndex -= animationDis;
                            } else if (animationDir == 4) {
                                xIndex += animationDis;
                            }

                        }
                        doingAnimation = false;
                        animationDir = 0;
                        animationDis = 0;
                        animationTotalFrames = 0;
                        animationDoneFrames = 0;
                        if (animationType == 1) {
                            afterMove();

                        } else if (animationType == 3) {
                            x = 999;
                            y = 999;
                            delay = 80;
                            toOrigin = true;
                        } else if (animationType == 2) {
                            x = LevelScene.playerOriginX;
                            y = LevelScene.playerOriginY;
                            img = GameUtils.playerImg;
                            animationType = 0;
                        } else {
                            animationType = 0;
                        }


                    }
                }
            } else {
                delay--;
                if (delay == 0) {
                    if (toOrigin) {
                        toOrigin = false;
                        x = LevelScene.playerOriginX;
                        y = LevelScene.playerOriginY;
                        setImg(GameUtils.getScaledImage(GameUtils.playerImg, 1, 1));
                        xIndex = LevelScene.xIndex;
                        yIndex = LevelScene.yIndex;
                        addScalAnimation(1, 20, 8, 2);
                    }
                }
            }
        }
    }
}
