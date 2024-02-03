package com.dadash.easeride;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AIpred extends AppCompatActivity {
    private ImageButton Homebtn, aisupbtn, wallbtn, pubbtn;
    private EditText editTextDate, editTextTime, editTextFare, predictai;
    private Spinner spinnerCarType;

    // Add these variables for date and time
    private int mYear, mMonth, mDay;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aipred);

        Homebtn = findViewById(R.id.btnAccount);
        aisupbtn = findViewById(R.id.btnChatSupport);
        wallbtn = findViewById(R.id.btnWallet);
        pubbtn = findViewById(R.id.btnAdd);
        editTextFare = findViewById(R.id.editTextFare);
        predictai = findViewById(R.id.predictai);
        spinnerCarType = findViewById(R.id.spinnerCarType);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_types, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCarType.setAdapter(adapter);

        OpenACtivity();
    }

    private void OpenACtivity() {
        pubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AIpred.this, publishride.class);
                startActivity(intent);
                finish();
            }
        });

        wallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AIpred.this, Wallet.class);
                startActivity(intent);
                finish();
            }
        });

        Homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AIpred.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        aisupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AIpred.this, AIsupport.class);
                startActivity(intent);
                finish();
            }
        });

        // Add these lines for date and time pickers
        findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        findViewById(R.id.buttonTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        findViewById(R.id.publishbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the predict button click
                // You can use the values from editTextDate, editTextTime, editTextFare, and spinnerCarType
                // for further processing or predictions
                String prediction = "Some prediction result"; // Replace with your actual prediction logic
                predictai.setText(prediction);
            }
        });
    }

    // Method to show date picker
    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // handle the selected date
                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    // Method to show time picker
    public void showTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // handle the selected time
                        editTextTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
