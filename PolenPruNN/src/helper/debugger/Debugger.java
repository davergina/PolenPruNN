package helper.debugger;

public class Debugger {

	private static final boolean DEBUG_MODE = true;
	
	public static void print(String string) {
		if (DEBUG_MODE) {
		}
	}
	
	public static void println(String string) {
		if (DEBUG_MODE) {
		}
	}
	
	public static void println() {
		println("");
	}
	
	public static void print(int number) {
		print(makeString(number));
	}
	
	public static void println(int number) {
		println(makeString(number));
	}
	
	public static void print(double realNumber) {
		print(makeString(realNumber));
	}
	
	public static void println(double realNumber) {
		println(makeString(realNumber));
	}
	
	private static String makeString(int number) {
		return "" + number;
	}
	
	private static String makeString(double realNumber) {
		return "" + realNumber;
	}
}
