package com.villcore.game.tetris.gui;

import com.villcore.game.tetris.core.Render;
import com.villcore.game.tetris.core.TetrisController;
import com.villcore.game.tetris.model.Block;
import com.villcore.game.tetris.model.DefaultBlockData;
import com.villcore.game.tetris.model.TetrisBundle;
import com.villcore.game.tetris.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class MainFrame extends JFrame {

    public MainFrame(Dimension dimension) {
        this.setSize(dimension);
        this.setTitle("Tetris@github.com/villcore/tetris");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });
    }

    public static void main(String[] args) {

        // data
        TetrisBundle tetrisBundle = new TetrisBundle();
        tetrisBundle.setRowNums(18);
        tetrisBundle.setColNums(10);
        tetrisBundle.setBlockSize(50);
        tetrisBundle.setMainFrameX(5);
        tetrisBundle.setMainFrameY(5);
        tetrisBundle.setMainFrameWidth(800);
        tetrisBundle.setMainFrameHeight(910);
        tetrisBundle.setMainX(5);
        tetrisBundle.setMainY(5);
        tetrisBundle.setMainWidth(500);
        tetrisBundle.setMainHeight(910);
        tetrisBundle.setMainBgImg(ImageUtil.getBufferedImage("themes/theme-1/main_bg.jpg"));
        tetrisBundle.setBlockImg(ImageUtil.getBufferedImage("themes/theme-1/block_icon.png"));

        tetrisBundle.setNextPieceX(555);
        tetrisBundle.setNextPieceY(60);
        tetrisBundle.setNextPieceWidth(200);
        tetrisBundle.setNextPieceHeight(200);
        tetrisBundle.setInfoX(555);
        tetrisBundle.setInfoY(400);
        tetrisBundle.setInfoWidth(200);
        tetrisBundle.setInfoHeight(400);

        // controller make change
        AtomicReference<TetrisController> tetrisControllerRef = new AtomicReference<>();

        // render & draw panel
        Render render = new Render(16L, tetrisBundle);
        JPanel drawPanel = new JPanel(true) {
            @Override
            public void paint(Graphics g) {
                render.render((Graphics2D) g);
            }
        };
        render.setRenderRefreshHandler(new Render.RenderRefreshHandler() {
            @Override
            public void refresh() {
                drawPanel.repaint();
            }
        });

        // frame
        Dimension dimension = new Dimension(810, 950);
        MainFrame mainFrame = new MainFrame(dimension);
        mainFrame.add(drawPanel);
        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                TetrisController controller = tetrisControllerRef.get();
                if (controller != null) {
                    controller.handlerUserInputKeyEvent(e);
                }
            }
        });
        mainFrame.setVisible(true);
        render.start();

        int[][] blockPoints = new int[tetrisBundle.getColNums()][tetrisBundle.getRowNums()];
        tetrisBundle.setBlockPoints(blockPoints);
        Block<DefaultBlockData>[][] blocks = new Block[tetrisBundle.getColNums()][tetrisBundle.getRowNums()];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                Block<DefaultBlockData> block = new Block<>();
                block.setX(i);
                block.setY(j);
                block.setEmpty(true);
                blocks[i][j] = block;
            }
        }
        tetrisBundle.setBlocks(blocks);
        TetrisController controller = new TetrisController(tetrisBundle);
        controller.start();
        tetrisControllerRef.set(controller);
    }
}
