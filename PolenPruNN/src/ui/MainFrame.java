package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.data.DataLocationHandler;
import ui.util.EventPanelView;


public class MainFrame extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = -8161507730821605805L;

	public MainFrame() {
		init();
		addPanels();
	}

	private void addPanels() {
		// TODO Auto-generated method stub
		this.add(ExperimentPanel.getInstance());
		this.add(FeaturePanel.getInstance());
		this.add(ClassificationPanel.getInstance());
		this.add(LeftPanel.getInstance());
		
		this.add(BackgroundPanel.getInstance());
		
		EventPanelView.featureView();
		
	}

	private void init() {
		this.setTitle("Polen Classification using ABCNN with Pruning");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setDefaultDatasetLocation();
		
//		this.setUndecorated(false);
	}

	private void setDefaultDatasetLocation() {
		// TODO Auto-generated method stub
		File fileLocation = new File("dataset/dataset_location.txt");
		JLabel currDirectory = new JLabel("");
		
		try{
			
			if (!fileLocation.exists()) {
				fileLocation.createNewFile();
			}
			
		BufferedReader br = null;
		
		String sCurrentLine;
		
		br = new BufferedReader(new FileReader("dataset/dataset_location.txt"));
		
		while((sCurrentLine = br.readLine())!=null){
			DataLocationHandler.setBaseFolder(sCurrentLine);
			currDirectory.setText(sCurrentLine);
		}
		
		if(br!=null) br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fileLocation = null;
		}
	}

}
