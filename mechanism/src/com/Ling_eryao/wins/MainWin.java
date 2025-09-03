package com.Ling_eryao.wins;

import com.Ling_eryao.scense.GameMenu;
import com.Ling_eryao.scense.LevelScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWin extends JFrame {

    public static int gameState = 0;
    // 0:初始界面
    // 1:设置
    // 2:关于
    // 3:开始游戏
    public static int currentLevel = 1; // 当前关卡
    public static int currentLevelObjNum = 0; // 当前创建的关卡对象的编号，用于判断是否重复创建
    Image offScreenImg = createImage(800, 600);
    GameMenu menu = new GameMenu(this);
    LevelScene levelScene = new LevelScene(1,this);
    Graphics gImage;


    public MainWin(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==32){
                    currentLevel++;
                }
            }
        });
    }


    public void launch() {
        this.setVisible(true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setTitle("Mechanism-Java");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            repaint();
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void prepareCanvas() {
        if (offScreenImg == null) {
            offScreenImg = createImage(800, 600);
        }
        gImage = offScreenImg.getGraphics();
        gImage.setColor(new Color(35, 35, 35));
        gImage.fillRect(0, 0, 800, 600);
    }

    @Override
    public void paint(Graphics g) {
        if (gameState == 0) {
            prepareCanvas();
            menu.paintScene(gImage);
        } else if (gameState == 1) {
            ;
        } else if (gameState == 2) {
            ;
        } else if (gameState == 3) {
            prepareCanvas();
            if(currentLevel!=currentLevelObjNum){
                currentLevelObjNum = currentLevel;
                levelScene.generateLevel(currentLevel);
//                System.out.println("NowLevel:");
//                System.out.println(currentLevel);
            }
            levelScene.paintScene(gImage);
        }

        //一次性绘制到屏幕
        g.drawImage(offScreenImg, 0, 0, null);
    }
}