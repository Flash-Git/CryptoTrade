package dev.flash.cryptotrade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Flash on 15/02/2017.
 */

public class NodeLoader {
	
	public static ArrayList<Node> readNodes(String path) {
		ArrayList<Node> nodes = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				nodes.add(new Node(Utils.parseBool(tokens[0]), Utils.parseFloat(tokens[1]), Utils.parseFloat(tokens[2])));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	public static ArrayList<Node> readTokenNodes(String path) {
		ArrayList<Node> nodes = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				String[] infoTokens = line.split("\\s+");
				
				//System.out.println(infoTokens[0] + ", " + infoTokens[1] + ", " + infoTokens[2] + ", " + infoTokens[3] + ", " + infoTokens[4] + ", " + infoTokens[5] + ", " + infoTokens[6]);
				//infoTokens[0] = block number
				//infoTokens[1] = buy/sell
				//infoTokens[2] = token
				//infoTokens[3] = ether
				//infoTokens[4] = price
				
				nodes.add(new Node(Utils.parseFloat(infoTokens[2]), Utils.parseFloat(infoTokens[4]),Utils.parseBuySell(infoTokens[1])));
			}
			System.out.println("nodes.size is "+nodes.size());
			Collections.reverse(nodes);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
}
