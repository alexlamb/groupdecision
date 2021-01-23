package original;

import java.util.ArrayList;
import java.util.List;


public class Agent {

	/* TODO
	 * What mechanism do we want for agents convincing each other?
	 * Decided agents should be less likely to change their minds.
	 * So we give each kind of agent a weight.
	 * If a random value crosses that threshold, then they're convinced. 
	 */
	
	//CONSTANTS
	public final static double UNDECIDED = 0.3;
	public final static double DECIDED = 0.7;
	
	public final static int NO_OPINION = 0;
	public final static int OPINION_A = 1;
	public final static int OPINION_B = 2;
	
	//VARIABLES
	private int opinion;
	private double confidence;
	private RandomList<Agent> neighbors;
	private int sampleCount;
	private List<Integer> samples;

	//CONSTRUCTOR
	public Agent() {
		this.opinion = NO_OPINION;
		this.confidence = UNDECIDED;
		neighbors = new RandomList<Agent>();
		samples = new ArrayList<Integer>();
	}

	//METHODS
	private int getConsensus() {
		int[] scores = new int[3];
		for (Integer sample: samples) {
			scores[sample]++;
		}
		int max = 0;
		int maxVal = 0;
		for (int i = 1; i < scores.length; i++) {
			if (scores[i] > maxVal) {
				max = i;
				maxVal = scores[i];
			}
		}
		return max;
	}
	
	public void addNeighbor(Agent agent) {
		if (neighbors.contains(agent)) {
			//We have this neighbor already
		} else {
			neighbors.add(agent);
			agent.addNeighbor(this);
		}
	}

	public void removeNeighbor(Agent agent) {
		if (neighbors.contains(agent)) {
			neighbors.remove(agent);
			agent.removeNeighbor(this);
		} else {
		}
	}

	public Agent getRandomNeighbor() {
		Agent result = null;
		if (neighbors.size() > 0) {
			result = neighbors.selectRandomElement();
//			System.out.println("XXNeighbors:"+neighbors.size());
		} else {
//			System.out.println("No neighbors");
		}
		return result;
	}
	
	public boolean hasNeighbor(Agent a) {
		return (neighbors.contains(a)); 
	}
	
	public boolean hasNeighbors(Agent a) {
		return (neighbors.size() > 0); 
	}
	
	
	public void addSample(int sample) {
		samples.add(sample);
		if (samples.size() >= sampleCount) {
			int consensus = getConsensus();
			if (consensus > 0 && Math.random() > confidence) {
				opinion = consensus;
			}
			samples.clear();
		}
	}
	
	//Getters and setters==========
	public int getOpinion() {
		return opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public RandomList<Agent> getNeighbors() {
		return neighbors;
	}


}
