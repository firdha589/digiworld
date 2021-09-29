package com.example.Digi.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.Digi.R;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;


public class SellerloginActivity extends AppCompatActivity {

    private EditText sellerLogiEmail,sellerLoginPassword;
    private Button sellerLoginBtn;
    private ProgressDialog loadingBar;
    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerlogin);

        //mAuth=FirebaseAuth.getInstance();

        sellerLogiEmail=findViewById(R.id.seller_login_email);
        sellerLoginPassword=findViewById(R.id.seller_login_password);
        sellerLoginBtn=findViewById(R.id.seller_login_btn);

        loadingBar=new ProgressDialog(this);

        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginSeller();
            }
        });
    }

//    private void loginSeller() {
//
//        String email=sellerLogiEmail.getText().toString();
//        String password=sellerLoginPassword.getText().toString();
//
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//
//            loadingBar.setTitle(" Seller Account loging");
//            loadingBar.setMessage("Please wait,while we are checking the credentials.");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//
//            mAuth.signInWithEmailAndPassword(email,password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//
//                                Toast.makeText(SellerloginActivity.this, "Seller Login successfully", Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(SellerloginActivity.this, SellerHomeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        }
//                    });
//        }else {
//            Toast.makeText(this, "Please , enter the email and password!", Toast.LENGTH_SHORT).show();
//        }
//    }
}