package dev.flash.cryptotrade;

/**
 * Created by Flash on 15/02/2017.
 */

public class Node {
	
	private boolean buy;
	private float ether;
	private float token;
	private float ratio;
	
	
	public Node(boolean buy, float ether, float token) {
		this.buy = buy;
		this.ether = ether;
		this.token = token;
		ratio = token / ether;
	}
	
	public Node(float token, float ratio, boolean buy) {
		this.buy = buy;
		this.token = token;
		this.ether = token * ratio;
		this.ratio = ratio;
		System.out.println("buy: " + buy + ", token: " + token + ", ether: " + ether + ", ratio: " + ratio);
	}
	
	//Getters and Setters
	
	public boolean isBuy() {
		return buy;
	}
	
	public float getEther() {
		return ether;
	}
	
	public float getToken() {
		return token;
	}
	
	public float getRatio() {
		float newRatio = (int) (ratio*100000);
		return newRatio / 1;////100
	}
	
	public float getAverageTotal() {
		return getRatio() * token;
	}
}
