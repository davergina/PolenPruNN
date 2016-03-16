package ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.constants.ColorFactory;
import ui.util.EventPanelView;

public class LeftPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3057847128933029231L;
	private GradientPaint honey;

	private JButton featureButton;
	private JButton experimentButton;
	private JButton classificationButton;

	public static LeftPanel instance = null;

	public static LeftPanel getInstance() {
		if (instance == null) {
			instance = new LeftPanel();
		}
		return instance;
	}

	public LeftPanel() {
		init();
		addPanels();
		addComponents();
	}

	private void addComponents() {
		// TODO Auto-generated method stub
		featureButton = new JButton("Feature");

		featureButton.setLocation(30, 60);
		featureButton.setSize(140, 30);
		this.add(featureButton);

		featureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				featureButtonClicked();
			}
		});

		experimentButton = new JButton("Experiment");

		experimentButton.setLocation(30, 100);
		experimentButton.setSize(140, 30);
		this.add(experimentButton);

		experimentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				experimentButtonClicked();
			}
		});

		classificationButton = new JButton("Classification");

		classificationButton.setLocation(30, 140);
		classificationButton.setSize(140, 30);
		this.add(classificationButton);

		classificationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				classificationButtonClicked();
			}
		});
	}

	protected void classificationButtonClicked() {
		// TODO Auto-generated method stub
		EventPanelView.classificationView();
	}

	protected void experimentButtonClicked() {
		// TODO Auto-generated method stub
		EventPanelView.experimentView();
	}

	protected void featureButtonClicked() {
		// TODO Auto-generated method stub
		EventPanelView.featureView();
	}

	private void initPanels() {
		// TODO Auto-generated method stub

	}

	private void addPanels() {
		// TODO Auto-generated method stub
		// this.add(HeaderPanel.getInstance());
		initPanels();

	}

	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setLocation(0, 0);
		this.setSize(200, 600);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		honey = ColorFactory.fill(ColorFactory.DEFAULT_BACKGROUND, getHeight());

		g2.setPaint(honey);
		g2.fill(getBounds());

		paintChildren(g2);

		g2.dispose();
		g.dispose();
	}

}
