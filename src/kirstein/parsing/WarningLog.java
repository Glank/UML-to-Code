package kirstein.parsing;

import java.util.LinkedList;

public class WarningLog {
	private static LinkedList<Exception> warnings = new LinkedList<Exception>();
	
	public static void clearLog(){
		warnings.clear();
	}
	
	public static void log(Exception e){
		warnings.add(e);
	}
	
	public static void printAll(){
		for(Exception e:warnings)
			e.printStackTrace();
	}
}
