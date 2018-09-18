package com.macneil.capstoneproject;


import processing.core.PApplet;

public class LevelOne extends PApplet{
	
	private final int BRICK_COLOR = color(100);
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 1200;
	private static final int BRICK_WIDTH = GAME_WIDTH/10;
	private static final int BRICK_HEIGHT = 20;
	
	
	void drawBricks(){
		//for(int i = 0; i < 10; i++) {
			fill(BRICK_COLOR);
			rectMode(CENTER);
			//stroke(204, 102, 0);
			rect(10, 10, BRICK_WIDTH, BRICK_HEIGHT);
		//}
	}
	
}
