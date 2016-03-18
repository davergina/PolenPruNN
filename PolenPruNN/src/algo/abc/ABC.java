package algo.abc;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import algo.nn.NeuralNetwork;

import constants.InputHandler;
import controller.data.Data;


//Jakes ABC
@SuppressWarnings({ "unused" })
public class ABC {
	private int runtime = 0, maxCycle = 0, dimension = 0, employedBeeSize = 0,
			onlookerBeeSize = 0, scoutBeeSize = 0, param2change = 0,
			neighbour = 0, bestIndex = 0;

	private double GlobalMin = 0, lb = -1.0, ub = 1.0, ObjValSol = 0,
			FitnessSol = 0, meanRun = 0, bestMin = 1000;

	private double[] MSE, GlobalParams, GlobalMins, solution, fitness, prob,
			trial;

	private double[][] Foods, Params;

	private Random rand;

	private double[][] input_data;
	private double[][] output_data;

	private boolean pause = false, terminate = false;

	/* START added code */
	private double[][] initialSolutions;

	/* END added code */
	private File solutionDir;

	private boolean isAtMaxCycle;
	
	public ABC(int runtime, int maxCycle, int employedBeeSize,
			int onlookerBeeSize, int dimension) {
		this.runtime = runtime;
		this.maxCycle = maxCycle;
		this.dimension = dimension;
		this.employedBeeSize = employedBeeSize;
		this.onlookerBeeSize = onlookerBeeSize;
		scoutBeeSize = (int) Math.ceil(employedBeeSize * .10);
		solutionDir = new File(InputHandler.getInstance().getExperimentName()
				+ "/" + "solution");
		MSE = new double[employedBeeSize];
		solution = new double[dimension];
		GlobalParams = new double[dimension];
		GlobalMins = new double[runtime];
		fitness = new double[employedBeeSize];
		prob = new double[employedBeeSize];
		trial = new double[employedBeeSize];

		Foods = new double[employedBeeSize][dimension];
		Params = new double[runtime][dimension];

		rand = new Random();
	}

	/**
	 * trains the neural netwok
	 * 
	 * @param trainingData
	 */
	

//	public void train(double[][] input, double[][] output,
//			CustomProgressPane progressPane) {
//		this.progressPane = progressPane;
//		progressPane.reset(maxCycle);
//		train(input, output);
//	}

	private void trainingSetTEST(Data trainingData) {
		// TODO Auto-generated method stub
		int trainingSize = trainingData.size();
		for(int i = 0; i<trainingSize; i++){
			System.out.println("");
			System.out.print("Input(features): [");
			for(int j = 0; j<trainingData.getInputVector()[i].length; j++){
				System.out.print(""+trainingData.getInputVector()[i][j]+", ");
			}
			System.out.print("]");
			System.out.println("");
			System.out.print("Output: [");
			for(int j = 0; j<trainingData.getOutputVector()[i].length; j++){
				System.out.print(""+trainingData.getOutputVector()[i][j]+", ");
			}
			System.out.print("]");
			System.out.println("");
		}
		
	}
	
	public void train(Data trainingData){
		
		
		trainingSetTEST(trainingData);
		
		train(trainingData.getInputVector(), trainingData.getOutputVector());
	}
	
