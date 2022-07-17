import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Class that extends Frame, instantiates controlPanel and drawingPanel to run our game
 */
public class App extends JFrame {

	public App() {
		super("SER Final Project");

		this.setLayout(new BorderLayout());

		JLabel label = new JLabel("Please Press the Start button to start playing.");

		DrawingPanel drawingPanel = new DrawingPanel();

		ControlPanel controlPanel = new ControlPanel(drawingPanel);

		this.setSize(1000, 800);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.add(controlPanel, BorderLayout.SOUTH);
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(label, BorderLayout.NORTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new App();
	}
}
