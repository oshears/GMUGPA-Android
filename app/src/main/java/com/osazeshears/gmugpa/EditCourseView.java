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

public class EditCourseView extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    Course newCourse;
    Spinner gradeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        final String courseName = getIntent().getStringExtra("courseName");
        final int totalCredits = getIntent().getIntExtra("courseCredits",0);
        final String strTotalPoints = getIntent().getStringExtra("courseGrade");

        final Double totalPoints = Course.gradeToQuals(strTotalPoints);

        //Find the text fields
        final EditText courseNameTxt = (EditText) findViewById(R.id.editCourseName);
        courseNameTxt.setText(courseName);
        final EditText courseCreditsTxt = (EditText) findViewById(R.id.editCourseCredits);
        courseCreditsTxt.setText(Integer.toString(totalCredits));
        final TextView qualPts = (TextView) findViewById(R.id.editQualPoints);
        qualPts.setText(Double.toString(totalPoints));

        //Set spinner data
        final Spinner gradeSpinner = (Spinner) findViewById(R.id.editGradeSpinner);
        this.gradeSpinner = gradeSpinner;
        this.gradeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);
        Double[] gradesArray = {4.0,3.67,3.33,3.0,2.67,2.33,1.67,1.0,0.0};
        Log.d("Total Points",Double.toString(totalPoints));
        Log.d("Grades Array",Integer.toString(java.util.Arrays.asList(gradesArray).indexOf(totalPoints)));
        gradeSpinner.setSelection(java.util.Arrays.asList(gradesArray).indexOf(totalPoints));

        final Button addCourse = (Button) findViewById(R.id.doneEditBtn);
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
                returnIntent.putExtra("coursePosition",getIntent().getIntExtra("coursePosition",0));
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        final TextView qualPts = (TextView) findViewById(R.id.editQualPoints);
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
