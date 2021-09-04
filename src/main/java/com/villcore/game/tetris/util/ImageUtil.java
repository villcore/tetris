package com.villcore.game.tetris.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class ImageUtil {

    public static BufferedImage getBufferedImage(String path){
        try {
            File input = new File(path);
            return ImageIO.read(input);
        } catch (Exception e) {
            log.error("Get theme main bg error ", e);
        }
        return null;
    }
}