	public void train(double[][] input, double[][] output) {
		/*
		 * START remove unused code input_data = trainingData.getInputVector();
		 * output_data = trainingData.getOutputVector(); END remove unused code
		 */

		/* START added code */
		input_data = input;
		output_data = output;
		/* END added code */
		/* Removed Prunning Solution Directory */
		for (int run = 0; run < runtime; run++) {
			
			/* Removed Prunning Solution Directory */
//			progressPane.reset(maxCycle);
//			progressPane.setCurrentProcess("ABC Training");
			if (pause) {
				continue;
			}
			if (terminate) {
				break;
			}
			double start = System.currentTimeMillis();

			/* START added code */
			// if (InputHandler.getInstance().isHybrid()) {
			// initialSolutions = InitialSolutionsGenerator
			// .getInitialSolutions(employedBeeSize);
			// Debugger.println("ABC line 131: Hybrid with ABC");
			// }
			// /* END added code */

			initializePopulation();
			memorizeBestSource();

			for (int cycle = 0; cycle < maxCycle;) {
				
				/*added code for debuggung*/
				if(cycle==maxCycle-1){
					isAtMaxCycle=true;
				}
				
				sendEmployedBees();
				calculateProbabilities();
				sendOnlookerBees();
				memorizeBestSource();
				sendScoutBees();
				/*
				 * START remove unused code
				 * ExperimentPane.getInstance().incrementCycle(cycle+1); END
				 * remove unused code
				 */

				/* START added code */
				showCycleResults(cycle, GlobalParams, GlobalMin);
				/* END added code */

				cycle++;
//				progressPane.incrementBar();
			}
			double elapsedTime = (System.currentTimeMillis() - start) / 1000;

			/*
			 * START remove unused code
			 * ExperimentPane.getInstance().incrementRuntime(run+1); END remove
			 * unused code
			 */
			GlobalMins[run] = GlobalMin;
			meanRun += GlobalMin;

			Params[run] = GlobalParams;
			if (GlobalMin <= bestMin) {
				bestMin = GlobalMin;
				bestIndex = run;
			}

			/* START added code */
			showRunResults(run, elapsedTime);
//			write.writeWeights(getSolution());

			NeuralNetwork network = new NeuralNetwork();
//			network.setDebugMode(true);
			network.buildNetwork(getSolution(), InputHandler.getInstance(),
					input_data, output_data);
			double trainingPercentage = network.getPercentageCorrect();
			double trainingMSE = network.getAverageMSE();
			
			
//			network.buildNetwork(getSolution(), InputHandler.getInstance(),
//					InputHandler.getInstance().getTestInput(), InputHandler
//							.getInstance().getTestOutput());

			System.out.println("");
			System.out.println("Training Percentage: "+trainingPercentage+", TrainingMSE: "+trainingMSE);
			
			/*Removed Prunning Codes */

			/* END added code */

			/*
			 * START remove unused code
			 * ExperimentPane.getInstance().returnResult(GlobalMin,
			 * Params[bestIndex], elapsedTime, run+1); END remove unused code
			 */
			
			
			
			System.out.println("Best Min:"+bestMin+", Time: "+elapsedTime+", Run: "+run+1);
		
			
		
		}
		/*
		 * START remove unused code ExperimentPane.getInstance().resetButtons();
		 * if(terminate) new
		 * MessageDialog("The optimization has been stopped.").setVisible(true);
		 * else new MessageDialog("Done optimizing.").setVisible(true); END
		 * remove unused code
		 */

		/* START added code */
		
		/* Removed Prunning Codes */

		/* END added code */
	}

	/**
	 * initialize all food sources / networks
	 */
	private void initializePopulation() {
		
		for (int i = 0; i < employedBeeSize; i++)
			initializeEachFood(i);
		GlobalMin = MSE[0];
		for (int j = 0; j < dimension; j++)
			GlobalParams[j] = Foods[0][j];
	}

	private void initializeEachFood(int index) {
		double r;
		for (int j = 0; j < dimension; j++) {

			/* START added code */
			r = (Math.random() * 32767 / ((double) 32767 + (double) (1)));
			Foods[index][j] = r * (ub - lb) + lb;
			/* END added code */

			solution[j] = Foods[index][j];
		}
		/*
		 * START remove unused code networks[index] = new MLPNetwork(solution,
		 * input_data, output_data); MSE[index] =
		 * calculateObjectiveFunction(networks[index]); END remove unused code
		 */

		/* START added code */
		NeuralNetwork nn = new NeuralNetwork();
		
//		if(index==0){
//			nn.setDebugMode(true);
//		}
		
		nn.reset(new ArrayList<Integer>(), new ArrayList<Integer>());
		nn.buildNetwork(solution, InputHandler.getInstance(), input_data,
				output_data);
		
		
		MSE[index] = nn.getAverageMSE();
		/* END added code */

		fitness[index] = calculateFitness(MSE[index]);
		trial[index] = 0;

	}

	private void memorizeBestSource() {
		for (int i = 0; i < employedBeeSize; i++)
			if (MSE[i] < GlobalMin) {
				GlobalMin = MSE[i];
				for (int j = 0; j < dimension; j++)
					GlobalParams[j] = Foods[i][j];
			}
	}

	private void sendEmployedBees() {
		for (int i = 0; i < employedBeeSize; i++) {
			neighborhoodSearch(i);
			evaluatePopulation();
			greedySelection(i);
		}
	}

	private void sendOnlookerBees() {
		for (int i = 0; i < employedBeeSize; i++) {
			double r = (Math.random() * 32767 / ((double) (32767) + (double) (1)));
			if (r < prob[i]) {
				int maxOnlooker = (int) (prob[i] * onlookerBeeSize);

				for (int j = 0; j < maxOnlooker; j++) {
					neighborhoodSearch(i);
					evaluatePopulation();
					greedySelection(i);
				}
			}
		}
	}

