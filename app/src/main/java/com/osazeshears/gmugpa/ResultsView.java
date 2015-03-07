package com.osazeshears.gmugpa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class ResultsView extends ActionBarActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_view);


        final TextView gpaTxt = (TextView) findViewById(R.id.gpaTxt);
        final TextView pointsTxt = (TextView) findViewById(R.id.qualPtsTxt);
        final int totalCredits = getIntent().getIntExtra("totalCredits",0);
        //Log.d("ActionBarActivity","Credits: "+totalCredits);
        final Double totalPoints = getIntent().getDoubleExtra("totalPoints",0);
        final Double gpa = totalPoints/totalCredits;

        gpaTxt.setText(String.valueOf(String.format( "%.2f", gpa )));
        pointsTxt.setText(String.valueOf(String.format( "%.2f", totalPoints )));

        final Button doneBtn = (Button) findViewById(R.id.doneViewing);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        final Button calcCumGPA = (Button) findViewById(R.id.calcCumGPA);
        calcCumGPA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayUpdatedGPA(totalCredits,totalPoints);
            }
        });

    }

    public void displayUpdatedGPA(int totalCredits,Double totalPoints){
        final EditText transCreditsTxt = (EditText) findViewById(R.id.transCreditsTxt);
        final EditText transPointsTxt = (EditText) findViewById(R.id.transQualPointsTxt);
        Double allQualPoints;
        try {
            allQualPoints = totalPoints + Double.valueOf(transPointsTxt.getText().toString());
        }
        catch (Exception e){
            allQualPoints = totalPoints;
        }
        Double fullGPA;
        try {
            fullGPA = allQualPoints / (totalCredits + Double.valueOf(transCreditsTxt.getText().toString()));
        }
        catch (Exception e){
            if (totalCredits==0) fullGPA = 0.0;
            else fullGPA = allQualPoints / totalCredits;
        }
        final TextView cumGPA = (TextView) findViewById(R.id.cumGPAtxt);
        final TextView cumPts = (TextView) findViewById(R.id.cumQualPtsTxt);

        cumGPA.setText(String.valueOf(String.format( "%.2f", fullGPA )));
        cumPts.setText(String.valueOf(String.format( "%.2f", allQualPoints )));
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
