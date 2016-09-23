package com.example.phase3;

import java.io.File;
import java.io.IOException;

import com.example.NurseManager.Nurse;
import com.example.patient_information.Patient;
import com.example.patient_information.VitalSign;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/** Add the vitalsign.*/
public class AddVitalSignActivity extends Activity {
	
	/** This is a Nurse*/
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vital_sign);
		Intent intent = getIntent();
     	nurse = (Nurse) intent.getSerializableExtra("nurse");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_vital_sign, menu);
		return true;
	}
	
	/** Adds the vital sign of patient.
	 * @param view
	 * @throws IOException if failed to set a new vital sign.
	 */
	public void AddVitalSigns(View view) throws IOException {
    	//get the input information
    	EditText healthcardnumberText = (EditText) findViewById(R.id.healthcardnum);
    	String healthcardnumber = healthcardnumberText.getText().toString().trim();
    	
    	EditText temperatureText = (EditText) findViewById(R.id.temperature);
    	String temperature1 = temperatureText.getText().toString();
    	
    	EditText systolicText = (EditText) findViewById(R.id.systolic);
    	String systolic1 = systolicText.getText().toString();
    	
    	EditText diastolicText = (EditText) findViewById(R.id.diastolic);
    	String diastolic1 = diastolicText.getText().toString();
    	
    	EditText heartrateText = (EditText) findViewById(R.id.heartrate);
    	String heartrate1 = heartrateText.getText().toString();
    	
    	EditText dayText = (EditText) findViewById(R.id.currentday);
    	String day1 = dayText.getText().toString();
    	
    	EditText monthText = (EditText) findViewById(R.id.currentmonth);
    	String month1 = monthText.getText().toString();
    	
    	EditText yearText = (EditText) findViewById(R.id.currentyear);
    	String year1 = yearText.getText().toString();
    	
    	EditText hourText = (EditText) findViewById(R.id.currenthour);
    	String hour1 = hourText.getText().toString();
    	
    	EditText minuteText = (EditText) findViewById(R.id.currentminute);
    	String minute1 = minuteText.getText().toString();
    	
    	if (healthcardnumber.equals("") || temperature1.equals("") || systolic1.equals("") || 
    			diastolic1.equals("") || heartrate1.equals("") || year1.equals("") 
    			|| month1.equals("") || day1.equals("") || hour1.equals("") 
    			|| minute1.equals("")) {
    		
    		new AlertDialog.Builder(this)
            .setTitle("sorry")
            .setMessage("invalid information entered")
            .setPositiveButton("try again", null)
            .show();
    	} else {
    		
    		double temperature = Double.valueOf(temperature1).doubleValue();
    		
    		int systolic = Integer.valueOf(systolic1).intValue();
    		
    		int diastolic = Integer.valueOf(diastolic1).intValue();
    		
    		int heartrate = Integer.valueOf(heartrate1).intValue();
    		
    		int day = Integer.valueOf(day1).intValue();
        	
    		int month = Integer.valueOf(month1).intValue();
    		
    		int year = Integer.valueOf(year1).intValue();
    		
    		int hour = Integer.valueOf(hour1).intValue();
    		
    		int minute = Integer.valueOf(minute1).intValue();
    		
    	    //get the nurse
    	    Intent intent = getIntent();
    	    nurse = (Nurse) intent.getSerializableExtra("nurse");
    
    	    nurse.getPatients().clear();
    	
    	
    	    //get the filepath,and read the file
    	    File file = new File(this.getApplicationContext().getFilesDir(), "patient.txt");
    	    String filepath = file.toString();
    	    nurse.readFromFile(filepath);
    	
    	
    	    //judge whether the patient exists or not
    	    if (nurse.cotainsPatient(healthcardnumber)) {
    		    Patient patient = nurse.getPatients().get(healthcardnumber);
    		
    		    //create vitalsign.
    	        VitalSign vitalsign = new VitalSign(temperature, systolic, diastolic, heartrate, 
    	        		hour, minute, day, month, year);

                nurse.readFromFile(filepath);
            
                //add the vitalsign to patient.
    	        nurse.addVitalSign1(patient, vitalsign, filepath);
    	        new AlertDialog.Builder(this)
                .setTitle("approved")
                .setMessage("VitalSign has been add successfully")
                .setPositiveButton("finish", null)
                .show();
    	   
    	    
    	        //set the nurse information back.
    	        Intent intentC = new Intent(this,ChoiceActivity.class);
    		    intentC.putExtra("nurse", nurse);
    		    startActivity(intentC);
       
    	
    	
    	
    	    } else {
        	    //if does not exit,raise it
    	        new AlertDialog.Builder(this)
                .setTitle("sorry")
                .setMessage("Patient doesn't exist")
                .setPositiveButton("try again", null)
                .show();
            }
        }
	}

}
