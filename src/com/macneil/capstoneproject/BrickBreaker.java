package com.macneil.capstoneproject;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

/*
 * TO DO LIST
 * 
 * IMPLEMENT LIVES SYSTEM
 * IMPLEMENT GAMEOVER SCREEN
 * IMPLEMENT GAME WINNING SCREEN (WILL HAVE TO DO WITH NUMBER OF BRICKS ON SCREEN)
 * 
 * IMPLEMENT DIFFERENT LEVELS SETTING UP BRICKS AT THE START
 * WANT TO KEEP THIS AS GENERAL AS POSSIBLE SO CODE IS DOWN
 */


public class BrickBreaker extends PApplet {

	int ballX, ballY;
	int ballSize = 20;
	int racketWidth = 80;
	int racketHeight = 10;
	int ballSpeedVert = 8;
	int ballSpeedHori = 1;
	//Stores the bricks in the game so that we know which ones to draw
	ArrayList<Brick> bricks = new ArrayList<>();
	//Store the button we create so that we can make decisions with them later
	ArrayList<Button> buttons = new ArrayList<>();
	Button button;
	private int buttonWidth = 255;
	private int buttonHeight = 75;
	//Game starts off at the intial screen 0
	private int gameScreen = 0;

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "com.macneil.capstoneproject.BrickBreaker" });
	}

	public void settings() {
		size(800, 1200);
	}

	public void setup() {
		size(800, 1200);
		ballX = width / 2;
		ballY = height - 40;
		drawLevelOne();
	}

	public void draw() {
		switch (gameScreen) {
		case 0:
			init();
			break;
		case 1:
			levelOne();
			break;
		}

	}

	void init() {
		background(0);
		fill(255);
		text("Please Choose A Level", width/2, 200);
		textSize(30);
		textAlign(CENTER);
		fill(255);
		mainMenu();

	}
	void mainMenu() {
		String label = "";
		float x = width/2;
		float y = 700;
		//Using hard values, not good practice but these should not change
		for(int i = 0; i < 3; i++) {
		fill(255);
		rect(x, y + (i*100), buttonWidth, buttonHeight);
		rectMode(CENTER);
		switch(i) {
		case 0:
			label = "LEVEL ONE";
			break;
		case 1:
			label = "LEVEL TWO";
			break;
		case 2:
			label = "LEVEL THREE";
			break;
		}
		fill(20);
		//Spaced out by 100(x) + 10 due to text size
		text(label, x, (y+10+(i*100)));
		//Adding button info so that it is accessible later by our main menu
		buttons.add(new Button(x, y+(i*100), label));
		}
	}
	void levelOne() {
		background(255);
		drawBall(ballX, ballY);
		drawRacket();
		drawBricks();
		drawBricks();
		veriticalMovement();
		horizontalMovement();
		hitWalls();
		hitRacket();
		hitBrick();
	}

	/*
	 * DRAWING RESOURCES
	 */
	void drawBall(int ballX, int ballY) {
		fill(0);
		ellipse(ballX, ballY, ballSize, ballSize);
	}

	void drawRacket() {
		fill(color(255, 255, 0));
		rectMode(CENTER);
		rect(mouseX, height - 20, racketWidth, racketHeight);
	}

	public void drawLevelOne() {
		// This sets up the bricks in the level
		// TIER ONE
		int bWidth = 80;
		for (int i = 0; i < 10; i++) {
			// TIER ONE
			bricks.add(new Brick((i * bWidth), 0));
			// TIER TWO
			bricks.add(new Brick((i * bWidth), 70));
			// TIER THREE
			bricks.add(new Brick((i * bWidth), 140));
			// TIER FOUR
			bricks.add(new Brick((i * bWidth), 210));
			// TIER FIVE
			bricks.add(new Brick((i * bWidth), 280));
		}
		// TIER 2

		// TIER3
	}

	void drawBricks() {
		for (Brick newBrick : bricks) {
			fill(newBrick.getColor());
			rectMode(CENTER);
			stroke(204, 102, 0);
			rect(newBrick.getxCor(), newBrick.getyCor(), newBrick.getWidth(), newBrick.getHeight());
		}
	}

	/*
	 * BALL MOVEMENT
	 */
	void veriticalMovement() {
		ballY += ballSpeedVert;
	}

	void horizontalMovement() {
		ballX += ballSpeedHori;
	}

	/*
	 * COLLISION MECHANICS
	 */
	void hitWalls() {
		if (ballX + (ballSize / 2) > width) {
			bounceRight(width);
		}
		if (ballX - (ballSize / 2) < 0) {
			bounceLeft(0);
		}
		if (ballY + (ballSize / 2) > height) {
			bounceUp(height);
		}
		if (ballY - (ballSize / 2) < 0) {
			bounceDown(0);
		}
	}

	void hitRacket() {
		if (ballX + (ballSize / 2) > (mouseX - racketWidth) && (ballX - (ballSize / 2) < (mouseX + racketWidth))) {
			if (ballY + (ballSize / 2) > (height - 20)) {
				bounceUp(height - 20);
				ballSpeedHori = (ballX - mouseX) / 5;
			}
		}
	}

	void hitBrick() {
		// See if it is in line with any one of the bricks
		Iterator<Brick> b = bricks.iterator();
		while (b.hasNext()) {
			Brick brick = b.next();
			// Setting up reusable values
			float brX = brick.getxCor();
			float brY = brick.getyCor();
			float brW = brick.getWidth();
			float brH = brick.getHeight();
			// Rect Sides
			float brLeft = brX - brW / 2;
			float brRight = brX + brW / 2;
			float brTop = brY - brH / 2;
			float brBottom = brY + brH / 2;
			// Ball sides
			float baLeft = ballX - ballSize / 2;
			float baRight = ballX + ballSize / 2;
			float baTop = ballY - ballSize / 2;
			float baBottom = ballY + ballSize / 2;

			if ((baRight >= brLeft) && (baLeft <= brRight)) {
				// It's between the bottom of the brick and the middle
				if ((baTop <= brBottom) && (baTop >= brY)) {
					// If true change vertical momentum
					brick.hit();
					if (brick.getDelete()) {
						bricks.remove(brick);
					}
					bounceDown(brBottom);
					return;
				}
				// It's between the top of the brick and middle
				if ((baBottom >= brTop) && (baBottom <= brY)) {
					// Change vertical movement
					brick.hit();
					if (brick.getDelete()) {
						bricks.remove(brick);
					}
					bounceUp(brTop);
					return;
				}
			}
			if ((baTop <= brBottom) && (baBottom >= brTop)) {
				// It's between the right side of the brick and the middle
				if ((baLeft <= brRight) && (baLeft >= brX)) {
					// Change horizontal movement
					brick.hit();
					if (brick.getDelete()) {
						bricks.remove(brick);
					}
					bounceLeft(brRight);

					return;
				}
				// It's between the left side of the brick and the middle
				if ((baRight >= brLeft) && (baRight <= brX)) {
					// Change horizontal movement
					brick.hit();
					if (brick.getDelete()) {
						bricks.remove(brick);
					}
					bounceRight(brLeft);
					return;
				}
			}
		}
	}

	/*
	 * BOUNCING MECHANICS
	 */
	void bounceUp(float surface) {
		ballY = (int) surface - (ballSize / 2);
		ballSpeedVert *= -1;
		veriticalMovement();
	}

	void bounceDown(float surface) {
		ballY = (int) surface + (ballSize / 2);
		ballSpeedVert *= -1;
		veriticalMovement();
	}

	void bounceLeft(float surface) {
		ballX = (int) surface + (ballSize / 2);
		ballSpeedHori *= -1;
		horizontalMovement();
	}

	void bounceRight(float surface) {
		ballX = (int) surface - (ballSize / 2);
		ballSpeedHori *= -1;
		horizontalMovement();
	}
	
	/*
	 * USER INTERACTION
	 */
	public void mousePressed() {
		//If we are on the main menu
		if(gameScreen == 0) {
			//If we click level one...need coordinated of this
			for(Button button : buttons) {
				if(mouseX >= (button.getX() - (buttonWidth/2)) && (mouseX <= (button.getX() + (buttonWidth/2)))) {
					//The mouse was clicked in the same horizontal space as this button
					if(mouseY >= (button.getY() - (buttonHeight/2)) && (mouseY <= (button.getY() + (buttonHeight/2)))) {
						//The mouse is in the same vertical space as one of the buttons
						//Unsure of the button, so we can do a switch case to find it by label
						switch(button.getLabel()) {
						case "LEVEL ONE":
							gameScreen = 1;
							break;
						case "LEVEL TWO":
							//gameScreen = 2
							break;
						case "LEVEL THREE":
							//gameScreen = 3
							break;
						}
					}
				}
			}
		}
	}
	
	
	
	class Button extends PApplet{
		
		//Coordinates
		float x, y;
		String label;
		
		public Button(float x, float y, String label) {
			this.x = x;
			this.y = y;
			this.label = label;
		}
		public void setup() {
			size(200, 75);
		}
		public void draw() {
			display();
		}
		
		void display() {
			fill(200);
			rect(x, y, (textWidth(label) + 50), 75);
			fill(20);
			text(label, x, (y + 35));
		}
		public float getX() {
			return x;
		}
		public float getY() {
			return y;
		}
		public String getLabel() {
			return label;
		}
		
		
		
	}
}