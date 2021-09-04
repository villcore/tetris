package com.villcore.game.tetris.model.pieces;

import lombok.Data;

@Data
public class TetrisPieceBlock {

    private int x;
    private int y;
    private boolean fill;

    public static TetrisPieceBlock of(int x, int y, boolean fill) {
        TetrisPieceBlock tetrisPieceBlock = new TetrisPieceBlock();
        tetrisPieceBlock.x = x;
        tetrisPieceBlock.y = y;
        tetrisPieceBlock.fill = fill;
        return tetrisPieceBlock;
    }
}