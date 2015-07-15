package org.buaa.xuxian.net.broadcast;

public class P2pConfig {
	public String DES_IP;
	public int port;
	public int dataLen;
	
	public P2pConfig(String dES_IP, int port, int dataLen) {
		super();
		DES_IP = dES_IP;
		this.port = port;
		this.dataLen = dataLen;
	}
	
}
