package original;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScaleFreeNetworkBuilder implements NetworkBuilder {

	//CONSTANTS
	public static final String NAME = "ScaleFree";
	
	//VARIABLES
	private int neighborMin;
	
	//CONSTRUCTOR
	public ScaleFreeNetworkBuilder() {
		this.neighborMin = 0;
	}

	public ScaleFreeNetworkBuilder(int neighborMin) {
		this.neighborMin = neighborMin;
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
					Agent gossipCandidate = (Agent) candidate;
					for (int j = 0; j < gossipCandidate.getNeighbors().size(); j++) {
						selectionList.add(gossipCandidate);
					}
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
	}
}
