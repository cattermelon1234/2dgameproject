package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler class, tracks keys input
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean doorPress;
    GamePanel gp;

    /**
     * KeyHandler constructor
     * @param gp GamePanel
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * if a key is typed
     * @param e a key event
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * adjusts boolean values to true according to what key(s) are being pressed
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        gp.handleKeyEvent(this);
    }

    /**
     * adjusts boolean values to false according to what keys are being released
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        gp.handleKeyEvent(this);
    }

    /**
     * checks to see if any key is being pressed
     * @return if the key is pressed or not
     */
    public boolean anyKeyPressed() {
        if (upPressed == true || leftPressed == true ||
                downPressed == true || rightPressed == true) {
            return true;
        }
        return false;
    }
}
