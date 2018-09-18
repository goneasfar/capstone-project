package com.macneil.capstoneproject;
import processing.core.PApplet;

public class Button extends PApplet{
	
	//Coordinates
	int x, y;
	String label;
	
	public Button(int x, int y, String label) {
		super();
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
	
	
}
