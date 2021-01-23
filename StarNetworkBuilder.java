package original;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class StarNetworkBuilder implements NetworkBuilder {

	//CONSTANTS
	public static final String NAME = "Star";
	
	//VARIABLES
	private int minDegree;
	
	//CONSTRUCTOR
	public StarNetworkBuilder() {
		this.minDegree = 0;
	}

	public StarNetworkBuilder(int minDegree) {
		this.minDegree = minDegree;
	}
	
	//METHODS
	public String getName() {
		return NAME;
	}
	
	public void setMinDegree(int minDegree) {
		this.minDegree = minDegree;
	}
	
	public void buildNetwork(Environment environment) {
		
		List<Agent> agents = environment.getAgents();

		RandomList<Agent> copy = new RandomList<Agent>(agents);
		Set<Agent> hubs = new HashSet<Agent>();
		for (int i = 0 ; i < minDegree; i++) {
			Agent hub = copy.takeRandomElement();
			hubs.add(hub);
		}
		
		for (Agent agent: copy) {
			for (Agent hub: hubs) {
				agent.addNeighbor(hub);
			}
		}
	}
}
