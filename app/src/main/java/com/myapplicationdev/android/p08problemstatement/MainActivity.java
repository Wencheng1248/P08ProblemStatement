package com.myapplicationdev.android.p08problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight,etHeight;
    Button btnCalc , btnReset;
    TextView tvShowDate,tvShowBmi, tvShowCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvShowBmi = findViewById(R.id.textViewBmi);
        tvShowDate = findViewById(R.id.textViewDate);
        tvShowCondition = findViewById(R.id.textViewCondition);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float Weight = Float.parseFloat(etWeight.getText().toString());
                float Height = Float.parseFloat(etHeight.getText().toString());

                float cal = Weight / (Height*Height);

                String condition = "" ;
                if(cal >= 27.5){
                    condition += "You are Obese!";


                }else if(cal< 27.5 && cal> 23.0){
                    condition +="You are at a healthy range !";
                }else{
                    condition += "You are underweight!";
                }
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putFloat("bmi",cal);
                prefEdit.putString("date",datetime);
                prefEdit.putString("condition",condition);
                tvShowBmi.setText(cal+"");
                tvShowDate.setText(datetime);
                tvShowCondition.setText(condition);
                prefEdit.commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvShowDate.setText("Last Calculated Date:");
                tvShowBmi.setText("Last Calculated BMI");
                tvShowCondition.setText("BMI Guage");

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        float WeightPause = Float.parseFloat(etWeight.getText().toString());
        float HeightPause = Float.parseFloat(etHeight.getText().toString());
        float cal = WeightPause / (HeightPause*HeightPause);
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        String condition = "" ;
        if(cal >= 27.5){
            condition += "You are Obese!";


        }else if(cal< 27.5 && cal> 23.0){
            condition +="You are at a healthy range !";
        }else{
            condition += "You are underweight!";
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();


        prefEdit.putFloat("bmi",cal);
        prefEdit.putString("date",datetime);
        prefEdit.putString("condition",condition);


        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        float bmi = prefs.getFloat("bmi",0);
        String date = prefs.getString("date","");
        String condition = prefs.getString("condition","");


        tvShowBmi.setText(bmi + "");
        tvShowDate.setText(date);
        tvShowCondition.setText(condition);




    }
}
