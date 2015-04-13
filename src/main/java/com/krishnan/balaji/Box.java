package com.krishnan.balaji;

import java.util.HashSet;
import java.util.Set;

public class Box {

	public int finalValue;
	public boolean isValueSet;
	public Set<Integer> possibleValues;
	
	public Box()
	{
		isValueSet=false;
		possibleValues = new HashSet<Integer>();
		for( int i=1;i<10;i++)
			possibleValues.add(i);
	}
	
	public String toString(){
		return finalValue+"";
	}
	
}