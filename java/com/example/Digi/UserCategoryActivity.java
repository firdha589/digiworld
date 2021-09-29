package com.example.Digi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class UserCategoryActivity extends AppCompatActivity {

    private Button mens,womens,watch,babies,health,sports,srilankan,electronics;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);


        mens=findViewById(R.id.mens_expand);
        womens=findViewById(R.id.womens_expand);
        watch=findViewById(R.id.watches_expand);
        babies=findViewById(R.id.babies_kids_expand);
        health=findViewById(R.id.health_expand);
        sports=findViewById(R.id.sports_expand);
        srilankan=findViewById(R.id.srilankan_expand);
        electronics=findViewById(R.id.electronics_expand);
        back=findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","men's fashion");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        womens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","women's fashion");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        babies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","babies and toys");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","Watches and accessories");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","sports and outdoor");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","electronics devices");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","Health and Beauty");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });
        srilankan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,UserSpecificCategoryActivity.class);
                intent.putExtra("category","srilankan traditional");
                startActivity(intent);
                Animatoo.animateSlideDown(UserCategoryActivity.this);
            }
        });





    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(UserCategoryActivity.this);
    }
}