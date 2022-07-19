import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	/*
	 * Buttons that will appear at the bottom of our frame
	 */
	private JButton button1, button2;

	public ControlPanel(DrawingPanel drawingPanel) {
		drawingPanel.controlPanel = this;

		this.setLayout(new GridLayout());

		/*
		 * Button Logic will start the game 
		 */
		button1 = new JButton("Start");
		button1.setFocusable(false);
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawingPanel.state.setData(DrawingPanel.GameState.STARTED);
				button1.setEnabled(false);
			}
		});
		button1.setFocusable(false);
		/*
		 * Button Logic will pause / resume the game from its operations
		 */
		button2 = new JButton("Pause");
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawingPanel.pauseGame();

				if (drawingPanel.state.getData() == DrawingPanel.GameState.PAUSED) {
					button2.setText("Continue");
				} else {
					button2.setText("Pause");

				}
			}

		});
		button2.setFocusable(false);

		this.add(button1);
		this.add(button2);

	}
	/*
	 * Method used to play game again
	 */
	public void resetStartButton() {
		this.button1.setEnabled(true);
	}
}
