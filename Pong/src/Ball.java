import java.awt.Color;

public class Ball extends Sprite{
	final static Color ballColour = Color.WHITE;
	final static int ballWidth = 25;
	final static int ballHeight = 25;
	
	public Ball(int panelWidth, int panelHeight) {
		setColour(ballColour);
		setWidth(ballWidth);
		setHeight(ballHeight);
		setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
		resetToInitialPosition();
	}
}
