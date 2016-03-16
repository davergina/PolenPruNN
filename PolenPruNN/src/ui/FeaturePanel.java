package ui;

import ij.process.ColorProcessor;
import image.ImageProcessor;
import ui.constants.ColorFactory;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import feature.glcm.Haralick;

public class FeaturePanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -3057847128933029231L;
	private GradientPaint honey;

	private JButton loadButton;
	private JButton extractFeaturesButton;

	private ImageProcessor imageProcessor;
	private JLabel inputIcon;
	private JFileChooser imageFileChooser;

	private JLabel contrastLabel, idmLabel, sumAverageLabel, sumVarianceLabel,
			differenceVarianceLabel, differenceEntropyLabel, imcLabel;

	private final String CONTRAST = "Contrast",
			IDM = "Inverse Difference Moment", SUM_AVG = "Sum Average",
			SUM_VAR = "Sum Variance", DIFF_VAR = "Difference Variance",
			DIFF_ENT = "Difference Entropy",
			IMC = "Information Measures Correlation";

	private BufferedImage input, temp, processed;

	private JPanel scrollingPanel;
	private JScrollPane scroll;

	public static FeaturePanel instance = null;

	public static FeaturePanel getInstance() {
		if (instance == null) {
			instance = new FeaturePanel();
		}
		return instance;
	}

	public FeaturePanel() {
		init();
		initComponents();
		addComponents();

	}

	private void addComponents() {
		// TODO Auto-generated method stub
		loadButton.setBounds(50, 50, 120, 30);
		this.add(loadButton);
		extractFeaturesButton.setBounds(50, 90, 120, 30);
		this.add(extractFeaturesButton);
		inputIcon.setBounds(300, 50, 200, 200);
		this.add(inputIcon);

		contrastLabel.setBounds(50, 140, 200, 15);
		add(contrastLabel);
		idmLabel.setBounds(50, 160, 200, 15);
		add(idmLabel);
		sumAverageLabel.setBounds(50, 180, 200, 15);
		add(sumAverageLabel);
		sumVarianceLabel.setBounds(50, 200, 200, 15);
		add(sumVarianceLabel);
		differenceVarianceLabel.setBounds(50, 220, 200, 15);
		add(differenceVarianceLabel);
		differenceEntropyLabel.setBounds(50, 240, 200, 15);
		add(differenceEntropyLabel);
		imcLabel.setBounds(50, 260, 200, 15);
		add(imcLabel);

		scroll.setBounds(50, 300, 500, 240);
		add(scroll);

	}

	private void initComponents() {
		// TODO Auto-generated method stub
		contrastLabel = new JLabel(CONTRAST + " : ");
		idmLabel = new JLabel(IDM + " : ");
		sumAverageLabel = new JLabel(SUM_AVG + " : ");
		sumVarianceLabel = new JLabel(SUM_VAR + " : ");
		differenceVarianceLabel = new JLabel(DIFF_VAR + " : ");
		differenceEntropyLabel = new JLabel(DIFF_ENT + " : ");
		imcLabel = new JLabel(IMC + " : ");

		loadButton = new JButton("Load Image");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				loadButtonClicked();
			}
		});

		extractFeaturesButton = new JButton("Extract Features");
		extractFeaturesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				extractFeaturesClicked();
			}
		});

		inputIcon = new JLabel();
		// inputIcon.setText("Icon");

		imageFileChooser = new JFileChooser();
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageFileChooser.setFileFilter(new FileNameExtensionFilter("", "jpg",
				"jpeg"));

		scrollingPanel = new JPanel();
		scrollingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollingPanel.setBackground(Color.WHITE);
		// TODO test panel size
		// scrollingPanel.setPreferredSize(new Dimension(800, 200));
		scroll = new JScrollPane(scrollingPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

	}

	private void addImageToScrollPane(Image image) {
		JLabel newLabel = new JLabel(new ImageIcon(image));
		scrollingPanel.add(newLabel);
		revalidate();
		updateUI();
	}

	protected void extractFeaturesClicked() {
		// TODO Auto-generated method stub
		cleanFeaturesScrollingPane();
		showProcessedImages();
		showFeatures();
	}

	private void cleanFeaturesScrollingPane() {
		if (scrollingPanel.getComponentCount() > 0) {
			scrollingPanel.removeAll();
			scrollingPanel.revalidate();
		}
	}

	private void showProcessedImages() {
		processed = imageProcessor.process(temp, 200, 200);
		addImageToScrollPane(temp);
		addImageToScrollPane(imageProcessor.getBlueChannel());
		addImageToScrollPane(imageProcessor.getGrayscale());
		addImageToScrollPane(imageProcessor.getBinaryMask());
		addImageToScrollPane(imageProcessor.getSegmented());
		// addImageToScrollPane(imageProcessor.getCropped());
		addImageToScrollPane(processed);
	}

	protected void loadButtonClicked() {
		// TODO Auto-generated method stub
		showInput();
	}

	private void showInput() {
		File file = selectInputImage();
		if (file != null) {
			showInput(file);
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
		updateUI();
	}

	private File selectInputImage() {
		File file = null;
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (imageFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			file = imageFileChooser.getSelectedFile();
		return file;
	}

	private void showFeatures() {
		Haralick haralick = new Haralick();

		haralick.run(new ColorProcessor(processed));

		double haralickFeatures[] = haralick.getHaralickFeatures();

		contrastLabel.setText(CONTRAST+": "+haralickFeatures[1]);
		idmLabel.setText(IDM+" : "+haralickFeatures[4]);
		sumAverageLabel.setText(SUM_AVG+" : "+haralickFeatures[5]);
		sumVarianceLabel.setText(SUM_VAR+" : "+haralickFeatures[6]);
		differenceVarianceLabel.setText(DIFF_VAR+" : "+haralickFeatures[9]);
		differenceEntropyLabel.setText(DIFF_ENT+" : "+haralickFeatures[10]);
		imcLabel.setText(IMC+" : "+haralickFeatures[11]);
	}
	
	private void clearFeatures(){
		contrastLabel.setText(CONTRAST+": ");
		idmLabel.setText(IDM+" : ");
		sumAverageLabel.setText(SUM_AVG+" : ");
		sumVarianceLabel.setText(SUM_VAR+" : ");
		differenceVarianceLabel.setText(DIFF_VAR+" : ");
		differenceEntropyLabel.setText(DIFF_ENT+" : ");
		imcLabel.setText(IMC+" : ");
	}

	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setLocation(200, 0);
		this.setSize(600, 600);

		imageProcessor = new ImageProcessor();
	}

}
