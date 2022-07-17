import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel {
	public static enum GameState {
		NOT_STARTED,
		STARTED,
		UNPAUSED,
		PAUSED,
		WON,
		LOST
	};

	public Holder<GameState> state = new Holder<GameState>(null);
	public ControlPanel controlPanel;
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private Sprite enemy, objective, player;
	private ArrayList<Sprite> enemies = new ArrayList<Sprite>();
	private Timer timer;
	private int x;

	

	/*
	 * The clownfish is the player. The sea anemone is the objective and the
	 * sea mines are the enemies. I don't know if you had a specific theme in mind
	 * or if you already found.
	 */
	
	public DrawingPanel() {
		super();

		state.setCallback(newState -> {
			switch (newState) {
				case NOT_STARTED:
					// the game just started up
					this.reset();
					break;
				case STARTED:
					// the game is starting!
					this.reset();
					this.startGame();
					this.timer.start();
					break;
				case UNPAUSED:
					// unpaused
					// different from started
					this.timer.start();
					break;
				case PAUSED:
					// the game is being paused
					this.timer.stop();
					break;
				case WON:
					// they won just now
					this.timer.stop();
					this.controlPanel.resetStartButton();
					repaint();
					break;
				case LOST:
					// they lost just now
					this.timer.stop();
					this.controlPanel.resetStartButton();
					repaint();
					break;
			}
		});

		Random rand = new Random();

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				player.tick();
				
				for (int i = 0; i < enemies.size(); i++) {

					Sprite enemy = enemies.get(i);
					
					enemy.setLocation(enemy.getX(), enemy.getY() + 7);
					// we don't want the player to leave the screen..
					if (DrawingPanel.this.getHeight() == player.getY()+ player.getHeight()) {
						player.setY(650);
					} else if (player.getY() == 0) {
						player.setY(10);
					} else if (player.getX() >= DrawingPanel.this.getWidth()) {
						player.setX(900);
					} else if (player.getX() == 0) {
						player.setX(10);
					}

					if (enemy.getY()  > DrawingPanel.this.getHeight()) {
						enemies.remove(i);
					}
					
					// check if enemy intersects with player or player intersects with the objective
					if (enemy.intersect(player)) {
						state.setData(GameState.LOST);
						return;
					} else if (player.intersect(objective)) {
						state.setData(GameState.WON);
						return;
					}

					if (enemies.size() == 0) {
						int random = rand.nextInt(1, 15);
						for (int j = 0; j < random; j++) {

							try {
								enemy = new Sprite("./images/mine.png") {

									@Override
									public void tick() {}

								};
								enemy.setWidth(enemy.getWidth() / 8);
								enemy.setHeight(enemy.getHeight() / 8);
								enemies.add(enemy);

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
						x = rand.nextInt(10, 900);
						enemies.get(i).setX(x);

					}
				

				repaint();
					}
				}
		});

		// Key configs for our game
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = true;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

					downPressed = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

					leftPressed = true;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

					rightPressed = true;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

					downPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

					leftPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

					rightPressed = false;
				}
			}

		});

		try {
			player = new Sprite("./images/clown-fish.png") {

				@Override
				public void tick() {
					// TODO Auto-generated method stub
					if (upPressed) {
						// do something
						player.setLocation(player.getX(), player.getY() - 2);
					} else if (downPressed) {
						// do something
						player.setLocation(player.getX(), player.getY() + 2);
					} else if (leftPressed) {
						// do something
						player.setLocation(player.getX() - 2, player.getY());
					} else if (rightPressed) {
						// do something
						player.setLocation(player.getX() + 2, player.getY());
					}
				}
			};

			player.setWidth(player.getWidth() / 12);
			player.setHeight(player.getHeight() / 10);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.state.setData(GameState.NOT_STARTED);
	}

	public void reset() {
		this.setBackground(new Color(255,255,255)); // for the game, but can be changed to a different color.

		// Instiate our Sprites with the abstract method from sprite class
		try {
			enemy = new Sprite("./images/mine.png") {

				@Override
				public void tick() {
					// TODO Auto-generated method stub

				}

			};
			enemy.setWidth(enemy.getWidth() / 8);
			enemy.setHeight(enemy.getHeight() / 8);
			enemy.setLocation(400, 200);

			objective = new Sprite("./images/home.png") {

				@Override
				public void tick() {
					// TODO Auto-generated method stub

				}

			};
			objective.setWidth(objective.getWidth() / 10);
			objective.setHeight(objective.getHeight() / 10);
			objective.setLocation(900, 200);

			player.setLocation(50, 100);

		} catch (IOException e1) {
			// Will trigger an exception if images were unable to load.
			e1.printStackTrace();
		}
		setFocusable(true);

		// implementing movement logic for the player
	}

	// will start the game when the start button is pressed (NEEDS WORK)
	public void startGame() {
		enemies.clear();
		Random rand = new Random();
		int random = rand.nextInt(1, 15);
		for (int j = 0; j < random; j++) {

			try {
				enemy = new Sprite("./images/mine.png") {

					@Override
					public void tick() {}

				};

				enemy.setWidth(enemy.getWidth() / 8);
				enemy.setHeight(enemy.getHeight() / 8);
				enemy.setLocation(900, 200);
				enemies.add(enemy);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}

	// Stops the timer in the drawing panel constructor
	public void pauseGame() {
		if (state.getData() == GameState.PAUSED) {
			state.setData(GameState.UNPAUSED);
		} else if (state.getData() == GameState.STARTED || state.getData() == GameState.UNPAUSED) {
			state.setData(GameState.PAUSED);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		// paint components
		if (player != null) player.paint(brush);
		if (objective != null) objective.paint(brush);
		if (enemy != null) enemy.paint(brush);
		// will paint the enemies for the game
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(brush);
		}

		switch (state.getData()) {
			case WON:
				brush.setFont(new Font(" Serif", Font.BOLD, 70));
				brush.setColor(Color.BLACK);
				brush.drawString("YOU WIN", 325, 400);
				break;
			case LOST:
				brush.setFont(new Font(" Serif", Font.BOLD, 70));
				brush.setColor(Color.BLACK);
				brush.drawString("TRY AGAIN", 325, 400);
				break;
			default:
				// nothing special
				break;
		}
	}

}
