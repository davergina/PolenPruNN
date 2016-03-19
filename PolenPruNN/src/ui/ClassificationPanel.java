package ui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import algo.nn.NeuralNetwork;
import constants.InputHandler;
import controller.MainController;
import controller.GlobalVariables;
import controller.data.Data;
import controller.data.DataLocationHandler;
import controller.data.DataReader;
import feature.glcm.Haralick;
import helper.file.Directory;
import ij.process.ColorProcessor;
import image.ImageProcessor;
import ui.constants.ColorFactory;

public class ClassificationPanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -3057847128933029231L;
	private GradientPaint honey;
	
	private JButton selectClassifierButton;
	private JButton selectImageButton;
	private JButton singleClassificationButton;
	private JButton batchClassificationButton;
	
	private JFileChooser imageFileChooser;
	
	private ImageProcessor imageProcessor;
	
	private File testFile;
	
	private File singleImageFile;
	
	private Data testData;
	
	private JLabel inputIcon;
	
	private BufferedImage input, temp, processed;
	
	
	private JFileChooser testFileChooser;
	
	
	

	public static ClassificationPanel instance = null;

	public static ClassificationPanel getInstance() {
		if (instance == null) {
			instance = new ClassificationPanel();
		}
		return instance;
	}

	public ClassificationPanel() {
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
		selectClassifierButton.setBounds(50, 50, 150, 30);
		add(selectClassifierButton);
		
		
		selectImageButton.setBounds(50, 90, 150, 30);
		add(selectImageButton);
		
		
		
		
		batchClassificationButton.setBounds(50, 130, 150, 30);
//		add(batchClassificationButton);
		
		inputIcon.setBounds(300, 50, 200, 200);
		add(inputIcon);
		
		singleClassificationButton.setBounds(300, 270, 150, 30);
		add(singleClassificationButton);
		singleClassificationButton.setVisible(false);
		
	}

	private void initCompoenents() {
		// TODO Auto-generated method stub
//		this.add(HeaderPanel.getInstance());
		selectClassifierButton = new JButton("Select Classifier");
		selectClassifierButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				selectClassifierClicked();
			}
		});
		
		selectImageButton = new JButton("Select Image");
		selectImageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				selectImageButtonClicked();
			}
		});
		
		singleClassificationButton = new JButton("Classify");
		singleClassificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				singleClassificationButtonClicked();
			}
		});
		
		batchClassificationButton = new JButton("Batch Classifcation");
		batchClassificationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				batchClassificationButtonClicked();
			}
		});
		
		testFileChooser = new JFileChooser(Directory.DATA);
		
		inputIcon = new JLabel();
		
		imageFileChooser = new JFileChooser();
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageFileChooser.setFileFilter(new FileNameExtensionFilter("", "jpg",
				"jpeg"));
		
		imageProcessor = new ImageProcessor();
		
		
	}

	protected void singleClassificationButtonClicked() {
		// TODO Auto-generated method stub
		classifyCurrentImage();
	}

	

	protected void batchClassificationButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	protected void selectImageButtonClicked() {
		// TODO Auto-generated method stub
		showInput();
	}

	protected void selectClassifierClicked() {
		// TODO Auto-generated method stub
		
	}
	
	private void classifyCurrentImage() {
		// TODO Auto-generated method stub
//		processed = imageProcessor.process(temp, 200, 200);
		
		ArrayList<BufferedImage> imagesToClassify = new ArrayList<BufferedImage>();
		ArrayList<Integer> equivalentOutputList = new ArrayList<Integer>();
		
		imagesToClassify.add(temp);
		equivalentOutputList.add(Integer.parseInt(singleImageFile.getParentFile().getName()));
		
		double[] weights = InputHandler.getInstance().getTrainedABC().getSolution();
		double[][] totalSample = imageProcessor.createInputVectorArray(imagesToClassify);
		double[][] output = imageProcessor.createOutputVector(equivalentOutputList);
		
		System.out.println("New Network");
		System.out.println("");
		NeuralNetwork nn = new NeuralNetwork();
		nn.reset(new ArrayList<Integer>(), new ArrayList<Integer>());
		nn.buildNetwork(weights, InputHandler.getInstance(), totalSample, output);
		
		
		System.out.println("Correct: "+nn.getTotalCorrect());
		
	}
	
	private void showInput() {
		singleImageFile = selectInputImage();
		if (singleImageFile != null) {
			showInput(singleImageFile);
		}
	}
	
	private void showInput(File inputFile) {
		// filenameLabel.setText("<html>"+inputFile.getName()+"</html>");
		// filenameLabel.setText(inputFile.getName());
		// filenameLabel.setToolTipText(inputFile.getName());
		try {
			input = ImageIO.read(inputFile);
		} catch (IOException e1) {
			// Debugger.printError("IO error in "+this.getClass().getName());
		}
		temp = imageProcessor.resizeImage(input, 200, 200);
		inputIcon.setIcon(new ImageIcon(temp));
		singleClassificationButton.setVisible(true);
		updateUI();
	}
	
	private File selectInputImage() {
		File file = null;
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (imageFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			file = imageFileChooser.getSelectedFile();
		return file;
	}

	protected void prepareTrainingButtonClicked() {
		// TODO Auto-generated method stub
		
		new Thread( new Runnable() {
			@Override
			public void run() {
				System.out.println("Locating Files...");
				DataReader dl = new DataReader(testFile);
				System.out.println("Done locating... \nPreparing Files");
				if(dl.read()) {									//Reads the Datafile
					testData = dl.getData();
//					DataReader dl2 = new DataReader(progressPane, testFile);
//					if(dl2.read()) {
//						testData = dl2.getData();
//						hasData = true;
//						new MessageDialog("Done preparing.").setVisible(true);
//					}
					System.out.println("Done Preparing...");
				}
				
//				setComponents(true);
			}
			
		}).start();
	}

	protected void trainingFileClicked() {
		// TODO Auto-generated method stub
		testFileChooser.showOpenDialog(this);
		testFile = testFileChooser.getSelectedFile();
		if(testFile!=null){
			System.out.println(""+testFile.getName());
		}
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
