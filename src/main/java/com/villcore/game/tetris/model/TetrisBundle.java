package com.villcore.game.tetris.model;

import com.villcore.game.tetris.model.pieces.TetrisPiece;
import lombok.Data;

@Data
public class TetrisBundle {

    private int mainFrameX;
    private int mainFrameY;
    private int mainFrameWidth;
    private int mainFrameHeight;

    private int mainX;
    private int mainY;
    private int mainWidth;
    private int mainHeight;

    private int nextPieceX;
    private int nextPieceY;
    private int nextPieceWidth;
    private int nextPieceHeight;

    private int infoX;
    private int infoY;
    private int infoWidth;
    private int infoHeight;

    // 背景
    private int blockSize;
    private int rowNums;
    private int colNums;
    private int[][] blockPoints;
    private Block<DefaultBlockData>[][] blocks;

    // 右侧展示栏
    private int level;
    private int score;
    private TetrisPiece nextPiece;
    private int[][] nextPieceBlockPoints;
    private Block<DefaultBlockData>[] nextPieceBlocks;

    private TetrisPiece isolatedPiece;
    private int isolatedPieceRotateCount;
    private Block<DefaultBlockData>[] isolatedBlocks;

    private boolean stop;
}
