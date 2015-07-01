package org.buaa.xuxian.assign;

import java.util.Arrays;

public class Matrix {
	int row;
	int col;
	Index max_index;
	double data[][];
	
	public Matrix(int i, int j){
		this.row = i;
		this.col = j;
		this.max_index = new Index(i, j);
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], 0);
		}
	}
	
	public Matrix(int i, int j, double value){
		this.row = i;
		this.col = j;
		this.max_index = new Index(i, j);
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], value);
		}
	}
	
	private void SetRow(int row, double value){
		Arrays.fill(this.data[row], value);
	}
	
	private void SetCol(int col, double value){
		
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

 class Index {
	protected int row_index[];
	protected double row_value[];
	protected int col_index[];
	protected double col_value[];
	protected int i;
	protected int j;
	protected double value;
	
	protected Index(int m, int n){
		
		this.row_index = new int[m];
		Arrays.fill(this.row_index, -1);
		this.row_value = new double[m];
		Arrays.fill(this.row_value, 0);
		
		this.col_index = new int[n];
		Arrays.fill(this.col_index, -1);
		this.col_value = new double[n];
		Arrays.fill(this.col_value, 0);
		
		this.i = -1;
		this.j = -1;
		this.value = 0;
	}
}
