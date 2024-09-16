package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

       ImageView ivLogo;
       TextView tvTitle;

        Animation fadeInAnim;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            ivLogo = findViewById(R.id.ivMainLogo);
            tvTitle = findViewById(R.id.tvLoginSignup);

            fadeInAnim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
            ivLogo.setAnimation(fadeInAnim);
           // tvTitle.setAnimation(fadeInAnim);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run(){
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                }
            },3000);
            }

}

