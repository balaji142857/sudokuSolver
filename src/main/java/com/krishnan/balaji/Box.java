package com.krishnan.balaji;

import java.util.HashSet;
import java.util.Set;


public class Box {
	
	int xStart;
	int xEnd;
	int yStart;
	int yEnd;
	Set<Integer> alreadySet = new HashSet<Integer>();
	public Box(int xStart,int xEnd,int yStart,int yEnd){
		this.xStart=xStart;
		this.xEnd=xEnd;
		this.yStart=yStart;
		this.yEnd=yEnd;
	}
}