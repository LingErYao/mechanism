package com.Ling_eryao.obj.gameObj;

import com.Ling_eryao.Main;
import com.Ling_eryao.obj.gameObj.motionAnimationObj.MotionAnimation;
import com.Ling_eryao.obj.levelObj.Level;
import com.Ling_eryao.scense.LevelScene;
import com.Ling_eryao.utils.GameUtils;
import com.Ling_eryao.wins.MainWin;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class playerObj extends GameObj{
    int level;
    boolean del = false;
    boolean doingAnimation = false; // 正在执行动画？
    int animationDir = 0; // 1：上 2：下 3：左 4：右
    int animationDis = 0; // 动画距离（单位:格）
    int animationTotalFrames = 0; // 动画总帧数
    int animationDoneFrames = 0;  // 动画已经完成的帧数
    int animationCount = 0;//坐标修正用 进行了几次动画
    public playerObj(int x, int y, MainWin frame,int level) {
        super(x, y, frame);
        this.level = level;
        img = GameUtils.playerImg;

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!doingAnimation && !del) {
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

    public MotionAnimation loadAnimationObj(String name){
        ObjectMapper objectMapper = new ObjectMapper();
        com.Ling_eryao.obj.gameObj.motionAnimationObj.MotionAnimation motionAnimation = null;
        try {
            motionAnimation = objectMapper.readValue(new File(String.format("data/animationData/%s.json",name)), MotionAnimation.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return motionAnimation;
    }

    public void loadAnimation(String name){
        animationCount++;
        MotionAnimation motionAnimation = loadAnimationObj(name);
        this.animationDir = motionAnimation.animationDir;
        this.animationDis = motionAnimation.animationDis;
        this.animationTotalFrames = motionAnimation.animationTotalFrames;
        this.animationDoneFrames = 0;
        this.doingAnimation = true;
    }

    @Override
    public void paintSelf(Graphics g) {
        if (!del) {
            if (MainWin.currentLevel != this.level) {
                LevelScene.delObjList.add(this);
            }
            int blockSize; // 坐标修正后的格子长
            super.paintSelf(g);
            if (doingAnimation) {
                if (animationCount % 2 == 0) {
                    blockSize = 34;
                } else {
                    blockSize = 33;
                }
                if (animationDoneFrames <= animationTotalFrames) {
                    if (animationDir == 1) {
                        this.y -= (double) (blockSize * animationDis) / animationTotalFrames;
                    } else if (animationDir == 2) {
                        this.y += (double) (blockSize * animationDis) / animationTotalFrames;
                    } else if (animationDir == 3) {
                        this.x -= (double) (blockSize * animationDis) / animationTotalFrames;
                    } else if (animationDir == 4) {
                        this.x += (double) (blockSize * animationDis) / animationTotalFrames;
                    }
                    animationDoneFrames++;
                } else {
                    doingAnimation = false;
                }
            }
        }
    }
}
