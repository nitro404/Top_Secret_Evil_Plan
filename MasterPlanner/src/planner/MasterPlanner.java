package planner;

import shared.*;
import com.rbcapp.planner.*;

public class MasterPlanner extends Planner {
	
	public MasterPlanner(RBCPlannerHandler handler) {
		super(handler);
		userTitle = "";
		SystemManager.initialize(this);
	}
	
	public void receivedPoseFromTracker(Pose robotPose) {
		if(robotPose == null) { return; }
		SystemManager.handlePose(new Position(robotPose.x, robotPose.y), robotPose.angle);
	}
	
	public void receivedDataFromRobot(int[] data) {
		SystemManager.handleRobotData(castToByteArray(data));
	}
	
	public void receivedDataFromStation(int stationID, int[] data) {
		SystemManager.handleStationData(stationID, castToByteArray(data));
	}
	
	public void receivedDataFromTracker(int[] data) {
		SystemManager.handleTrackerData(castToByteArray(data));
	}
	
	public void sendDataToRobot(byte[] data) {
		super.sendDataToRobot(data);
	}
	
	public void sendDataToStation(int station, byte[] data) {
		super.sendDataToStation(station, data);
	}
	
	public void sendDataToTraceFile(String data) {
		super.sendDataToTraceFile(data);
	}
	
	public void sendDataToTracker(byte[] data) {
		super.sendDataToTracker(data);
	}
	
	public void sendEstimatedPositionToTracker(int x, int y, int angle) {
		super.sendEstimatedPositionToTracker(x, y, angle);
	}
	
	public void setTrackerFrameRate(int frameRate) {
		if(frameRate >= 1 && frameRate <= 30) {
			trackerFrameRate = frameRate;
		}
	}
	
	public byte[] castToByteArray(int[] data) {
		if(data == null) { return null; }
		byte[] newData = new byte[data.length];
		for(int i=0;i<data.length;i++) {
			newData[i] = (byte) data[i];
		}
		return newData;
	}
	
}
