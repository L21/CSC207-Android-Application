package com.example.phase3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.NurseManager.Nurse;
import com.example.NurseManager.Physician;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/** View Patient Record */
public class PatientRecordActivity extends Activity {
	
	/** This is a Nurse.*/
	private Nurse nurse;
	
	/** This is a Physician.*/
	private Physician physician;
	
	/** This is the health card number.*/
	private String healthcardnumber;
	
	/** This is Map contains the patient's data.*/
	private Map<String, ArrayList<String>> list_information;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_record);
		
		Intent intent = getIntent();
		nurse = (Nurse) intent.getSerializableExtra("nurse");
		physician = (Physician) intent.getSerializableExtra("physician");
		healthcardnumber = (String) intent.getSerializableExtra("HCcard");
		
		ListView list = (ListView) findViewById(R.id.ListView02);
		
		//determined whether the user is nurse or physician.
		if (nurse == null) {
			Physician people = physician;
			list_information = people.getPatientInformation(healthcardnumber);
		} else {
			 Nurse people = nurse;
			list_information = people.getPatientInformation(healthcardnumber);
		}
		ArrayList<String> information_list = new ArrayList<String>();

		information_list.addAll(list_information.get("patient_information"));
		information_list.addAll(list_information.get("vitalsign"));
		String title = "Health Card Number: " + healthcardnumber;
		
		ArrayList<Map<String, Object>> list_of_patient_information = new ArrayList<Map<String, Object>>();
		for (int i =0 ; i < information_list.size(); i++) {
			String information = information_list.get(i);
			HashMap<String, Object> viewrecord = new HashMap<String, Object>();
			viewrecord.put("Health_Card_Number",title);
			viewrecord.put("patient_information_view", information);
			list_of_patient_information.add(viewrecord);
		}
		
		//show the information.
		SimpleAdapter listItemAdapter = new SimpleAdapter(this,list_of_patient_information, 
				R.layout.activity_patient_record_help, new String[] {"Health_Card_Number", 
				"patient_information_view"}, 
				new int[] {R.id.num,R.id.patient_information_view});  
		list.setAdapter(listItemAdapter);

}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_record, menu);
		return true;
	}

}
