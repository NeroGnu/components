package org.buaa.xuxian.assign;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.buaa.xuxian.net.broadcast.*;

import com.google.gson.Gson;

public class AssignRuntime extends Thread {
	public AssignConfig config;
	public Assign assignMethod;
	public final int selfID;
	public P2pSocket assigSocket;
	private ReceiveChannel rChannel;
	public IDRow ioEfficiency;
	public double[] selfEfficiency;
	
//	public AssignRuntime(AssignConfig aConfig, int channelLen, P2pConfig config){
//		this.selfID = aConfig.selfID;
//		this.assignMethod = new Assign(aConfig.plane, aConfig.target, aConfig.selfID);
//		this.rChannel = new ReceiveChannel(channelLen);
//		this.assigSocket = new P2pSocket(config.DES_IP, config.port, config.dataLen, this.rChannel);
//	}
	
	public AssignRuntime(AssignConfig aConfig, int channelLen, P2pConfig config){
		this.config = aConfig;
		this.selfID = aConfig.selfID;
		this.assignMethod = new Assign(aConfig.plane, aConfig.target, aConfig.selfID, aConfig.median, aConfig.divider);
		this.rChannel = new ReceiveChannel(channelLen);
		this.assigSocket = new P2pSocket(config.DES_IP, config.port, config.dataLen, this.rChannel);
	}
	
	public double[] computeEfficiency(double[] e){
		
		this.selfEfficiency = e;
		return e;
	}
	
	public void updateSelfAssign(){
		this.assignMethod.update(this.config.level, this.selfEfficiency, this.selfID, System.currentTimeMillis(), this.config.dis, this.config.g_ratio, this.config.excep_ID);
	}
	
	public void sendEfficiency(){
		IDRow toSend = new IDRow(this.selfID, this.selfEfficiency);
		Gson gson = new Gson();
		this.assigSocket.send(gson.toJson(toSend));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Gson gson = new Gson();
		this.assigSocket.start();
		while (true){
			IDRow receiveObj = gson.fromJson(this.rChannel.takePacket(), IDRow.class);
			this.assignMethod.update(this.config.level, receiveObj.e, receiveObj.ID, 
					System.currentTimeMillis(), this.config.dis, this.config.g_ratio, this.config.excep_ID);
			System.out.println(receiveObj);
			System.out.println(this.assignMethod);
		}	
	}
	
	public static void main(String[] args){
		Random random = new Random();
		AssignConfig aConfig = new AssignConfig(1, 3, 4, AlgorithmLevel.ALGOR_LEVEL_2);
		P2pConfig pConfig = new P2pConfig("255.255.255.255", 4444, 4096);
		AssignRuntime ar = new AssignRuntime(aConfig, 10, pConfig);
		
		ar.start();
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			scan.nextLine().getBytes();
			ar.computeEfficiency(new double[] {random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble()});
			ar.updateSelfAssign();
			ar.sendEfficiency();
			System.out.println("send:\r\n");
			System.out.println(Arrays.toString(ar.selfEfficiency));
		}
	}

}
