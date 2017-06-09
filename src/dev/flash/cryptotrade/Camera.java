package dev.flash.cryptotrade;

/**
 * Created by Flash on 16/02/2017.
 */

public class Camera {
	
	private float x, low, width, high;
	
	public Camera(float x, float low, float width, float high) {
		this.x = x;
		this.low = low;
		this.width = width;
		this.high = high;
	}
	
	
	//Getters and Setters
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getLow() {
		return low;
	}
	
	public void setLow(float low) {
		this.low = low;
	}
		
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHigh() {
		return high;
	}
	
	public void setHigh(float high) {
		this.high = high;
	}
}
