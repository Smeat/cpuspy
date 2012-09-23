package com.smeat.shell;

import java.io.IOException;

public class Shell {
	
	public static boolean execAsRoot(String command) throws RootException{
		try {
			Process process = Runtime.getRuntime().exec("su -c " + command);
			
			process.waitFor();
			if(process.exitValue() != 0){
				throw new RootException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	


}
