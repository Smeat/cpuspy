package com.bvalosek.cpuspy.ui;

import com.bvalosek.cpuspy.CPUGovernorManager;
import com.bvalosek.cpuspy.R;
import com.smeat.shell.RootException;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
	
	ListPreference _cpuGovernor = null;
	
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.preferences);
        
        findViews();
        
        setGovernorListValues();
        
        addListeners();
        
    }
	 
	 private void addListeners() {
		_cpuGovernor.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String value = (String)newValue;
				setGovernor(value);
				return false;
			}
		});
		
	}

	private void findViews() {
		 _cpuGovernor = (ListPreference)findPreference("cpu_governor_list");
	 }
	 
	 private void setGovernorListValues(){		 
		 String[] values = CPUGovernorManager.getAvailableGovernors();
		 
		 if(values != null){
			_cpuGovernor.setEntries(values);
			_cpuGovernor.setEntryValues(values);
		 }
		 
		 String currentGovernor = CPUGovernorManager.getCurrentGovernor();
		 _cpuGovernor.setValue(currentGovernor);
		 _cpuGovernor.setSummary(currentGovernor);
	 }
	 
	 private void setGovernor(String governor){
		 try{
			 if(CPUGovernorManager.setGovernor(governor)){
				 _cpuGovernor.setValue(governor);
				 _cpuGovernor.setSummary(governor);
			 }
		 }
		 catch (RootException e) {
			showRootError();
		}
	 }
	 
	 private void showRootError(){
		 Context context = getApplicationContext();
		 CharSequence text = getString(R.string.Root_error);
		 int duration = Toast.LENGTH_SHORT;

		 Toast toast = Toast.makeText(context, text, duration);
		 toast.show();
	 }
}
