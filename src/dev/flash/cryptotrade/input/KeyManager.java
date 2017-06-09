package dev.flash.cryptotrade.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;

    public boolean up, down, left, right, c, m, p, r, b, e, space;
	
    public KeyManager() {
        keys = new boolean[256];
    }

    public void updateKeys() {
        try {
            if(false) {
                //AZERTY KEYBOARD
                up = (keys[KeyEvent.VK_Z] || keys[KeyEvent.VK_UP]);
                down = (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]);
                left = (keys[KeyEvent.VK_Q] || keys[KeyEvent.VK_LEFT]);
                right = (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]);
            } else {
                //QWERTY KEYBOARD
                up = (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]);
                down = (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]);
                left = (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]);
                right = (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]);
            }
        } catch(NullPointerException e) {
            System.out.println("CAUGHT IT " + e.getStackTrace());
        }

        c = keys[KeyEvent.VK_C];
        m = keys[KeyEvent.VK_M];
        p = keys[KeyEvent.VK_P];
        r = keys[KeyEvent.VK_R];
        b = keys[KeyEvent.VK_B];
        e = keys[KeyEvent.VK_E];
        space = keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        updateKeys();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        updateKeys();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
