package org.buaa.xuxian.assign;

import java.util.Arrays;

public class Matrix {
	int row;
	int col;
	double data[][];
	
	public Matrix(int i, int j){
		this.row = i;
		this.col = j;
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], 0);
		}
	}
	
	public Matrix(int i, int j, double value){
		this.row = i;
		this.col = j;
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], value);
		}
	}
	
	public void SetRow(int row, double value){
		Arrays.fill(this.data[row], value);
	}
	
	public void SetCol(int col, double value){
		
	}
	
	
	
	public static void main(String[] args){
		Matrix mat = new Matrix(10, 10);
		System.out.println("data is:");
		for (int i = 0; i < mat.row; i++){
			for (int j = 0; j < mat.col; j++){
				System.out.println(mat.data[i][j]);
			}
		}
	}

}
