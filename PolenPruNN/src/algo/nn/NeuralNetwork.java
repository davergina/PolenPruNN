/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algo.nn;

import constants.InputHandler;

/**
 * 
 * @author jonnah
 */
@SuppressWarnings({ "unused" })
public class NeuralNetwork {

	private static NeuralNetwork instance = null;

	private java.util.List<Integer> classification = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());
	private boolean print = false, isHyperbolic = false, isRegularizationModeActive = false;
	private double totalSSE = 0, regularizationTerm = 0, delta = 0;
	private int totalSample = 0, totalCorrect = 0, correct = 0, numOutput = 0, connections = 0;
	private String pruningMode = "NONE";
	private double[] derivative; // diagonal entries of hessian matrix
	private double[][] completeHessian;

	private java.util.List<Integer> deactivatedweight = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());
	private java.util.List<Integer> deactivatedNode = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());
	private java.util.List<Integer> misClassified = java.util.Collections
			.synchronizedList(new java.util.ArrayList<Integer>());

	public static NeuralNetwork getInstance() {
		return (instance == null ? instance = new NeuralNetwork() : instance);
	}

	public void reset(java.util.List<Integer> deactivatedweight, java.util.List<Integer> deactivatedNode) {
		print = false;
		isHyperbolic = false;
		isRegularizationModeActive = false;
		totalSSE = 0;
		regularizationTerm = 0;
		delta = 0;
		totalSample = 0;
		totalCorrect = 0;
		correct = 0;
		numOutput = 0;
		this.deactivatedNode = deactivatedNode;
		this.deactivatedweight = deactivatedweight;
		this.misClassified.clear();
		this.classification.clear();
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public void setPruningMode(String mode) {
		this.pruningMode = mode;
	}

	// build Artificial NeuralNetwork
	public void buildNetwork(double[] weights, InputHandler handler, double[][] totalSample, double[][] _output) {
		this.isHyperbolic = handler.getActivationFunctionMode();
		this.isRegularizationModeActive = handler.getRegularizationMode();
		this.delta = handler.getRegularizationDelta();

		this.totalSample = totalSample.length;
		this.numOutput = handler.numOutput;
		totalCorrect = 0;

		// Removed Prunning part

		if (isRegularizationModeActive)
			computeRegularizationTerm(weights);

		for (int k = 0; k < totalSample.length; k++) {
			// read kth training data
			double input[] = totalSample[k];
			double output[] = _output[k];

			/* Neural Network Simulation - start */
			simulateNetwork(handler.numHidden, weights, input, output);

			/* Removed Prunned Network */
			// simulateNetwork_Optimized(handler.numHidden, weights, input,
			// output, k);
			/* Neural Network Simulation - end */
		}
//		System.out.println("Total SSE: "+totalSSE);
	}

	/*
	 * computes the errorGradient with respect to weight
	 */
	private void computedE_dW(int numInput, int numHidden, int numOutput, double[] delivered, double[] target,
			double[] dE_dW, double[] weight) {

		double[] sumWeightsPerOutput = new double[numOutput];
		double pb = 0;

		for (int i = (numInput + 1) * numHidden, ctr = 0, index = numInput + 1; i < weight.length; i++, ctr++) {

			if (ctr == numOutput) {
				ctr = 0;
				index++;
			}

			/*
			 * de/dw = dE/dZ*dZ/dW
			 */
			dE_dW[i] = /*
						 * ((delivered[numInput+numHidden+ctr+2] -
						 * target[ctr])/totalSample)
						 */firstDerivative(delivered[numInput + numHidden + ctr + 2]);

			if (i >= ((numInput + 1) * numHidden) + numOutput) {
				if (!deactivatedweight.contains(i)) {
					sumWeightsPerOutput[ctr] += weight[i];
				}
				dE_dW[i] *= delivered[index];
			}

		}

		for (int i = 0, index = (numInput + 1) * numHidden; i < numOutput; i++, index++) {
			pb += dE_dW[index] * sumWeightsPerOutput[i];
		}

		for (int i = 0, ctr = 0, index = 0; i < (numInput + 1) * numHidden; i++, ctr++) {
			if (ctr == numHidden) {
				ctr = 0;
				index++;
			}
			dE_dW[i] = pb * firstDerivative(delivered[numInput + ctr + 2]) * delivered[index];
		}
	}

	/*
	 * Adds the diagonal entries of all the hessian matrices from different
	 * samples
	 * 
	 * @param temp the dE/dW errorGradient with respect to weight
	 * temp[i]*temp[i] = diagonal entries of hessian hessian = (temp[i]*(temp[j]
	 * from j=0 to n)) i from i=0 to n divided by the totalSample
	 */
	private void compute2ndDerivatives(double[] derivative, double[] temp) {
		for (int i = 0; i < temp.length; i++) {
			derivative[i] += (temp[i] * temp[i]);
		}
	}

	private void computeCompleteHessian(double[][] derivative, double[] temp) {
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				derivative[i][j] += (temp[i] * temp[j]);
			}
		}

	}

	// Removed Optimized Simulate Function

	private void simulateNetwork(int numHidden, double[] weight, double[] input, double[] output) {

		double[] inputNode = new double[input.length + 1], hiddenNode = new double[numHidden + 1],
				outputNode = new double[output.length];
		int i = 0;
		double errorSummation = 0;
		correct = 0;

		// add bias node to the first index of inputNode
		for (i = 0; i < inputNode.length; i++) {
			if (i != 0)
				inputNode[i] = input[i - 1];
			else
				inputNode[i] = 1.0;
		}

		// compute for the value of the hidden node
		for (i = 0; i < numHidden + 1; i++) {
			if (i != 0)
				hiddenNode[i] = activationFunction(adderFunction(inputNode, weight, numHidden, i, 0)); // computes
																										// the
																										// activationFunction
			else
				hiddenNode[i] = 1.0; // bias Node
		}

		// compute for the value of the output node
		for (i = 0; i < outputNode.length; i++) {
			outputNode[i] = activationFunction(
					adderFunction(hiddenNode, weight, numOutput, i + 1, inputNode.length * numHidden));
			errorSummation = errorSummation + calculateError(output[i], outputNode[i]);

			getNumCorrectAnswerPerSample(output[i], outputNode[i]);
		}
		calculateSSE(totalSSE, errorSummation / outputNode.length);
//		System.out.println("Total SSE: "+totalSSE);
		if (correct == output.length)
			totalCorrect++;

		inputNode = null;
		hiddenNode = null;
		outputNode = null;

	}

	private void printOutput(double output, double act) {

		System.out.println("Activation Function: " + act + " ? " + output + "\n");
	}

	private void computeRegularizationTerm(double[] weights) {
		double total = 0;
		for (int i = 0; i < weights.length; i++) {
			if (!deactivatedweight.contains(i))
				total = total + (weights[i] * weights[i]);
		}
		regularizationTerm = total * delta;
	}

	public double getAverageMSE() {
		if (isRegularizationModeActive)
			return (((0.5 * totalSSE) / (totalSample * numOutput)) + regularizationTerm);

		else
			/*
			 * START remove unused code return ((0.5 * totalSSE) / (totalSample
			 * * numOutput)); END remove unused code
			 */
			return totalSSE / (totalSample * numOutput);

	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public int getTotalSample() {
		return totalSample;
	}

	public int getOutputNumber() {
		return numOutput;
	}

	// error classification
	public double getAccuracy() {
		return (1 - (totalCorrect / (totalSample)));
	}

	public java.util.List<Integer> getMisClassified() {
		return misClassified;
	}

	public double getPercentageCorrect() {
		return (((double) totalCorrect) / (totalSample)) * 100;
	}

	public double[] getHessianMatrix() {
		return derivative;
	}

	public double[][] getCompleteHessianMatrix() {
		return completeHessian;
	}

	public void getNumCorrectAnswerPerSample(double desiredOutput, double actual) {
		double limit = 0.5;
		if (isHyperbolic)
			limit = 0;

		if (desiredOutput > limit) {
			if (actual > limit)
				correct++;
		} else {
			if (actual < limit)
				correct++;
		}
	}

	public void destroy(int len) {

		if (completeHessian != null)
			for (int i = 0; i < completeHessian.length; i++)
				completeHessian[i] = null;
		completeHessian = null;
		derivative = null;
		System.gc();
	}

	// Removed Optimized Adder Function

	private double adderFunction(double[] node, double[] weight, int numCurNodeInLayer, int curIndex, int prevWeight) {
		double sum = 0;
		for (int i = 0; i < node.length; i++) {
			sum = sum + (node[i] * weight[prevWeight + (i * numCurNodeInLayer) + (curIndex - 1)]);
		}
		return sum;
	}

	private double activationFunction(double total) {
		if (isHyperbolic) { // hyperbolic tangent function
			return (Math.tanh(total));
		} else
			// sigmoid function
			return (1 / (1 + (Math.exp(-1 * total))));
	}

	/*
	 * computes first derivative of the activation function
	 * 
	 * @param f the result of the activationFunction return the derivative of
	 * the activationFunction
	 */
	private double firstDerivative(double f) {
		// double f = activationFunction(x);
		if (isHyperbolic) {
			return 1 - f * f;
		} else
			return f * (1 - f);
	}

	/*
	 * second derivative of the activation function
	 */
	// private double secondDerivative( double x ){
	// double f = activationFunction(x);
	// if(isHyperbolic){
	// return (-2*f*(1 - f*f));
	// }
	// else
	// return f*(1-f)*(1-2*f);
	// }

	private double calculateError(double desiredOutput, double actual) {
		return desiredOutput - actual;
	}

	private double calculateSSE(double SSE, double error) {
		return totalSSE = (SSE + (error * error));
	}

	public int getConnectionsExamined() {
		return connections;
	}

	private boolean isCorrect(double desiredOutput, double[] output) {

		int maxIndex = 0;
		for (int i = 0; i < output.length; i++) {
			if (output[i] > output[maxIndex])
				maxIndex = i;
		}
		classification.add(maxIndex + 1);
		return maxIndex + 1 == desiredOutput;

	}

	public java.util.List<Integer> getClassification() {
		return classification;
	}

	private int normalizeOutput(double[] output) {
		int maxIndex = 0;

		for (int i = 1; i < output.length; i++) {

			if (output[i] > output[maxIndex])
				maxIndex = i;
		}
		return maxIndex + 1;
	}

	private int returnFullValue(double[] _output) {
		int returnable = 0;
		for (int j = 0; j < _output.length; j++) {
			if (_output[j] == 1) {
				returnable = j + 1;
				break;
			}
		}

		return returnable;
	}
}