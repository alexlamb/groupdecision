package original;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmallWorldNetworkBuilder implements NetworkBuilder {

	//CONSTANTS
	public static final String NAME = "SmallWorld";

	//VARIABLES
	private int neighbors;
	private double beta;
	
	//CONSTRUCTOR
	public SmallWorldNetworkBuilder(double beta) {
		this.neighbors = 0;
		this.beta = beta;
	}
	
	public SmallWorldNetworkBuilder(int neighbors, double beta) {
		this.neighbors = neighbors;
		this.beta = beta;
	}

	//METHODS
	public String getName() {
		return NAME;
	}
	
	public void setMeanDegree(int neighbors) {
		this.neighbors = neighbors;
	}
	
	public void setMinDegree(int minDegree) {
		this.neighbors = minDegree * 2;
	}

	public void buildNetwork(Environment environment) {
		RandomList<Agent> agents = environment.getAgents();
		List<Agent> agentList = new ArrayList<Agent>(agents);
		
		//Generate a ring network
		for (int i = 0; i < agentList.size(); i++) {
			Agent agent = (Agent) agentList.get(i);
			
			int startPosition = -neighbors / 2;
			for (int j = 0; j < neighbors; j++) {
				if (i + startPosition + j != i) {
					int index = (i + startPosition + j + agentList.size()) % agentList.size();
					Agent neighborAgent = (Agent) agentList.get(index);
					agent.addNeighbor(neighborAgent);
					neighborAgent.addNeighbor(agent);
				}
			}
		}

		//Replace existing links at random
		for (Agent agent: agentList) {
			
			Set<Agent> neighborsCopy = new HashSet<Agent>(agent.getNeighbors());
			for (Agent neighbor: neighborsCopy) {
				
				//Only rewire links for which j > i
				if (agentList.indexOf(neighbor) > agentList.indexOf(agent)) {
					if (Math.random() < beta) {
						
						//If we decide to change a link, first we remove the existing one
						agent.removeNeighbor(neighbor);

						//Then we look for a new node to link to
						boolean linkFound = false;
						while (!linkFound) {
							Agent newNeighbor = agents.selectRandomElement();
							
							//We exclude the starting node and its existing neighbors as viable neighbors
							if (newNeighbor != agent && !agent.getNeighbors().contains(newNeighbor)) {
								agent.addNeighbor(newNeighbor);
								linkFound = true;
							}
						}
					}
				}
			}
		}
	}
}
