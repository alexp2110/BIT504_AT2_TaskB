import javax.swing.JFrame;

public class Pong extends JFrame{
	
	final String windowTitle = "Pong";
	final int windowWidth = 800;
	final int windowHeight = 600;
	
	
	public Pong() {
		setTitle(windowTitle);
		setSize(windowWidth,windowHeight);
		setResizable(false);
		add(new PongPanel());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Pong();
			}
		});
	}
}
