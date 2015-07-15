package org.buaa.xuxian.net.broadcast;

import net.sf.json.JSONObject;

public class ReceiveChannel {
	private final int MAX_QUEUE;
	private final String[] transQueue;
	private int tail;
	private int head;
	private int count;
	
	public ReceiveChannel(int queueLenth){
		this.MAX_QUEUE = queueLenth;
		this.transQueue = new String[this.MAX_QUEUE];
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}
	
	public synchronized void putPacket(String packetStr){
		while (count >= this.transQueue.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		this.transQueue[this.tail] = packetStr;
		this.tail = (this.tail + 1) % this.transQueue.length;
		count++;
		notifyAll();
	}
	
	public synchronized String takePacket(StringBuffer senderIP){
		while (count <= 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String tempStr = this.transQueue[head];
		this.head = (this.head + 1) % this.transQueue.length;
		count--;
		JSONObject rJson = JSONObject.fromObject(tempStr);
		senderIP.append(rJson.getString("IP").substring(1));
		notifyAll();
		return rJson.getString("content");
	}
	
	public synchronized String takePacket(){
		while (count <= 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String tempStr = this.transQueue[head];
		this.head = (this.head + 1) % this.transQueue.length;
		count--;
		JSONObject rJson = JSONObject.fromObject(tempStr);
		notifyAll();
		return rJson.getString("content");
	}

}
