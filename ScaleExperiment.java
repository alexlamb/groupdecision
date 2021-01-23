package original;


public class ScaleExperiment {

	//CONSTANTS
	public static final int A_COUNT = 6;
	public static final int B_COUNT = 4;
	public static final int SAMPLE_SIZE = 3;
	
	public static final int SANITY = 10000000;
//	public static final int RUNS = 100;
	public static final int RUNS = 500;
//	public static final int RUNS = 5000;

	public static final int COUNT_MIN = 10;
	public static final int COUNT_MULT = 2;
//	public static final int COUNT_MAX = 160;
	public static final int COUNT_MAX = 640;
//	public static final int COUNT_MAX = 1280;
//	public static final int COUNT_MAX = 2560;
	
//	public static final String FILE_IDENTIFIER = "Groupdec-";
	public static final String FILE_IDENTIFIER = "";
	public static final String FILE_SUFFIX = ".csv";

	
	//MAIN
	public static void main(String[] args) {
		
		for (int netType = 0; netType < 5; netType++) {
			
			NetworkBuilder builder = new RandomNetworkBuilder();
			if (netType == 1) builder = new ScaleFreeNetworkBuilder();
			if (netType == 2) builder = new JusticeNetworkBuilder(0.1);
			if (netType == 3) builder = new SmallWorldNetworkBuilder(0);
			if (netType == 4) builder = null;
			
			for (int biasType = 0; biasType < 2; biasType++) {
				boolean bias = false;
				if (biasType > 0) bias = true;

				String netName = "Well Mixed";
				if (builder != null) netName = builder.getName();
			    
			    String biasString = " natural";
			    if (bias) biasString = " biassed";

			    String resultFile = FILE_IDENTIFIER + netName + biasString + FILE_SUFFIX;
			    System.out.println("Building for "+resultFile);
			    FileGenerator ft = new FileGenerator(resultFile);

				for (int scale = COUNT_MIN; scale <= COUNT_MAX; scale = scale * COUNT_MULT) {

					int[] results = new int[3];
					for (int i = 0 ; i < RUNS; i++) {
						
						Environment env = new Environment();
						env.setAgentCount(scale);
						env.setOpinionACount(A_COUNT);
						env.setOpinionBCount(B_COUNT);
						env.setSampleSize(SAMPLE_SIZE);
						
						if (builder != null) builder.setMinDegree(3);
						env.setBuilder(builder);
						env.setBias(bias);
						
						env.init();

						int convergence = 0;
						int stepCount = 0;
						while (convergence == 0 && stepCount < SANITY) {
							env.advance();
							convergence = env.isConverged();
							stepCount++;
						}
//						System.out.println(""+stepCount+","+convergence);
						results[convergence]++;
					}
					
					double ratio = (double) results[1] / (double)(RUNS);
					ft.addLine(""+scale+","+ratio);

					System.out.println("RESULT:"+
							"Net:"+ netName+","+
							"Bias:"+ bias+","+
							"Size:"+ scale+","+
							results[1]+","+
							results[2]+","+
							ratio);
				}
				ft.finish();
			}
		}
	}

}
