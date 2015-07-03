package org.buaa.xuxian.assign;

import java.util.Arrays;

public class Matrix implements Cloneable {
	protected int row;
	protected int col;
	protected Index max_index;
	protected double data[][];
	
	protected Matrix(int i, int j){
		this.row = i;
		this.col = j;
		this.max_index = new Index(i, j);
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], 0);
		}
	}
	
	protected Matrix(int i, int j, double value){
		this.row = i;
		this.col = j;
		this.max_index = new Index(i, j);
		this.data = new double[this.row][this.col];
		for (int counter = 0; counter < this.row; counter++){
			Arrays.fill(this.data[counter], value);
		}
	}
	
	@Override
	public Matrix clone(){
		Matrix o = null;
		try {
			o = (Matrix) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		o.max_index = this.max_index.clone();
		o.data = new double[this.row][this.col];
		for (int i = 0; i < this.row; i++){
			o.data[i] = Arrays.copyOf(this.data[i], this.data[i].length);
		}
		return o;
	}
	
	protected double sum(){
		double Sum = 0;
		for (int i = 0; i < this.row; i++){
			for (int j = 0; j < this.col; j++){
				Sum += this.data[i][j];
			}
		}
		return Sum;
	}
	
	protected void dotMultiply2(Matrix matb){
		for (int i = 0; i < this.row; i++){
			for (int j = 0; j < this.col; j++){
				this.data[i][j] = this.data[i][j] * matb.data[i][j];
			}
		}
	}
	
	protected Matrix dotMultiply3(Matrix matb){
		Matrix matc = new Matrix(this.row, this.col);
		for (int i = 0; i < this.row; i++){
			for (int j = 0; j < this.col; j++){
				matc.data[i][j] = this.data[i][j] * matb.data[i][j];
			}
		}
		return matc;
	}
	
	protected void setRow(int row, double value){
		Arrays.fill(this.data[row], value);
	}
	
	protected void setRow(int row, double[] data){
		this.data[row] = Arrays.copyOf(data, this.data[row].length);
	}
	
	protected void setCol(int col, double value){
		for (int i = 0; i < this.row; i++){
			this.data[i][col] = value;
		}
	}
	
	protected void indexingMax(){
		rowMax(0);
		this.max_index.value = this.max_index.row_value[0];
		this.max_index.i = 0;
		this.max_index.j = this.max_index.row_index[0];
		
		for (int i = 1; i < this.row; i++){
			rowMax(i);
			if (this.max_index.row_value[i] > this.max_index.value){
				this.max_index.value = this.max_index.row_value[i];
				this.max_index.i = i;
				this.max_index.j = this.max_index.row_index[i];
			}
		}
		
		for (int j = 0; j < this.col; j++){
			colMax(j);
		}
	}
	
	@Override
	public String toString(){
		StringBuffer datastr = new StringBuffer();
		for (int i = 0; i < this.row; i++){
			datastr.append(Arrays.toString(this.data[i])).append("\r\n");
		}
		return "row is: " + this.row + "\r\n" + 
			   "col is: " + this.col + "\r\n" +
			   datastr.toString() + 
			   this.max_index.toString();
	}
	
	private void rowMax(int i){
		this.max_index.row_value[i] = this.data[i][0];
		this.max_index.row_index[i] = 0;
		for (int counter = 0; counter < this.col; counter++){
			if (this.data[i][counter] > this.max_index.row_value[i]){
				this.max_index.row_value[i] = this.data[i][counter];
				this.max_index.row_index[i] = counter;
			}
		}
	}
	
	private void colMax(int j){
		this.max_index.col_value[j] = this.data[0][j];
		this.max_index.col_index[j] = 0;
		for (int counter = 0; counter < this.row; counter++){
			if (this.data[counter][j] > this.max_index.col_value[j]){
				this.max_index.col_value[j] = this.data[counter][j];
				this.max_index.col_index[j] = counter;
			}
		}
	}
	
	public static void main(String[] args){
		Matrix mat = new Matrix(10, 10, 10);
		Matrix matb = mat.clone();
		mat.setRow(0, 122);
		mat.indexingMax();
		System.out.println(mat);
		System.out.println(matb);
	}

}

 class Index implements Cloneable {
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
	
	@Override
	public String toString(){
		return "max value is: " + this.value + "\r\n" + 
			   "max i is: " + this.i + "\r\n" + 
			   "max j is: " + this.j + "\r\n" +
			   "row max index is: " + Arrays.toString(this.row_index) + "\r\n" +
			   "row max value is: " + Arrays.toString(this.row_value) + "\r\n" +
			   "col max index is: " + Arrays.toString(this.col_index) + "\r\n" +
			   "col max value is: " + Arrays.toString(this.col_value) + "\r\n";	   
	}
	
	@Override
	public Index clone(){
		Index o = null;
		try {
			o = (Index) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		o.row_index = Arrays.copyOf(this.row_index, this.row_index.length);
		o.row_value = Arrays.copyOf(this.row_value, this.row_value.length);
		
		o.col_index = Arrays.copyOf(this.col_index, this.col_index.length);
		o.col_value = Arrays.copyOf(this.col_value, this.col_value.length);
		
		return o;
	}
}
