public class DrawPathType {
	
	final public static byte Disabled = 0;
	final public static byte ActivePathOnly = 1;
	final public static byte AllPaths = 2;
	
	final public static String[] drawPathTypes = {
		"Disabled",
		"Active Path Only",
		"All Paths"
	};
	
	public static boolean isValid(byte drawPathType) {
		return drawPathType >= 0 && drawPathType < drawPathTypes.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<drawPathTypes.length;i++) {
			if(temp.equalsIgnoreCase(drawPathTypes[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte drawPathType) {
		return !isValid(drawPathType) ? "Invalid" : drawPathTypes[drawPathType];
	}
	
}
