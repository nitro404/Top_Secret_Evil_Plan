package planner;

import com.rbcapp.planner.*;

public class MasterPlanner extends Planner {
	
	public MasterPlanner(RBCPlannerHandler handler) {
		super(handler);
		
		userTitle = "";
		
		SystemManager.initialize(this);
	}
	
	// receive robot's pose from tracker
	public void receivedPoseFromTracker(Pose robotPose) {
		try {
			if(robotPose == null) { return; }
			SystemManager.handlePose(new Position(robotPose.x, robotPose.y), robotPose.angle);
		}
		catch(Exception e) {
			System.out.println(e.getClass().getSimpleName() + " thrown while executing receivedPoseFromTracker: " + e.getMessage());
			e.printStackTrace();
			System.out.println();
		}
	}
	
	// receive data from robot
	public void receivedDataFromRobot(int[] data) {
		try {
			SystemManager.handleRobotData(castToByteArray(data));
		}
		catch(Exception e) {
			System.out.println(e.getClass().getSimpleName() + " thrown while executing receivedDataFromRobot: " + e.getMessage());
			e.printStackTrace();
			System.out.println();
		}
	}
	
	// receive data from another station
	public void receivedDataFromStation(int stationID, int[] data) {
		try {
			SystemManager.handleStationData(stationID, castToByteArray(data));
		}
		catch(Exception e) {
			System.out.println(e.getClass().getSimpleName() + " thrown while executing receivedDataFromStation: " + e.getMessage());
			e.printStackTrace();
			System.out.println();
		}
	}
	
	// receive data from another tracker
	public void receivedDataFromTracker(int[] data) {
		try {
			SystemManager.handleTrackerData(castToByteArray(data));
		}
		catch(Exception e) {
			System.out.println(e.getClass().getSimpleName() + " thrown while executing receivedDataFromTracker: " + e.getMessage());
			e.printStackTrace();
			System.out.println();
		}
	}
	
	public void setTrackerFrameRate(int frameRate) {
		if(frameRate >= 1 && frameRate <= 30) {
			trackerFrameRate = frameRate;
		}
	}
	
	// convert an integer array to a byte array
	public byte[] castToByteArray(int[] data) {
		if(data == null) { return null; }
		byte[] newData = new byte[data.length];
		for(int i=0;i<data.length;i++) {
			newData[i] = (byte) data[i];
		}
		return newData;
	}
	
}