	private void sendScoutBees() {
		int[] sortedIndices = sortByFitness();
		for (int i = 0; i < scoutBeeSize; i++) {
			neighborhoodSearch(sortedIndices[i]);
			evaluatePopulation();
			greedySelection(sortedIndices[i]);
		}
	}

	private void neighborhoodSearch(int foodIndex) {
		double r = (Math.random() * 32767 / ((double) (32767) + (double) (1)));
		param2change = (int) (r * dimension);

		neighbour = rand.nextInt(employedBeeSize);
		while (neighbour == foodIndex)
			neighbour = rand.nextInt(employedBeeSize);

		for (int j = 0; j < dimension; j++)
			solution[j] = Foods[foodIndex][j];

		/* v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
		solution[param2change] = Foods[foodIndex][param2change]
				+ (Foods[foodIndex][param2change] - Foods[neighbour][param2change])
				* (r - 0.5) * 2;
	}

	/**
	 * evaluates current and new solution
	 */
	private void evaluatePopulation() {
		/*
		 * START remove unused code MLPNetwork temp = new MLPNetwork(solution,
		 * input_data, output_data); ObjValSol =
		 * calculateObjectiveFunction(temp); END remove unused code
		 */
		/* START added code */
		NeuralNetwork nn = new NeuralNetwork();
		
		if(isAtMaxCycle){
			nn.setDebugMode(true);
		}
		nn.reset(new ArrayList<Integer>(), new ArrayList<Integer>());
		nn.buildNetwork(solution, InputHandler.getInstance(), input_data,
				output_data);
		
		
		if(isAtMaxCycle){
			
		}
		
		
		ObjValSol = nn.getAverageMSE();
		/* END added code */
		FitnessSol = calculateFitness(ObjValSol);
	}

	private void greedySelection(int foodIndex) {
		if (FitnessSol >= fitness[foodIndex]) {
			trial[foodIndex] = 0;
			for (int j = 0; j < dimension; j++)
				Foods[foodIndex][j] = solution[j];

			MSE[foodIndex] = ObjValSol;
			fitness[foodIndex] = FitnessSol;
		} else
			trial[foodIndex] = trial[foodIndex] + 1;
	}

	private void calculateProbabilities() {
		double maxfit = fitness[0];
		for (int i = 1; i < employedBeeSize; i++) {
			if (fitness[i] > maxfit)
				maxfit = fitness[i];
		}

		for (int i = 0; i < employedBeeSize; i++)
			prob[i] = (0.9 * (fitness[i] / maxfit)) + 0.1;

	}

	private double calculateFitness(double fun) {
		double result = 0;
		if (fun >= 0)
			result = 1 / (fun + 1);
		else
			result = 1 + Math.abs(fun);
		return result;
	}

	/**
	 * 
	 * @return the indices of the food sources sorted by fitness ASCENDING
	 */
	private int[] sortByFitness() {
		int[] indices = new int[employedBeeSize];
		for (int i = 0; i < employedBeeSize; i++)
			indices[i] = i;

		int tempIndex;
		double[] fitnessClone = fitness.clone();
		double tempFitness;

		for (int i = 0; i < employedBeeSize - 1; i++) {
			for (int j = 0; j < employedBeeSize - 1; j++) {
				if (fitnessClone[j] > fitnessClone[j + 1]) {
					tempFitness = fitnessClone[j];
					tempIndex = indices[j];
					fitnessClone[j] = fitnessClone[j + 1];
					indices[j] = indices[j + 1];
					fitnessClone[j + 1] = tempFitness;
					indices[j + 1] = tempIndex;
				}
			}
		}
		return indices;
	}

	public double[] getSolution() {
		return Params[bestIndex];
	}

	public void pause() {
		pause = true;
	}

	public void resume() {
		pause = false;
	}

	public void terminate() {
		terminate = true;
	}

	private void showCycleResults(int cycle, double[] solutionOfThisCycle,
			double mse) {
//		TrainingController.showCycleResults(cycle, solutionOfThisCycle, mse);
	}

	private void showRunResults(int run, double timeElapsed) {
//		TrainingController.showRunResults(run, timeElapsed);
	}

	private void showOverallResults() {
//		TrainingController.showOverallResults();
	}
}
