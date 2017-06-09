package dev.flash.cryptotrade;

import dev.flash.cryptotrade.input.KeyManager;
import dev.flash.cryptotrade.input.MouseManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Flash on 15/02/2017.
 */

public class Instance implements Runnable {
	
	//display handles JFrame
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	//Total ticks
	public static int tickCount;
	
	//Instance variables
	private int width, height;
	private String title;
	private boolean running;
	private Thread thread;
	private int fps;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Handler
	private Handler handler;
	
	private NodeManager nodeManager;
	private Camera camera;
	
	public Instance(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		handler = new Handler(this);
		
		//Create window
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		//Load the game's assets
		//Assets.init();
		
		handler.setCamera(camera = new Camera(0, 0, 600, 500));
		
		nodeManager = new NodeManager(handler);
		nodeManager.setNodes(NodeLoader.readTokenNodes("res/files/batToken.txt"));
		
		ArrayList<Node> buyNodes = new ArrayList<>();
		ArrayList<Node> sellNodes = new ArrayList<>();
		
		System.out.println("TESTING BUY NODES SIZE: " + buyNodes.size());
		
		for(Node node : nodeManager.getNodes()) {
			if(node.isBuy()) {
				buyNodes.add(node);
			} else {
				sellNodes.add(node);
			}
		}
		
		float totalBuyToken = 0;
		float totalSellToken = 0;
		float averageBuyTokenTotal = 0;
		float averageSellTokenTotal = 0;
		
		for(Node node : buyNodes) {
			totalBuyToken += node.getToken();
			averageBuyTokenTotal += node.getAverageTotal();
		}
		for(Node node : sellNodes) {
			totalSellToken += node.getToken();
			averageSellTokenTotal += node.getAverageTotal();
		}
		
		System.out.println("totalBuyToken: " + totalBuyToken + ", totalSellToken: " + totalSellToken);
		
		float averageBuy = averageBuyTokenTotal / totalBuyToken;
		float averageSell = averageSellTokenTotal / totalSellToken;
		
		
		System.out.println("averageBuyTokenTotal: " + averageBuyTokenTotal + ", averageSellTokenTotal: " + averageSellTokenTotal + ", averageBuy: " + averageBuy + ", averageSell: " + averageSell);
		
		System.out.println("averageBuy: " + averageBuy + ", averageSell: " + averageSell);
	}
	
	private void tick(double delta) {
		nodeManager.tick();
		handleScrolling(delta);
		
		float newdelta = (float) (delta / 1000 * 60);
		
		if(keyManager.left) {
			camera.setX(camera.getX() + 7 * newdelta);
		}
		if(keyManager.right) {
			camera.setX(camera.getX() - 7 * newdelta);
		}
		if(keyManager.up) {
			camera.setHigh(camera.getHigh() + 3.5f * newdelta);
			camera.setLow(camera.getLow() + 3.5f * newdelta);
		}
		if(keyManager.down) {
			camera.setHigh(camera.getHigh() - 3.5f * newdelta);
			camera.setLow(camera.getLow() - 3.5f * newdelta);
		}
	}
	
	private void handleScrolling(double delta) {
		int scrollAmount;
		
		if((scrollAmount = (int) mouseManager.getWheelRotation()) == 0)
			return;
		
		float mouseY = mouseManager.getMouseY();
		float height = handler.getHeight();
		float convertedMouseY = mouseY / height;
		float scale = (camera.getHigh() - camera.getLow()) / 30;
		
		camera.setHigh(camera.getHigh() + scale * convertedMouseY * scrollAmount);
		camera.setLow(camera.getLow() - scale * (1 - convertedMouseY) * scrollAmount);
		
		mouseManager.setWheelRotation(0);
	}
	
	private void render() {
		//Draw frames before displaying them
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(2);//amount of stored up frames ready before pushing to screen
			return;
		}
		g = bs.getDrawGraphics();
		
		//Clear Screen
		g.clearRect(0, 0, width, height);
		g.setColor(new Color(20, 20, 20));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		//Draw Here
		nodeManager.render(g);
		
		//End Draw
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		init();
		int targetFps = 32;
		double timePerTick = 1000000000 / targetFps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		long deltaTime;//better to have this be a long that converts to int or pass a long into tick()?
		long deltaLastTime = System.nanoTime();
		
		long deltaNow;
		
		while(running) {
			now = System.nanoTime();
			timer += now - lastTime;
			delta += (now - lastTime) / timePerTick;
			
			lastTime = now;
			
			if(delta >= 1) {//this should avoid lost or gained frames from speeding up or slowing down the game
				deltaNow = System.nanoTime();
				deltaTime = deltaNow - deltaLastTime;
				
				tick(deltaTime / 1000000);//converts nano to milli
				render();
				ticks++;
				delta--;
				tickCount++;
				deltaLastTime = deltaNow;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Ticks and Frames: " + ticks);
				fps = ticks;
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	//Creates the thread
	public synchronized void start() {
		if(running == true) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Stops the code cleanly
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Getters and Setters
	
	public int getFPS() {
		return fps;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}