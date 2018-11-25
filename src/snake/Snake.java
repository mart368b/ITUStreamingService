package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener{
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Snake g = new Snake();
		frame.add(g);
		frame.addKeyListener(g);
		frame.pack();
		frame.setVisible(true);
		g.run();
	}


	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	private static final Random rand = new Random();
	private static final int animationSpeed = 10;
	private static final int gameSpeed = 200;
	
	private boolean running = true;
	private int[] head;
	private ArrayList<int[]> tails;
	private int[] food;
	private int action = 0, lastAction = 0;
	private boolean animation = false;
	private ArrayList<int[]> spiralArr = new ArrayList<>(WIDTH*HEIGHT);
	private int sleepTime = gameSpeed;
	private int pauseTimer = 0, animationDisplayed = WIDTH*HEIGHT;
	
	
	public Snake() {
		super();
		setPreferredSize(new Dimension(WIDTH*20, HEIGHT*20));
		playSpiral(true);
	}
	
	public void reset() {
		this.tails = new ArrayList<int[]>();
		this.head = new int[]{WIDTH/2, HEIGHT/2};
		placeFood();
		action = 0;
		lastAction = 0;
	}

	private void placeFood() {
		ArrayList<int[]> availableSpots = new ArrayList<>(WIDTH*HEIGHT - (tails.size() + 1));
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				int[] cords = new int[] {x, y};
				boolean used = head[0] == cords[0] && head[1] == cords[1];
				if (used) {
					continue;
				}
				for(int[] tail: tails) {
					if (tail[0] == cords[0] && tail[1] == cords[1]) {
						used = true;
						break;
					}
				}
				if (!used) {
					availableSpots.add(cords);
				}
			}
		}
		if(availableSpots.size() == 0) {
			playSpiral(true);
			return;
		}
		food = availableSpots.get(rand.nextInt(availableSpots.size()));
	}

	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, WIDTH*20 - 1, HEIGHT*20 - 1);
		if (animation) {
			for (int i = 0; i < animationDisplayed; i++) {
				if (i >= spiralArr.size()) {
					break;
				}
				int[] segment = spiralArr.get(i);
				drawSquare(segment[0], segment[1], g);
			}
		}else {
			g.setColor(Color.BLACK);
			for (int[] tail: tails) {
				drawSquare(tail[0], tail[1], g);
			}
			drawSquare(head[0], head[1], g);
			g.setColor(Color.RED);
			drawSquare(food[0], food[1], g);
		}
	}
	
	public void drawSquare(int x, int y, Graphics g) {
		g.fillRect(x*20, y*20, 20, 20);
	}
	

	private void run() {
		while(running) {
			tick();
			repaint();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void tick() {
		if (animation) {
			if (animationDisplayed < WIDTH*HEIGHT) {
				animationDisplayed++;
			}else if (pauseTimer > 0) {
				pauseTimer--;
				return;
			}else {
				spiralArr.remove(0);
				if ( spiralArr.size() == 0) {
					animation = false;
					sleepTime = gameSpeed;
					reset();
				}				
			}
			return;
		}
		int[] lastPos = head.clone();
		switch (action) {
			case 0:
				moveHead( head[0] == 0, WIDTH - 1, -1, true);
				break;
			case 1:
				moveHead( head[1] == 0, HEIGHT - 1, -1, false);
				break;
			case 2:
				moveHead( head[0] == WIDTH - 1, -(WIDTH - 1), 1, true);
				break;
			case 3:
				moveHead(  head[1] == HEIGHT - 1, -(HEIGHT - 1), 1, false);
				break;
		}
		lastAction = action;
		for (int[] tail: tails) {
			if ( head[0]==tail[0] && head[1]==tail[1]) {
				animationDisplayed = 0;
				playSpiral(false);
			}
		}
		if ( head[0]==food[0] && head[1]==food[1]) {
			tails.add(lastPos);
			placeFood();
		}else if (tails.size() > 0){
			shiftTails(lastPos);
		}
	}
	
	private void intitalizeSpiral(boolean direction) {
		int horizontalSide = WIDTH , verticalSide = HEIGHT, count = 1, swap = 0, placed = 0;
		int x = -1, y = 0, dx = 1, dy = 0, temp = 0;
		boolean dir = true;
		spiralArr.clear();
		while ( placed < WIDTH*HEIGHT && horizontalSide > 0 && verticalSide > 0) {
			int sideLength = dir? horizontalSide: verticalSide;
			placed += sideLength;
			for(int i = 0; i < sideLength; i++) {
				x += dx;
				y += dy;
				if (direction) {
					spiralArr.add(0, new int[] {x, y});
				}else {
					spiralArr.add(new int[] {x, y});
				}
			}
			if (++count == 2) {
				count = 0;
			}
			if (++swap == 2) {
				dx = -dx;
				dy = -dy;
				swap = 0;
			}
			dir = !dir;
			if (dir) {
				horizontalSide--;
			}else {
				verticalSide--;
			}
			temp = dx;
			dx = dy;
			dy = temp;
		}
	}

	private void playSpiral(boolean b) {
		intitalizeSpiral(b);
		animation = true;
		sleepTime = animationSpeed;
		pauseTimer = 20;
	}

	private void shiftTails(int[] cords) {
		for(int i = 0; i < tails.size() - 1; i++) {
			tails.set(i, tails.get(i + 1));
		}
		tails.set(tails.size() - 1, cords);
	}

	private void moveHead(boolean criteria, int snapMovement, int normalMovement, boolean horizontal) {
		int index = horizontal? 0: 1;
		if (criteria) {
			head[index] = head[index] +  snapMovement;
		}else {
			head[index] = head[index] + normalMovement;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A: case KeyEvent.VK_LEFT:
				action = 0;
				break;
			case KeyEvent.VK_W: case KeyEvent.VK_UP:
				action = 1;
				break;
			case KeyEvent.VK_D: case KeyEvent.VK_RIGHT:
				action = 2;
				break;
			case KeyEvent.VK_S: case KeyEvent.VK_DOWN:
				action = 3;
				break;
		}
		if ( Math.abs(lastAction - action) == 2 ) {
			action = lastAction;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	

}
