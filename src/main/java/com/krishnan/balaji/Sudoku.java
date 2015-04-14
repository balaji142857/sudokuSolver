package com.krishnan.balaji;

public class Sudoku {
	
	public static Box[][] cells;
	static int computed = 0;
	static{
		cells = new  Box[9][9];
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				cells[i][j]=new Box();
	}
	
	public static void initialize(Integer[][] inputValues)
	{
		for(int i= 0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(inputValues[i][j]!=null &&  	inputValues[i][j] > 0)
				{
					cells[i][j].finalValue = inputValues[i][j];
					cells[i][j].isValueSet = true;
				}
			}
		}
	}
	
	public static Integer[][] solveIt()
	{
		Integer[][] solvedArray = new Integer[9][9];
		System.out.println("Initial state");
		display();
		boolean nextAttempt =true;
		int attemptCount = 0;
		while(nextAttempt)
		{
			nextAttempt = attempt();
			attemptCount++;
			System.out.println("after "+attemptCount+" attempts");
			System.out.println("continue: "+nextAttempt);
			display();
		}
		System.out.println("computed: "+computed);
		for(int i= 0; i<9; i++)
			for(int j=0; j<9; j++)
				if(cells[i][j].isValueSet)
				{
					solvedArray[i][j]=cells[i][j].finalValue;
				}
		return solvedArray;
	}
	
	public static void display()
	{
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++)
				System.out.print(cells[i][j]+" ");
			System.out.println();
		}
	}
	
	public static boolean attempt(){ 
		boolean anyThingNew = false;
		for(int i= 0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(cells[i][j].isValueSet)
				{
					//remove possibilities from the same row and column based on the current cell value
					for(int k=0; k<9; k++){
						if(!cells[i][k].isValueSet)
						{
							cells[i][k].possibleValues.remove(cells[i][j].finalValue);
							if(cells[i][k].possibleValues.size()==1)
							{
								cells[i][k].finalValue=cells[i][k].possibleValues.iterator().next();
								cells[i][k].isValueSet=true;
								anyThingNew = true;
								computed++;
							}
						}
						if(!cells[k][j].isValueSet)
						{
							cells[k][j].possibleValues.remove(cells[i][j].finalValue);
							if(cells[k][j].possibleValues.size()==1)
							{
								cells[k][j].finalValue=cells[k][j].possibleValues.iterator().next();
								cells[k][j].isValueSet=true;
								anyThingNew = true;
								computed++;
							}
						}
					}
					//remove the set value from other cells in the same box
					anyThingNew = anyThingNew || removePossibleValueFromOtherCellInThreeByThreeBoxes(i,j);
				}
				else
				{
					//remove the possible values in the current cell based on the values in the same row and column
					for(int k=0; k<9; k++){
						if(cells[i][k].isValueSet)
							cells[i][j].possibleValues.remove(cells[i][k].finalValue);
						if(cells[k][j].isValueSet)
							cells[i][j].possibleValues.remove(cells[k][j].finalValue);
						if(cells[i][j].possibleValues.size()==1){
							cells[i][j].finalValue=cells[i][j].possibleValues.iterator().next();
							cells[i][j].isValueSet=true;
							computed++;
							anyThingNew=true;
						}
					}
					//remove the possible values in the current cell based on values from the same box
					if(i<3)
						if(j<3)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(0,3,0,3,i,j);
						else if(j<6)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(0,3,3,6,i,j);
						else if(j<9)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(0,3,6,9,i,j);
					else if(i <6)
						if(j<3)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(3,6,0,3,i,j);
						else if(j<6)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(3,6,3,6,i,j);
						else if(j<9)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(3,6,6,9,i,j);
					else if(i<9)
						if(j<3)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(6,9,0,3,i,j);
						else if(j<6)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(6,9,3,6,i,j);
						else if(j<9)
							anyThingNew = anyThingNew || filterCurrentCellValueFromSameBoxValues(6,9,6,9,i,j);
				}
				//if a number has a possibility of occurrence in only one cell of the 3*3 then fill it..
				//there may be other numbers still in the possibility set of that cell. but it can be filled in
				anyThingNew = anyThingNew || filterCurrentCellOnPossibilityBasisInBoxes(i,j);
			}
	}
		return anyThingNew;
	}
	
	private static boolean filterCurrentCellOnPossibilityBasisInBoxes(int i, int j) {
		boolean returnValue =  false;
		if(i<3)
			if(j<3)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(0,3,0,3);
			else if(j<6)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(0,3,3,6);
			else if(j<9)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(0,3,6,9);
		else if(i <6)
			if(j<3)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(3,6,0,3);
			else if(j<6)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(3,6,3,6);
			else if(j<9)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(3,6,6,9);
		else if(i<9)
			if(j<3)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(6,9,0,3);
			else if(j<6)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(6,9,3,6);
			else if(j<9)
				returnValue = returnValue || filterCurrentCelOnPossibilityBasisInBox(6,9,6,9);	
		return returnValue;
	}

	private static boolean filterCurrentCelOnPossibilityBasisInBox(int xStart,int xEnd,int yStart,int yEnd){
		boolean returnValue = false;
		for(int num =1;num<10;num++)
		{
		int count = 0;
		int x=0,y =0,finalValue =0;
		for(int k=xStart;k<xEnd;k++)
		{
			for(int l=yStart;l<yEnd;l++)
			{
				if(cells[k][l].isValueSet && cells[k][l].finalValue==num)
					continue;
				if(cells[k][l].isValueSet)
					continue;
				if(count>1)
					continue;
				if(cells[k][l].possibleValues.contains(num))
				{
					count++;
					x=k;
					y=l;
					finalValue =num;
				}
			}
		}
		if(count==1)
		{
			cells[x][y].finalValue = finalValue;
			cells[x][y].isValueSet = true;
			returnValue = true;
			computed++;
			System.out.println("from box removal: x,y,value: "+x+y+cells[x][y].finalValue);
		}
		}
		return returnValue;
	}
	
	private static boolean filterCurrentCellValueFromSameBoxValues(int xStart,int xEnd,int yStart,int yEnd,int i, int j){
		boolean returnValue = false;
		for(int m=xStart;m<xEnd;m++){
			for(int n=yStart;n<yEnd;n++){
				if(!cells[m][n].isValueSet)
					continue;
				cells[i][j].possibleValues.remove(cells[m][n].finalValue);
				if(cells[i][j].possibleValues.size()==1)
				{
					cells[i][j].finalValue=cells[i][j].possibleValues.iterator().next();
					cells[i][j].isValueSet=true;
					computed++;
					returnValue = true;
				}
			}
		}
		return returnValue;
	}
	
	private static boolean removeFromOtherCellsInThreeByThreeBox(int xStart,int xEnd,int yStart,int yEnd,int i,int j){
		boolean newValueFound = false;
		for(int k=xStart;k<xEnd;k++){
			for(int l=yStart;l<yEnd;l++){
				if(cells[k][l].isValueSet)
					continue;
				cells[k][l].possibleValues.remove(cells[i][j].finalValue);
				if(cells[k][l].possibleValues.size()==1){
					cells[k][l].finalValue=cells[k][l].possibleValues.iterator().next();
					cells[k][l].isValueSet=true;
					computed++;
					newValueFound = true;
				}
			}
		}
		return newValueFound;
	}
	
	private static boolean removePossibleValueFromOtherCellInThreeByThreeBoxes(int i, int j) {
		boolean returnValue = false;
		if(i<3)
			if(j<3)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(0,3,0,3,i,j);
			else if(j<6)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(0,3,3,6,i,j);
			else if(j<9)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(0,3,6,9,i,j);
		else if(i <6)
			if(j<3)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(3,6,0,3,i,j);
			else if(j<6)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(3,6,3,6,i,j);
			else if(j<9)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(3,6,6,9,i,j);
		else if(i<9)
			if(j<3)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(6,9,3,0,i,j);
			else if(j<6)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(6,9,3,6,i,j);
			else if(j<9)
				returnValue = returnValue || removeFromOtherCellsInThreeByThreeBox(6,9,6,9,i,j);
		return returnValue;
	}

	public static void main(String[] args) {
		//new SudokuView().initialize();
		Integer[][] input = new Integer[9][9];
		/*input[0][1]=7;
		input[0][3]=8;
		input[0][4]=4;
		
		input[1][0]=5;
		input[1][2]=2;
		input[1][4]=7;
		input[1][7]=8;
		
		input[2][2]=3;
		input[3][2]=9;
		input[4][2]=1;
		input[7][2]=5;
		
		input[6][3]=2;
		input[8][3]=1;
		
		input[1][4]=8;
		input[7][4]=3;
		
		input[0][5]=6;
		input[2][5]=5;
		
		input[1][6]=5;
		input[4][6]=6;
		input[5][6]=1;
		input[6][6]=7;
		
		input[1][7]=1;
		input[4][7]=9;
		input[6][7]=5;
		input[8][7]=3;
		
		input[4][8]=5;
		input[5][8]=8;
		input[7][8]=1;*/
		input[2][0]=6;
		input[3][0]=3;
		input[7][0]=2;
		input[8][0]=9;
		
		input[1][1]=4;
		input[3][1]=1;
		input[4][1]=6;
		input[5][1]=2;
		input[6][1]=3;
		input[7][1]=7;
		
		input[1][2]=3;
		input[8][2]=6;
		
		input[0][3]=6;
		input[1][3]=7;
		input[2][3]=4;
		input[6][3]=5;
		
		input[0][4]=5;
		input[1][4]=1;
		input[3][4]=7;
		input[5][4]=6;
		input[8][4]=4;
		
		input[2][5]=3;
		input[6][5]=6;
		input[7][5]=1;
		input[8][5]=7;
		
		input[0][6]=4;
		input[2][6]=7;
		input[7][6]=6;

		input[0][7]=3;
		input[1][7]=6;
		input[2][7]=1;
		input[3][7]=9;
		input[4][7]=7;
		input[5][7]=5;
		input[7][7]=4;
		
		input[0][8]=8;
		input[1][8]=5;
		input[3][8]=6;
		input[5][8]=4;
		input[6][8]=7;
		initialize(input);
		solveIt();
	}
}