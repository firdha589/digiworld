package com.example.Digi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;


    Animation topAnim,bottomAnim;
    ImageView image;
    TextView slogan1,slogan2,slogan3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image=(ImageView)findViewById(R.id.imageView2);
        slogan1=(TextView)findViewById(R.id.textView);
        slogan2=(TextView)findViewById(R.id.textView2);
        slogan3=(TextView)findViewById(R.id.textView3);
        image.setAnimation(topAnim);
        slogan1.setAnimation(bottomAnim);
        slogan2.setAnimation(bottomAnim);
        slogan3.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, DashboardActivity.class);
                Pair[] pairs =new Pair[3];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(slogan1,"logo_text");
                pairs[2]=new Pair<View,String>(slogan2,"logo_text2");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }


            }
        },SPLASH_SCREEN);






    }
}
