package com.dadash.easeride;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class maps extends AppCompatActivity {
    private String formattedTo, formattedFrom;
    private Button publishButton2;

    private ApiService apiService;
    private String FAre , Distance;
     double fare   ,Lower, Upper;

     private TextView rangeview;

    // Define Retrofit service interface
    public interface ApiService {
        @POST("http://192.168.9.126:8000/calculate_distance")  // Replace with your actual endpoint
        Call<ResponseBody> postData(@Body RequestBody requestBody);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        if (intent != null) {
            String to = intent.getStringExtra("to");
            String from = intent.getStringExtra("from");
            formattedTo = formatInput(to);
            formattedFrom = formatInput(from);

            WebView webView = findViewById(R.id.mapview);
            webView.getSettings().setJavaScriptEnabled(true);

            // Load a link into the WebView
            String url = "https://www.mappls.com/direction-from-" + formattedFrom + "?-to-" + formattedTo + "?";
            webView.loadUrl(url);
        }

        publishButton2 = findViewById(R.id.publishbb);
        rangeview= findViewById(R.id.rangeview);
        publishButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServerAndNavigate();
            }
        });

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.126:8000/")  // Replace with your FastAPI server address
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create service instance
        apiService = retrofit.create(ApiService.class);
    }

    public String formatInput(String input) {
        // Replace spaces with hyphens and add prefixes
        return input.toLowerCase().replace(" ", "-");
    }

    private void sendToServerAndNavigate() {
        // Create a JSON object with "to" and "from" values
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to", formattedTo);
            jsonObject.put("from", formattedFrom);
            jsonObject.put("date", getIntent().getStringExtra("date"));
            jsonObject.put("time", getIntent().getStringExtra("time"));
            jsonObject.put("fare", getIntent().getStringExtra("fare"));
            jsonObject.put("carType", getIntent().getStringExtra("carType"));
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
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        Log.d("Output form server", responseData);
                        // Handle the response as needed
                        // You can update UI or navigate to another activity here
                        // Save data to Firestore

                        // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(responseData);
                        double ogValue = jsonResponse.optDouble("og");
                        double distanceValue = jsonResponse.optDouble("distance");
                        double lowervalue = jsonResponse.optDouble("fare_l");
                        double uppervalue = jsonResponse.optDouble("fare_u");


                        // Convert values to strings
                         FAre = String.valueOf(ogValue);
                         Distance = String.valueOf(distanceValue);
                         Log.d("output form server 2",FAre + "  +" + Distance + lowervalue);
                         Lower = Math.round(lowervalue);
                         Upper = Math.round(uppervalue);

                         fare = Double.parseDouble(getIntent().getStringExtra("fare"));
                         if (fare>=Lower && fare<= Upper){
                        saveDataToFirestore();
                        //server
                        navigateToAnotherActivity();
                         }
                         else {
                             publishButton2.setError("");
                             rangeview.setText("Please enter fare between "+ Lower + " and "+ Upper+".");
                             rangeview.setVisibility(View.VISIBLE);
                         }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle the case where the response is not successful
                    Log.e("Server Response", "Error: " + response.code() + " " + response.message());
                    // You can handle the error here, display a message, etc.
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                // Handle failure as needed
            }
        });
    }

    private void navigateToAnotherActivity() {
        // Create an Intent to start the other activity
        Intent intent = new Intent(this, Home.class);

        // Add any additional data to the Intent if needed
        // intent.putExtra("key", value);

        // Start the other activity
        startActivity(intent);
//        finish();
    }

    private void saveDataToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ridesCollection = db.collection("rides");
        String uniqueCollectionName = UUID.randomUUID().toString();

        // Create a Map with your data
        Map<String, Object> rideData = new HashMap<>();
        rideData.put("to", formattedTo);
        rideData.put("from", formattedFrom);
        rideData.put("date", getIntent().getStringExtra("date"));
        rideData.put("time", getIntent().getStringExtra("time"));
        rideData.put("fare", getIntent().getStringExtra("fare")+" Rs.");
        rideData.put("vehicleType", getIntent().getStringExtra("carType"));
        rideData.put("distance",Distance.toString()+" KM");
        rideData.put("Predicted_Fare",FAre.toString()+" Rs.");

        ridesCollection.document(uniqueCollectionName)
                .set(rideData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + uniqueCollectionName);
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });
    }
}