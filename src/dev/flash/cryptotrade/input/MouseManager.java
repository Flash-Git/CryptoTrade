package dev.flash.cryptotrade.input;

import java.awt.event.*;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY;
	private float wheelRotation;
	
    public MouseManager() {
    	
    }

    // Implemented methods
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelRotation += e.getWheelRotation();
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;

        } else if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseMoved(e);
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
        mouseMoved(e);//refresh mouse location on uiManager switch
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    // Getters
    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
	
	public float getWheelRotation() {
		return wheelRotation;
	}
	
	public void setWheelRotation(float wheelRotation) {
		this.wheelRotation = wheelRotation;
	}
}
