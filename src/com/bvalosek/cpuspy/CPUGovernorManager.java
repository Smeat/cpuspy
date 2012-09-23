package com.bvalosek.cpuspy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.smeat.shell.RootException;
import com.smeat.shell.Shell;

public class CPUGovernorManager {
	
	 private static final String GOVERNOR_SETTING_PATH =
		        "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
	 
	 private static final String GOVERNOR_INFO_PATH =
			 "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
	 
	 public static String[] getAvailableGovernors(){		 
		try {
			InputStream is = new FileInputStream(GOVERNOR_INFO_PATH);
			InputStreamReader ir = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(ir);
        	
        	String line, result = new String();
        	
        	while((line = br.readLine()) != null){
        		result += line;
        	}
        	
        	return result.split(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	 }
	 
	 public static String getCurrentGovernor(){

		try {
			InputStream is = new FileInputStream(GOVERNOR_SETTING_PATH);
			InputStreamReader ir = new InputStreamReader(is);
	     	BufferedReader br = new BufferedReader(ir);
	     	
	     	return br.readLine();
	     	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	 }
	 
	 public static boolean setGovernor(String governor) throws RootException{
		if(!isValidGovernor(governor)) return false;
		return Shell.execAsRoot("echo " + governor + " > " + GOVERNOR_SETTING_PATH);	 
	 }
	 
	 private static boolean isValidGovernor(String governor){
		 String[] governors = getAvailableGovernors();
		 
		 for(String availableGovernor : governors){
			 if(governor.equals(availableGovernor)) return true;
		 }
		 
		 return false;
		 
	 }

}
