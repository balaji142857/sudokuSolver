package com.krishnan.balaji;



public class Sudoku {

	public static Box[][] cells;
	
	static{
		cells = new  Box[9][9];
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				cells[i][j]=new Box();
	}
	
	public static void initialize(Integer[][] inputValues)
	{
		for(int i= 0; i<9; i++)
			for(int j=0; j<9; j++)
				if(inputValues[i][j]!=null &&  	inputValues[i][j] > 0)
				{
					cells[i][j].finalValue = inputValues[i][j];
					cells[i][j].isValueSet = true;
				}
	}
	
	public static void solveIt()
	{
		System.out.println("Initial state");
		display();
		boolean nextAttempt =true;
		int attemptCount = 0;
		while(nextAttempt)
		{
			nextAttempt = attempt();
			attemptCount++;
			System.out.println("after "+attemptCount+" attempts");
			display();
		}
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
			for(int j=0; j<9; j++)
			{
				if(cells[i][j].isValueSet)
				{
					for(int k=0; k<9; k++){
						if(!cells[i][k].isValueSet)
						{
							cells[i][k].possibleValues.remove(cells[i][j].finalValue);
							if(cells[i][k].possibleValues.size()==1)
							{
								cells[i][k].finalValue=cells[i][k].possibleValues.iterator().next();
								cells[i][k].isValueSet=true;
								anyThingNew = true;
								System.out.println("set cells["+i+"]["+j+"] as "+cells[i][k].finalValue);
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
								System.out.println("set cells["+k+"]["+j+"] as "+cells[k][j].finalValue);
							}
						}
					}
					getBoxes(i,j);
				}
				else
				{
					getBoxes(i,j);
				}
			}
		return anyThingNew;
	}
	
	private static boolean processBox(Box box,int i,int j){
		boolean newValueFound = false;
		box.possibleValues.remove(cells[i][j].finalValue);
		if(box.possibleValues.size()==1){
			box.finalValue=box.possibleValues.iterator().next();
			box.isValueSet=true;
			System.out.println("box thing worked: "+box.finalValue);
			newValueFound = true;
		}
		return newValueFound;
	}
	
	private static boolean getBoxes(int i, int j) {
		boolean returnValue = false;
		if(i<3)
		{
			if(j<3)
				for(int k=0;k<3;k++)
					for(int l=0;l<3;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<6)
				for(int k=0;k<3;k++)
					for(int l=3;l<6;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<9)
				for(int k=0;k<3;k++)
					for(int l=6;l<9;l++)
						returnValue = processBox(cells[k][l],i,j);
		}
		else if(i <6)
		{
			if(j<3)
				for(int k=3;k<6;k++)
					for(int l=0;l<3;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<6)
				for(int k=3;k<6;k++)
					for(int l=3;l<6;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<9)
				for(int k=3;k<6;k++)
					for(int l=6;l<9;l++)
						returnValue = processBox(cells[k][l],i,j);
		}
		else if(i<9)
		{
			if(j<3)
				for(int k=6;k<9;k++)
					for(int l=0;l<3;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<6)
				for(int k=6;k<9;k++)
					for(int l=3;l<6;l++)
						returnValue = processBox(cells[k][l],i,j);
			else if(j<9)
				for(int k=6;k<9;k++)
					for(int l=6;l<9;l++)
						returnValue = processBox(cells[k][l],i,j);
		}
		return returnValue;
	}

	public static void main(String[] args) {
		Integer[][] input = new Integer[9][9];
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
		//input[8][2]=6;
		
		//input[0][3]=6;
		input[1][3]=7;
		input[2][3]=4;
		input[6][3]=5;
		
		input[0][4]=5;
		/*input[1][4]=1;
		input[3][4]=7;
		input[5][4]=6;*/
		input[8][4]=4;
		
		input[2][5]=3;
		input[6][5]=6;
		input[7][5]=1;
		//input[8][5]=7;
		
		/*input[0][6]=4;
		input[2][6]=7;*/
		input[7][6]=6;

		//input[0][7]=3;
		input[1][7]=6;
		input[2][7]=1;
		input[3][7]=9;
		input[4][7]=7;
		input[5][7]=5;
		input[7][7]=4;
		
		input[0][8]=8;
		input[1][8]=5;
		//input[3][8]=6;
		input[5][8]=4;
		input[6][8]=7;
		initialize(input);
		solveIt();
	}
}