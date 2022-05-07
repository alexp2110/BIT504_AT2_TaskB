import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	private int xPosition, yPosition; 
	private int initialXPosition, initialYPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private Color colour;
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}

	public void setXPosition(int newXPosition) {
		this.xPosition = newXPosition;
	}
	
	public void setYPosition(int newYPosition) {
		this.yPosition = newYPosition;
	}
	
	public void setXPosition(int newXPosition, int panelWidth) {
		this.xPosition = newXPosition;
		if(newXPosition < 0) {
			this.xPosition = 0;
		}
		else if(newXPosition + width > panelWidth) {
			this.xPosition = panelWidth - width;
		}
	}
	
	public void setYPosition(int newYPosition, int panelHeight) {
		this.yPosition = newYPosition;
		if(newYPosition < 0) {
			this.yPosition = 0;
		}
		else if(newYPosition + height > panelHeight) {
			this.yPosition = panelHeight - height;
		}
	}
	
	public void setInitialPosition(int initialX, int initialY) {
		this.initialXPosition = initialX;
		this.initialYPosition = initialY;
	}
	
	public void resetToInitialPosition() {
		setXPosition(this.initialXPosition);
		setYPosition(this.initialYPosition);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
	
	public int getXVelocity() {
		return xVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public void setXVelocity(int newXVelocity) {
		this.xVelocity = newXVelocity;
	}
	
	public void setYVelocity(int newYVelocity) {
		this.yVelocity = newYVelocity;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
		
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setColour(Color newColour) {
		this.colour = newColour;
	}
}
