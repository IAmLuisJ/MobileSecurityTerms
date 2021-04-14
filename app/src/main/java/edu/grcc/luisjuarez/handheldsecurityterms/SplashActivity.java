package edu.grcc.luisjuarez.handheldsecurityterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();  //usually the actionbar is hidden for a splash screen
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, SwitchBoardActivity.class);
                startActivity(intent);
                finish();  //close Splash activity when we return
            }
        }, 5000);   //5 seconds before SwitchboardActivity pops up!

    }
}
