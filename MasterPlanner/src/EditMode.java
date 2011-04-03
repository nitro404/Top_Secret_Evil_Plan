public class EditMode {
	
	final public static byte ViewOnly = 0;
	final public static byte Path = 1;
	final public static byte Robot = 2;
	final public static byte Block = 3;
	final public static byte Pot = 4;
	
	final public static String[] displayEditModes = {
		"View Only", "Path", "Robot", "Block", "Pot"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < displayEditModes.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<displayEditModes.length;i++) {
			if(temp.equalsIgnoreCase(displayEditModes[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte displayPanelEditMode) {
		return !isValid(displayPanelEditMode) ? "Invalid" : displayEditModes[displayPanelEditMode];
	}
	
}
