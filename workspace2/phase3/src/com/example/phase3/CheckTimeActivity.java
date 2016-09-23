package com.example.phase3;

import java.io.File;
import java.io.FileNotFoundException;

import com.example.NurseManager.Nurse;
import com.example.patient_information.Patient;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**Check the time seen by physician.*/
public class CheckTimeActivity extends Activity {
	
	/** This is a Nurse*/
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_time);
		Intent intent = getIntent();
     	nurse = (Nurse) intent.getSerializableExtra("nurse");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_time, menu);
		return true;
	}
	
	/**Check the time when the patient seen by physician.
	 * @param view
	 * @throws FileNotFoundException if file not exists.
	 */
	public void CheckTime(View view) throws FileNotFoundException {
		
		EditText healthcardnumberText = (EditText) findViewById(R.id.healthnum);
		String healthcardnumber = healthcardnumberText.getText().toString().trim();
		
		EditText dayText = (EditText) findViewById(R.id.day1);
    	String day1 = dayText.getText().toString();
    	
    	EditText monthText = (EditText) findViewById(R.id.month1);
    	String month1 = monthText.getText().toString();
    	
    	EditText yearText = (EditText) findViewById(R.id.year1);
    	String year1 = yearText.getText().toString();
    	
    	EditText hourText = (EditText) findViewById(R.id.hour1);
    	String hour1 = hourText.getText().toString();
    	
    	EditText minuteText = (EditText) findViewById(R.id.minute1);
    	String minute1 = minuteText.getText().toString();
		
		if (healthcardnumber.equals("") || year1.equals("") || month1.equals("") || day1.equals("")
    			|| hour1.equals("") || minute1.equals("")) {
			new AlertDialog.Builder(this)
            .setTitle("sorry")
            .setMessage("invalid information entered")
            .setPositiveButton("try again", null)
            .show();
		} else {
			
			File file = new File(this.getApplicationContext().getFilesDir(), "patient.txt");
    	    String filepath = file.toString();
    	    nurse.readFromFile(filepath);
    	    
    	    //if contains this patient,go ahead.
			if (nurse.cotainsPatient(healthcardnumber)) {
				//get the patient.
				Patient patient = nurse.getPatients().get(healthcardnumber);
				//determined whether the patient have already seen by physician.
				if (patient.getSeen_physician().equals("Haven't seen physician yet")) {
					
					String time = "seen by physician:" + day1 + "/" + month1 + "/" + year1 +" " + 
					hour1 + ":" + minute1;
					nurse.addCheckTime(patient, time, filepath);
					
					new AlertDialog.Builder(this)
		            .setTitle("Good")
		            .setMessage("Time add successfully")
		            .setPositiveButton("finish", null)
		            .show();
					
					Intent intentC = new Intent(this,ChoiceActivity.class);
	    		    intentC.putExtra("nurse", nurse);
	    		    startActivity(intentC);
	    		    
				} else {
					new AlertDialog.Builder(this)
		            .setTitle("sorry")
		            .setMessage("patient has already seen by the doctor")
		            .setPositiveButton("try again", null)
		            .show();
				}
			
				//if patient doesn't exists.
			} else {
				new AlertDialog.Builder(this)
	            .setTitle("sorry")
	            .setMessage("patient doesn't exist")
	            .setPositiveButton("try again", null)
	            .show();
			}
		}
	}

}
