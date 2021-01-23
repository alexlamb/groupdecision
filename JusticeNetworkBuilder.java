package original;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JusticeNetworkBuilder implements NetworkBuilder {

	//CONSTANTS
	public static final String NAME = "Justice";
	
	//VARIABLES
	private int neighborMin;
//	private double beta;
	
	//CONSTRUCTOR
	public JusticeNetworkBuilder(double beta) {
		this.neighborMin = 0;
//		this.beta = beta;
	}

	public JusticeNetworkBuilder(int neighborMin, double beta) {
		this.neighborMin = neighborMin;
//		this.beta = beta;
	}
	
	//METHODS
	public String getName() {
		return NAME;
	}
	
	public void setMinDegree(int minDegree) {
		this.neighborMin = minDegree;
	}
	
	public void buildNetwork(Environment environment) {
		RandomList<Agent> agents = environment.getAgents();
		Set<Agent> joinedSet = new HashSet<Agent>();

		//Generate a starting pair
		//The BA scale free algorithm only works if every node already has m neighbors
        int count = 0;
        for (Agent agent: agents) {
        	
        	if (count < neighborMin+1) {
        		for (Agent other: joinedSet) {
        			Agent otherAgent = (Agent) other;
        			agent.addNeighbor(otherAgent);
        		}
        		joinedSet.add(agent);
        		count++;
        		
        	} else {
		
				//Reinterpreted Barabasi-Albert algorithm
				List<Agent> selectionList = new ArrayList<Agent>();
				for (Agent candidate: joinedSet) {
					for (int j = 0; j < candidate.getNeighbors().size(); j++) {
						selectionList.add(candidate);
					}
				}

				//RANDOM NEIGHBOR
				{
					Agent neighbor = agent;
					while (neighbor == agent) {
						neighbor = agents.selectRandomElement();
					}
					agent.addNeighbor(neighbor);
				}
				
				//Perform scale free linking
            	while (agent.getNeighbors().size() < neighborMin) {
		
					int index = (int)(Math.random() * (double)(selectionList.size()));
					Agent neighbor = selectionList.get(index);
					
					agent.addNeighbor(neighbor);
					joinedSet.add(agent);
				}
        	}
        }
        
		//Replace existing links at random
//		for (Agent agent: agents) {
//			
//			Set<Agent> neighborsCopy = new HashSet<Agent>(agent.getNeighbors());
//			for (Agent neighbor: neighborsCopy) {
//				
//				//Only rewire links for which j > i
//				if (agents.indexOf(neighbor) > agents.indexOf(agent)) {
//					if (Math.random() < beta) {
//						
//						//If we decide to change a link, first we remove the existing one
//						agent.removeNeighbor(neighbor);
//
//						//Then we look for a new node to link to
//						boolean linkFound = false;
//						while (!linkFound) {
//							Agent newNeighbor = agents.selectRandomElement();
//							
//							//We exclude the starting node and its existing neighbors as viable neighbors
//							if (newNeighbor != agent && !agent.getNeighbors().contains(newNeighbor)) {
//								agent.addNeighbor(newNeighbor);
//								linkFound = true;
//							}
//						}
//					}
//				}
//			}
//		}
        
		//Replace one link for each agent
//		for (Agent agent: agents) {
//			
//			RandomList<Agent> neighborsCopy = new RandomList<Agent>(agent.getNeighbors());
//
//			int max = 0;
//			Agent maxAgent = null;
//			for (Agent neighbor: neighborsCopy) {
//				if (neighbor.getNeighbors().size() > max) {
//					maxAgent= neighbor;
//					max = neighbor.getNeighbors().size();
//				}
//			}			
//			agent.removeNeighbor(maxAgent);
//
//			
//			
//			//Then we look for a new node to link to
//			boolean linkFound = false;
//			while (!linkFound) {
//				Agent newNeighbor = agents.selectRandomElement();
//				
//				//We exclude the starting node and its existing neighbors as viable neighbors
//				if (newNeighbor != agent && !agent.getNeighbors().contains(newNeighbor)) {
//					agent.addNeighbor(newNeighbor);
//					linkFound = true;
//				}
//			}
//		}
	}
}
