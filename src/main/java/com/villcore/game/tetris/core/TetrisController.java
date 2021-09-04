package com.villcore.game.tetris.core;

import com.villcore.game.tetris.model.Block;
import com.villcore.game.tetris.model.DefaultBlockData;
import com.villcore.game.tetris.model.TetrisBundle;
import com.villcore.game.tetris.model.pieces.TetrisPiece;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.*;

@Slf4j
public class TetrisController {
    private TetrisBundle tetrisBundle;
    private ScheduledExecutorService controllerScheduler;

    public TetrisController(TetrisBundle tetrisBundle) {
        this.tetrisBundle = tetrisBundle;
        this.controllerScheduler = createScheduler();
    }

    private ScheduledExecutorService createScheduler() {
        return Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "controller");
            t.setDaemon(true);
            return t;
        });
    }

    public synchronized void handlerUserInputKeyEvent(KeyEvent event) {
        Block<DefaultBlockData>[] isolatedBlocks = tetrisBundle.getIsolatedBlocks();
        TetrisPiece tetrisPiece = tetrisBundle.getIsolatedPiece();
        int isolatedPieceRotateCount = tetrisBundle.getIsolatedPieceRotateCount();
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP: {
                log.info("Rotate");
                isolatedPieceRotateCount = isolatedPieceRotateCount + 1;
                int[][][] pieceBlocks = tetrisPiece.getRotateTetrisPieceBlocks();
                int[][] rotatePieceBlocks = pieceBlocks[isolatedPieceRotateCount % pieceBlocks.length];
                Block<DefaultBlockData>[] afterRotatePieceBlocks = new Block[4 * 4];
                for (int i = 0; i < rotatePieceBlocks.length; i++) {
                    for (int j = 0; j < rotatePieceBlocks[i].length; j++) {
                        Block<DefaultBlockData> block = new Block<>();
                        block.setX(isolatedBlocks[0].getX() + j);
                        block.setY(isolatedBlocks[0].getY() + i);
                        block.setEmpty(rotatePieceBlocks[i][j] == 0);
                        block.setDefaultBlockData(isolatedBlocks[0].getDefaultBlockData());
                        afterRotatePieceBlocks[i * 4 + j] = block;
                    }
                }
                tetrisBundle.setIsolatedBlocks(afterRotatePieceBlocks);
                tetrisBundle.setIsolatedPieceRotateCount(isolatedPieceRotateCount);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                log.info("Move right");
                for (Block<DefaultBlockData> block : isolatedBlocks) {
                    int x = block.getX();
                    block.setX(x + 1);
                    // TODO: check
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                // down
                log.info("Down");
                break;
            }

            case KeyEvent.VK_LEFT: {
                // move left
                log.info("Move left");
                for (Block<DefaultBlockData> block : isolatedBlocks) {
                    int x = block.getX();
                    block.setX(x - 1);
                    // TODO: check
                }
                break;
            }
        }
    }

    private void run() {
        try {
            doRun();
        } catch (Throwable e) {
            log.error("Do run controller error ", e);
        }
    }

    private synchronized void doRun() {
        if (tetrisBundle.isStop()) {
            return;
        }

        // tick and move cur piece
        tick();

        // try make next piece
        tryMakeNextPiece();

        // try remove line
        tryRemoveLine();

        // update score & level
    }

    private void tick() {
        Block<DefaultBlockData>[] isolatedBlocks = tetrisBundle.getIsolatedBlocks();
        if (isolatedBlocks == null || isolatedBlocks.length == 0) {
            return;
        }

        // move down
        boolean markImmutable = false;
        for (Block<DefaultBlockData> block : isolatedBlocks) {
            if (block.isEmpty()) {
                continue;
            }

            // touch boundary
            if (block.getY() >= tetrisBundle.getRowNums() - 1) {
                markImmutable = true;
                break;
            }

            // touch filled block
            if (tetrisBundle.getBlockPoints()[block.getX()][block.getY() + 1] == 1) {
                markImmutable = true;
                break;
            }
        }

        if (markImmutable) {
            for (Block<DefaultBlockData> block : isolatedBlocks) {
                if (!block.isEmpty()) {
                    // mark immutable
                    int[][] blockPoints = tetrisBundle.getBlockPoints();
                    Block<DefaultBlockData>[][] blocks = tetrisBundle.getBlocks();
                    blockPoints[block.getX()][block.getY()] = 1;
                    blocks[block.getX()][block.getY()] = block;
                }
            }

            tetrisBundle.setIsolatedPiece(null);
            tetrisBundle.setIsolatedBlocks(null);
            tetrisBundle.setNextPieceBlockPoints(null);
            tetrisBundle.setIsolatedPieceRotateCount(0);
            return;
        }

        for (Block<DefaultBlockData> block : isolatedBlocks) {
            block.setX(block.getX());
            block.setY(block.getY() + 1);
        }
    }

    private void tryMakeNextPiece() {
        Block<DefaultBlockData>[] isolatedBlocks = tetrisBundle.getIsolatedBlocks();
        int[][] nextPieceBlockPoints = tetrisBundle.getNextPieceBlockPoints();
        Block<DefaultBlockData>[] nextPieceBlocks = isolatedBlocks;
        TetrisPiece nextPiece = tetrisBundle.getNextPiece();
        if (isolatedBlocks == null || isolatedBlocks.length == 0) {
            nextPieceBlocks = new Block[4 * 4];
            for (int loop = 0; loop < 3; loop++) {
                if (nextPieceBlockPoints == null || nextPieceBlockPoints.length == 0) {
                    nextPiece = makeNextPiece();
                    tetrisBundle.setNextPiece(nextPiece);
                    int[][] pieceBlockPoints = rotatePiece(nextPiece);
                    tetrisBundle.setNextPieceBlockPoints(pieceBlockPoints);
                    Color color = makeColor();
                    for (int i = 0; i < pieceBlockPoints.length; i++) {
                        for (int j = 0; j < pieceBlockPoints[i].length; j++) {
                            Block<DefaultBlockData> block = new Block<DefaultBlockData>();
                            block.setX(j);
                            block.setY(i);
                            block.setEmpty(pieceBlockPoints[i][j] == 0);
                            DefaultBlockData defaultBlockData = new DefaultBlockData();
                            defaultBlockData.setColor(color);
                            block.setDefaultBlockData(defaultBlockData);
                            nextPieceBlocks[i * 4 + j] = block;
                        }
                    }
                    tetrisBundle.setNextPieceBlocks(nextPieceBlocks);
                    nextPieceBlockPoints = pieceBlockPoints;
                } else {
                    Block<DefaultBlockData>[] newIsolatedBlocks = new Block[4 * 4];
                    for (int i = 0; i < nextPieceBlockPoints.length; i++) {
                        for (int j = 0; j < nextPieceBlockPoints[i].length; j++) {
                            Block<DefaultBlockData> block = new Block<>();
                            block.setX(j);
                            block.setY(i);
                            block.setEmpty(nextPieceBlockPoints[i][j] == 0);
                            block.setDefaultBlockData(nextPieceBlocks[i * 4 + j].getDefaultBlockData());
                            newIsolatedBlocks[i * 4 + j] = block;
                        }
                    }
                    tetrisBundle.setIsolatedPiece(nextPiece);
                    tetrisBundle.setIsolatedBlocks(newIsolatedBlocks);
                    // tetrisBundle.setNextPieceBlockPoints(null);
                    tetrisBundle.setIsolatedPieceRotateCount(0);
                    nextPieceBlockPoints = null;
                }
            }
        }
    }

    private TetrisPiece makeNextPiece() {
        TetrisPiece[] tetrisPieces = TetrisPiece.values();
        int nextPieceIndex = ThreadLocalRandom.current().nextInt(tetrisPieces.length);
        return tetrisPieces[nextPieceIndex];
    }

    private Color makeColor() {
        Color[] colors = new Color[]{
                Color.YELLOW, Color.RED,
                Color.PINK, Color.ORANGE, Color.MAGENTA,
                Color.GREEN, Color.DARK_GRAY, Color.CYAN,
                Color.BLUE
        };
        int nextPieceIndex = ThreadLocalRandom.current().nextInt(colors.length);
        return colors[nextPieceIndex];
    }

    private int[][] rotatePiece(TetrisPiece piece) {
        int[][][] rotatePieceBlocks = piece.getRotateTetrisPieceBlocks();
        int nextRotate = ThreadLocalRandom.current().nextInt(rotatePieceBlocks.length);
        return rotatePieceBlocks[nextRotate];
    }

    private void tryRemoveLine() {
        int[][] blockPoints = tetrisBundle.getBlockPoints();
        Block<DefaultBlockData>[][] blocks = tetrisBundle.getBlocks();

        // 从低向上
        for (int i = 0; i < blocks[0].length; i++) {
            boolean removeLine = true;
            for (int j = 0; j < blocks.length; j++) {
                if (blockPoints[i][j] == 0) {
                    removeLine = false;
                }
            }
        }
    }

    public void start() {
        this.controllerScheduler.scheduleWithFixedDelay(this::run, 200L, 200L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (this.controllerScheduler != null) {
            this.controllerScheduler.shutdownNow();
        }
    }
}