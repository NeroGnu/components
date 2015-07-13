package org.buaa.xuxian.net.broadcast;

import java.util.Random;

import net.sf.json.JSONObject;

public class Test2 implements Runnable, JsonObjectSwitch<Test2>{
	private Random random;
	public long a;
	public double b;
	public String c;
	public ReceiveChannel channel;
	
	public Test2(ReceiveChannel channel){
		this.channel = channel;
		this.random = new Random();
		this.a = random.nextLong();
		this.b = random.nextDouble();
		this.c = random.nextInt() + "+" + random.nextDouble();
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.accumulate("a", this.a);
		json.accumulate("b", this.b);
		json.accumulate("c", this.c);
		return json;
	}
	
	public Test2 toObject(JSONObject json){
		Test2 temp = new Test2(null);
		temp.a = json.getLong("a");
		temp.b = json.getDouble("b");
		temp.c = json.getString("c");
		return temp;
	}
	
	@Override
	public void run(){
		JSONObject json;
		while (true){
			json = JSONObject.fromObject(this.channel.takePacket());
			System.out.println("receive:" + toObject(json));
		}
	}
	
	@Override
	public String toString(){
		return "class Test2 is:\r\n" + 
			   "Random random is:" + random.nextFloat() +
			   "long a is: " + this.a + "\r\n" + 
			   "double b is: " + this.b + "\r\n" + 
			   "String c is: " + this.c;
	}

}
