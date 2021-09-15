package com.villcore.game.tetris.core;

import com.villcore.game.tetris.model.Block;
import com.villcore.game.tetris.model.DefaultBlockData;
import com.villcore.game.tetris.model.TetrisBundle;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Render {

    private long refreshTimeMs;
    private TetrisBundle tetrisBundle;
    private RenderRefreshHandler renderRefreshHandler;
    private ScheduledExecutorService renderScheduler;

    public Render(long refreshTimeMs, TetrisBundle tetrisBundle) {
        this.refreshTimeMs = refreshTimeMs;
        this.renderScheduler = createScheduler();
        this.tetrisBundle = tetrisBundle;
    }

    private ScheduledExecutorService createScheduler() {
        return Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "render");
            t.setDaemon(false);
            return t;
        });
    }

    public void render(Graphics2D graphics) {
        if (tetrisBundle == null) {
            return;
        }

        graphics.setColor(Color.GRAY);
        graphics.draw3DRect(tetrisBundle.getMainFrameX(), tetrisBundle.getMainFrameY(), tetrisBundle.getMainFrameWidth(), tetrisBundle.getMainFrameHeight(), true);
        graphics.draw3DRect(tetrisBundle.getMainX(), tetrisBundle.getMainY(), tetrisBundle.getMainWidth(), tetrisBundle.getMainHeight(), true);
        graphics.draw3DRect(tetrisBundle.getNextPieceX(), tetrisBundle.getNextPieceY(), tetrisBundle.getNextPieceWidth(), tetrisBundle.getNextPieceHeight(), true);

        /*
        BufferedImage img = tetrisBundle.getMainBgImg();
        if (img != null) {
            graphics.drawImage(img, tetrisBundle.getMainX(), tetrisBundle.getMainY(), tetrisBundle.getMainWidth(), tetrisBundle.getMainHeight(), null);
        }
        */

        int blockSize = tetrisBundle.getBlockSize();
        for (int i = 0; i <= tetrisBundle.getMainWidth() / blockSize; i++) {
            graphics.drawLine(tetrisBundle.getMainX() + blockSize * i, tetrisBundle.getMainY(), tetrisBundle.getMainX() + blockSize * i, tetrisBundle.getMainHeight());
        }

        for (int i = 0; i <= tetrisBundle.getMainHeight() / blockSize; i++) {
            graphics.drawLine(tetrisBundle.getMainX(), tetrisBundle.getMainY() + blockSize * i, tetrisBundle.getMainX() + tetrisBundle.getMainWidth(), tetrisBundle.getMainY() + blockSize * i);
        }

        graphics.draw3DRect(tetrisBundle.getInfoX(), tetrisBundle.getInfoY(), tetrisBundle.getInfoWidth(), tetrisBundle.getInfoHeight(), true);

        // draw next piece block
        Block<DefaultBlockData>[] nextPieceBlocks = tetrisBundle.getNextPieceBlocks();
        if (nextPieceBlocks != null && nextPieceBlocks.length > 0) {
            for (Block<DefaultBlockData> block : nextPieceBlocks) {
                if (!block.isEmpty()) {
                    graphics.setColor(block.getDefaultBlockData().getColor());
                    graphics.fillRoundRect(
                            tetrisBundle.getNextPieceX() + block.getX() * tetrisBundle.getBlockSize(),
                            tetrisBundle.getNextPieceY() + block.getY() * tetrisBundle.getBlockSize(),
                            blockSize,
                            blockSize,
                            10,
                            10);
                }
            }
        }

        // draw isolated block
        Block<DefaultBlockData>[] isolatedBlocks = tetrisBundle.getIsolatedBlocks();
        if (isolatedBlocks != null && isolatedBlocks.length > 0) {
            for (Block<DefaultBlockData> block : isolatedBlocks) {
                graphics.setColor(block.getDefaultBlockData().getColor());
                if (!block.isEmpty()) {
                    graphics.fillRoundRect(
                            tetrisBundle.getMainX() + block.getX() * tetrisBundle.getBlockSize(),
                            tetrisBundle.getMainY() + block.getY() * tetrisBundle.getBlockSize(),
                            blockSize, blockSize, 10, 10);
                }
            }
        }

        // draw immutable background
        Block<DefaultBlockData>[][] blocks = tetrisBundle.getBlocks();
        if (blocks != null && blocks.length > 0) {
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    Block<DefaultBlockData> block = blocks[i][j];
                    if (!block.isEmpty()) {
                        graphics.setColor(block.getDefaultBlockData().getColor());
                        graphics.fillRoundRect(
                                tetrisBundle.getMainX() + block.getX() * tetrisBundle.getBlockSize(),
                                tetrisBundle.getMainY() + block.getY() * tetrisBundle.getBlockSize(),
                                blockSize, blockSize, 10, 10);

                        /*
                        BufferedImage blockIconImg = tetrisBundle.getBlockImg();
                        if (blockIconImg != null) {
                            graphics.drawImage(blockIconImg,
                                    tetrisBundle.getMainX() + block.getX() * tetrisBundle.getBlockSize(),
                                    tetrisBundle.getMainY() + block.getY() * tetrisBundle.getBlockSize(),
                                    blockSize, blockSize, null);
                        }
                        */
                    }
                }
            }
        }
    }

    public void setRenderRefreshHandler(RenderRefreshHandler renderRefreshHandler) {
        this.renderRefreshHandler = renderRefreshHandler;
    }

    public void start() {
        this.renderScheduler.scheduleAtFixedRate(() -> renderRefreshHandler.refresh(),
                refreshTimeMs, refreshTimeMs, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (renderScheduler != null) {
            renderScheduler.shutdownNow();
        }
    }

    public static abstract class RenderRefreshHandler {
        public abstract void refresh();
    }
}
