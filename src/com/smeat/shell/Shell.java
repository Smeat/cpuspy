package com.smeat.shell;

import java.io.DataOutputStream;
import java.io.IOException;

public class Shell {
	
	/**
	 * Runs the given command as root.
	 * @param command the command which shall executed as root
	 * @return returns if the command was successfully executed
	 * @throws RootException thrown if command couldn't be executed
	 * @todo separate error detection for su and the actual command 
	 */
	public static boolean execAsRoot(String command) throws RootException{
		Process process = null;
		DataOutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			
			out = new DataOutputStream(process.getOutputStream());
			out.writeBytes(command + '\n');
			out.writeBytes("exit\n");
			out.flush();			
			process.waitFor();
			
			if(process.exitValue() != 0){
				throw new RootException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally{
			try{
				if(out != null){
					out.close();
				}
				if(process != null){
					process.destroy();
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				return false;
			}
			
		}
		return true;
	}
	


}
