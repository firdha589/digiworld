package com.example.Digi.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.Digi.R;


import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;


public class SellersRegistrationActivity extends AppCompatActivity {

    private EditText sellerName,sellerPhone,sellerEmail,sellerPassword,sellerAddress;
    private TextView alreadyAccount;
    private Button sellerRegi;
    //private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_registration);

        //mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);

        sellerName=findViewById(R.id.seller_name);
        sellerAddress=findViewById(R.id.seller_shop_address);
        sellerPhone=findViewById(R.id.seller_phone);
        sellerEmail=findViewById(R.id.seller_email);
        sellerPassword=findViewById(R.id.seller_password);
        alreadyAccount=findViewById(R.id.seller_allready_have_account_btn);
        sellerRegi=findViewById(R.id.seller_register_btn);

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellersRegistrationActivity.this, SellerloginActivity.class);
                startActivity(intent);
            }
        });


        sellerRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sellerRegister();
            }
        });
    }

//    private void sellerRegister() {
//
//        String name=sellerName.getText().toString();
//        String phone=sellerPhone.getText().toString();
//        String email=sellerEmail.getText().toString();
//        String password=sellerPassword.getText().toString();
//        String address=sellerAddress.getText().toString();
//
//        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(address) ){
//
//            try {
//
//                loadingBar.setTitle("Create Seller Account");
//                loadingBar.setMessage("Please wait,while we are checking the credentials.");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//
//                mAuth.createUserWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    final DatabaseReference rootRef;
//                                    rootRef= FirebaseDatabase.getInstance().getReference();
//
//                                    String sid=mAuth.getCurrentUser().getUid();
//
//                                    HashMap<String,Object>sellerMap=new HashMap<>();
//                                    sellerMap.put("sid",sid);
//                                    sellerMap.put("phone",phone);
//                                    sellerMap.put("email",email);
//                                    sellerMap.put("address",address);
//                                    sellerMap.put("name",name);
//
//                                    rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                                    loadingBar.dismiss();
//                                                    Toast.makeText(SellersRegistrationActivity.this, "You are registered successfuly!", Toast.LENGTH_SHORT).show();
//                                                    Intent intent=new Intent(SellersRegistrationActivity.this, SellerHomeActivity.class);
//                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    startActivity(intent);
//                                                    finish();
//                                                }
//                                            });
//                                }
//                            }
//                        });
//
//            }catch (Exception e){
//
//                Toast.makeText(this, "error is "+e, Toast.LENGTH_SHORT).show();
//            }
//
//
//
//        }
//        else {
//
//            Toast.makeText(this, "Please compleste the registration form!", Toast.LENGTH_SHORT).show();
//        }
//    }


}