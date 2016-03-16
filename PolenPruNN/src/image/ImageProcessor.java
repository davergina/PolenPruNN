package image;

import helper.constants.Values;
import ij.process.ColorProcessor;
import image.process.BackgroundRemover;
import image.process.BilinearInterpolation;
import image.process.Cropper;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import controller.GlobalVariables;
import feature.glcm.Haralick;

public class ImageProcessor {

	private static ImageProcessor instance = null;
	private BackgroundRemover bRemover = null;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;

	private Haralick haralick;

	private BufferedImage cropped;

	public ImageProcessor() {
		bRemover = BackgroundRemover.getInstance();
	}

	public static ImageProcessor getInstance() {
		if (instance == null)
			instance = new ImageProcessor();
		return instance;
	}

	// public BufferedImage process(BufferedImage image, int width, int height)
	// {
	// return removeBackground(BilinearInterpolation.resize(image, WIDTH,
	// HEIGHT));
	// }

	public BufferedImage process(BufferedImage image, int width, int height) {
		if (image == null) {
		}
		return BilinearInterpolation.resize(
				cropImage(removeBackground(BilinearInterpolation.resize(image,
						width, height))), 64, 64);
		// return resizeImage(cropImage(removeBackground(resizeImage(image,
		// WIDTH, HEIGHT))), 64, 64);
	}

	public BufferedImage resizeImage(BufferedImage image, int scaledWidth,
			int scaledHeight) {
		BufferedImage resizedImage = new BufferedImage(scaledWidth,
				scaledHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();
		return resizedImage;
	}

	public BufferedImage removeBackground(BufferedImage image) {
		return bRemover.removeBackground(image);
	}

	/*******************************************/
	/* getters to BackgroundRemover */
	/*******************************************/
	public BufferedImage getBlueChannel() {
		return bRemover.getBlueChannel();
	}

	public BufferedImage getFilteredBlue() {
		return bRemover.getFilteredBlue();
	}

	public BufferedImage getGrayscale() {
		return bRemover.getGrayscale();
	}

	public BufferedImage getBinaryMask() {
		return bRemover.getBinaryMask();
	}

	public BufferedImage getSegmented() {
		return bRemover.getSegmented();
	}

	public BufferedImage getCropped() {
		return cropped;
	}

	/**
	 * creates a vector of features for each image
	 * 
	 * @param input_data
	 * @param progressPane
	 * @return
	 */
	// public double[][] createInputVectorArray(
	// ArrayList<BufferedImage> input_data,
	// ArrayList<Integer> output_list, ProgressPane progressPane) {
	// int patternSize = input_data.size();
	// double[][] inputArray = new
	// double[patternSize][GlobalVariables.NUMBER_OF_INPUT];
	//
	// for (int i = 0; i < patternSize; i++) {
	//
	// BufferedImage extractedInput = process(input_data.get(i), 200, 200);
	// inputArray[i] = getFeatures(extractedInput);
	//
	// progressPane.incrementBar();
	// }
	//
	// return inputArray;
	// }

	public double[][] createInputVectorArray(
			ArrayList<BufferedImage> input_data,
			ArrayList<Integer> output_list) {
		int patternSize = input_data.size();
		double[][] inputArray = new double[patternSize][GlobalVariables.NUMBER_OF_INPUT];

		for (int i = 0; i < patternSize; i++) {

			BufferedImage extractedInput = process(input_data.get(i), 200, 200);
			inputArray[i] = getFeatures(extractedInput);

		}

		return inputArray;
	}

	/**
	 * creates a vector and sets the corresponding class index to 1, other to 0
	 * 
	 * @param output_data
	 * @return
	 */
	public double[][] createOutputVector(ArrayList<Integer> output_data) {
		int patternSize = output_data.size();
		double[][] outputArray = new double[patternSize][GlobalVariables.NUMBER_OF_OUTPUT];

		// set the output node value of the expected class to 1
		for (int i = 0; i < patternSize; i++)
			outputArray[i][output_data.get(i) - 1] = 1.0;

		return outputArray;
	}

	public BufferedImage cropImage(BufferedImage image) {
		cropped = Cropper.crop(image);
		return cropped;
	}

	/**
	 * extracts color features of an image
	 * 
	 * @param processedImage
	 * @return feature vector
	 */
	public double[] getFeatures(BufferedImage processedImage) {

		double[] features = new double[GlobalVariables.NUMBER_OF_INPUT];

		haralick = new Haralick();
		haralick.run(new ColorProcessor(processedImage));

		double haralickFeatures[] = haralick.getHaralickFeatures();

//		if (GlobalVariables.SUBMENU == Values.EXPERIMENT) {
//
//			// switch (ExperimentPanel.getInstance().getNumberOfInputs()) {
//			switch (Values.SETUP_1) {
//
//			case Values.SETUP_4:// 1 6 10 9 11 5 4
//				features[5] = haralickFeatures[5];
//				features[6] = haralickFeatures[4];
//
//			case Values.SETUP_3: // 1 6 10 9 11
//				features[3] = haralickFeatures[9];
//				features[4] = haralickFeatures[11];
//
//			case Values.SETUP_2:// 1 6 10
//				features[1] = haralickFeatures[6];
//				features[2] = haralickFeatures[10];
//
//			case Values.SETUP_1:// 1
//				features[0] = haralickFeatures[1];
//				break;
//			}
//		} else {
//
//			features[0] = haralickFeatures[1];
//			features[1] = haralickFeatures[6];
//			features[2] = haralickFeatures[10];
//			features[3] = haralickFeatures[9];
//			features[4] = haralickFeatures[11];
//			features[5] = haralickFeatures[5];
//			features[6] = haralickFeatures[4];
//		}
		
		if(features.length==7){
		features[0] = haralickFeatures[1];
		features[1] = haralickFeatures[6];
		features[2] = haralickFeatures[10];
		features[3] = haralickFeatures[9];
		features[4] = haralickFeatures[11];
		features[5] = haralickFeatures[5];
		features[6] = haralickFeatures[4];
		}else{
			System.out.println("Features not set at default");
		}
		haralick = null;

		return features;
	}
}
