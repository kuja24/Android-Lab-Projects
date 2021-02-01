package com.example.experiment_1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView targetText;
    private Spinner colourSpinner,fontSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetText = findViewById(R.id.target_text);
        colourSpinner = findViewById(R.id.colour_spinner);
        colourSpinner.setOnItemSelectedListener(this);

        fontSpinner = findViewById(R.id.font_spinner);
        fontSpinner.setOnItemSelectedListener(this);



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId()==R.id.colour_spinner) {
            String clr = adapterView.getSelectedItem().toString();
            if (clr.toLowerCase().equals("red"))
                targetText.setTextColor(Color.RED);
            else if (clr.toLowerCase().equals("blue"))
                targetText.setTextColor(Color.BLUE);
            else if (clr.toLowerCase().equals("green"))
                targetText.setTextColor(Color.GREEN);
        }
        if(spinner.getId()==R.id.font_spinner){
            String font = adapterView.getSelectedItem().toString();
            if(font.equals("Small Font"))
                targetText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            else if(font.equals("Medium Font"))
                targetText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            else if(font.equals("Large Font"))
                targetText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        targetText.setTextColor(Color.BLACK);
    }
}