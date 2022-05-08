import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Font;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	private final static Color backgroundColour = Color.WHITE;
	private final static int timerDelay = 5;
	private final int ballMovementSpeed = 6;
	private final int paddleMovementSpeed = 5;
	private final static int pointsToWin = 11;
	private final static int scoreFontXPadding = 100;
	private final static int scoreFontYPadding = 100;
	private final static int scoreFontSize = 50;
	private final static int winFontXPadding = 200;
	private final static int winFontYPadding = 300;
	private final static int winFontSize = 60;
	private final static String font = "Serif";
	private final static String winMessage = "WIN!";
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	Ball ball;
	GameState gameState = GameState.Initialising;
	Paddle paddle1, paddle2;
	
	public PongPanel() {
		setBackground(backgroundColour);
		Timer timer = new Timer(timerDelay, this);
			timer.start();
			addKeyListener(this);
			setFocusable(true);
	}
		
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP) {
            paddle2.setYVelocity(-paddleMovementSpeed);
		} else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(paddleMovementSpeed);
        }
		
		if(event.getKeyCode() == KeyEvent.VK_W) {
            paddle1.setYVelocity(-paddleMovementSpeed);
		} else if(event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setYVelocity(paddleMovementSpeed);
        }
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(0);
        }
		
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setYVelocity(0);
        }
		
	}

	private void update() {
		switch(gameState) {
		case Initialising: {
			createObjects();
			gameState = GameState.Playing;
			ball.setXVelocity(ballMovementSpeed);
			ball.setYVelocity(ballMovementSpeed);
			break;
		}
		case Playing: {
			moveObject(paddle1); // move paddle 1
			moveObject(paddle2); // move paddle 2
			moveObject(ball);	// move ball
			checkWallBounce();	// check for wall bounce
			checkPaddleBounce(); // check for paddle bounce
			checkWin();
			break;
		}
		case GameOver: {
			break;
		}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.Initialising) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWin(g, gameWinner);
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
			g2d.setStroke(dashed);
			g2d.setPaint(Color.WHITE);
			g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
			g2d.dispose();
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void moveObject(Sprite obj) {
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(), getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(), getHeight());
	}
	
	private void checkWallBounce() {
		int xVelocity, yVelocity;
		
		if (ball.getXPosition() <= 0) { // Ball has hit the left side of the screen.
			xVelocity = ball.getXVelocity() * -1;
			addScore(Player.Two);
			resetBall();
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) { // Ball has hit the right side of the screen.
			xVelocity = ball.getXVelocity() * -1;
			addScore(Player.One);
			resetBall();
		}
		else {
			xVelocity = ball.getXVelocity();
		}
		
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) { // Ball has hit the top of the screen.
			yVelocity = ball.getYVelocity() * -1;
		}
		else {
			yVelocity = ball.getYVelocity();
		}
		ball.setXVelocity(xVelocity);
		ball.setYVelocity(yVelocity);
	}
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	private void checkPaddleBounce() {
		if(ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setXVelocity(ballMovementSpeed);
		}
		if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setXVelocity(-ballMovementSpeed);
		}
	}
	
	private void addScore(Player player) {
		if(player == Player.One) {
			player1Score++;
		}
		if(player == Player.Two) {
			player2Score++;
		}
	}
	
	private void checkWin() {
		if(player1Score >= pointsToWin) {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		else if (player2Score >= pointsToWin) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
	
	private void paintScores(Graphics g) {
		Font scoreFont = new Font(font, Font.BOLD, scoreFontSize);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		int rightScoreWidth = g.getFontMetrics().stringWidth(rightScore);
		g.setFont(scoreFont);
		g.drawString(leftScore, scoreFontXPadding, scoreFontYPadding);
		g.drawString(rightScore, getWidth() - scoreFontXPadding - rightScoreWidth, scoreFontYPadding);
	}
	
	private void paintWin(Graphics g, Player player) {
		Font winFont = new Font(font, Font.BOLD, winFontSize);
		int winMessageWidth = g.getFontMetrics().stringWidth(winMessage);
		g.setFont(winFont);
		if(player == Player.One) {
			g.drawString(winMessage, getWidth() - winFontXPadding - winMessageWidth, winFontYPadding);
		}
		if(player == Player.Two) {
			g.drawString(winMessage, winFontXPadding, winFontYPadding);
		}
	}
}
