package com.example.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btncal, btnreset;
    TextView tvDate, tvBMI, tvOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btncal = findViewById(R.id.cal);
        btnreset = findViewById(R.id.reset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOpt = findViewById(R.id.textViewOption);

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBMI.setText("Last Calculated Date:");
                tvDate.setText("Last Calculated BMI:");
                tvOpt.setText("");
            }
        });

        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                float W = Float.parseFloat(etWeight.getText().toString());
                float H = Float.parseFloat(etHeight.getText().toString());

                float total = W/(H*H);

                tvDate.setText("Last Calculated Date: " + datetime);
                tvBMI.setText(String.format("Last Calculated BMI: %.3f" ,total));
                etHeight.setText("");
                etWeight.setText("");

                if (total <18.5){
                    tvOpt.setText("You are underweight");
                }
                else if (total >=  18.5 && total <= 24.9){
                    tvOpt.setText("Your BMI is normal");
                }
                else if (total >= 25 && total <= 29.9){
                    tvOpt.setText("You are overweight");
                }
                else if (total > 30){
                    tvOpt.setText("You are obese");
                }
                else{
                    tvOpt.setText("Invalid BMI");
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        String strDate = tvDate.getText().toString();
        String strBMI = tvBMI.getText().toString();
        String strOPT = tvOpt.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", strDate);
        prefEdit.putString("bmi", strBMI);
        prefEdit.putString("option", strOPT);
        prefEdit.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String strDate = prefs.getString("date","Last Calculated Date:");
        String strBMI = prefs.getString("bmi", "Last Calculated BMI: ");
        String strOPT = prefs.getString("option", "");

        tvDate.setText(strDate);
        tvOpt.setText(strOPT);
        tvBMI.setText(strBMI);
    }
}
