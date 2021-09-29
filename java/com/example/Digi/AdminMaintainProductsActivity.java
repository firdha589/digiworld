package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {

    private EditText name,price,description;
    private Button applyChangesBtn,deleteBtn;
    private ImageView imageView;
    private  String productID="";
    private DatabaseReference productsRef;
    private DatabaseReference productsCateRef ;
    private String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        categoryName=getIntent().getStringExtra("category");

        productID=getIntent().getStringExtra("pid");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        productsCateRef= FirebaseDatabase.getInstance().getReference().child("Products Categoty").child(categoryName).child(productID);

        name=findViewById(R.id.product_name_maintain);
        price=findViewById(R.id.product_price_maintain);
        description=findViewById(R.id.product_description_maintain);
        applyChangesBtn=findViewById(R.id.apply_product_changes_btn);
        imageView=findViewById(R.id.product_image_maintain);
        deleteBtn=findViewById(R.id.delete_product_btn);

        displaySpecificProductInfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyChanges();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct();
            }
        });


    }

    private void deleteThisProduct() {


        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Intent intent =new Intent(AdminMaintainProductsActivity.this,AdminCategoryActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminMaintainProductsActivity.this, "The product deleted successfully ", Toast.LENGTH_SHORT).show();
            }
        });
        productsCateRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

            }
        });
    }

    private void ApplyChanges() {
        String pName=name.getText().toString();
        String pPrice=price.getText().toString();
        String pDescription=description.getText().toString();

        if (pName.equals("")){
            Toast.makeText(this, "Write down product Name", Toast.LENGTH_SHORT).show();
        }else if (pPrice.equals("")){
            Toast.makeText(this, "Write down product Price", Toast.LENGTH_SHORT).show();
        }else if (pDescription.equals("")){
            Toast.makeText(this, "Write down product Description", Toast.LENGTH_SHORT).show();
        }else {

            HashMap<String ,Object> productMap=new HashMap<>();
            productMap.put("pid",productID);
            productMap.put("description",pDescription);
            productMap.put("price",pPrice);
            productMap.put("pname",pName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                   if (task.isSuccessful()){

                       Toast.makeText(AdminMaintainProductsActivity.this, "Changes apply successfully", Toast.LENGTH_SHORT).show();
                       Intent intent =new Intent(AdminMaintainProductsActivity.this,AdminCategoryActivity.class);
                       startActivity(intent);
                       finish();

                   }
                }
            });
            productsCateRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){


                    }
                }
            });



        }

    }

    private void displaySpecificProductInfo() {

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String pName=snapshot.child("pname").getValue().toString();
                    String pPrice=snapshot.child("price").getValue().toString();
                    String pDescription=snapshot.child("description").getValue().toString();
                    String pImage=snapshot.child("image").getValue().toString();

                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    Picasso.get().load(pImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(AdminMaintainProductsActivity.this);
    }
}