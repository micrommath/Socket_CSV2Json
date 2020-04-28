package models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueRead {

	private static BlockingQueue<String> queueData;
	private static boolean terminatedAdd;
	
	
	public QueueRead(int capacity) {
		queueData = new ArrayBlockingQueue<String>(capacity);
		terminatedAdd = false;
	}

	public static String getData() {
		try {
			if (queueData.size() > 0)
				return queueData.take();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void addData(String data) {
		try {
			queueData.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int getSize() {
		return queueData.size();
	}
	
	public static boolean getTerminatedAdd() {
		return terminatedAdd;
	}
	
	public static void setTerminatedAdd(boolean terminate) {
		terminatedAdd = terminate;
	}
}
