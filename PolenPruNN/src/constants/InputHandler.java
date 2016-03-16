/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constants;

/* START remove unused code
 import csonn.CSO.Cat_Main;
 END remove unused code */
import helper.debugger.Debugger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import algo.abc.ABC;

/**
 * 
 * @author jonnah
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class InputHandler {

	// Parameter setting:

	private static InputHandler instance = null;
	private String experimentType = "";

	public String getExperimentType() {
		return experimentType;
	}

	public void setExperimentType(String experimentType) {
		this.experimentType = experimentType;
	}

	/* END added code */
	private boolean applyOCD;
	private boolean applyOBD;

	/**
	 * @return the applyOBD
	 */
	public boolean isApplyOBD() {
		return applyOBD;
	}

	/**
	 * @param applyOBD
	 *            the applyOBD to set
	 */
	public void setApplyOBD(boolean applyOBD) {
		this.applyOBD = applyOBD;
	}

	/**
	 * @return the applyOBS
	 */
	public boolean isApplyOBS() {
		return applyOBS;
	}

	/**
	 * @param applyOBS
	 *            the applyOBS to set
	 */
	public void setApplyOBS(boolean applyOBS) {
		this.applyOBS = applyOBS;
	}

	/**
	 * @return the applyMBP
	 */
	public boolean isApplyMBP() {
		return applyMBP;
	}

	/**
	 * @param applyMBP
	 *            the applyMBP to set
	 */
	public void setApplyMBP(boolean applyMBP) {
		this.applyMBP = applyMBP;
	}

	/**
	 * @return the randomizePerIteration
	 */
	public boolean isRandomizePerIteration() {
		return randomizePerIteration;
	}

	/**
	 * @param randomizePerIteration
	 *            the randomizePerIteration to set
	 */
	public void setRandomizePerIteration(boolean randomizePerIteration) {
		this.randomizePerIteration = randomizePerIteration;
	}

	private boolean applyOBS;
	private boolean applyMBP;
	private boolean randomizePerIteration;

	public int dimension = 0;
	public int numSamples = 0;
	public int numInput = 0;
	public int numOutput = 0;
	public int numHidden = 0;
	private String filename = "";
	private double treshold = 0.1;
	private double ESInterval = 0;
	private double regularizationDelta = 0;
	private double validationPercent = 0;
	private boolean isHyperbolic = false;
	private boolean isEarlyStoppingActivated = false;
	private boolean isBreakableAtESPoint = false;
	private boolean isRegularizationModeActive = false;
	private String pruningAllowed = "NONE";
	private int quantityBasedPruning = -1; // -1 not set
	private double[][] trainingDataInput = null;
	private double[][] trainingDataOutput = null;
	private double[][] trainingDataValidationInput = null;
	private double[][] trainingDataValidationOutput = null;
	private double[][] testInput = null;
	private double[][] testOutput = null;
	private double[][] readInput = null;
	private double[][] readOutput = null;
	/* Parameter for CSO algorithm and NN- End */

	private String outputTestDataFileName;
	private String inputTestDataFileName;
	private String outputTrainingDataFileName;
	private String inputTrainingDataFileName;
	private String experimentName;

	private java.util.List<Integer> tempList = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());
	private java.util.List<Integer> outputList = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());

	public static InputHandler getInstance() {
		return (instance == null ? instance = new InputHandler() : instance);
	}

	public void setNumSamples(int samples) {
		this.numSamples = samples;
	}

	public void setNumInput(int numInput) {
		this.numInput = numInput;
	}

	public void setNumOutput(int numOutput) {
		this.numOutput = numOutput;
	}

	public void setNumHidden(int numHidden) {
		this.numHidden = numHidden;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public void setValidationPercent(int percent) {
		this.validationPercent = ((double) percent) / 100;
	}

	public void setTestDataInput(double[][] testInput) {
		this.testInput = null;
		this.testInput = new double[testInput.length][testInput[0].length];
		copy(this.testInput, testInput);
	}

	public void setTestDataOutput(double[][] testOutput) {
		this.testOutput = null;
		this.testOutput = new double[testOutput.length][testOutput[0].length];
		copy(this.testOutput, testOutput);
	}

	public void setTrainingDataInput(double[][] trainingDataInput) {
		this.trainingDataInput = null;
		this.trainingDataInput = new double[trainingDataInput.length][trainingDataInput[0].length];
		copy(this.trainingDataInput, trainingDataInput);
	}

	public void setTrainingDataOutput(double[][] trainingDataOutput) {
		this.trainingDataOutput = null;
		this.trainingDataOutput = new double[trainingDataOutput.length][trainingDataOutput[0].length];
		copy(this.trainingDataOutput, trainingDataOutput);
	}

	public void setTrainingDataValidationInput(
			double[][] trainingDataValidationInput) {
		this.trainingDataValidationInput = null;
		this.trainingDataValidationInput = new double[trainingDataValidationInput.length][trainingDataValidationInput[0].length];
		copy(this.trainingDataValidationInput, trainingDataValidationInput);
	}

	public void setTrainingDataValidationOutput(
			double[][] trainingDataValidationOutput) {
		this.trainingDataValidationOutput = null;
		this.trainingDataValidationOutput = new double[trainingDataValidationOutput.length][trainingDataValidationOutput[0].length];
		copy(this.trainingDataValidationOutput, trainingDataValidationOutput);
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public void setRegularizationDelta(double delta) {
		this.regularizationDelta = delta;
	}

	public void setActivationFunctionMode(boolean isHyperbolic) {
		this.isHyperbolic = isHyperbolic;
	}

	public void setRegularizationMode(boolean regularization) {
		this.isRegularizationModeActive = regularization;
	}

	public void setPruningMode(String pruning) {
		this.pruningAllowed = pruning;
	}

	public void setEarlyStoppingActivation(boolean isEarlyStoppingActivated) {
		this.isEarlyStoppingActivated = isEarlyStoppingActivated;
	}

	public void setBreakAtEarlyStoppingPoint(boolean isBreakableAtESPoint) {
		this.isBreakableAtESPoint = isBreakableAtESPoint;
	}

	public void setESInterval(double interval) {
		this.ESInterval = interval;
	}

	public void Treshold(double delta) {
		this.treshold = delta;
	}

	public void setQuantityBasedPruning(int q) {
		this.quantityBasedPruning = q;
	}

	public double getTreshold() {
		return treshold;
	}

	public int getQuantityBasedPruning() {
		return quantityBasedPruning;
	}

	public double getESInterval() {
		return ESInterval;
	}

	public String getPruningMode() {
		return pruningAllowed;
	}

	public boolean isEarlyStoppingActivated() {
		return isEarlyStoppingActivated;
	}

	public boolean breakAtEarlyStoppingPoint() {
		return isBreakableAtESPoint;
	}

	public boolean getActivationFunctionMode() {
		return isHyperbolic;
	}

	public boolean getRegularizationMode() {
		return isRegularizationModeActive;
	}

	public double[][] getTrainingDataValidationInput() {
		return trainingDataValidationInput;
	}

	public double[][] getTrainingDataValidationOutput() {
		return trainingDataValidationOutput;
	}

	public double[][] getTrainingDataInput() {
		return trainingDataInput;
	}

	public double[][] getTrainingDataOutput() {
		return trainingDataOutput;
	}

	public double[][] getTestInput() {
		return testInput;
	}

	public double[][] getTestOutput() {
		return testOutput;
	}

	public double[][] getReadInput() {
		return readInput;
	}

	public double[][] getReadOutput() {
		return readOutput;
	}

	public String getFileName() {
		return filename.substring(9);
	}

	public double getRegularizationDelta() {
		return regularizationDelta;
	}

	public void readData(String filename) {
		this.filename = filename;
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filename));
			String readln = "", temp = "";
			int ctr = 1, spaceIndex = 0;

			// read d first line and get the number of parameters
			readln = buff.readLine();
			do {
				spaceIndex = readln.indexOf(" ");
				if (spaceIndex == -1)
					spaceIndex = readln.length();

				temp = readln.substring(0, spaceIndex);
				// System.out.println("Read: "+temp);

				if (ctr == 1)
					numSamples = Integer.parseInt(temp.trim());
				if (ctr == 2)
					numInput = Integer.parseInt(temp.trim());// -20;
				if (ctr == 3) {
					numOutput = Integer.parseInt(temp.trim());
					break;
				}

				readln = readln.substring(spaceIndex + 1);
				// System.out.println("ReadLN:::"+readln);
				ctr++;
			} while (true);

			readInput = new double[numSamples][numInput];
			readOutput = new double[numSamples][numOutput];

			for (int i = 0; i < numSamples; i++) { // read input of k Samples
				readln = buff.readLine();
				for (int j = 0; j < numInput/* +20 */; j++) {
					spaceIndex = readln.indexOf(" ");
					if (spaceIndex == -1)
						spaceIndex = readln.length();

					temp = readln.substring(0, spaceIndex);
					// System.out.println("Read: "+temp);
					// if(j==20)
					readInput[i][j/*-20*/] = Double.parseDouble(temp.trim());

					readln = readln.substring(spaceIndex + 1);
					// System.out.println("ReadLN:::"+readln+"...."+spaceIndex);

					if ((j + 1) == numInput/* +20 */) {
						temp = readln.substring(0);
						int read = Integer.parseInt(temp.trim());
						if (!outputList.contains(read))
							outputList.add(read);
						tempList.add(Integer.parseInt(temp.trim()));
						// System.out.println("Read: "+temp);
						convertToBinary(read, readOutput[i]);
						// System.out.println("Finished Reading   "+i+"   sample with "+j+" input");
					}
				}
			}
			if (buff != null)
				buff.close();

		} catch (FileNotFoundException ex) {
			/*
			 * START remove unused code
			 * Logger.getLogger(Cat_Main.class.getName()).log(Level.SEVERE,
			 * null, ex); END remove unused code
			 */

			/* START added code */
			Debugger.println(ex.getMessage());
			/* END added code */
		} catch (IOException ex) {
			/*
			 * START remove unused code
			 * Logger.getLogger(Cat_Main.class.getName()).log(Level.SEVERE,
			 * null, ex); END remove unused code
			 */

			/* START added code */
			Debugger.println(ex.getMessage());
			/* END added code */
		}
	}

	private void convertToBinary(int num, double[] dataOutput) {

		String binary = "";
		int remainder, ctr = dataOutput.length;

		do {
			remainder = num % 2;
			num = num / 2;
			binary = remainder + binary;
			dataOutput[--ctr] = remainder;
		} while (num != 0);

		if (ctr != 0) {
			do {
				dataOutput[--ctr] = 0;
			} while (ctr != 0);
		}

		// for(int i=0; i<trainingDataOutput[index].length; i++){
		// System.out.print(trainingDataOutput[index][i]);
		// }
	}

	public void initialize() {
		if (!isEarlyStoppingActivated) {
			setTrainingDataInput(getReadInput());
			setTestDataOutput(getReadOutput());
		} else
			initializeTrainingANDValidationSets(trainingDataInput,
					trainingDataOutput);
	}

	public void initializeTrainingANDValidationSets(
			double[][] trainingDataInput, double[][] trainingDataOutput) {

		java.util.List<Integer> validationIndexList = java.util.Collections
				.synchronizedList(new java.util.ArrayList<Integer>());
		java.util.Collections.sort(outputList); // a list of possible output

		Object[] obj = new Object[outputList.size()];

		int valLength = 0;

		if (numInput > 100) {
			validationPercent = 1 / Math.sqrt(2 * numInput);
		} else
			validationPercent = (Math.sqrt((2 * numInput) - 1) - 1)
					/ (2 * (numInput - 1));

		for (int j = 0; j < outputList.size(); j++)
			obj[j] = new ArrayList();

		// groups the output into classes
		for (int i = 0; i < tempList.size(); i++) {
			for (int j = 0; j < outputList.size(); j++) {
				if (tempList.get(i) == outputList.get(j)) {
					((ArrayList) (obj[j])).add(i); // stores the index
					break;
				}
			}
		}

		for (int i = 0; i < obj.length; i++) {
			valLength = (int) Math.ceil(((ArrayList) (obj[i])).size()
					* validationPercent);
			java.util.Collections.shuffle((ArrayList) (obj[i]));
			validationIndexList.addAll(0,
					((ArrayList) (obj[i])).subList(0, valLength));
		}


		this.trainingDataInput = new double[numSamples
				- validationIndexList.size()][numInput];
		this.trainingDataOutput = new double[numSamples
				- validationIndexList.size()][numOutput];
		this.trainingDataValidationInput = new double[validationIndexList
				.size()][numInput];
		this.trainingDataValidationOutput = new double[validationIndexList
				.size()][numOutput];

		for (int i = 0, ctr = 0, ctrVal = 0; i < numSamples; i++) {

			if (validationIndexList.contains(i)) {
				copy(this.trainingDataValidationInput[ctrVal],
						trainingDataInput[i]);
				copy(this.trainingDataValidationOutput[ctrVal],
						trainingDataOutput[i]);
				ctrVal++;
			} else {
				copy(this.trainingDataInput[ctr], trainingDataInput[i]);
				copy(this.trainingDataOutput[ctr], trainingDataOutput[i]);
				ctr++;
			}

		}

	}

	private void copy(double[] dataTo, double[] dataFrom) {

		for (int i = 0; i < dataFrom.length; i++) {
			dataTo[i] = dataFrom[i];
		}

	}

	private void copy(double[][] dataTo, double[][] dataFrom) {

		for (int i = 0; i < dataFrom.length; i++) {
			for (int j = 0; j < dataFrom[i].length; j++) {
				dataTo[i][j] = dataFrom[i][j];
			}
		}

	}

	/* START added code */
	private char trainingAlgo;
	private int abcMaxRun;
	private int abcMaxCycle;
	private int employedBeeSize;
	private int onlookerBeeSize;
	private int totalMaxCycle;

	public int getDimension() {
		return dimension;
	}

	public char getTrainingAlgo() {
		return trainingAlgo;
	}

	public void setTrainingAlgo(char trainingAlgo) {
		this.trainingAlgo = trainingAlgo;
	}

	public int getAbcMaxRun() {
		return abcMaxRun;
	}

	public void setAbcMaxRun(int abcRunTime) {
		this.abcMaxRun = abcRunTime;
	}

	public int getAbcMaxCycle() {
		return abcMaxCycle;
	}

	public void setAbcMaxCycle(int abcMaxCycle) {
		this.abcMaxCycle = abcMaxCycle;
	}

	public int getEmployedBeeSize() {
		return employedBeeSize;
	}

	public void setEmployedBeeSize(int employedBeeSize) {
		this.employedBeeSize = employedBeeSize;
	}

	public int getOnlookerBeeSize() {
		return onlookerBeeSize;
	}

	public void setOnlookerBeeSize(int onlookerBeeSize) {
		this.onlookerBeeSize = onlookerBeeSize;
	}

	public int getNumInput() {
		return numInput;
	}

	public int getNumHidden() {
		return numHidden;
	}

	public int getNumOutput() {
		return numOutput;
	}

	public int getTotalMaxCycle() {
		return totalMaxCycle;
	}

	public void setTotalMaxCycle(int totalMaxCycle) {
		this.totalMaxCycle = totalMaxCycle;
	}

	private int trainingAlgoLabel;
	private ABC trainedABC;

	public void setTrainingAlgoLabel(int trainingLabel) {
		this.trainingAlgoLabel = trainingLabel;

	}

	public int getTrainingAlgoLabel() {
		return trainingAlgoLabel;
	}

	/**
	 * @return the outputTestDataFileName
	 */
	public String getOutputTestDataFileName() {
		return outputTestDataFileName;
	}

	/**
	 * @param outputTestDataFileName
	 *            the outputTestDataFileName to set
	 */
	public void setOutputTestDataFileName(String outputTestDataFileName) {
		this.outputTestDataFileName = outputTestDataFileName;
	}

	/**
	 * @return the inputTestDataFileName
	 */
	public String getInputTestDataFileName() {
		return inputTestDataFileName;
	}

	/**
	 * @param inputTestDataFileName
	 *            the inputTestDataFileName to set
	 */
	public void setInputTestDataFileName(String inputTestDataFileName) {
		this.inputTestDataFileName = inputTestDataFileName;
	}

	/**
	 * @return the outputTrainingDataFileName
	 */
	public String getOutputTrainingDataFileName() {
		return outputTrainingDataFileName;
	}

	/**
	 * @param outputTrainingDataFileName
	 *            the outputTrainingDataFileName to set
	 */
	public void setOutputTrainingDataFileName(String outputTrainingDataFileName) {
		this.outputTrainingDataFileName = outputTrainingDataFileName;
	}

	/**
	 * @return the inputTrainingDataFileName
	 */
	public String getInputTrainingDataFileName() {
		return inputTrainingDataFileName;
	}

	/**
	 * @param inputTrainingDataFileName
	 *            the inputTrainingDataFileName to set
	 */
	public void setInputTrainingDataFileName(String inputTrainingDataFileName) {
		this.inputTrainingDataFileName = inputTrainingDataFileName;
	}

	public String getExperimentName() {
		return experimentName;
	}

	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	public boolean isApplyOCD() {
		return applyOCD;
	}

	public void setApplyOCD(boolean applyOCD) {
		this.applyOCD = applyOCD;
	}

	public void setTrainedABC(ABC trainedABC) {
		// TODO Auto-generated method stub
		this.trainedABC = trainedABC;
	}
	
	public ABC getTrainedABC(){
		return trainedABC;
	}

	/* END added code */
}
