package org.buaa.xuxian.assign;

import java.util.Arrays;

public class IDRow {
	public int ID;
	public double[] e;
	
	public IDRow(int ID, double[] e){
		this.ID = ID;
		this.e = e;
	}

	@Override
	public String toString() {
		return "IDRow [ID=" + ID + ", e=" + Arrays.toString(e) + "]";
	}	
	
	
}
