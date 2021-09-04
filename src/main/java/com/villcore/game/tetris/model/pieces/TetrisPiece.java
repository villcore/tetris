package com.villcore.game.tetris.model.pieces;

import com.villcore.game.tetris.model.Point;

public enum TetrisPiece {

//    O(
//            new Point(2, 1),
//            new int[][]{
//                    new int[]{0, 0, 0, 0},
//                    new int[]{0, 1, 1, 0},
//                    new int[]{0, 1, 1, 0},
//                    new int[]{0, 0, 0, 0},
//            },
//            new int[][][]{
//                    new int[][]{
//                            new int[]{0, 0, 0, 0},
//                            new int[]{0, 1, 1, 0},
//                            new int[]{0, 1, 1, 0},
//                            new int[]{0, 0, 0, 0},
//                    }
//            }
//    ),

    I(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{1, 1, 1, 1},
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{1, 1, 1, 1},
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                    },
            }
    ),

    S(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 0, 1, 1},
                    new int[]{0, 1, 1, 0},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 1, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 0, 1},
                            new int[]{0, 0, 0, 0},
                    },
            }
    ),

    Z(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 1, 1, 0},
                    new int[]{0, 0, 1, 1},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 1, 1, 0},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 0, 1},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
            }
    ),

    L(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 1, 1, 1},
                    new int[]{0, 1, 0, 0},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 1, 0, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 0, 1},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 1, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
            }
    ),

    J(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 1, 1, 1},
                    new int[]{0, 0, 0, 1},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 0, 0, 1},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 1, 0, 0},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 1, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
            }
    ),

    T(
            new Point(2, 1),
            new int[][]{
                    new int[]{0, 0, 0, 0},
                    new int[]{0, 1, 1, 1},
                    new int[]{0, 0, 1, 0},
                    new int[]{0, 0, 0, 0},
            },
            new int[][][]{
                    new int[][]{
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 1, 1},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 1, 1, 1},
                            new int[]{0, 0, 0, 0},
                            new int[]{0, 0, 0, 0},
                    },
                    new int[][]{
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 1, 1, 0},
                            new int[]{0, 0, 1, 0},
                            new int[]{0, 0, 0, 0},
                    },
            }
    )

    ;

    private Point basePoint;
    private int[][] tetrisPieceBlocks;
    private int[][][] rotateTetrisPieceBlocks;

    TetrisPiece(Point basePoint, int[][] tetrisPieceBlocks, int[][][] rotateTetrisPieceBlocks) {
        this.basePoint = basePoint;
        this.tetrisPieceBlocks = tetrisPieceBlocks;
        this.rotateTetrisPieceBlocks = rotateTetrisPieceBlocks;
    }

    public int[][] rotate(int rotateIndex) {
        return rotateTetrisPieceBlocks[rotateIndex % rotateTetrisPieceBlocks.length];
    }

    public Point getBasePoint() {
        return basePoint;
    }

    public int[][] getTetrisPieceBlocks() {
        return tetrisPieceBlocks;
    }

    public int[][][] getRotateTetrisPieceBlocks() {
        return rotateTetrisPieceBlocks;
    }
}
