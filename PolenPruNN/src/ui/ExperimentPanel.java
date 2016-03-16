package ui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import controller.MainController;
import controller.GlobalVariables;
import controller.data.Data;
import controller.data.DataLocationHandler;
import controller.data.DataReader;
import helper.file.Directory;
import ui.constants.ColorFactory;

public class ExperimentPanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -3057847128933029231L;
	private GradientPaint honey;
	
	private JButton trainingFileButton;
	private JButton prepareTrainingButton;
	private JButton startTrainButton;
	
	private File trainingFile;
	
	private Data trainingData;
	
	
	private JFileChooser trainingFileChooser;
	
	
	

	public static ExperimentPanel instance = null;

	public static ExperimentPanel getInstance() {
		if (instance == null) {
			instance = new ExperimentPanel();
		}
		return instance;
	}

	public ExperimentPanel() {
		init();
		initCompoenents();
		addComponenets();
		
		setDatasetFolder();

	}

	private void setDatasetFolder() {
		// TODO Auto-generated method stub
		
	}

	private void addComponenets() {
		// TODO Auto-generated method stub
		trainingFileButton.setBounds(50, 50, 150, 30);
		add(trainingFileButton);
		
		prepareTrainingButton.setBounds(50, 90, 150, 30);
		add(prepareTrainingButton);
		
		startTrainButton.setBounds(50, 130, 150, 30);
		add(startTrainButton);
	}

	private void initCompoenents() {
		// TODO Auto-generated method stub
//		this.add(HeaderPanel.getInstance());
		trainingFileButton = new JButton("Load Training Data");
		trainingFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				trainingFileClicked();
			}
		});
		
		prepareTrainingButton = new JButton("Prepare Network");
		prepareTrainingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				prepareTrainingButtonClicked();
			}
		});
		
		startTrainButton = new JButton("Train Network");
		startTrainButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				startTrainButtonClicked();
			}
		});
		
		trainingFileChooser = new JFileChooser(Directory.DATA);
		
	}

	protected void startTrainButtonClicked() {
		// TODO Auto-generated method stub
		trainingSetTEST();
		MainController.runABC(trainingData);
	}

	private void trainingSetTEST() {
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

	protected void prepareTrainingButtonClicked() {
		// TODO Auto-generated method stub
		System.out.println("Prepare clicked..");
		
		initNetwork();
		new Thread( new Runnable() {
			@Override
			public void run() {
				startTrainButton.setEnabled(false);
				System.out.println("Locating Files...");
				DataReader dl = new DataReader(trainingFile);
				System.out.println("Done locating... Preparing Files");
				if(dl.read()) {									//Reads the Datafile
					trainingData = dl.getData();
//					DataReader dl2 = new DataReader(progressPane, testFile);
//					if(dl2.read()) {
//						testData = dl2.getData();
//						hasData = true;
//						new MessageDialog("Done preparing.").setVisible(true);
//					}
					System.out.println("Done Preparing...");
				}
				startTrainButton.setEnabled(true);
//				setComponents(true);
			}
			
		}).start();
	}

	protected void trainingFileClicked() {
		// TODO Auto-generated method stub
		trainingFileChooser.showOpenDialog(this);
		trainingFile = trainingFileChooser.getSelectedFile();
		if(trainingFile!=null){
			System.out.println(""+trainingFile.getName());
		}
	}
	
	private void initNetwork() {
		// TODO Auto-generated method stub
		GlobalVariables.setMode(GlobalVariables.STANDARD_RUN);
	}
	

	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setLocation(200, 0);
		this.setSize(600, 600);
	}
	
	public int getNumberOfInputs() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void incrementRuntime(int i) {
		// TODO Auto-generated method stub
		System.out.println("training");
	}

	public void incrementCycle(int i) {
		// TODO Auto-generated method stub
		System.out.println("cycle = "+i);
	}

	public void returnResult(double globalMin, double[] ds, double elapsedTime,
			int i) {
		// TODO Auto-generated method stub
		
	}

	public void resetButtons() {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public void paintComponent(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
//		honey = ColorFactory.fill(ColorFactory.DEFAULT_BACKGROUND, getHeight());
//
//		g2.setPaint(honey);
//		g2.fillRect(0, 0, 600, 600);
//
//		paintChildren(g2);
//
//		g2.dispose();
//		g.dispose();
//	}

	

}
