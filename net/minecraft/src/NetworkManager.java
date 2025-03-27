package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NetworkManager {
	public static final Object threadSyncObject = new Object();
	public static int numReadThreads;
	public static int numWriteThreads;
	private Object sendQueueLock = new Object();
	private Socket networkSocket;
	private DataInputStream socketInputStream;
	private DataOutputStream socketOutputStream;
	private boolean isRunning = true;
	private List readPackets = Collections.synchronizedList(new LinkedList());
	private List dataPackets = Collections.synchronizedList(new LinkedList());
	private List chunkDataPackets = Collections.synchronizedList(new LinkedList());
	private NetHandler netHandler;
	private boolean isServerTerminating = false;
	private Thread writeThread;
	private Thread readThread;
	private boolean isTerminating = false;
	private String terminationReason = "";
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;
	private int chunkDataSendCounter = 0;

	public NetworkManager(Socket var1, String var2, NetHandler var3) throws IOException {
		this.networkSocket = var1;
		this.netHandler = var3;
		var1.setTrafficClass(24);
		this.socketInputStream = new DataInputStream(var1.getInputStream());
		this.socketOutputStream = new DataOutputStream(var1.getOutputStream());
		this.readThread = new NetworkReaderThread(this, var2 + " read thread");
		this.writeThread = new NetworkWriterThread(this, var2 + " write thread");
		this.readThread.start();
		this.writeThread.start();
	}

	public void addToSendQueue(Packet var1) {
		if(!this.isServerTerminating) {
			Object var2 = this.sendQueueLock;
			synchronized(var2) {
				this.sendQueueByteLength += var1.getPacketSize() + 1;
				if(var1.isChunkDataPacket) {
					this.chunkDataPackets.add(var1);
				} else {
					this.dataPackets.add(var1);
				}

			}
		}
	}

	private void sendPacket() {
		try {
			boolean var1 = true;
			Packet var2;
			Object var3;
			if(!this.dataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				synchronized(var3) {
					var2 = (Packet)this.dataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				Packet.writePacket(var2, this.socketOutputStream);
			}

			if((var1 || this.chunkDataSendCounter-- <= 0) && !this.chunkDataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				synchronized(var3) {
					var2 = (Packet)this.chunkDataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				Packet.writePacket(var2, this.socketOutputStream);
				this.chunkDataSendCounter = 50;
			}

			if(var1) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException var8) {
		} catch (Exception var9) {
			if(!this.isTerminating) {
				this.onNetworkError(var9);
			}
		}

	}

	private void readPacket() {
		try {
			Packet var1 = Packet.readPacket(this.socketInputStream);
			if(var1 != null) {
				this.readPackets.add(var1);
			} else {
				this.networkShutdown("End of stream");
			}
		} catch (Exception var2) {
			if(!this.isTerminating) {
				this.onNetworkError(var2);
			}
		}

	}

	private void onNetworkError(Exception var1) {
		var1.printStackTrace();
		this.networkShutdown("Internal exception: " + var1.toString());
	}

	public void networkShutdown(String var1) {
		if(this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = var1;
			(new NetworkMasterThread(this)).start();
			this.isRunning = false;

			try {
				this.socketInputStream.close();
			} catch (Throwable var5) {
			}

			try {
				this.socketOutputStream.close();
			} catch (Throwable var4) {
			}

			try {
				this.networkSocket.close();
			} catch (Throwable var3) {
			}

		}
	}

	public void processReadPackets() {
		if(this.sendQueueByteLength > 1048576) {
			this.networkShutdown("Send buffer overflow");
		}

		if(this.readPackets.isEmpty()) {
			if(this.timeSinceLastRead++ == 1200) {
				this.networkShutdown("Timed out");
			}
		} else {
			this.timeSinceLastRead = 0;
		}

		int var1 = 100;

		while(!this.readPackets.isEmpty() && var1-- >= 0) {
			Packet var2 = (Packet)this.readPackets.remove(0);
			var2.processPacket(this.netHandler);
		}

		if(this.isTerminating && this.readPackets.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason);
		}

	}

	static boolean isRunning(NetworkManager var0) {
		return var0.isRunning;
	}

	static boolean isServerTerminating(NetworkManager var0) {
		return var0.isServerTerminating;
	}

	static void readNetworkPacket(NetworkManager var0) {
		var0.readPacket();
	}

	static void sendNetworkPacket(NetworkManager var0) {
		var0.sendPacket();
	}

	static Thread getReadThread(NetworkManager var0) {
		return var0.readThread;
	}

	static Thread getWriteThread(NetworkManager var0) {
		return var0.writeThread;
	}
}
