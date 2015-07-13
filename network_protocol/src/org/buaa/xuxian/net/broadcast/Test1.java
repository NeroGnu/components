package org.buaa.xuxian.net.broadcast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import net.sf.json.JSONObject;

public class Test1 implements Runnable, JsonObjectSwitch<Test1> {
	public int a;
	public int b;
	public int c;
	private Random random;
	private ReceiveChannel channel;
	
	public Test1(ReceiveChannel channel){
		this.channel = channel;
		this.random = new Random();
		this.a = random.nextInt();
		this.b = random.nextInt();
		this.c = random.nextInt();
	}
	
	@Override
	public void run(){
		JSONObject json;
		InetAddress senderIP = null;
		StringBuffer strIP = new StringBuffer();
		String[] tempStr;
		while (true){
			json = JSONObject.fromObject(this.channel.takePacket(strIP));
			tempStr = strIP.toString().split("/");
			try {
				senderIP = InetAddress.getByName(tempStr[1]);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strIP.replace(0, strIP.length(), "");
			System.out.println("from" + senderIP + "receive:" + toObject(json));
		}
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.accumulate("a", this.a);
		json.accumulate("b", this.b);
		json.accumulate("c", this.c);
		return json;
	}
	
	public Test1 toObject(JSONObject json){
		Test1 temp = new Test1(null);
		temp.a = json.getInt("a");
		temp.b = json.getInt("b");
		temp.c = json.getInt("c");
		return temp;
	}
	
	@Override
	public String toString(){
		return this.a + " " + this.b + " " + this.c;
	}

}
