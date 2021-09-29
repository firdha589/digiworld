package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.Users;
import com.example.Digi.Prevalent.prevalent;
import com.example.Digi.Seller.SellersRegistrationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView inputPhonenumber,inputPassword;
    private ProgressDialog loadingBar;
    private TextView Adminlink,NotAdminLnk,ForgetPassword,becomeSeller;

    private String parentDbName="Users";
    private CheckBox chkBoxRememberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=(Button)findViewById(R.id.main_login_btn);
        inputPhonenumber=(TextView)findViewById(R.id.login_phone_number_input);
        inputPassword=(TextView)findViewById(R.id.login_password_input);
        Adminlink=(TextView)findViewById(R.id.admin_panel_link);
        NotAdminLnk=(TextView)findViewById(R.id.not_admin_panel_link);
        ForgetPassword=(TextView)findViewById(R.id.forget_password);
        becomeSeller=(TextView)findViewById(R.id.want_become_seller);

        loadingBar=new ProgressDialog(this);

        chkBoxRememberMe=(CheckBox)findViewById(R.id.remember_me_check);
        Paper.init(this);

        becomeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(LoginActivity.this, SellersRegistrationActivity.class);
//                startActivity(intent);
            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
                Animatoo.animateSwipeLeft(LoginActivity.this);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });

        Adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Admin");
                Adminlink.setVisibility(View.INVISIBLE);
                NotAdminLnk.setVisibility(View.VISIBLE);
                parentDbName="Admin";
            }
        });
        NotAdminLnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                Adminlink.setVisibility(View.VISIBLE);
                NotAdminLnk.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }

    private void LoginUser() {

        String phone=inputPhonenumber.getText().toString();
        String password=inputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            try {
                AllowAccessToAccount(phone,password);
            }catch (Exception e){
                Toast.makeText(this, "error is"+e, Toast.LENGTH_SHORT).show();

            }


        }

    }

    private void AllowAccessToAccount(String phone, String password) {
        if (chkBoxRememberMe.isChecked()){
            Paper.book().write(prevalent.UserPhonekey,phone);
            Paper.book().write(prevalent.UserPasswordkey,password);

        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users usersData= dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){

                            if (parentDbName.equals("Admin")){

                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent =new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                startActivity(intent);
                                Animatoo.animateSlideDown(LoginActivity.this);
                            }else if (parentDbName.equals("Users")){

                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                                prevalent.currentOnlineUser=usersData;
                                startActivity(intent);
                                Animatoo.animateSlideDown(LoginActivity.this);
                            }

                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }


                }else {

                    Toast.makeText(LoginActivity.this, "Account with this "+phone+" number does not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onBackPressed(){
        AlertDialog.Builder dialogBoxShow=new AlertDialog.Builder(this);
        dialogBoxShow.setTitle("EXIT !");
        dialogBoxShow.setMessage("Do you want to  EXIT application?");
        dialogBoxShow.setCancelable(false);
        dialogBoxShow.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);;

            }
        });
        dialogBoxShow.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialogBoxOpen=dialogBoxShow.create();
        alertDialogBoxOpen.show();


    }

}