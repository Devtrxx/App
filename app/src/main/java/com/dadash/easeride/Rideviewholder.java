package com.dadash.easeride;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Rideviewholder extends RecyclerView.ViewHolder {
    private TextView toTextView;
    private TextView fromTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView distanceTextView;
    private TextView vehicleTypeTextView;
    private TextView fareTextView;

    public Rideviewholder(@NonNull View itemView) {
        super(itemView);
        toTextView = itemView.findViewById(R.id.toTextView);
        fromTextView = itemView.findViewById(R.id.fromTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        timeTextView = itemView.findViewById(R.id.timeTextView);
        distanceTextView = itemView.findViewById(R.id.distanceTextView);
        vehicleTypeTextView = itemView.findViewById(R.id.vehicleTypeTextView);
        fareTextView = itemView.findViewById(R.id.fareTextView);
        // Initialize other TextViews as needed
    }

    public void bindRide(Ride ride) {
        toTextView.setText("To = " + ride.getTo());
        fromTextView.setText("From = " + ride.getFrom());
        dateTextView.setText(ride.getDate());
        timeTextView.setText(ride.getTime());
        distanceTextView.setText("Distance: " + ride.getDistance());
        vehicleTypeTextView.setText("Vehicle Type: " + ride.getVehicleType());
        fareTextView.setText("Fare: " + ride.getFare());
    }
    // Add methods to set other ride details to corresponding TextViews
}

