package ui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ui.constants.ColorFactory;

public class BackgroundPanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -3057847128933029231L;
	private GradientPaint honey;

	public static BackgroundPanel instance = null;

	public static BackgroundPanel getInstance() {
		if (instance == null) {
			instance = new BackgroundPanel();
		}
		return instance;
	}

	public BackgroundPanel() {
		init();
		addPanels();
		initPanels();

	}

	private void initPanels() {
		// TODO Auto-generated method stub
		
	}

	private void addPanels() {
		// TODO Auto-generated method stub
//		this.add(HeaderPanel.getInstance());
		
	}

	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setLocation(0, 0);
		this.setSize(800, 600);
	}
	
	

}
