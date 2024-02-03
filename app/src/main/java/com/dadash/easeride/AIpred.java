package com.dadash.easeride;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class AIpred extends AppCompatActivity {
    private Spinner spinnerCarType;
    private EditText editTextTo, editTextFrom, editTextFare;
    private Button buttonDate, buttonTime, publishButton;
    private TextView predictedOutput;
    private ApiService apiService;
    private String date,time;
    private ImageButton Homebtn, aisupbtn, wallbtn, pubbtn;

    // Define Retrofit service interface
    public interface ApiService {
        @POST("http://192.168.9.126:8000/rides")  // Replace with your actual endpoint
        Call<ResponseBody> postData(@Body RequestBody requestBody);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aipred);

        spinnerCarType = findViewById(R.id.spinnerCarType);
        editTextTo = findViewById(R.id.editTextTo);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextFare = findViewById(R.id.editTextFare);
        buttonDate = findViewById(R.id.buttonDate);
        buttonTime = findViewById(R.id.buttonTime);
        publishButton = findViewById(R.id.publishbutton);
        predictedOutput = findViewById(R.id.predictedoutput);

        Homebtn = findViewById(R.id.btnAccount);
        aisupbtn = findViewById(R.id.btnChatSupport);
        wallbtn = findViewById(R.id.btnWallet);
        pubbtn = findViewById(R.id.btnAdd);

        OpenACtivity();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.50.126:8000/")  // Replace with your FastAPI server address
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create service instance
        apiService = retrofit.create(ApiService.class);

        // Spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarType.setAdapter(adapter);

        // Spinner item selected listener
        spinnerCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Date picker dialog setup
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Time picker dialog setup
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // Publish button click listener
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText and Spinner
                String toValue = editTextTo.getText().toString();
                String fromValue = editTextFrom.getText().toString();
                String fareValue = editTextFare.getText().toString();
                String carTypeValue = spinnerCarType.getSelectedItem().toString().toLowerCase();

                // Create a JSON object with values
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("to", toValue);
                    jsonObject.put("from", fromValue);
                    //jsonObject.put("fare", fareValue);
                    jsonObject.put("carType", carTypeValue);
                    jsonObject.put("date",date);
                    jsonObject.put("time",time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create a request body
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                // Make API request
                Call<ResponseBody> call = apiService.postData(requestBody);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        handleFailure(t);
                    }
                });
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        buttonDate.setText(selectedDate);
                        date=selectedDate;
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time
                        String selectedTime = hourOfDay + ":" + minute;
                        buttonTime.setText(selectedTime);
                        time=selectedTime;
                    }
                }, hourOfDay, minute, true);

        timePickerDialog.show();
    }

    // Handle the response from the server
    private void handleResponse(Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                String responseData = response.body().string();
                // Update UI or handle the response as needed
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        predictedOutput.setText(responseData);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception as needed
            }
        } else {
            // Handle the unsuccessful response
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    predictedOutput.setText("Server Error: " + response.message());
                }
            });
        }
    }

    // Handle the failure of the API request
    private void handleFailure(Throwable t) {
        t.printStackTrace();
        // Handle the failure
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                predictedOutput.setText("Network Error: " + t.getMessage());
            }
        });
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
    }
}
