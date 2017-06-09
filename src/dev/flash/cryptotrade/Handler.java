package dev.flash.cryptotrade;

/**
 * Created by Flash on 15/02/2017.
 */

public class Handler {
	public Instance instance;
	public Camera camera;
	
	public Handler(Instance instance) {
		this.instance = instance;
	}
	
	public int getHeight(){
		return instance.getHeight();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
