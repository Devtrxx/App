package com.dadash.easeride;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class apitest extends AppCompatActivity {
    private TextView responseText;
    private ApiService apiService;

    // Define Retrofit service interface
    public interface ApiService {
        @GET("/")
        Call<ResponseBody> getHelloWorld();

        @POST("http://192.168.50.126:8000/calculate_distance")  // Replace with your actual endpoint
        Call<ResponseBody> postData(@Body RequestBody requestBody);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitest);
        responseText = findViewById(R.id.responseText);

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.50.126:8000/")  // Replace with your FastAPI server address
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create service instance
        apiService = retrofit.create(ApiService.class);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtain data from the previous intent (replace with your actual data retrieval logic)
                Intent previousIntent = getIntent();
                String to = previousIntent.getStringExtra("to");  // Replace "to" with your actual key
                String from = previousIntent.getStringExtra("from");  // Replace "from" with your actual key

                // Create a JSON object with "to" and "from" values
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("to", to);
                    jsonObject.put("from", from);
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
                                // Update UI or handle the response as needed
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseText.setText(responseData);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                responseText.setText("Error: " + t.getMessage());
                            }
                        });
                    }
                });

            }
        });
    }
}