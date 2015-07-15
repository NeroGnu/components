package org.buaa.xuxian.net.broadcast;

import java.util.Random;
import java.util.Scanner;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class Test {
	
	public static void main(String[] args){
		ReceiveChannel Test1Channel = new ReceiveChannel(10);
		ReceiveChannel Test2Channel = new ReceiveChannel(10);
		P2pSocket Test1Socket = new P2pSocket("255.255.255.255", 4444, 4096, Test1Channel, 2);
		P2pSocket Test2Socket = new P2pSocket("255.255.255.255", 4445, 4096, Test2Channel);
		
		Test1 test1Show = new Test1(Test1Channel);
		Test2 test2Show = new Test2(Test2Channel);
		
//		int[] d = {1, 2, 3, 5};
//		test1Show.setD(d);
//		int[] gd = test1Show.getD();
//		System.out.println(" " + gd[0] + gd[1]);
//		
//		Gson gson = new Gson();
//		String json = gson.toJson(test1Show);
//		System.out.println(json);
//		
//		Test1 test12 = gson.fromJson(json, Test1.class);
//		System.out.println(test12);
		
		new Thread(test1Show).start();
		new Thread(test2Show).start();
		
		Test1Socket.start();
		Test2Socket.start();
		
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		JSONObject tempJson;
		int count1, count2;
		Gson gson = new Gson();
		
		while(scan.hasNextLine()){
			scan.nextLine().getBytes();
			count1 = rand.nextInt(3) + 1;
			count2 = rand.nextInt(3) + 1;
			
			for (int i = 0; i < count1; i++){
//				temp1 = new Student(10, "hrhehh", 17);
//				tempJson = new Test1(null).toJson();
				String temp = gson.toJson(new Test1(null));
				System.out.println(temp);
//				tempJson = JSONObject.
//				System.out.println("send:");
//				System.out.println("send:" + tempJson.toString());
				Test1Socket.send(temp);
				try {
					Thread.sleep(rand.nextInt(100));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for (int i = 0; i < count2; i++){
				tempJson = new Test2(null).toJson();
				Test2Socket.send(tempJson.toString());
				try {
					Thread.sleep(rand.nextInt(100));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		scan.close();
		
	}
}

//class Test1 implements Runnable {
//	private int a;
//	protected int b;
//	public int c;
//	private Random random;
//	private ReceiveChannel<Test1> channel;
//	
//	public Test1(ReceiveChannel<Test1> channel){
//		this.channel = channel;
//		this.random = new Random();
//		this.a = random.nextInt();
//		this.b = random.nextInt();
//		this.c = random.nextInt();
//	}
//	
//	@Override
//	public void run(){
//		while (true){
//			System.out.println(this.channel.takePacket());
//		}
//	}
//	
//	@Override
//	public String toString(){
//		return "class Test1 is:\r\n" + 
//			   "Random random is:" + random.nextInt() +
//			   "int a is: " + this.a + "\r\n" + 
//			   "int b is: " + this.b + "\r\n" + 
//			   "int c is: " + this.c;
//	}
//}

//class Test2 implements Runnable {
//	private Random random;
//	private long a;
//	protected double b;
//	public String c;
//	private ReceiveChannel<Test2> channel;
//	
//	public Test2(ReceiveChannel<Test2> channel){
//		this.channel = channel;
//		this.random = new Random();
//		this.a = random.nextLong();
//		this.b = random.nextDouble();
//		this.c = random.nextInt() + "+" + random.nextDouble();
//	}
//	
//	@Override
//	public void run(){
//		while (true){
//			System.out.println(this.channel.takePacket());
//		}
//	}
//	
//	@Override
//	public String toString(){
//		return "class Test1 is:\r\n" + 
//			   "Random random is:" + random.nextFloat() +
//			   "long a is: " + this.a + "\r\n" + 
//			   "double b is: " + this.b + "\r\n" + 
//			   "String c is: " + this.c;
//	}
//}