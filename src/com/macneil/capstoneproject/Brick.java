package com.macneil.capstoneproject;

import java.util.Random;

import processing.core.PApplet;

public class Brick extends PApplet{
	private final int RED = color(255, 0, 0);
	private int xCor;
	private int yCor;
	private int width;
	private int height;
	//Depends on the health.
	private int c;
	//How many times it can be hit. 
	private int health;
	private boolean delete = false;
	
	public Brick(int xCor, int yCor, int bColor) {
		//The 10 is half of it's width. This should be a variable later
		this.xCor = (xCor + 40);
		//Not sure if 10 affects this...for testing
		this.yCor = (yCor + 10);
		//This is 1/10 of the width
		this.width = 80;
		this.height = 20;
		this.c = bColor;
		if(bColor == color(255, 0, 0)) {
			this.health = 1;
		}else if(bColor == color(0, 255, 0)) {
			this.health = 2;
		}else {
			this.health = 3;
		}
	}

	public void setyCor(int yCor) {
		this.yCor = yCor;
	}

	public int getxCor() {
		return xCor;
	}
	public boolean getDelete() {
		return delete;
	}
	
	public int getyCor() {
		return yCor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColor() {
		return c;
	}

	public int getHealth() {
		return health;
	}
	
	public void hit() {
		loseHealth();
	}
	private void loseHealth() {
		this.health--;
		if(health < 1) {
			this.delete = true;
		}else {
			changeColor();
		}
	}
	private void changeColor() {
		switch (this.health) {
		case 1:
			this.c = color(255,0,0);
			break;
		case 2:
			this.c = color(0, 255 ,0);
			break;
		}
	}
	
}
