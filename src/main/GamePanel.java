package main;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize = 16; // pixel size of objects
	final int scale = 3;
	final int tileSize = originalTileSize * scale;
	
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int ScreenWidth = tileSize * maxScreenCol;
	final int ScreenHeight = tileSize * maxScreenRow;
	
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler();
	
	// Set Player's default position

	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	public GamePanel() {
		this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); // this mean GamePanel class
		gameThread.start();
	}
	/*@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/100;
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(gameThread != null) {
			update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 100000; // nano to milli
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	public void run() {
		double drawInterval = 1000000000/60;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		// Created for Show FPS
		long timer = 0;
		long drawCount = 0;
		while(gameThread!= null) {
			currentTime = System.nanoTime();
			timer+=(currentTime - lastTime);
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if(delta>=1) {
				update();
				repaint();
				delta--;
				drawCount++;
				 
			}
			if(timer >=1000000000) {
				System.out.println("FPS : " + drawCount);;
				drawCount = 0;
				timer=0;
			}
			
		}
	}
	public void update() {
		// W-A-S-D 
		if(keyHandler.upPressed == true) { 
			playerY -= playerSpeed;
			
		}
		else if(keyHandler.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyHandler.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyHandler.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		g2.dispose();
		
	}
}
