package models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueParse {

	private static BlockingQueue<Brasil> queueData;
	private static boolean terminatedAdd;
	
	public QueueParse(int capacity) {
		queueData = new ArrayBlockingQueue<Brasil>(capacity);
		terminatedAdd = false;
	}

	public static Brasil getData() {
		try {
			if (queueData.size() > 0)
				return queueData.take();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void addData(Brasil data) {
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
