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

/**Add a new patient.*/
public class NewPatientActivity extends Activity {
	
	/** This is a Nurse.*/
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		Intent intent = getIntent();
     	nurse = (Nurse) intent.getSerializableExtra("nurse");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_patient, menu);
		return true;
	}
	
	/**Records the patient's personal information.
	 * @param view
	 * @throws FileNotFoundException if file not exists.
	 */
	public void RecordData(View view) throws FileNotFoundException {
    	//get the input information
    	EditText nameText = (EditText) findViewById(R.id.name);
    	String name = nameText.getText().toString().trim();
    	
    	EditText year_of_birthText = (EditText) findViewById(R.id.birth_of_year);
    	String yearofbirth = year_of_birthText.getText().toString();
    	
    	EditText month_of_birthText = (EditText) findViewById(R.id.birth_of_month);
    	String monthofbirth = month_of_birthText.getText().toString();
    	
    	EditText day_of_birthText = (EditText) findViewById(R.id.birth_of_day);
    	String dayofbirth = day_of_birthText.getText().toString();
    	
    	EditText cardnumberText = (EditText) findViewById(R.id.healthcard);
    	String cardnumber = cardnumberText.getText().toString().trim();
    	
    	EditText yearText = (EditText) findViewById(R.id.current_year);
    	String current_year = yearText.getText().toString();
    	
    	EditText monthText = (EditText) findViewById(R.id.current_month);
    	String current_month = monthText.getText().toString();
    	
    	EditText dayText = (EditText) findViewById(R.id.current_day);
    	String current_day = dayText.getText().toString();
    	
    	EditText hourText = (EditText) findViewById(R.id.current_hour);
    	String current_hour = hourText.getText().toString();
    	
    	EditText minuteText = (EditText) findViewById(R.id.current_minute);
    	String current_minute = minuteText.getText().toString();
    	
    	
    	if (name.equals("") || yearofbirth.equals("") || monthofbirth.equals("") || 
    			dayofbirth.equals("") || cardnumber.equals("") || current_year.equals("") || 
    			current_month.equals("") || current_day.equals("") || current_hour.equals("") 
    			|| current_minute.equals("")) {
    		
    		new AlertDialog.Builder(this)
            .setTitle("sorry")
            .setMessage("invalid information entered")
            .setPositiveButton("try again", null)
            .show();
    	
    	} else {
    		
    		int year_of_birth = Integer.valueOf(yearofbirth).intValue();
    		
    		int month_of_birth = Integer.valueOf(monthofbirth).intValue();
    		
    		int day_of_birth = Integer.valueOf(dayofbirth).intValue();
    		
    		int year = Integer.valueOf(current_year).intValue();
    		
    		int month = Integer.valueOf(current_month).intValue();
    		
    		int day = Integer.valueOf(current_day).intValue();
    		
    		int hour = Integer.valueOf(current_hour).intValue();
    		
    		int minute = Integer.valueOf(current_minute).intValue();
    		
    		//create the patient
    	    Patient patient = new Patient(name, day_of_birth, month_of_birth, year_of_birth, 
    	    		cardnumber, hour, minute, day, month, year);
    	
    	    //get the nurse
    	    Intent intent = getIntent();
    	    nurse = (Nurse) intent.getSerializableExtra("nurse");
    	
    	
    	    File file = new File(this.getApplicationContext().getFilesDir(),"patient.txt");
    	    String filepath = file.toString();
    	
    	
    	    //judge whether the patient exists or not
    	    if (nurse.cotainsPatient(cardnumber)) {
    		    new AlertDialog.Builder(this)
                .setTitle("sorry")
                .setMessage("patient already existed ")
                .setPositiveButton("try again", null)
                .show();
    	    }
    	
    	
    	
    	    else {
    		    //add the patient
    		    nurse.add1(patient, filepath);
    		    new AlertDialog.Builder(this)
                .setTitle("approved")
                .setMessage("new patient has been added ")
                .setPositiveButton("finish", null)
                .show();
    		    //set the nurse information back.
    		    Intent intentC = new Intent(this,ChoiceActivity.class);
    		    intentC.putExtra("nurse", nurse);
    		    startActivity(intentC);
    	    }
    	}
	}

}
