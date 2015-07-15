package org.buaa.xuxian.assign;

public class AssignConfig {
	public final int selfID;
	public final int plane;
	public final int target;
	public final double median;
	public final double divider;
	
	public AlgorithmLevel level;
	public double dis;
	public double g_ratio;
	public int excep_ID;
	public AssignConfig(int selfID, int plane, int target, double median,
			double divider, AlgorithmLevel level, double dis, double g_ratio,
			int excep_ID) {
		super();
		this.selfID = selfID;
		this.plane = plane;
		this.target = target;
		this.median = median;
		this.divider = divider;
		this.level = level;
		this.dis = dis;
		this.g_ratio = g_ratio;
		this.excep_ID = excep_ID;
	}
	
	public AssignConfig(int selfID, int plane, int target,
			AlgorithmLevel level, double dis, double g_ratio, int excep_ID) {
		super();
		this.selfID = selfID;
		this.plane = plane;
		this.target = target;
		this.level = level;
		this.dis = dis;
		this.g_ratio = g_ratio;
		this.excep_ID = excep_ID;
		this.median = 150;
		this.divider = 8;
	}
	
	public AssignConfig(int selfID, int plane, int target, AlgorithmLevel level) {
		super();
		this.selfID = selfID;
		this.plane = plane;
		this.target = target;
		this.level = level;
		this.dis = 1000;
		this.g_ratio = 0;
		this.excep_ID = -1;
		this.median = 150;
		this.divider = 8;
	}
	
}
