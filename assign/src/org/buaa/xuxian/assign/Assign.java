package org.buaa.xuxian.assign;

import java.util.Arrays;
/**
 * This is the main body of assign algorithm.
 * @author Nero
 * @version v1.0
 *
 */
public class Assign {
	public Matrix eff_mat;
	public Matrix res_mat;
	public final int plane_num;
	public final int target_num;
	public final int self_ID;
	public final double median;
	public final double divider;
	
	public Assign(int plane, int target, int selfID){
		this.plane_num = plane;
		this.target_num = target;
		this.self_ID = selfID;
		this.eff_mat = new Matrix(plane, target);
		this.res_mat = new Matrix(plane, target);
		this.median = 150;
		this.divider = 8;
	}
	
	public Assign(int plane, int target, int selfID, double median, double divider){
		this.plane_num = plane;
		this.target_num = target;
		this.self_ID = selfID;
		this.eff_mat = new Matrix(plane, target);
		this.res_mat = new Matrix(plane, target);
		this.median = median;
		this.divider = divider;
	}
	
	public synchronized double execute(){
		Matrix eff_mat_a = this.eff_mat.clone();
		Matrix eff_mat_b = this.eff_mat.clone();
		Matrix res_mat_a = new Matrix(this.plane_num, this.target_num);
		Matrix ant_res_mat = new Matrix(this.plane_num, this.target_num, 1);
		int i, j, round;
		
		round = this.plane_num / this.target_num;
		for (j = 0; j < round; j++){
			for (i = j * this.target_num; i < (j + 1) * this.target_num; i++){
				eff_mat_a.indexingMax();
				if (0.0 != eff_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j]){
					res_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j] = 1;
					ant_res_mat.setRow(eff_mat_a.max_index.i, 0);
					eff_mat_a.setRow(eff_mat_a.max_index.i, 0);
					eff_mat_a.setCol(eff_mat_a.max_index.j, 0);
				}
			}
			eff_mat_a = this.eff_mat.clone();
			eff_mat_a.dotMultiply2(ant_res_mat);
		}
		
		round = this.plane_num % this.target_num;
		for (i = 0; i < round; i++){
			eff_mat_a.indexingMax();
			if (0.0 != eff_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j]){
				res_mat_a.data[eff_mat_a.max_index.i][eff_mat_a.max_index.j] = 1;
				ant_res_mat.setRow(eff_mat_a.max_index.i, 0);
				eff_mat_a.setRow(eff_mat_a.max_index.i, 0);
				eff_mat_a.setCol(eff_mat_a.max_index.j, 0);
			}
		}
		this.res_mat = res_mat_a;
		eff_mat_b.dotMultiply2(res_mat_a);
		return eff_mat_b.sum();
	}
	
	public synchronized double comparison(int time){
		Matrix mat_ope = this.eff_mat.dotMultiply3(this.res_mat);
		Matrix mat_b = this.eff_mat.clone();
		for (int j = 0; j < this.target_num; j++){
			mat_ope.indexingMax();
			for (int i = 0; i < this.plane_num; i++){
				if (1.0 == this.res_mat.data[i][j]){
					if ((mat_ope.max_index.col_value[j] - mat_ope.data[i][j]) / 
							mat_ope.data[i][j] > mySigmoid(this.median, this.divider, time)){
						this.res_mat.data[i][j] = 0;
					}
				}
			}
		}
		mat_b.dotMultiply2(this.res_mat);
		return mat_b.sum();
	}
	
	public synchronized double disFilter(double dis, double g_ratio, int time, int excep_ID){
		return 1.0;
	}
	
	public synchronized void update(AlgorithmLevel LEVEL, double[] data, int ID, 
			int time, double dis, double g_ratio, int excep_ID){
		this.eff_mat.setRow(ID, data);
		switch (LEVEL){
		case ALGOR_LEVEL_1:
			execute();
			break;
		case ALGOR_LEVEL_2:
			execute();
			comparison(time);
			break;
		case ALGOR_LEVEL_3:
			execute();
			comparison(time);
			disFilter(dis, g_ratio, time, excep_ID);
			break;
		}
	}
	
	public int getResult(){
		for (int i = 0; i < this.eff_mat.data[this.self_ID].length; i++){
			if (1.0 == this.res_mat.data[this.self_ID][i]){
				return i;
			}
		}
		return -1;
	}
	
	private double mySigmoid(double median, double divider, int x){
		return 1 / (Math.exp((median - x) / divider) + 1);
	}
	
	public static void main(String[] args){
		double a[][]={{1,2,3,4,5,6,7},
					  {3,6,8,2,6,1,8},
					  {3,5,6,7,8,1,4},
					  {9,2,3,4,5,6,7},
					  {6,7,3,4,5,6,7},
					  {6,5,4,3,2,1,1},
					  {4,5,6,7,3,9,4},
					  {7,6,5,8,3,6,7}};
		double newdata[] = {1,2,5,6,7,4,3};
		
		Assign ass = new  Assign(8, 7, 3);
		for (int i = 0; i < 8; i++){
			ass.eff_mat.data[i] = Arrays.copyOf(a[i], 7);
		}
		ass.execute();
		System.out.println("after Execute:");
		System.out.println(ass.eff_mat);
		System.out.println(ass.res_mat);
		System.out.println(ass.getResult());
		
		ass.update(AlgorithmLevel.ALGOR_LEVEL_1, newdata, 3, 100, 100, 1, 4);
		System.out.println("after update:");
		System.out.println(ass.eff_mat);
		System.out.println(ass.res_mat);
		System.out.println(ass.getResult());
	}
}
