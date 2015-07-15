package org.buaa.xuxian.net.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.sf.json.JSONObject;

public class P2pSocket extends Thread{
	private final int DES_PORT;
	private final int DATA_LEN;
	private DatagramSocket socket = null;
	private InetAddress desAddress = null;
	private byte[] inBuff;
	private DatagramPacket inPacket;
	private DatagramPacket outPacket;
	private ReceiveChannel receiveChannel;
	private NeighbourList nbrList;
	private int repeatCounter;
	
	public P2pSocket(String DES_IP, int port, int dataLen, ReceiveChannel rChannel) {
		this.DES_PORT = port;
		this.DATA_LEN = dataLen;
		this.inBuff = new byte[this.DATA_LEN];
		this.receiveChannel = rChannel;
		this.nbrList = new NeighbourList();
		this.repeatCounter = 1;
		
		try {
			this.socket = new DatagramSocket(this.DES_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.desAddress = InetAddress.getByName(DES_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.inPacket = new DatagramPacket(inBuff , inBuff.length);
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
	}
	
	public P2pSocket(String DES_IP, int port, int dataLen, ReceiveChannel rChannel, int repeatCounter) {
		this.DES_PORT = port;
		this.DATA_LEN = dataLen;
		this.inBuff = new byte[this.DATA_LEN];
		this.receiveChannel = rChannel;
		this.nbrList = new NeighbourList();
		this.repeatCounter = 1;
		this.repeatCounter = repeatCounter;
		
		try {
			this.socket = new DatagramSocket(this.DES_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.desAddress = InetAddress.getByName(DES_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.inPacket = new DatagramPacket(inBuff , inBuff.length);
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
	}
	
	public P2pSocket(P2pConfig config, ReceiveChannel rChannel, int repeatCounter) {
		this.DES_PORT = config.port;
		this.DATA_LEN = config.dataLen;
		this.inBuff = new byte[this.DATA_LEN];
		this.receiveChannel = rChannel;
		this.nbrList = new NeighbourList();
		this.repeatCounter = repeatCounter;
		
		try {
			this.socket = new DatagramSocket(this.DES_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.desAddress = InetAddress.getByName(config.DES_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.inPacket = new DatagramPacket(inBuff , inBuff.length);
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
	}
	
	public P2pSocket(P2pConfig config, ReceiveChannel rChannel) {
		this.DES_PORT = config.port;
		this.DATA_LEN = config.dataLen;
		this.inBuff = new byte[this.DATA_LEN];
		this.receiveChannel = rChannel;
		this.nbrList = new NeighbourList();
		this.repeatCounter = 1;
		
		try {
			this.socket = new DatagramSocket(this.DES_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.desAddress = InetAddress.getByName(config.DES_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.inPacket = new DatagramPacket(inBuff , inBuff.length);
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
	}
	
	public int getRepeatCounter() {
		return repeatCounter;
	}

	public void setRepeatCounter(int repeatCounter) {
		this.repeatCounter = repeatCounter;
	}

	public synchronized void setDesAddress(String BROADCAST_IP){
		try {
			this.desAddress = InetAddress.getByName(BROADCAST_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
	}
	
	public synchronized void send(String DES_IP, String str){
		try {
			this.desAddress = InetAddress.getByName(DES_IP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.outPacket = new DatagramPacket(new byte[0] , 0 , this.desAddress , this.DES_PORT);
		
		String time_packet = "{\"time\":" + System.currentTimeMillis() + 
				 			 ",\"content\":" + str +
				 			 "}";
		outPacket.setData(time_packet.getBytes());
		for (int i = 0; i < this.repeatCounter; i++){
			
			try {
				socket.send(outPacket);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
	}
	
	public synchronized void send(String str){
		String time_packet = "{\"time\":" + System.currentTimeMillis() + 
							 ",\"content\":" + str +
							 "}";
		outPacket.setData(time_packet.getBytes());
		
		for (int i = 0; i < this.repeatCounter; i++){
			try {
				socket.send(outPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
	}
	
	@Override
	public void run(){
		String tempStr;
		JSONObject tempJason;
		ReceviveTime tempRecord;
		InetAddress selfAddr = null;
		
		try {
			selfAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (true){
				this.socket.receive(inPacket);
				if (!inPacket.getAddress().equals(selfAddr)){
					if (inPacket.getPort() == this.DES_PORT){
						tempStr = new String(inBuff, 0, inPacket.getLength());
						tempJason = JSONObject.fromObject(tempStr);
						tempRecord = new ReceviveTime(inPacket.getAddress(), 
								tempJason.getLong("time"), System.currentTimeMillis());
						if (this.nbrList.update(tempRecord)){
							tempStr = "{\"IP\":" + "\"" + inPacket.getAddress().toString() + "\"" + ",\"content\":" + 
									tempJason.getString("content") + "}";
							this.receiveChannel.putPacket(tempStr);
						}
					}
				}
			}
		} catch (IOException ex){
			ex.printStackTrace();
			if (null != this.socket){
				this.socket.close();
			}
			System.exit(1);
		}
	}

}

class ReceviveTime{
	public InetAddress IP;
	public long transTime;
	public long receviveTime;
	
	public ReceviveTime(InetAddress ip, long transTime, long receviveTime){
		this.IP = ip;
		this.transTime = transTime;
		this.receviveTime = receviveTime;
	}
	
	@Override
	public String toString(){
		return "sender IP: " + this.IP + "\r\n" +
			   "transmission time: " + this.transTime + ", " + "recevive time" + this.receviveTime;
	}
	
}

class NeighbourList{
	public ArrayList<ReceviveTime> list;
	
	public NeighbourList(){
		this.list = new ArrayList<ReceviveTime>();
	}
	
	public boolean update(ReceviveTime newrt){
		int len = list.size();
		for (int i = 0; i < len; i++){
			if (list.get(i).IP == newrt.IP){
				if (list.get(i).transTime == newrt.transTime){
					list.set(i, newrt);
					return false;
				} else {
					list.set(i, newrt);
					return true;
				}
			}
		}
		list.add(newrt);
		return true;
	}
}
