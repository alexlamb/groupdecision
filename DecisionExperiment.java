package original;

public class DecisionExperiment {

	//CONSTANTS
	public static final int AGENT_COUNT = 1000;
	public static final int A_COUNT = 6;
	public static final int B_COUNT = 4;
	public static final int SAMPLE_SIZE = 3;
	
	public static final int SANITY = 10000000;
	public static final int RUNS = 100;

	
	//MAIN
	public static void main(String[] args) {
		
		int[] results = new int[3];
		for (int i = 0 ; i < RUNS; i++) {
			
			Environment env = new Environment();
			env.setAgentCount(AGENT_COUNT);
			env.setOpinionACount(A_COUNT);
			env.setOpinionBCount(B_COUNT);
			env.setSampleSize(SAMPLE_SIZE);
			
//			NetworkBuilder builder = new RandomNetworkBuilder();
			NetworkBuilder builder = new ScaleFreeNetworkBuilder();
			builder.setMinDegree(3);
			env.setBuilder(builder);
//			env.setBias(true);
			env.setBias(false);
			
			env.init();

			int convergence = 0;
			int stepCount = 0;
//			System.out.println("Starting");
			while (convergence == 0 && stepCount < SANITY) {
				env.advance();
				convergence = env.isConverged();
				stepCount++;
			}
//			System.out.println(""+stepCount+","+convergence);
			results[convergence]++;
		}
		System.out.println("FINAL:"+results[1]+","+results[2]);
	}

}
