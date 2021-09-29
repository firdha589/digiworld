package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {

    private TextView createAccountButton;
    private EditText inputName,inputPhonenumber,inputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        createAccountButton=(TextView) findViewById(R.id.register_login_btn);
        inputName=(EditText) findViewById(R.id.register_name_input);
        inputPhonenumber=(EditText) findViewById(R.id.register_phone_number_input);
        inputPassword=(EditText) findViewById(R.id.register_password_input);
        loadingBar=new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAccount();

            }
        });
    }

    private void CreateAccount() {
        String name=inputName.getText().toString();
        String phone=inputPhonenumber.getText().toString();
        String password=inputPassword.getText().toString();


        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatephoneNumber(name,phone,password);
        }
    }

    private void ValidatephoneNumber(String name, String phone, String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){

                    HashMap<String,Object> userdataMap =new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(RegisterActivity2.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();
                                       Intent intent =new Intent(RegisterActivity2.this,LoginActivity.class);
                                       startActivity(intent);
                                   }else {
                                       loadingBar.dismiss();
                                       Toast.makeText(RegisterActivity2.this, "Network ERROR! Please try again later", Toast.LENGTH_SHORT).show();

                                   }
                                }
                            });


                }else {
                    Toast.makeText(RegisterActivity2.this, "This"+phone+"number already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity2.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(RegisterActivity2.this,MainActivity.class);
                    startActivity(intent);
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
        Animatoo.animateSlideLeft(RegisterActivity2.this);
    }
}