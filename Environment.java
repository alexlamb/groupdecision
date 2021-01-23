package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Environment {
	
	//VARIABLES
	private RandomList<Agent> agents;
	private int agentCount;
	private int opinionACount;
	private int opinionBCount;
	private int sampleSize;
	private boolean bias;
	private NetworkBuilder builder;
	

	//CONSTRUCTOR
	public Environment() {
		agents = new RandomList<Agent>();
	}
	
	//METHODS
	public void init() {
		for (int i = 0; i < agentCount; i++) {
			Agent agent = new Agent();
			agent.setSampleCount(sampleSize);
			agents.add(agent);
		}

		if (builder != null) {
			builder.buildNetwork(this);
		}

		if (bias) {
			List<Agent> sortedAgents = new ArrayList<Agent>();
			
			/* We sort the agents in terms of node degree
			 * Nodes with the most neighbors are allocated to group B
			 * Nodes with the least are allocated to A
			 */
			sortedAgents.addAll(agents);
			Collections.sort(sortedAgents, new DegreeComparator());
			
			for (int i = 0; i < opinionACount; i++) {
				Agent a = sortedAgents.get(i);
				a.setOpinion(Agent.OPINION_A);
				a.setConfidence(Agent.DECIDED);
			}

			for (int i = 0; i < opinionBCount; i++) {
				Agent b = sortedAgents.get(agents.size()-1-i);
				b.setOpinion(Agent.OPINION_B);
				b.setConfidence(Agent.DECIDED);
			}
			
		} else {
			Set<Agent> opinionAGroup = new HashSet<Agent>();
			for (int i = 0; i < opinionACount; i++) {
				Agent a = agents.takeRandomElement();
				a.setOpinion(Agent.OPINION_A);
				a.setConfidence(Agent.DECIDED);
				opinionAGroup.add(a);
			}
			
			Set<Agent> opinionBGroup = new HashSet<Agent>();
			for (int i = 0; i < opinionBCount; i++) {
				Agent b = agents.takeRandomElement();
				b.setOpinion(Agent.OPINION_B);
				b.setConfidence(Agent.DECIDED);
				opinionBGroup.add(b);
			}
			
			agents.addAll(opinionAGroup);
			agents.addAll(opinionBGroup);
		}
		
	}
	
	public void advance() {

		Agent a = agents.selectRandomElement();
		Agent b = a;

		if (builder == null) {			
			while(a == b) {
				b = agents.selectRandomElement();
			}			
		} else {
			b = a.getRandomNeighbor();
		}		
		
		int opinionA = a.getOpinion();
		int opinionB = b.getOpinion();
		a.addSample(opinionB);
		b.addSample(opinionA);
	}
	
	public String measureResults() {
		int[] results = new int[3];
		
		for (Agent a: agents) {
			results[a.getOpinion()]++;
		}

		String result = ""+results[0]+","+results[1]+","+results[2];
		return result;	
	}

	public int isConverged() {
		int[] results = new int[3];
		
		for (Agent a: agents) {
			results[a.getOpinion()]++;
		}
		
		int count = 0;
		int max = 0;
		int maxVal = 0;
		for (int i = 0; i < results.length; i++) {
			if (results[i] == 0) count++;
			else if (results[i] > maxVal) {
				max = i;
				maxVal = results[i];
			}
		}

		int result = 0;
		if (count == 2) {
			result = max;
		}
		return result;
	}

	//Getters and Setters===============
	public int getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}

	public int getOpinionACount() {
		return opinionACount;
	}

	public void setOpinionACount(int opinionACount) {
		this.opinionACount = opinionACount;
	}

	public int getOpinionBCount() {
		return opinionBCount;
	}

	public void setOpinionBCount(int opinionBCount) {
		this.opinionBCount = opinionBCount;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public RandomList<Agent> getAgents() {
		return agents;
	}

	public void setBuilder(NetworkBuilder builder) {
		this.builder = builder;
	}
	
	public boolean getBias() {
		return bias;
	}

	public void setBias(boolean bias) {
		this.bias = bias;
	}

	public class DegreeComparator implements Comparator<Agent> {

		public int compare(Agent a, Agent b) {
			return a.getNeighbors().size() - b.getNeighbors().size();
		}
		
	}
	
}
