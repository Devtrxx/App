package com.dadash.easeride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
;

import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity {
    private ImageButton pubbtn, aisupbtn,wallbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pubbtn =findViewById(R.id.btnAdd);
        aisupbtn =findViewById(R.id.btnChatSupport);
        wallbtn =findViewById(R.id.btnWallet);


        OpenACtivity();

    }
    private void OpenACtivity(){
        pubbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(Home.this, publishride.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );

        wallbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(Home.this, Wallet.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );
        aisupbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Home.this, AIsupport.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
        );
    }
}

