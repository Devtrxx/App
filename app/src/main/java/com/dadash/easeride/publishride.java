package com.dadash.easeride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;

public class publishride extends AppCompatActivity {

    private EditText editTextTo, editTextFrom, editTextFare;
    private Spinner spinnerCarType;
    private Button publishButton, dateButton, timeButton;
    private ImageButton Homebtn, aisupbtn,wallbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishride);

        editTextTo = findViewById(R.id.editTextTo);
        editTextFrom = findViewById(R.id.editTextFrom);
        dateButton = findViewById(R.id.buttonDate);
        timeButton = findViewById(R.id.buttonTime);
        editTextFare = findViewById(R.id.editTextFare);
        spinnerCarType = findViewById(R.id.spinnerCarType);
        publishButton = findViewById(R.id.publishbutton);
        Homebtn =findViewById(R.id.btnAccount);
        aisupbtn =findViewById(R.id.btnChatSupport);
        wallbtn =findViewById(R.id.btnWallet);


        OpenACtivity();
        // Array adapter for the Spinner
        ArrayAdapter<CharSequence> carTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.car_types,
                android.R.layout.simple_spinner_item
        );
        carTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        spinnerCarType.setAdapter(carTypeAdapter);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from EditText fields
                String to = editTextTo.getText().toString();
                String from = editTextFrom.getText().toString();
                String date = dateButton.getText().toString();
                String time = timeButton.getText().toString();
                String fare = editTextFare.getText().toString();
                String carType = spinnerCarType.getSelectedItem().toString().toLowerCase();

                if (carType.equals("select")) {
                    carType = "xl intercity";
                    sendToOtherActivity(to, from, date, time, fare, carType);
                } else {
                    // Send all data to the next activity
                    sendToOtherActivity(to, from, date, time, fare, carType);
                }
            }
        });

        // Implement the DatePickerDialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Implement the TimePickerDialog
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void sendToOtherActivity(String to, String from, String date, String time, String fare, String carType) {
        // Create an Intent to start the other activity
        Intent intent = new Intent(this, maps.class);

        // Add all data to the Intent
        intent.putExtra("to", to);
        intent.putExtra("from", from);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("fare", fare);
        intent.putExtra("carType", carType);

        // Start the other activity
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the dateButton with the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateButton.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog and show it
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the timeButton with the selected time
                        String selectedTime = hourOfDay + ":" + minute;
                        timeButton.setText(selectedTime);
                    }
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void OpenACtivity(){
        Homebtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(publishride.this, Home.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );

        wallbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(publishride.this, Wallet.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );
        aisupbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(publishride.this, AIsupport.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );
    }


}