package com.dadash.easeride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Wallet extends AppCompatActivity {
    private ImageButton pubbtn, aisupbtn,homebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        pubbtn =findViewById(R.id.btnAdd);
        aisupbtn =findViewById(R.id.btnChatSupport);
        homebtn =findViewById(R.id.btnAccount);


        OpenACtivity();
    }

    private void OpenACtivity(){
        pubbtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent = new Intent(Wallet.this, publishride.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }
        );

        homebtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(Wallet.this, Home.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );
        aisupbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Wallet.this, AIsupport.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
        );
    }
}