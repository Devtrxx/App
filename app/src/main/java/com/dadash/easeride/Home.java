package com.dadash.easeride;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home extends AppCompatActivity {
    private ImageButton pubbtn, aisupbtn, wallbtn,aipredbtn;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Ride, Rideviewholder> adapter;
    private CollectionReference ridesRef = FirebaseFirestore.getInstance().collection("rides");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pubbtn = findViewById(R.id.btnAdd);
        aisupbtn = findViewById(R.id.btnChatSupport);
        wallbtn = findViewById(R.id.btnWallet);
        aipredbtn = findViewById(R.id.aipred);

        recyclerView = findViewById(R.id.recyclerViewRides);
        setUpRecyclerView();

        openActivity();
    }

    private void setUpRecyclerView() {
        Query query = ridesRef.orderBy("date", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Ride> options = new FirestoreRecyclerOptions.Builder<Ride>()
                .setQuery(query, Ride.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Ride, Rideviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Rideviewholder holder, int position, @NonNull Ride model) {
                holder.bindRide(model);
                // Set other ride details to the ViewHolder as needed
            }

            @NonNull
            @Override
            public Rideviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ride, parent, false);
                return new Rideviewholder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openActivity() {
        pubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, publishride.class);
                startActivity(intent);
                finish();
            }
        });

        wallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Wallet.class);
                startActivity(intent);
                finish();
            }
        });

        aisupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AIsupport.class);
                startActivity(intent);
                finish();
            }
        });

        aipredbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AIpred.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}







