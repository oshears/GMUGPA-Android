package com.osazeshears.gmugpa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.ListView;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class CourseView extends ActionBarActivity{

    Course[] userCourses = new Course[0];

    private ListView courseListView;

    static final int GET_NEW_COURSE_REQUEST = 1; //Request Code
    static final int GET_UPDATED_COURSE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        final CourseView caller = this;

        final Button addCourse = (Button) findViewById(R.id.addCourseButton);
        addCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("CourseView","Add course clicked...");
                getNewCourse(caller);
            }
        });
        final Button calcGPA = (Button) findViewById(R.id.calcGPAButton);
        calcGPA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("CourseView","Calc GPA clicked...");
                showResultsActivity(caller);
            }
        });


        courseListView = (ListView) findViewById(R.id.courseListView);
        updateCourseList();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position,long id) {
                final int pos = position;
                /*Delte Course Clicked
                List<Course>CurrCourses=getListCourses();
                CurrCourses.remove(position);
                userCourses=makeCourseArray(CurrCourses);
                updateCourseList();
                */
                new AlertDialog.Builder(caller)
                        .setTitle("Modify Entry")
                        .setMessage("What would you like to do to this entry?")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Open the edit entry activity
                                modifyCourseInformationActivity(caller,pos);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete course
                                List<Course>CurrCourses=getListCourses();
                                CurrCourses.remove(pos);
                                userCourses=makeCourseArray(CurrCourses);
                                updateCourseList();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

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

    public Course[] makeCourseArray(List courseList){
        Course[] courseArray = new Course[courseList.size()];
        courseList.toArray(courseArray);
        return courseArray;
    }

    public ArrayList getListCourses(){

        return new ArrayList<>(Arrays.asList(userCourses));
    }


    private void getNewCourse(CourseView caller){
        Intent addCourseIntent = new Intent(caller, AddCourseView.class);
        startActivityForResult(addCourseIntent, GET_NEW_COURSE_REQUEST);
    }
    private void showResultsActivity(CourseView caller){
        int totalCredits = 0;
        double totalPoints = 0;
        for (int i=0;i<userCourses.length;i++){
            totalCredits += userCourses[i].courseCredits;
            totalPoints += userCourses[i].coursePoints;
        }
        Intent showResults = new Intent(caller, ResultsView.class);
        showResults.putExtra("totalCredits",totalCredits);
        showResults.putExtra("totalPoints",totalPoints);
        startActivity(showResults);
    }


    private void modifyCourseInformationActivity(CourseView caller,int position){
        List<Course>CurrCourses=getListCourses();
        Intent editCourseIntent = new Intent(caller,EditCourseView.class);
        editCourseIntent.putExtra("courseName",CurrCourses.get(position).courseName);
        editCourseIntent.putExtra("courseCredits",CurrCourses.get(position).courseCredits);
        editCourseIntent.putExtra("courseGrade",CurrCourses.get(position).courseGrade);
        editCourseIntent.putExtra("coursePosition",position);
        startActivityForResult(editCourseIntent,GET_UPDATED_COURSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_NEW_COURSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d("CourseView","Received the result from activity...");
                Course newCourse = new Course(data.getStringExtra("newCourseName"),
                        data.getStringExtra("newCourseGrade"),
                        data.getIntExtra("newCourseCredits",0));
                List<Course> CurrCourses = getListCourses();
                CurrCourses.add(newCourse);
                this.userCourses=makeCourseArray(CurrCourses);
                updateCourseList();
            }
        }
        if (requestCode == GET_UPDATED_COURSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Log.d("CourseView","Received the result from activity...");
                Course newCourse = new Course(data.getStringExtra("newCourseName"),
                        data.getStringExtra("newCourseGrade"),
                        data.getIntExtra("newCourseCredits",0));

                this.userCourses[data.getIntExtra("coursePosition",0)] = newCourse;
                updateCourseList();
            }
        }
    }

    public void updateCourseList(){


        // Populate the list, through the adapter



        courseListView = (ListView) findViewById(R.id.courseListView);
        final CourseEntryAdapter courseEntryAdapter = new CourseEntryAdapter(this, R.layout.list_item);
        courseListView.setAdapter(courseEntryAdapter);
        /*for(final NewsEntry entry : getNewsEntries()) {
            newsEntryAdapter.add(entry);
        }*/
        //ArrayList<String> courseNames = new ArrayList<String>();
        courseEntryAdapter.addAll(userCourses);

    }


}
