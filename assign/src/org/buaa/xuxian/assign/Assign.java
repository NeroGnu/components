package org.buaa.xuxian.assign;

import java.util.Arrays;

public class Assign {
	public Matrix eff_mat;
	public Matrix res_mat;
	private int plane_num;
	private int target_num;
	private int self_ID;
	
	public Assign(int plane, int target, int selfID){
		this.plane_num = plane;
		this.target_num = target;
		this.self_ID = selfID;
		this.eff_mat = new Matrix(plane, target);
		this.res_mat = new Matrix(plane, target);
	}
	
	public double Execute(){
		Matrix eff_mat_a = this.eff_mat.clone();
		Matrix eff_mat_b = this.eff_mat.clone();
		Matrix res_mat_a = new Matrix(this.plane_num, this.target_num);
		Matrix ant_res_mat = new Matrix(this.plane_num, this.target_num, 1);
		int i, j, round;
		
		round = this.plane_num / this.target_num;
		for (j = 0; j < round; j++){
			for (i = j * this.target_num; i < (j + 1) * this.target_num; i++){
				eff_mat_a.IndexingMax();
				if (0.0 != eff_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j]){
					res_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j] = 1;
					ant_res_mat.SetRow(eff_mat_a.max_index.i, 0);
					eff_mat_a.SetRow(eff_mat_a.max_index.i, 0);
					eff_mat_a.SetCol(eff_mat_a.max_index.j, 0);
				}
			}
			eff_mat_a = this.eff_mat.clone();
			eff_mat_a.DotMultiply2(ant_res_mat);
		}
		
		round = this.plane_num % this.target_num;
		for (i = 0; i < round; i++){
			eff_mat_a.IndexingMax();
			if (0.0 != eff_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j]){
				res_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j] = 1;
				ant_res_mat.SetRow(eff_mat_a.max_index.i, 0);
				eff_mat_a.SetRow(eff_mat_a.max_index.i, 0);
				eff_mat_a.SetCol(eff_mat_a.max_index.j, 0);
			}
		}
		this.res_mat = res_mat_a;
		eff_mat_b.DotMultiply2(res_mat_a);
		return eff_mat_b.Sum();
	}
	
	public double Comparison(int time){
		Matrix mat_ope = this.eff_mat.DotMultiply3(this.res_mat);
		Matrix mat_b = this.eff_mat.clone();
		for (int j = 0; j < this.target_num; j++){
			mat_ope.IndexingMax();
			for (int i = 0; i < this.plane_num; i++){
				if (1.0 == this.res_mat.data[i][j]){
					if ((mat_ope.max_index.col_value[j] - mat_ope.data[i][j]) / 
							mat_ope.data[i][j] > MySigmoid(150, 8, time)){
						this.res_mat.data[i][j] = 0;
					}
				}
			}
		}
		mat_b.DotMultiply2(this.res_mat);
		return mat_b.Sum();
	}
	
	public double DisFilter(double dis, double g_ratio, int excep_ID){
		return 1.0;
	}
	
	public int GetResult(){
		return Arrays.binarySearch(this.res_mat.data[this.self_ID], 1.0);
	}
	
	private double MySigmoid(double median, double divider, int x){
		return 1 / (Math.exp((median - x) / divider) + 1);
	}
	
	public static void main(String[] args){
		double a[][]={{10,2,3,4,5,6,7},
					  {3,6,8,2,6,1,8},
					  {3,5,6,7,8,1,4},
					  {9,2,3,4,5,6,7},
					  {6,7,3,4,5,6,7},
					  {6,5,4,3,2,1,1},
					  {4,5,6,7,3,9,4},
					  {7,6,5,8,3,6,7}};
		Assign ass = new  Assign(8, 7,3);
		for (int i = 0; i < 8; i++){
			ass.eff_mat.data[i] = Arrays.copyOf(a[i], 7);
		}
		ass.Execute();
		System.out.println("after Execute:");
		System.out.println(ass.eff_mat);
		System.out.println(ass.res_mat);
	}
}
