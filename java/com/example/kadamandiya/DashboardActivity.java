package com.example.Digi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.Users;
import com.example.Digi.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;

import io.paperdb.Paper;



public class DashboardActivity extends AppCompatActivity {
    private Button login_btn,join_btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        login_btn=(Button)findViewById(R.id.main_login_btn);
        join_btn=(Button)findViewById(R.id.main_join_btn);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DashboardActivity.this,LoginActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(DashboardActivity.this);

            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(DashboardActivity.this,RegisterActivity2.class);
                startActivity(intent1);
                Animatoo.animateInAndOut(DashboardActivity.this);


            }
        });

        String UserPhonekey=Paper.book().read(prevalent.UserPhonekey);
        String UserPasswordkey=Paper.book().read(prevalent.UserPasswordkey);
        if (UserPhonekey !="" && UserPasswordkey !=""){
            if (!TextUtils.isEmpty(UserPhonekey) && !TextUtils.isEmpty(UserPasswordkey)){

                AllowAccess(UserPhonekey,UserPasswordkey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }

        }



    }

    private void AllowAccess(String phone, String password) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){

                    Users usersData= dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(DashboardActivity.this, " You are already logged in", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(DashboardActivity.this,HomeActivity.class);
                            prevalent.currentOnlineUser=usersData;

                            startActivity(intent);

                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(DashboardActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }


                }else {

                    Toast.makeText(DashboardActivity.this, "Account with this "+phone+" number does not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(DashboardActivity.this);
    }



}