package com.Ling_eryao.utils;

import java.awt.*;

public class GameUtils {

    public static Image loadImg(String filename) {
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    public static Image getScaledImage(Image img, int targetWidth, int targetHeight) {
        // 使用 Image.SCALE_SMOOTH 模式获得平滑的缩放效果（质量较高）
        return img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    }

    public static Image titleImg = getScaledImage(loadImg("assets/img/UI/Title.png"), (int) (646 * 0.5), (int) (306 * 0.5));
    public static Image startButtonImg_1 = getScaledImage(loadImg("assets/img/UI/button/StartButton-0.png"), (int) (164 * 0.85), (int) (88 * 0.85));
    public static Image startButtonImg_2 = getScaledImage(loadImg("assets/img/UI/button/StartButton-1.png"), (int) (164 * 0.85), (int) (88 * 0.85));

    public static Image commonTileImg = getScaledImage(loadImg("assets/img/game/tile/commonTile.png"), 35, 35);
    public static Image barrierTileImg = getScaledImage(loadImg("assets/img/game/tile/barrierTile.png"), 35, 35);
    public static Image destTileImg = getScaledImage(loadImg("assets/img/game/tile/destTile.png"), 35, 35);
    public static Image originTileImg = getScaledImage(loadImg("assets/img/game/tile/originTile.png"), 35, 35);
    public static Image darkShallowImg = getScaledImage(loadImg("assets/img/game/tile/darkShallow.png"), 35, 35);

    public static Image lightShallow = getScaledImage(GameUtils.loadImg("assets/img/game/tile/LightDarkShallow.png"), 35, 35);
    public static Image darkShallow = getScaledImage(GameUtils.loadImg("assets/img/game/tile/darkShallow.png"), 35, 35);

    public static Image playerImg = getScaledImage(GameUtils.loadImg("assets/img/game/player/player.png"), 20, 20);


}
