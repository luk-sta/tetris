package cz.hyperion.view;

import cz.hyperion.KeyStrokes;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    private final KeyStrokes keyStrokes;

    public KeyListener(KeyStrokes keyStrokes) {
        this.keyStrokes = keyStrokes;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keyStrokes.keyUp();
                break;
            case KeyEvent.VK_DOWN:
                keyStrokes.keyDown();
                break;
            case KeyEvent.VK_RIGHT:
                keyStrokes.keyRight();
                break;
            case KeyEvent.VK_LEFT:
                keyStrokes.keyLeft();
                break;
            case KeyEvent.VK_SPACE:
                keyStrokes.keySpaceOn();
                break;
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                keyStrokes.keyQuit();
                break;
            case KeyEvent.VK_P:
                keyStrokes.keyPauseResume();
                break;
            case KeyEvent.VK_N:
                keyStrokes.keyNew();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                keyStrokes.keySpaceOff();
                break;
        }
    }
}
