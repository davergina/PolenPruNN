package ui.util;

import ui.ClassificationPanel;
import ui.ExperimentPanel;
import ui.FeaturePanel;


public class EventPanelView {

	public static void featureView(){
		hideEventPanels();
		FeaturePanel.getInstance().setVisible(true);
	}
	
	public static void experimentView(){
		hideEventPanels();
		ExperimentPanel.getInstance().setVisible(true);
	}
	
	public static void classificationView(){
		hideEventPanels();
		ClassificationPanel.getInstance().setVisible(true);
	}

	private static void hideEventPanels() {
		// TODO Auto-generated method stub
		ExperimentPanel.getInstance().setVisible(false);
		FeaturePanel.getInstance().setVisible(false);
		ClassificationPanel.getInstance().setVisible(false);
	}

}
