package com.example.mysocial;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;



public class WelcomeActivity extends AppCompatActivity {

    private TextView titleTextView;
    private View[] dots;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private Handler handler;
    private int delay = 500;
    private int currentDotIndex = 0;
    private int[] dotColors = {0xFF0000FF, 0xFF00FF00, 0xFFFF0000, 0xFF00FFFF, 0xFFFFFF00}; // Add more colors as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        titleTextView = findViewById(R.id.titleTextView);
        dots = new View[]{
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3),
                findViewById(R.id.dot4),
                findViewById(R.id.dot5)
        };

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        handler = new Handler();

        animateText();
        animateDots();
    }

    private void animateText() {
        String text = "Social Media";
        titleTextView.setText("");

        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    titleTextView.append(String.valueOf(text.charAt(index)));

                    // Check if text animation is complete
                    if (index == text.length() - 1) {
                        // Navigate to next activity
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                navigateToNextActivity();
                            }
                        }, delay);
                    }
                }

                private void navigateToNextActivity() {
                    Intent intent = new Intent(WelcomeActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, delay * i);
        }
    }


    private void animateDots() {
        updateDotColor();
        dots[currentDotIndex].startAnimation(fadeInAnimation);
        dots[currentDotIndex].setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dots[currentDotIndex].startAnimation(fadeOutAnimation);
                dots[currentDotIndex].setVisibility(View.INVISIBLE);
                currentDotIndex = (currentDotIndex + 1) % dots.length;
                animateDots();
            }
        }, delay);
    }

    private void updateDotColor() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(dotColors[currentDotIndex]);
        dots[currentDotIndex].setBackground(drawable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}