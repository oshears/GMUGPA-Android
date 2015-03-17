package com.osazeshears.gmugpa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.content.Intent;

import com.osazeshears.gmugpa.db.CourseDBHelper;


public class AddCourseView extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    Course newCourse;
    Spinner gradeSpinner;
    private CourseDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //Find the text fields
        final EditText courseNameTxt = (EditText) findViewById(R.id.courseName);
        final EditText courseCreditsTxt = (EditText) findViewById(R.id.courseCredits);

        //Set spinner data
        final Spinner gradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        this.gradeSpinner = gradeSpinner;
        this.gradeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);



        final Button addCourse = (Button) findViewById(R.id.submitCourseBtn);
        addCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String courseName = courseNameTxt.getText().toString();
                int courseCredits = Integer.valueOf(courseCreditsTxt.getText().toString());
                String courseGrade = gradeSpinner.getSelectedItem().toString();
                newCourse = new Course(courseName,courseGrade,courseCredits);
                //String grade = gradeSpinner.get
                String out = courseName + " " +courseCredits + " " + courseGrade;
                Log.d("AddCourseView",out);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newCourseName",courseName);
                returnIntent.putExtra("newCourseGrade",courseGrade);
                returnIntent.putExtra("newCourseCredits",courseCredits);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        final TextView qualPts = (TextView) findViewById(R.id.qualPoints);
        Log.d("AddCourseView",String.valueOf(Course.gradeToQuals(
                parent.getItemAtPosition(pos).toString())));
        qualPts.setText(String.valueOf(Course.gradeToQuals(
                parent.getItemAtPosition(pos).toString())));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_view, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
