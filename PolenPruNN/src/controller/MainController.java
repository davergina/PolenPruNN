package controller;

import algo.abc.ABC;
import constants.InputHandler;
import controller.data.Data;

public class MainController {


	public static void runABC(Data trainingData) {
		// TODO Auto-generated method stub
		System.out.println("Running ABC");
		/* Default (Standard) Setting */
		
		GlobalVariables.setMode(GlobalVariables.STANDARD_RUN);
		
		int runtime = 1;
		int maxCycle = 250;
		int employedBeeSize = 15;
		int onlookerBeeSize = 15;
		int dimension = GlobalVariables.DIMENSIONS;
		
		InputHandler handler = InputHandler.getInstance();
		
		handler.setActivationFunctionMode(false);
		handler.setRegularizationMode(false);
		
		handler.setAbcMaxRun(runtime);
		handler.setAbcMaxCycle(maxCycle);
		handler.setEmployedBeeSize(employedBeeSize);
		handler.setOnlookerBeeSize(onlookerBeeSize);
		handler.setDimension(dimension);
		
		handler.setNumInput(GlobalVariables.STANDARD_INPUT_NODES);
		handler.setNumHidden(GlobalVariables.STANDARD_HIDDEN_NODES);
		handler.setNumOutput(GlobalVariables.NUMBER_OF_OUTPUT);
		
		ABC abc = new ABC(runtime, maxCycle, employedBeeSize, onlookerBeeSize, dimension);
		abc.train(trainingData);
		
		handler.setTrainedABC(abc);
		
	}
	
	public static void classifyObject(){
		
	}

	
	

}
