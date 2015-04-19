package com.krishnan.balaji;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sudoku1 {
	static boolean initialized = false;
	private static Cell[][] cells = new Cell[9][9];
	private static Map<Integer,Box> boxes = new HashMap<Integer, Box>();
	
	static{
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				cells[i][j] = new Cell();
		boxes.put(1, new Box(0,3,0,3,1));
		boxes.put(2, new Box(0,3,3,6,2));
		boxes.put(3, new Box(0,3,6,9,3));
		boxes.put(4, new Box(3,6,0,3,4));
		boxes.put(5, new Box(3,6,3,6,5));
		boxes.put(6, new Box(3,6,6,9,6));
		boxes.put(7, new Box(6,9,0,3,7));
		boxes.put(8, new Box(6,9,3,6,8));
		boxes.put(9, new Box(6,9,6,9,9));
	}
	
	public static void display(){
		for(int row=0;row<9;row++){
			for(int col=0;col<9;col++){
				System.out.print(cells[col][row].finalValue+" ");
			}
			System.out.println();
		}
	}

	public static void initialize(Integer[] input){
		Set<Integer> numbers = new HashSet<Integer>();
		numbers.add(1);numbers.add(2);numbers.add(3);numbers.add(4);numbers.add(5);
		numbers.add(6);numbers.add(7);numbers.add(8);numbers.add(9);
		for(int row=0;row<9;row++){
			for(int col=0;col<9;col++){
				int value = input[row*9+col];
				if(value>0){
					cells[row][col].finalValue= value;
					Box box =getBox(row, col);
					if(box!=null)
					{
						box.alreadySet.add(value);
						boxes.put(box.boxNum, box);
					}
					else
					{
						System.out.println("test "+boxes);
						System.out.println("Null box for "+row+","+col);
					}
						
				}
				else{
					cells[row][col].possiblieValues = new HashSet<Integer>();
					cells[row][col].possiblieValues.addAll(numbers);	
				}
				System.out.print(value);
			}
		}
		initialized = true;
	}

	public static void solve(){
		if(initialized==false)
			return;
		boolean nextAttempt  = true;
		int attemptCount = 0;
		while(nextAttempt){
			attemptCount++;
			nextAttempt = attempt();
			System.out.println("After "+attemptCount+",nextAttempt "+nextAttempt);
			display();
		}
		System.out.println("\n\n\nFinal\n\n\n");
		display();
	}

	private static boolean attempt()
	{
		boolean nextAttempt = false;
		for(int row=0;row<9;row++)
		{
			for(int col=0;col<9;col++)
			{
				if(cells[row][col].finalValue>0)
				{
					for(int temp=0;temp<9;temp++)
					{
						if(cells[row][temp].finalValue<=0)
						{
							cells[row][temp].possiblieValues.remove(cells[row][col].finalValue);
							if(cells[row][temp].possiblieValues.size()==1)
							{
								cells[row][temp].finalValue=cells[row][temp].possiblieValues.iterator().next();
								Box box = getBox(row, temp);
								box.alreadySet.add(cells[row][temp].finalValue);
								boxes.put(box.boxNum, box);
								nextAttempt = true;
							}
						}
						if(cells[temp][col].finalValue<=0)
						{
							cells[temp][col].possiblieValues.remove(cells[row][col].finalValue);
							if(cells[temp][col].possiblieValues.size()==1)
							{
								cells[temp][col].finalValue=cells[temp][col].possiblieValues.iterator().next();
								Box box = getBox(temp, col);
								box.alreadySet.add(cells[temp][col].finalValue);
								boxes.put(box.boxNum, box);
								nextAttempt = true;
							}
						}
					}
					nextAttempt = nextAttempt || removePossibleValeusFromOtherCellsinSameBox(getBox(row, col),row,col);
				}
				else
				{
					for(int temp =0;temp<9;temp++)
					{
						if(cells[temp][col].finalValue>0)
						{
							cells[row][col].possiblieValues.remove(cells[temp][col].finalValue);
							if(cells[row][col].possiblieValues.size()==1)
							{
								cells[row][col].finalValue = cells[row][col].possiblieValues.iterator().next();
								Box box = getBox(row, col);
								box.alreadySet.add(cells[row][col].finalValue);
								boxes.put(box.boxNum, box);
								nextAttempt = true;
							}
						}
						if(cells[row][temp].finalValue>0)
						{
							cells[row][col].possiblieValues.remove(cells[row][temp].finalValue);
							if(cells[row][col].possiblieValues.size()==1)
							{
								cells[row][col].finalValue = cells[row][col].possiblieValues.iterator().next();
								Box box = getBox(row, col);
								box.alreadySet.add(cells[row][col].finalValue);
								boxes.put(box.boxNum, box);
								nextAttempt = true;
							}
						}
					}
					nextAttempt = nextAttempt || removePossibleValeusFromCurrentCell(getBox(row, col),row,col);
					nextAttempt = nextAttempt || tryForAllNumbersWithinSameBox(row,col);
				}
			}
		}
		return nextAttempt;
	}

	private static boolean tryForAllNumbersWithinSameBox(int row, int col) {
		boolean returnValue = false;
		Box currentBox = getBox(row,col);
		for(int currentNum=1;currentNum<10;currentNum++)
		{
			int possiblePlaces = 0;
			int px = 0,py=0;
			if(currentBox.alreadySet.contains(currentNum))
				continue;
			for(int x = currentBox.xStart;x<currentBox.xEnd;x++)
			{
				for(int y=currentBox.yStart;y<currentBox.yEnd;y++)
				{
					if(possiblePlaces>1)
						continue;
					if(cells[x][y].finalValue<=0 && cells[x][y].possiblieValues.contains(currentNum))
					{
						possiblePlaces++;
						px=x;
						py=y;
					}
				}
			}
			if(possiblePlaces==1)
			{
				cells[px][py].finalValue=currentNum;
				currentBox.alreadySet.remove(currentNum);
				boxes.put(currentBox.boxNum, currentBox);
				returnValue = true;
			}
		}
		return returnValue;
	}

	private static Box getBox(int row, int col) {
		if(row<3)
		{
			if(col<3)
				return boxes.get(1);
			else if(col<6)
				return boxes.get(2);
			else if(col<9)
				return boxes.get(3);	
		}
			
		else if(row <6)
		{
			if(col<3)
				return boxes.get(4);
			else if(col<6)
				return boxes.get(5);
			else if(col<9)
				return boxes.get(6);
		}
		else if(row<9)
		{
			if(col<3)
				return boxes.get(7);
			else if(col<6)
				return boxes.get(8);
			else if(col<9)
				return boxes.get(9);			
		}
		System.out.println("returning null box for "+row+", "+col);
		return null;
	}

	private static boolean removePossibleValeusFromCurrentCell(Box box, int row, int col) {
		boolean returnValue = false;
		cells[row][col].possiblieValues.removeAll(box.alreadySet);
		if(cells[row][col].possiblieValues.size()==1)
		{
			cells[row][col].finalValue = cells[row][col].possiblieValues.iterator().next();
			box.alreadySet.add(cells[row][col].finalValue);
			boxes.put(box.boxNum, box);
			returnValue = true;
		}
		return returnValue;
	}

	private static boolean removePossibleValeusFromOtherCellsinSameBox(Box box,int i,
			int j) {
		boolean returnValue = false;
		for(int x = box.xStart;x<box.xEnd;x++)
		{
			for(int y=box.yStart;y<box.yEnd;y++)
			{
				if(cells[x][y].finalValue<=0)
				{
					cells[x][y].possiblieValues.remove(box.alreadySet);
					if(cells[x][y].possiblieValues.size()==1)
					{
						cells[x][y].finalValue=cells[x][y].possiblieValues.iterator().next();						
						box.alreadySet.add(cells[x][y].finalValue);
						boxes.put(box.boxNum, box);
						returnValue = true;
					}
				}
			}
		}
		return returnValue;
	}

}