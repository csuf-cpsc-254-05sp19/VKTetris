//package tetrisVK;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

//**added libraries to implement images and JOptionPane**
import javax.swing.*;
//javax.swing.ImageIcon;
//javax.swing.JOptionPane;

public class VKTetris extends JPanel{

	private static final long serialVersionUID = -8715353373678321308L;

	static int initial=1000;
	
	private final Point[][][] Tetraminos = {
			// I-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
			},
			
			// J-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
			},
			
			// L-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
			},
			
			// O-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},
			
			// S-Piece
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},
			
			// T-Piece
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
			},
			
			// Z-Piece
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};
	
	private final Color[] tetraminoColors = {
		Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.magenta, Color.red
	};
	
	private Point pieceOrigin;
	private int currentPiece;
	private int rotation;
	private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

	private long score;
	private Color[][] well;
	//**made a bool variable that checks for game over**
	//**keeps game going if player did not lose yet**
	private boolean isGameOver = false;
	
	// Creates a border around the well and initializes the dropping piece
	private void init() {
		well = new Color[12][24];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					well[i][j] = Color.WHITE;
				} else {
					well[i][j] = Color.BLACK;
				}
				well[0][2]=Color.RED;
				well[11][2]=Color.RED;
			}
		}
		newPiece();
	}
	
	// Put a new, random piece into the dropping position
	public void newPiece() {
		pieceOrigin = new Point(5, 2);
		rotation = 0;
		if (nextPieces.isEmpty()) {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPieces);
		}
		currentPiece = nextPieces.get(0);
		nextPieces.remove(0);
	}

	//**ADDED A FUNCTION TO CHECK FOR GAME OVER**
	public boolean checkGameOver()
	{
		for(int i = 1; i < 11; i++)
		{
					
			if(well[i][2] != Color.BLACK)
			{
				isGameOver = true;
			}
		}
		
		return isGameOver;
	}
	
	// Collision test for the dropping piece
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			if (well[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}
	
	// Rotate the piece clockwise or counterclockwise
	public void rotate(int i) {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
			rotation = newRotation;
		}
		repaint();
	}
	
	// Move the piece left or right
	public void move(int i) {
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
			pieceOrigin.x += i;	
		}
		repaint();
	}
	
	// Drops the piece one line or fixes it to the well if it can't drop
	public void dropDown() {
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
			pieceOrigin.y += 1;
		} else {
			fixToWell();
		}	
		repaint();
	}
	
	// Make the dropping piece part of the well, so it is available for
	// collision detection.
	public void fixToWell() {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];
		}
		//clearRows();
		//newPiece();

		//**TEST FOR GAME OVER WHEN PLACING BLOCKS IN WELL**
		if(checkGameOver() == true)
		{
			ImageIcon gameOver = new ImageIcon("/home/student/OpenSourceProject/VKTetris/gameOver.gif");
		    	UIManager.put("OptionPane.okButtonText", "END GAME");
			JOptionPane.showMessageDialog(null, new JLabel(" ", gameOver, JLabel.CENTER), "You're in the End Game now", JOptionPane.PLAIN_MESSAGE);
		
			System.exit(0);
		}
		else
		{
			clearRows();
			newPiece();
		}
	}
	
	public void deleteRow(int row) {
		for (int j = row-1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				well[i][j+1] = well[i][j];
			}
		}
	}
	
	// Clear completed rows from the field and award score according to
	// the number of simultaneously cleared rows.
	public void clearRows() {
		boolean gap;
		int numClears = 0;
		
		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (well[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}
		
		switch (numClears) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
	}
	
	// Draw the falling piece
	private void drawPiece(Graphics g) {		
		g.setColor(tetraminoColors[currentPiece]);
		for (Point p : Tetraminos[currentPiece][rotation]) {
			g.fillRect((p.x + pieceOrigin.x) * 26, 
					   (p.y + pieceOrigin.y) * 26, 
					   25, 25);
		}
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		// Paint the well
		g.fillRect(0, 0, 26*12, 26*23);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(well[i][j]);
				g.fillRect(26*i, 26*j, 25, 25);
			}
		}
		
		// Display the score
		g.setColor(Color.WHITE);
		g.drawString("" + score, 19*12, 25);
		
		// Draw the currently falling piece
		drawPiece(g);
	}

	public static void main(String[] args) {
		//**CHANGED ADDED START PAGE**
		ImageIcon intro = new ImageIcon("/home/student/OpenSourceProject/VKTetris/tet.gif");
	    	UIManager.put("OptionPane.okButtonText", "START");
		JOptionPane.showMessageDialog(null, new JLabel(" ", intro, JLabel.CENTER), "Welcome", JOptionPane.PLAIN_MESSAGE);
		//**ADDED HOW TO PLAY**
		ImageIcon howTo = new ImageIcon("/home/student/OpenSourceProject/VKTetris/howTo.png");
		UIManager.put("OptionPane.okButtonText", "OK");
		JOptionPane.showMessageDialog(null, new JLabel(" ", howTo, JLabel.CENTER), "RULES", JOptionPane.PLAIN_MESSAGE);
		
		
		JFrame f = new JFrame("VKTetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(12*26+10, 26*24+25);
		
		final VKTetris game = new VKTetris();
		game.init();
		f.add(game);
		//**CENTERED GAME**
		f.setLocationRelativeTo(null);

		//ADDED JMENUBAR
		JMenu levels= new JMenu("Levels");
		JMenu help= new JMenu("Help");
		JMenu restart= new JMenu("Restart");
		JMenuItem ez= new JMenuItem("Easy");
		JMenuItem med= new JMenuItem("Medium");
		JMenuItem hard= new JMenuItem("Hard");
		JMenuItem extreme= new JMenuItem("Extreme");
		JMenuBar mainBar= new JMenuBar();
		f.setJMenuBar(mainBar);
		mainBar.add(levels);
		mainBar.add(help);
		mainBar.add(restart);
		levels.add(ez);
		levels.add(med);
		levels.add(hard);
		levels.add(extreme);
		
		// Keyboard controls
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				//**CHANGED FROM ROTATING TO DROP DOWN**
				case KeyEvent.VK_DOWN:
					game.dropDown();
					break;
				case KeyEvent.VK_LEFT:
					game.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					game.move(+1);
					break;
					
				//CHANGE: Speed drop down with space
				case KeyEvent.VK_SPACE:
					game.dropDown();
					break;
					
				//CHANGED: Added ESC 
				case KeyEvent.VK_ESCAPE:
				   System.exit(0);
				   break;

				} 
				
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});
		
		// Make the falling piece drop every second
		new Thread() {
			@Override public void run() {
				while (true) {
					try {
						Thread.sleep(1000); //1000 is one second
						game.dropDown();
						
						//**our mod = make the pieces fall faster at a certain score**
						if(game.score >= 1000)
						{
							//if the score is greater or equal to 4000
							if(game.score >= 4000)
							{
								Thread.sleep(60);
								game.dropDown();
							}
							else
							{
								Thread.sleep(300);
								game.dropDown();
							}
						}
					} catch ( InterruptedException e ) {}
				}
			}
		}.start();
		
		f.setVisible(true);
	}

}
