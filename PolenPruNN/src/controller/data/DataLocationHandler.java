package controller.data;

import java.io.File;

public class DataLocationHandler 
{
	private static String pathname = "";
	
	public DataLocationHandler(){}
	
	public static void setBaseFolder(String pathname2)
	{
		pathname = pathname2;
	}
	
	public static File getBaseFolder()
	{
		return new File(pathname);
	}
}
