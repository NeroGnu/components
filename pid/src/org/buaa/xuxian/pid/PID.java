package org.buaa.xuxian.pid;

public class PID {
	private double SetPoint;     //Desired Value
	
	private double Proportion;   //Proportional Const
	private double Integral;     //Integral Const
	private double Derivative;   //Derivative Const
	
	private double LastError;    //  Error[-1]
	private double PrevError;    //  Error[-2]
	private double SumError;     //  Sums of Errors
	
	public PID(){
		this.SetPoint = 0;
		
		this.Proportion = 0;
		this.Integral = 0;
		this.Derivative = 0;
		
		this.LastError = 0;
		this.PrevError = 0;
		this.SumError = 0;
	}
	
	public PID(double SetPoint, double Proportion, double Integral, double Derivative){
		this.SetPoint = SetPoint;
		
		this.Proportion = Proportion;
		this.Integral = Integral;
		this.Derivative = Derivative;
		
		this.LastError = 0;
		this.PrevError = 0;
		this.SumError = 0;
	}
	
	public void PID_set(double SetPoint, double Proportion, double Integral, double Derivative){
		this.SetPoint = SetPoint;
		
		this.Proportion = Proportion;
		this.Integral = Integral;
		this.Derivative = Derivative;
	}

	public void PID_reset(){
		this.LastError = 0;
		this.PrevError = 0;
		this.SumError = 0;
	}
	
	public double PID_calc(double Feedback){
		double dError, Error;
		
		Error = this.SetPoint - Feedback;
		this.SumError = this.SumError + Error;
		dError = this.LastError - this.PrevError;
		this.PrevError = this.LastError;
		this.LastError = Error;
		return (this.Proportion * Error + this.Integral * this.SumError + this.Derivative * dError);
	}
}
