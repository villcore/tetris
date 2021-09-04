package com.villcore.game.tetris.core;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InputEventUtils {

    private static final BlockingQueue<KeyEvent> keyEventQueue = new ArrayBlockingQueue<>(1000);

    public static KeyEvent pollKeyEvent() {
       return keyEventQueue.poll();
    }

    public static void addKeyEvent(KeyEvent keyEvent) {
        keyEventQueue.offer(keyEvent);
    }
}
