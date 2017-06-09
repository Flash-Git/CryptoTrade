package dev.flash.cryptotrade;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Flash on 15/02/2017.
 */

public class NodeManager {
	
	private Handler handler;
	private ArrayList<Node> nodes = new ArrayList<>();
	private Camera camera;
	
	public NodeManager(Handler handler) {
		this.handler = handler;
		this.camera = handler.getCamera();
	}
	
	public void addNode(Node newNode) {
		nodes.add(newNode);
	}
	
	public void removeNode(Node newNode) {
		for(Node node : nodes) {
			if(node.equals(newNode)) {
				nodes.remove(node);
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		float x = 15;
		
		int lines = 25;
		
		float low = camera.getLow();
		float high = camera.getHigh();
		float spread = high - low;
		
		int lowP = 15;
		int highP = handler.getHeight() - 30;
		float spreadP = highP - lowP;
		
		g.drawLine(15, lowP, 15, highP);
		
		for(int i = 0; i < lines + 1; i++) {
			float pY = i * handler.getHeight() / lines;//Pixel Coords height -> 0
			
			float nY = handler.getHeight() - pY;//Node Coords 0 -> heights
			nY = nY * (high - low) / handler.getHeight() + low;//Node Coords low -> high
			
			pY = pY * (highP - lowP) / handler.getHeight() + lowP;//Pixel Coords set to graph
			
			//Capping digits
			float cappednY = (int) (nY * 100);
			cappednY = cappednY / 100;
			
			g.drawString(cappednY + "", (int) x + 2, (int) pY + 5);
			g.drawLine((int) x - 2, (int) pY, (int) x + 2, (int) pY);
		}
		
		x = camera.getX();
		
		for(Node node : nodes) {
			x += 50;
			
			//out of bounds
			if(x < 50||x > handler.instance.getWidth() - 50) {
				continue;
			}
			
			float nY = handler.getHeight() - (node.getRatio() - low) / spread * handler.getHeight();//Ratio from low -> high
			int pY = (int) (nY * spreadP / handler.getHeight() + lowP);//Ratio set to graph
			
			//out of bounds
			if(pY < 50||pY > handler.instance.getHeight() - 50) {
				continue;
			}
			
			if(node.isBuy()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			
			g.fillRect((int) x + 30, pY - 2, 4, 4);
			//g.drawLine(0, pY, (int)x + 30, pY);
			g.drawString(node.getRatio() + "", (int) x + 13, pY - 5);
			g.drawString(node.getToken() + "", (int) x + 15, pY + 17);
		}
	}
	
	//Getters and Setters
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
}
