package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.products;
import com.example.Digi.Prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private TextView totalprice;

    private Button confirmOrderBtn;
    private  String productID="";
    private  String productPrice="";
    private  String productQuantity="";
    private  String productName="";
    int myNum;
    int myNum2;
    int myNum23;
    private Dialog dialog;
    private Button ShowDialog;
    DatabaseReference imageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        confirmOrderBtn=(Button)findViewById(R.id.confirm_final_order_btn);
        nameEditText=(EditText)findViewById(R.id.shippment_name);
        phoneEditText=(EditText)findViewById(R.id.shippment_phone_number);
        addressEditText=(EditText)findViewById(R.id.shippment_address);
        cityEditText=(EditText)findViewById(R.id.shippment_city);
        totalprice=(TextView)findViewById(R.id.total_price) ;

        productID=getIntent().getStringExtra("productId");
        productName=getIntent().getStringExtra("productname");
        productPrice=getIntent().getStringExtra("productprice");
        productQuantity=getIntent().getStringExtra("productQuentity");

        imageRef= FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });


        try {
            DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
            productsRef.child(productID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        products products1=snapshot.getValue(products.class);
                        myNum =Integer.parseInt(products1.getPrice());
                        myNum2 = Integer.parseInt(productQuantity);
                        myNum23= myNum*myNum2;
                        totalprice.setText("Total Price : "+String.valueOf(myNum23));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });


        } catch(NumberFormatException nfe) {

            Toast.makeText(this, "error is "+nfe, Toast.LENGTH_SHORT).show();


        }


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderConfirmed();
                orderConfirmed2();
            }
        });



    }


    private void orderConfirmed() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Confirmed Orders");
        final HashMap<String,Object> finalOrder=new HashMap<>();
        finalOrder.put("pid",productID);
        finalOrder.put("pname",productName);
        finalOrder.put("totalprice",String.valueOf(myNum23));
        finalOrder.put("date",saveCurrentDate);
        finalOrder.put("time",saveCurrentTime);
        finalOrder.put("quantity",productQuantity);
        finalOrder.put("discount","");
        finalOrder.put("buyers_name",nameEditText.getText().toString());
        finalOrder.put("buyers_shipping_address",addressEditText.getText().toString());
        finalOrder.put("buyers_phone_number",phoneEditText.getText().toString());
        finalOrder.put("buyers_city",cityEditText.getText().toString());
        finalOrder.put("order_state","not shipped");

        orderRef.child("All orders").child(prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(finalOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.show();

                        }
                    }
                });



    }

    private void orderConfirmed2() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());



        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Users");
        final HashMap<String,Object> finalOrder=new HashMap<>();
        finalOrder.put("pid",productID);
        finalOrder.put("pname",productName);
        finalOrder.put("totalprice",String.valueOf(myNum23));
        finalOrder.put("date",saveCurrentDate);
        finalOrder.put("time",saveCurrentTime);
        finalOrder.put("quantity",productQuantity);
        finalOrder.put("discount","");
        finalOrder.put("buyers_name",nameEditText.getText().toString());
        finalOrder.put("buyers_shipping_address",addressEditText.getText().toString());
        finalOrder.put("buyers_phone_number",phoneEditText.getText().toString());
        finalOrder.put("buyers_city",cityEditText.getText().toString());
        finalOrder.put("order_state","not shipped");
        finalOrder.put("image",imageRef.child("image").toString());


        orderRef.child(prevalent.currentOnlineUser.getPhone()).child("my orders").child(productID)
                .updateChildren(finalOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){


                        }
                    }
                });



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(ConfirmFinalOrderActivity.this);
    }
}

