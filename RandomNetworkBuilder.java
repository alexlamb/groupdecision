package original;



public class RandomNetworkBuilder implements NetworkBuilder {

	//CONSTANTS
	public static final String NAME = "Random";
	
	//VARIABLES
	private int minDegree;
	
	//CONSTRUCTOR
	public RandomNetworkBuilder() {
		this.minDegree = 0;
	}

	public RandomNetworkBuilder(int minDegree) {
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
		
		RandomList<Agent> agents = environment.getAgents();
		
//		int agentCount = agents.size();
//		int maxLinks = (agentCount * meanDegree)/2;
//		int linkCount = 0;
//		Set<Agent> connectedNodes = new HashSet<Agent>();
//		while (linkCount < maxLinks || connectedNodes.size() < agentCount) {
//			Agent a = agents.selectRandomElement();
//			Agent b = agents.selectRandomElement();
//			if (a != b && !a.hasNeighbor(b) && !b.hasNeighbor(a)) {
//				a.addNeighbor(b);
//				linkCount++;
//				connectedNodes.add(a);
//				connectedNodes.add(b);
//			}
//		}
		for (Agent agent: agents) {
			
			while(agent.getNeighbors().size() < minDegree) {
				Agent b = agents.selectRandomElement();
				if (b != agent) {
					agent.addNeighbor(b);
				}
			}
		}
	}
}
