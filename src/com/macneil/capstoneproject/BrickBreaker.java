package com.macneil.capstoneproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * TO DO LIST
 * 
 * 
 * 
 * MIGHT WANT TO INITIALIZE THE BUTTONS IN THE SETUP
 * 
 * 
 * SHIFT DOWN TO CHANGE EVERY 20 SECONDS?
 * 
 * DRAWING VARIABLE MIGHT NEED TO BE RENAMED
 * 
 * PUT IN A RULES BUTTON
 * 
 * WHEN YOU WIN, I WANT TO MAKE IT SO THAT THE BALL CAN BOUNCE AS MUCH AS IT WANTS....KEEP IT SO THAT YOU HAVE
 * TO PRESS A BUTTON TO GO TO THE NEXT LEVEL...GET RID OF THE YOU WON SCREEN AND JUST ADD THOSE THINGS TO THAT
 * GAMEPLAY PAGE
 * 
 * MAKE A BALL CLASS SO IN THE FUTURE YOU CAN START WITH MORE BALLS, BUT THAT ADDS IN SOME ISSUES WITH
 * YOU ONLY LOSE A LIFE WHEN ALL BALLS ARE GONE, SO WE NEED TO KNOW HOW MANY ARE ON SCREEN AT ONCE. 
 * 
 * THEN NEED A POWER UP CLASS TO GO INTO BRICKS, SO BRICK CLASS NEEDS TO CHANGE BUT THOSE THINGS CAN BE 
 * CHOSEN WITHIN THE BRICK CLASS AND DON'T NEED TO BE SEEN IN THE BRICKBREAKER CLASS. 
 * 
 * MADE SECRET LEVEL THAT IS ALWAYS RANDOM. I WANT TO MAKE IT SO YOU CAN'T LOSE THOUGH. MAYBE HAVE A VALUE THAT HAS TO BE CHECKED
 * 
 * THE PHYSICS SEEM JANKY WITH THE IMAGES AS THE BRICKS...AND WHEN THE BALL HITS THE PADDLE.
 * 
 * 	NEED TO ADD THE FACT THAT WHEN THE FLAG (levelWin) THAT THE SCREEN DOESNT CHANGE
 * 
 */

public class BrickBreaker extends PApplet {
	// Constants for the setGradiant Function
	final int Y_AXIS = 1;
	final int X_AXIS = 2;
	static PImage brickImg1, brickImg2, brickImg3, backgroundImg;

