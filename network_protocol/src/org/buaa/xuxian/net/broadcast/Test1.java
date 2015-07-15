package org.buaa.xuxian.net.broadcast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class Test1 implements Runnable, JsonObjectSwitch<Test1> {
	public int a;
	public int b;
	public int c;
	private int[] d;
	private Random random;
	private ReceiveChannel channel;
	
	public int[] getD() {
		return d;
	}

	public void setD(int[] d) {
		this.d = d;
	}

	
	
	public Test1(ReceiveChannel channel){
		this.channel = channel;
		this.random = new Random();
		this.a = random.nextInt();
		this.b = random.nextInt();
		this.c = random.nextInt();
		int[] e = {random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt()};
		this.setD(e);
//		this.d = new int[0];
	}
	
	@Override
	public void run(){
//		JSONObject json;
		InetAddress senderIP = null;
		StringBuffer strIP = new StringBuffer();
		Gson gson = new Gson();
		
//		String[] tempStr;
		while (true){
//			json = JSONObject.fromObject(this.channel.takePacket(strIP));
//			tempStr = strIP.toString().split("/");
			Test1 te1 = gson.fromJson(this.channel.takePacket(strIP), Test1.class);
			try {
				senderIP = InetAddress.getByName(strIP.toString());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strIP.replace(0, strIP.length(), "");
			System.out.println("this is Test1  " + "from" + senderIP + "receive:" + te1);
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
		return this.a + " " + this.b + " " + this.c + " " + this.d[3];
	}

}
