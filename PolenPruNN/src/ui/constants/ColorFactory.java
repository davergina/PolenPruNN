package ui.constants;

import java.awt.Color;
import java.awt.GradientPaint;

public class ColorFactory {

	public final static int STEEL = 0;
	public final static int DEFAULT_BACKGROUND = 1;
	public final static int MORE_HONEY = 2;
	public final static int MOST_HONEY = 3;
	public final static int NON_GRADIENT_HONEY = 4;
	public final static int BLACK = 5;
	public final static int BACK_COLOR = 6;
	public final static int GRADIENT_STEEL = 7;
	public final static int SMALLER_GRADIENT_STEEL = 8;
	public final static int SHINY_GRADIENT_STEEL = 9;
	
	
	public final static Color PANEL_FRONT = new Color(15, 15, 0);
	public final static Color BORDER_UNFOCUSED = new Color(60, 60, 30);
	public final static Color BORDER_FOCUSED = new Color(90, 90, 45);
	public final static Color HONEY_YELLOW_BRIGHT = new Color(255, 255, 220);
	public final static Color HONEY_YELLOW_DIM = new Color(250, 250, 50);
	public final static Color HONEY_FOR_TEXT = new Color(255, 214, 0);
	
	
	public final static Color BACKGROUND_COLOR = new Color(10,10,0);
	
	
	
	public final static Color TEXTFOCUSED = Color.WHITE;
	public final static Color NOTEXTFOCUSED = Color.GRAY;
	
	
	public static GradientPaint fill(int gradientColor, int height) {

		if (gradientColor == DEFAULT_BACKGROUND) {
			return new GradientPaint(0, -50, BACKGROUND_COLOR, 0,
					height, BACKGROUND_COLOR, false);
		} else if (gradientColor == MORE_HONEY) {
			return new GradientPaint(0, -50, new Color(255, 255, 0), 0, height,
					new Color(250, 250, 0), false);
		} else if (gradientColor == MOST_HONEY) {
			return new GradientPaint(0, -50, new Color(255, 240, 0), 0, height,
					new Color(255, 240, 0), false);
		} else if (gradientColor == NON_GRADIENT_HONEY) {
			return new GradientPaint(0, -50, new Color(250, 200, 0), 0, height,
					new Color(250, 200, 0), false);
		} else if (gradientColor == BLACK){
			return new GradientPaint(0, -50, new Color(100, 100, 100), 0,
					height, new Color(0, 0, 0), false);
		} else if(gradientColor == BACK_COLOR){
			return new GradientPaint(0, -50, new Color(60, 60, 30), 0,
					height, new Color(60, 60, 30), false);
		}else if(gradientColor==GRADIENT_STEEL){
			return new GradientPaint(-2000, -1500, new Color(255, 255, 255), 250,
					height, PANEL_FRONT, false);
		}else if(gradientColor==SMALLER_GRADIENT_STEEL){
			return new GradientPaint(-2000, -1500, new Color(255, 255, 255), -200,
					height, PANEL_FRONT, false);
		}else if(gradientColor==SHINY_GRADIENT_STEEL){
			return new GradientPaint(-2000, -1500, Color.WHITE, -1900,
					(int)(height*1.5), PANEL_FRONT, false);
		}
		else{
			return new GradientPaint(0, -50, PANEL_FRONT, 0,
					height, PANEL_FRONT, false);
		}
	}

}
