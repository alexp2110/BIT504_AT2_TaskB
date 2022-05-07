import java.awt.Color;

public class Paddle extends Sprite {
	final static Color paddleColour = Color.WHITE;
	final static int paddleWidth = 10;
	final static int paddleHeight = 100;
	final static int distanceFromEdge = 40;
	
	public Paddle(Player player, int panelWidth, int panelHeight) {
		setColour(paddleColour);
		setWidth(paddleWidth);
		setHeight(paddleHeight);
		int xPos;
		if(player == Player.One) {
			xPos = distanceFromEdge;
		}
		else {
			xPos = panelWidth - distanceFromEdge - getWidth();
		}
		setInitialPosition(xPos, panelHeight / 2 - (getHeight() / 2));
		resetToInitialPosition();
	}
}
