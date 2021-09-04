package com.villcore.game.tetris.model;

import lombok.Data;

@Data
public class Block<T> {

    private int x;
    private int y;
    private boolean empty;
    private DefaultBlockData defaultBlockData;
}
