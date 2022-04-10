package com.example.moneytransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAlphaAnimation();
    }

    /**
     * Method for showing the first screen.
     * Gets the logo image view and runs the animation.
     * For more information on animation, please refer to res/anims/alpha_anims.
     * (increase alpha value from 0 to 1 for 3000 ms)
     * The animation listener automatically moves on to FirstPageActivity at the
     * end of the animation.
     * @version 1.0
     * @see     android.view.animation.AnimationUtils
     */
    public void showAlphaAnimation() {
        ImageView logoImageView = (ImageView)findViewById(R.id.logoImageView);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_anim);
        logoImageView.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /**
                 * Example of how to move on to the next Intent
                 * IMPORTANT: need to finish() after start the next activity.
                 * the current activity will be stayed if the method is not called.
                 */
                Intent intent = new Intent(getApplicationContext(), FirstPageActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}