	// This is set so setting up the brick array only happens once. will look into
	// it more
	boolean drawing = true;
	// If we need to display the rules
	boolean rules = false;
	// If the level is won then this flag is set and you can't die..could be a power
	// up later on
	boolean levelWon = false;
	//To speed up the ball. At this moment can only activated when game won for the time being
	boolean speedUp = true;
	//So that the level winning menu won't display on certain levels
	boolean displayMenu = true;
	// Set up of variables for the ball
	int ballX, ballY;
	final int ballSize = 20;
	// As of right now, the racket dimensions won't change in the game
	final int racketWidth = 80;
	final int racketHeight = 10;
	// These values may change soon
	int ballSpeedVert = 0;
	int ballSpeedHori = 0;
	int shootBallCount = 0;
	int mainMenuClickCount = 0;
	// Values to determine what screen your on and flow of game
	// playScreen let's the game over screen know which screen to go back to
	// Game starts off at the intial screen 0
	int gameScreen = 0;
	int playScreen = 0;
	int lives = 3;
	// This controls how much the blocks drop based on the hit variable
	int dropDown = 0;
	int hitPaddle = 0;
	// Stores the bricks in the game so that we know which ones to draw
	ArrayList<Brick> bricks = new ArrayList<>();
	// Store the button we create so that we can make decisions with them later
	// (main menu)
	ArrayList<Button> mainMenuButtons = new ArrayList<>();
	// Store buttons for the gameOver screen
	ArrayList<Button> gameOverButtons = new ArrayList<>();
	// Store buttons for the gameWin screen
	ArrayList<Button> gameWinButtons = new ArrayList<>();
	Button button;
	String[] rulesText = { "You start the game with three lives.", "Each time the ball hits the ground",
			"you lose a life. To win the game" + "you", "must destroy all of the bricks.",
			"The amount of times you must", "hit the brick is displayed on it." };
	int buttonWidth = 255;
	int buttonHeight = 75;
	int lastMouseX;

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "com.macneil.capstoneproject.BrickBreaker" });
	}

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		size(800, 800);
		ballX = width / 2;
		ballY = height - 60;
		backgroundImg = loadImage("gradientBackGround.jpg");
		brickImg1 = loadImage("brick1.jpg");
		brickImg2 = loadImage("brick2.jpg");
		brickImg3 = loadImage("brick3.jpg");
	}

	public void draw() {
		switch (gameScreen) {
		case 0:
			shootBallCount = 0;
			lives = 3;
			init();
			break;
		case 1:
			gamePlay();
			break;
		case 2:
			gamePlay();
			break;
		case 3:
			gamePlay();
			break;
		case 4:
			lives = 3;
			shootBallCount = 0;
			gameOver();
			break;
		case 9999:
			gamePlay();
			break;
		}

	}

	void init() {
		background(0);
		fill(255);
		text("Please Choose A Level", width / 2, 200);
		textSize(30);
		textAlign(CENTER);
		fill(255);
		if (rules) {
			rules();
		} else {
			mainMenu();
		}
	}

	void mainMenu() {
		String label = "";
		float x = width / 2;
		float y = height / 2;
		// Using hard values, not good practice but these should not change
		for (int i = 0; i < 4; i++) {
			fill(255);
			rect(x, y + (i * 100), buttonWidth, buttonHeight);
			rectMode(CENTER);
			switch (i) {
			case 0:
				label = "LEVEL ONE";
				break;
			case 1:
				label = "LEVEL TWO";
				break;
			case 2:
				label = "LEVEL THREE";
				break;
			case 3:
				label = "RULES";
				break;
			}
			fill(20);
			// Spaced out by 100(x) + 10 due to text size
			text(label, x, (y + 10 + (i * 100)));
			// Adding button info so that it is accessible later by our main menu
			mainMenuButtons.add(new Button(x, y + (i * 100), label));
		}
	}

	void rules() {
		float x = width / 2;
		float y = height / 2;
		fill(255);
		rectMode(CENTER);
		rect(x, (height / 3) * 2, x, y + 100, 90);
		fill(0);
		textAlign(CENTER);
		textSize(20);
		for (int i = 0; i < rulesText.length; i++) {
			text(rulesText[i], x, y + (i * 50));
		}
	}

	void gamePlay() {
		background(backgroundImg);
		switch (gameScreen) {
		case 1:
			drawText("Level One");
			break;
		case 2:
			drawText("Level Two");
			break;
		case 3:
			drawText("Level Three");
			break;
		case 9999:
			drawText("SECRET LEVEL");
			break;
		}
		if (shootBallCount < 1) {
			fill(0);
			textSize(20);
			textAlign(CENTER);
			text("Press Space To Start...", width / 2, (height / 2) + 150);
			ballX = mouseX;
			ballY = height - 60;
		}
		drawBall(ballX, ballY);
		drawRacket();
		if (hitPaddle == 10) {
			dropDown++;
			hitPaddle = 0;
			// Change all Y coordinates of the array to be 20 lower (One brick)
			for (Brick b : bricks) {
				b.setyCor(b.getyCor() + (20 * dropDown));
			}
		}
		drawBricks();
		veriticalMovement();
		horizontalMovement();
		hitWalls();
		hitRacket();
		hitBrick();
		if(checkWin()) {
			//So it has the ability not to display, like in the secret level
			if(displayMenu) {
			gameWin();
			hitButtons();
			}
			//Only called once so that it doesn't break the game
			if(speedUp) {
				ballSpeedHori *= 2;
				ballSpeedVert *= 2;
			}
			speedUp = false;
		}
		lastMouseX = mouseX;
	}

	void gameOver() {
		background(0);
		fill(color(255, 0, 0));
		textSize(70);
		text("GAME OVER", width / 2, 250);
		textAlign(CENTER);
		String label = "";
		float x = width / 2;
		float y = height / 2;
		textSize(40);
		for (int i = 0; i < 3; i++) {
			fill(255);
			rect(x, y + (i * 100), buttonWidth, buttonHeight);
			rectMode(CENTER);
			switch (i) {
			case 0:
				label = "RESTART";
				break;
			case 1:
				label = "LEVELS";
				break;
			case 2:
				label = "QUIT";
				break;
			}
			fill(20);
			// Spaced out by 100(x) + 10 due to text size
			text(label, x, (y + 12 + (i * 100)));
			// Adding button info so that it is accessible later by our main menu
			gameOverButtons.add(new Button(x, y + (i * 100), label));
		}
	}

	void gameWin() {
		fill(color(255, 255, 0));
		textSize(70);
		textAlign(CENTER);
		text("YOU WON!!!", width / 2, height / 3);
		String label = "";
		float x = width / 2;
		float y = (height / 2);
		textSize(40);
		for (int i = 0; i < 3; i++) {
			fill(255);
			rect(x, y + (i * 100), buttonWidth, buttonHeight);
			rectMode(CENTER);
			switch (i) {
			case 0:
				label = "NEXT LEVEL";
				break;
			case 1:
				label = "MAIN MENU";
				break;
			case 2:
				label = "QUIT";
				break;
			}
			fill(20);
			text(label, x, (y + 12 + (i * 100)));
			gameWinButtons.add(new Button(x, y + (i * 100), label));
		}
	}

	boolean checkWin() {
		if (bricks.size() == 0) {
			levelWon = true;
		}
		return levelWon;
	}

	/*
	 * DRAWING RESOURCES
	 */
	void drawBall(int ballX, int ballY) {
		fill(0);
		ellipse(ballX, ballY, ballSize, ballSize);
	}
	
	void resetBall() {
		//When you lose a life or go to the next level. The ball resets
		shootBallCount = 0;
		ballSpeedHori = 0;
		ballSpeedVert = 0;
	}

	void drawRacket() {
		fill(color(255, 255, 0));
		rectMode(CENTER);
		rect(mouseX, height - 20, racketWidth, racketHeight);
	}

	// Various different level schemas

	public void drawLevelOne() {
		int bWidth = 80;
		for (int i = 0; i < 1; i++) {
			// TIER ONE
			bricks.add(new Brick((i * bWidth), 0 + (dropDown * 20), 1, brickImg1, bWidth));
			// // TIER TWO
			// bricks.add(new Brick((i * bWidth), 70 + (dropDown * 20), 2, brickImg2,
			// bWidth));
			// // TIER THREE
			// bricks.add(new Brick((i * bWidth), 140 + (dropDown * 20), 3, brickImg3,
			// bWidth));
			// // TIER FOUR
			// bricks.add(new Brick((i * bWidth), 210 + (dropDown * 20), 2, brickImg2,
			// bWidth));
			// // TIER FIVE
			// bricks.add(new Brick((i * bWidth), 280 + (dropDown * 20), 1, brickImg1,
			// bWidth));
		}
	}

	public void drawLevelTwo() {
		int bWidth = 80;
		for (int j = 1; j < 3; j++) {
			for (int i = 0; i < 5; i++) {
				// TIER ONE
				bricks.add(new Brick(((i * bWidth) * 2), (j == 1) ? 0 + (dropDown * 20) : 120 + (dropDown * 20), 3,
						brickImg3, bWidth));
				// TIER TWO
				bricks.add(new Brick(((i * bWidth) * 2 + bWidth),
						(j == 1) ? 20 + (dropDown * 20) : 140 + (dropDown * 20), 2, brickImg2, bWidth));
				// TIER THREE
				bricks.add(new Brick(((i * bWidth) * 2), (j == 1) ? 40 + (dropDown * 20) : 160 + (dropDown * 20), 1,
						brickImg1, bWidth));
				// TIER FOUR
				bricks.add(new Brick(((i * bWidth) * 2 + bWidth),
						(j == 1) ? 60 + (dropDown * 20) : 180 + (dropDown * 20), 1, brickImg1, bWidth));
				// TIER FIVE
				bricks.add(new Brick(((i * bWidth) * 2), (j == 1) ? 80 + (dropDown * 20) : 200 + (dropDown * 20), 2,
						brickImg2, bWidth));
				// TIER SIX
				bricks.add(new Brick(((i * bWidth) * 2 + bWidth),
						(j == 1) ? 100 + (dropDown * 20) : 220 + (dropDown * 20), 3, brickImg3, bWidth));
			}
		}
	}

	public void drawLevelThree() {
		int bWidth = 80;
		for (int i = 0; i < 10; i++) {
			// TIER ONE
			if (i == 1 || i == 2) {
				bricks.add(new Brick(((i) * bWidth), 0 + (dropDown * 20), 3, brickImg3, bWidth));
			}
			// TIER TWO
			if (i == 0 || i == 3 || i == 5 || i == 6 || i == 8 || i == 9) {
				bricks.add(new Brick((i * bWidth), 40 + (dropDown * 20), 3, brickImg3, bWidth));
			}
			// TIER THREE
			if (i == 0 || i == 3 || i == 5 || i == 7 || i == 9) {
				bricks.add(new Brick((i * bWidth), 80 + (dropDown * 20), 3, brickImg3, bWidth));
			}
			// TIER FOUR
			if (i == 0 || i == 3 || i == 5 || i == 7 || i == 9) {
				bricks.add(new Brick((i * bWidth), 120 + (dropDown * 20), 3, brickImg3, bWidth));
			}
			// TIER FIVE
			if (!(i == 4 || i == 6 || i == 7 || i == 8)) {
				bricks.add(new Brick((i * bWidth), 160 + (dropDown * 20), 3, brickImg3, bWidth));
			}
			// TIER SIX
			if (i == 0 || i == 3 || i == 5 || i == 9) {
				bricks.add(new Brick((i * bWidth), 200 + (dropDown * 20), 3, brickImg3, bWidth));
			}
		}
	}

	public void drawAinsleighLevel() {
		int bWidth = 40;
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (rand.nextInt(2) == 1) {
					switch (rand.nextInt(3)) {
					case 0:
						bricks.add(new Brick((i * bWidth), j * 20 + (dropDown * 20), 1, brickImg1, bWidth));
						break;
					case 1:
						bricks.add(new Brick((i * bWidth), j * 20 + (dropDown * 20), 2, brickImg2, bWidth));
						break;
					case 2:
						bricks.add(new Brick((i * bWidth), j * 20 + (dropDown * 20), 3, brickImg3, bWidth));
						break;
					}
				}
			}
		}
	}

	/*
	 * Drawing the bricks from the array to the screen
	 */
	void drawBricks() {
		for (Brick newBrick : bricks) {
			image(newBrick.getImage(), newBrick.getxCor() - (newBrick.getWidth() / 2),
					newBrick.getyCor() - (newBrick.getHeight() / 2), newBrick.getWidth(), newBrick.getHeight());
			rectMode(CENTER);
			stroke(204, 102, 0);
			// rect(newBrick.getxCor(), newBrick.getyCor(), newBrick.getWidth(),
			// newBrick.getHeight());
			fill(255);
			textAlign(CENTER);
			textSize(20);
			text(newBrick.getHealth(), newBrick.getxCor(), newBrick.getyCor() + 7);
		}
	}

	void drawText(String s) {
		fill(0);
		textSize(20);
		textAlign(CENTER);
		text(s, width / 2, (height / 2) + 100);
		text("Lives left: " + lives, width / 2, (height / 2) + 125);
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
			if (!levelWon) {
				lives--;
				if (lives == 0) {
					gameScreen = 4;
				}
				resetBall();
			}else {
				//You can't die...the ball will continue to bounce (only happens when you win a level)
			bounceUp(height);
			}
		}
		if (ballY - (ballSize / 2) < 0) {
			bounceDown(0);
		}
	}

	void hitRacket() {
		if (ballX + (ballSize / 2) > (mouseX - racketWidth) && (ballX - (ballSize / 2) < (mouseX + racketWidth))) {
			if (ballY + (ballSize / 2) > (height - 20)) {
				bounceUp(height - 20);
				// Where the ball is compared to the racket gives it more speed but also how
				// much the racket
				// is being moved before it hits it as well. Not sure how this is affected by
				// coming it at
				// the ball from the right and hitting it on the right side (positive and
				// negative)
				ballSpeedHori = (ballX - mouseX) / 5 + ((mouseX - lastMouseX) / 4);
				hitPaddle++;
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
	
	//For when you win, I want the ball to interact with the buttons
	void hitButtons() {
		
		for(Button b : gameWinButtons) {
			// Setting up reusable values
			float buX = b.getX();
			float buY = b.getY();
			//Width == buttonWidth
			//Height == buttonHeight
			
			// Rect Sides
			float buLeft = buX - buttonWidth / 2;
			float buRight = buX + buttonWidth / 2;
			float buTop = buY - buttonHeight / 2;
			float buBottom = buY + buttonHeight / 2;
			// Ball sides
			float baLeft = ballX - ballSize / 2;
			float baRight = ballX + ballSize / 2;
			float baTop = ballY - ballSize / 2;
			float baBottom = ballY + ballSize / 2;
			
			//Same as hitting the bricks with some slight changes....
			if ((baRight >= buLeft) && (baLeft <= buRight)) {
				// It's between the bottom of the brick and the middle
				if ((baTop <= buBottom) && (baTop >= buY)) {
					// If true change vertical momentum
					bounceDown(buBottom);
					return;
				}
				// It's between the top of the brick and middle
				if ((baBottom >= buTop) && (baBottom <= buY)) {
					// Change vertical movement
					bounceUp(buTop);
					return;
				}
			}
			if ((baTop <= buBottom) && (baBottom >= buTop)) {
				// It's between the right side of the brick and the middle
				if ((baLeft <= buRight) && (baLeft >= buX)) {
					// Change horizontal movement
					bounceLeft(buRight);
					return;
				}
				// It's between the left side of the brick and the middle
				if ((baRight >= buLeft) && (baRight <= buX)) {
					// Change horizontal movement
					bounceRight(buLeft);
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
	 * 
	 * MOUSE PRESSED IS LONG AND PROBABLY TOO CHUNKY. IT IS DEALING WITH WHAT
	 * HAPPENS WITH DIFFERENT BUTTONS IN THE SCREENS THAT THEY EXIST.
	 */
	public void mousePressed() {
		// If we are on the main menu
		if (gameScreen == 0) {
			// So that we can't click the level buttons while displaying the rules
			if (mouseX <= width && mouseX >= width - 50 && mouseY >= 0 && mouseY <= 50) {
				// Secret level, no deaths, ball speed up, bricks spawn randomly?
				if (drawing) {
					bricks.clear();
					drawAinsleighLevel();
					playScreen = 9999;
					gameScreen = 9999;
					dropDown = 0;
					hitPaddle = 0;
					drawing = false;
					levelWon = true;
					displayMenu = false;
				}
			}
			if (mainMenuClickCount == 2) {
				mainMenuClickCount = 0;
			}
			if (mainMenuClickCount == 1 && rules) {
				rules = false;
				mainMenuClickCount = 2;
			}
			drawing = true;
			// If we click level one...need coordinated of this
			for (Button button : mainMenuButtons) {
				if (mouseX >= (button.getX() - (buttonWidth / 2)) && (mouseX <= (button.getX() + (buttonWidth / 2)))) {
					// The mouse was clicked in the same horizontal space as this button
					if (mouseY >= (button.getY() - (buttonHeight / 2))
							&& (mouseY <= (button.getY() + (buttonHeight / 2)))) {
						// The mouse is in the same vertical space as one of the buttons
						// Unsure of the button, so we can do a switch case to find it by label
						switch (button.getLabel()) {
						case "LEVEL ONE":
							if (mainMenuClickCount == 0 && !rules) {
								if (drawing) {
									bricks.clear();
									drawLevelOne();
									playScreen = 1;
									gameScreen = 1;
									dropDown = 0;
									hitPaddle = 0;
									drawing = false;
								}
							}
							break;
						case "LEVEL TWO":
							if (mainMenuClickCount == 0 && !rules) {
								if (drawing) {
									bricks.clear();
									drawLevelTwo();
									playScreen = 2;
									gameScreen = 2;
									dropDown = 0;
									hitPaddle = 0;
									drawing = false;
								}
							}
							break;
						case "LEVEL THREE":
							if (mainMenuClickCount == 0 && !rules) {
								if (drawing) {
									bricks.clear();
									drawLevelThree();
									playScreen = 3;
									gameScreen = 3;
									dropDown = 0;
									hitPaddle = 0;
									drawing = false;
								}
							}
							break;
						case "RULES":
							if (!rules) {
								// displays the rules
								rules = true;
								mainMenuClickCount = 1;
							}
							break;
						}
					}
				}
			}

		}
		if (gameScreen == 4) {
			// copied code from above
			drawing = true;
			for (Button button : gameOverButtons) {
				if (mouseX >= (button.getX() - (buttonWidth / 2)) && (mouseX <= (button.getX() + (buttonWidth / 2)))) {
					// The mouse was clicked in the same horizontal space as this button
					if (mouseY >= (button.getY() - (buttonHeight / 2))
							&& (mouseY <= (button.getY() + (buttonHeight / 2)))) {
						// The mouse is in the same vertical space as one of the buttons
						// Unsure of the button, so we can do a switch case to find it by label
						switch (button.getLabel()) {
						case "RESTART":
							// This will need to change as we'll need to keep track of our last gameScreen!!
							if (drawing) {
								switch (playScreen) {
								case 1:
									// Get rid of everything in the array and then reset it to what it was before
									bricks.clear();
									dropDown = 0;
									hitPaddle = 0;
									drawLevelOne();
									drawing = false;
									gameScreen = 1;
									break;
								case 2:
									bricks.clear();
									dropDown = 0;
									hitPaddle = 0;
									drawLevelTwo();
									drawing = false;
									gameScreen = 2;
									break;
								case 3:
									bricks.clear();
									dropDown = 0;
									hitPaddle = 0;
									drawLevelThree();
									drawing = false;
									gameScreen = 3;
									break;
								}
							}
							break;
						case "LEVELS":
							gameScreen = 0;
							dropDown = 0;
							hitPaddle = 0;
							break;
						case "QUIT":
							System.exit(0);
							break;
						}
					}
				}
			}
		}
		if(levelWon) {
			if(gameScreen == 1 || gameScreen == 2 || gameScreen == 3) {
				drawing = true;
				//The buttons will be placed here....then we need to know which one to go to next.
				for (Button button : gameWinButtons) {
					if (mouseX >= (button.getX() - (buttonWidth / 2)) && (mouseX <= (button.getX() + (buttonWidth / 2)))) {
						// The mouse was clicked in the same horizontal space as this button
						if (mouseY >= (button.getY() - (buttonHeight / 2))
								&& (mouseY <= (button.getY() + (buttonHeight / 2)))) {
							// The mouse is in the same vertical space as one of the buttons
							// Unsure of the button, so we can do a switch case to find it by label
							switch (button.getLabel()) {
							case "NEXT LEVEL":
								if (drawing) {
									if (playScreen == 1) {
										bricks.clear();
										drawLevelTwo();
										playScreen = 1;
										gameScreen = 1;
										dropDown = 0;
										hitPaddle = 0;
										drawing = false;
										levelWon = false;
										resetBall();
									} else if (playScreen == 2) {
										drawLevelThree();
										playScreen = 2;
										gameScreen = 2;
										dropDown = 0;
										hitPaddle = 0;
										drawing = false;
										levelWon = false;
										resetBall();
									} else {
										// Draw text here to say there isn't another level
									}
								}
								break;
							case "MAIN MENU":
								gameScreen = 0;
								dropDown = 0;
								hitPaddle = 0;
								break;
							case "QUIT":
								System.exit(0);
								break;
							}
						}
					}
				}
			}
		}
	}

	public void keyPressed() {
		if (gameScreen == 1 || gameScreen == 2 || gameScreen == 3 || gameScreen == 9999) {
			if (key == ' ') {
				if (shootBallCount == 0) {
					ballSpeedVert = -8;
					ballSpeedHori = (mouseX - lastMouseX) / 4;
					shootBallCount++;
				}
			}
		}
	}

	class Button extends PApplet {

		// Coordinates
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

	/*
	 * This is code taken from https://processing.org/examples/lineargradient.html
	 * This is code used to make a good looking gradiant for objects.
	 * 
	 * IS THIS USELESS NOW?
	 */
	void setGradient(int x, int y, float w, float h, int c1, int c2, int axis) {

		noFill();

		if (axis == Y_AXIS) { // Top to bottom gradient
			for (int i = y; i <= y + h; i++) {
				float inter = map(i, y, y + h, 0, 1);
				int c = lerpColor(c1, c2, inter);
				stroke(c);
				line(x, i, x + w, i);
			}
		} else if (axis == X_AXIS) { // Left to right gradient
			for (int i = x; i <= x + w; i++) {
				float inter = map(i, x, x + w, 0, 1);
				int c = lerpColor(c1, c2, inter);
				stroke(c);
				line(i, y, i, y + h);
			}
		}
	}
}