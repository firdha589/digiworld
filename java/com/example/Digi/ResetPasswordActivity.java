package com.example.Digi;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Prevalent.prevalent;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import android.os.Bundle;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check;
    private EditText phoneNumber,question01,question02,question03;
    private Button Verify;
    private TextView pageTitle,titleQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check=getIntent().getStringExtra("check");
        phoneNumber=findViewById(R.id.phone_number_reset);
        question01=findViewById(R.id.question_01);
        question02=findViewById(R.id.question_02);
        question03=findViewById(R.id.question_03);
        Verify=findViewById(R.id.verify_btn);
        pageTitle=findViewById(R.id.page_title);
        titleQuestion=findViewById(R.id.question_title);


    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneNumber.setVisibility(View.GONE);
        if (check.equals("settings")){

            pageTitle.setText("Set Questions");
            titleQuestion.setText("Please set Answers for the Following security Questions");
            Verify.setText("Set");

            DisplayPreviousAnswers();

            Verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();


                }
            });



        }else if(check.equals("login")){

            phoneNumber.setVisibility(View.VISIBLE);

            Verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    verifyUser();
                }
            });



        }
    }

    private void  setAnswers(){
        String answer1=question01.getText().toString().toLowerCase();
        String answer2=question02.getText().toString().toLowerCase();
        String answer3=question03.getText().toString().toLowerCase();

        if (TextUtils.isEmpty(answer1) || TextUtils.isEmpty(answer2) || TextUtils.isEmpty(answer3)){

            Toast.makeText(ResetPasswordActivity.this, "Please answer all questions!", Toast.LENGTH_SHORT).show();
        }else {

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(prevalent.currentOnlineUser.getPhone());
            HashMap<String, Object> userMap = new HashMap<>();
            userMap. put("answer1", answer1);
            userMap. put("answer2", answer2);
            userMap. put("answer3", answer3);

            ref.child("Security Question").updateChildren(userMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "You have set security questions successfully..", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(ResetPasswordActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(ResetPasswordActivity.this);
                            }

                        }
                    });
        }

    }

    private void DisplayPreviousAnswers(){

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(prevalent.currentOnlineUser.getPhone());

        ref.child("Security Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String ans1=snapshot.child("answer1").getValue().toString();
                    String ans2=snapshot.child("answer2").getValue().toString();
                    String ans3=snapshot.child("answer3").getValue().toString();

                    question01.setText(ans1);
                    question02.setText(ans2);
                    question03.setText(ans3);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }
    private void verifyUser(){

        String phone=phoneNumber.getText().toString();
        String answer1=question01.getText().toString().toLowerCase();
        String answer2=question02.getText().toString().toLowerCase();
        String answer3=question03.getText().toString().toLowerCase();

        if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2) && !TextUtils.isEmpty(answer3)){

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        if (snapshot.hasChild("Security Question")){

                            String ans1=snapshot.child("Security Question").child("answer1").getValue().toString();
                            String ans2=snapshot.child("Security Question").child("answer2").getValue().toString();
                            String ans3=snapshot.child("Security Question").child("answer3").getValue().toString();

                            if (!ans1.equals(answer1)){
                                Toast.makeText(ResetPasswordActivity.this, "your 1st answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else if(!ans2.equals(answer2)){
                                Toast.makeText(ResetPasswordActivity.this, "your 2nd answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else if(!ans3.equals(answer3)){
                                Toast.makeText(ResetPasswordActivity.this, "your 3rd answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                AlertDialog.Builder builder=new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword=new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("write new password here...");
                                builder.setView(newPassword);

                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (!newPassword.getText().toString().equals(" ")){

                                            ref.child("password").setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                dialog.dismiss();
                                                                Toast.makeText(ResetPasswordActivity.this, "password changed successfully", Toast.LENGTH_SHORT).show();

                                                                Intent intent1 =new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                startActivity(intent1);

                                                            }
                                                        }
                                                    });
                                        }
                                        else {
                                            Toast.makeText(ResetPasswordActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();

                            }



                        }
                        else {
                            Toast.makeText(ResetPasswordActivity.this, "you have not set the security questions!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, "this phone number not exists.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });


        }
        else {
            Toast.makeText(this, "please complete the questions!", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(ResetPasswordActivity.this);
    }
}